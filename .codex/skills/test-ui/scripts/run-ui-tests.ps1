param(
    [string] $ProjectRoot = (Get-Location).Path,
    [string] $PlanPath = "test/ui-test-plan.md"
)

$ErrorActionPreference = "Stop"

function Get-FencedBlock {
    param(
        [string[]] $Lines,
        [int] $StartIndex
    )

    $start = -1
    for ($i = $StartIndex; $i -lt $Lines.Length; $i++) {
        if ($Lines[$i] -eq '```text') {
            $start = $i + 1
            break
        }
    }

    if ($start -eq -1) {
        throw "Could not find opening text fence after line $($StartIndex + 1)."
    }

    $end = -1
    for ($i = $start; $i -lt $Lines.Length; $i++) {
        if ($Lines[$i] -eq '```') {
            $end = $i
            break
        }
    }

    if ($end -eq -1) {
        throw "Could not find closing fence after line $($start + 1)."
    }

    return @{
        Text = ($Lines[$start..($end - 1)] -join "`n")
        EndIndex = $end
    }
}

function Get-TestCases {
    param([string] $FullPlanPath)

    $lines = Get-Content -LiteralPath $FullPlanPath
    $cases = @()

    for ($i = 0; $i -lt $lines.Length; $i++) {
        if ($lines[$i] -match "^## Test Case \d+:\s*(.+)$") {
            $name = $Matches[1]
            $aim = ""

            for ($j = $i + 1; $j -lt $lines.Length; $j++) {
                if ($lines[$j] -match "^Aim:\s*(.+)$") {
                    $aimLines = @($Matches[1])
                    for ($k = $j + 1; $k -lt $lines.Length; $k++) {
                        if ($lines[$k].Trim().Length -eq 0) {
                            break
                        }
                        $aimLines += $lines[$k].Trim()
                    }
                    $aim = $aimLines -join " "
                    break
                }
            }

            $inputLabel = -1
            for ($j = $i + 1; $j -lt $lines.Length; $j++) {
                if ($lines[$j] -eq "Input:") {
                    $inputLabel = $j
                    break
                }
            }

            if ($inputLabel -eq -1) {
                throw "Missing Input section for test case '$name'."
            }

            $inputBlock = Get-FencedBlock -Lines $lines -StartIndex $inputLabel

            $expectedLabel = -1
            for ($j = $inputBlock.EndIndex + 1; $j -lt $lines.Length; $j++) {
                if ($lines[$j] -eq "Expected output contains:") {
                    $expectedLabel = $j
                    break
                }
                if ($lines[$j] -match "^## Test Case ") {
                    break
                }
            }

            if ($expectedLabel -eq -1) {
                throw "Missing Expected output contains section for test case '$name'."
            }

            $expectedBlock = Get-FencedBlock -Lines $lines -StartIndex $expectedLabel

            $cases += [pscustomobject]@{
                Name = $name
                Aim = $aim
                Input = $inputBlock.Text
                Expected = $expectedBlock.Text
            }
        }
    }

    return $cases
}

function Assert-ContainsInOrder {
    param(
        [string] $Actual,
        [string] $Expected
    )

    $position = 0
    $fragments = $Expected -split "`n" | Where-Object { $_.Trim().Length -gt 0 }

    foreach ($fragment in $fragments) {
        $next = $Actual.IndexOf($fragment, $position, [StringComparison]::Ordinal)
        if ($next -lt 0) {
            throw "Missing expected output fragment after character ${position}: $fragment"
        }
        $position = $next + $fragment.Length
    }
}

$fullProjectRoot = (Resolve-Path -LiteralPath $ProjectRoot).Path
$fullPlanPath = Join-Path $fullProjectRoot $PlanPath

if (-not (Test-Path -LiteralPath $fullPlanPath)) {
    throw "UI test plan not found: $fullPlanPath"
}

$cases = Get-TestCases -FullPlanPath $fullPlanPath
if ($cases.Count -eq 0) {
    throw "No test cases found in $fullPlanPath"
}

$buildDir = Join-Path ([System.IO.Path]::GetTempPath()) ("aegis-ui-test-" + [guid]::NewGuid().ToString("N"))
New-Item -ItemType Directory -Force -Path $buildDir | Out-Null

try {
    Push-Location $fullProjectRoot

    Write-Output "Compiling Java sources..."
    & javac -d $buildDir src/main/java/*.java
    if ($LASTEXITCODE -ne 0) {
        throw "Compilation failed."
    }

    foreach ($case in $cases) {
        Write-Output ""
        Write-Output "=== Test Case: $($case.Name) ==="
        Write-Output "Aim: $($case.Aim)"
        Write-Output ""
        Write-Output "Console input:"
        Write-Output $case.Input

        $inputFile = Join-Path $buildDir "input.txt"
        [System.IO.File]::WriteAllText($inputFile, $case.Input + "`n", [System.Text.Encoding]::ASCII)

        $actual = cmd /c "type `"$inputFile`" | java -cp `"$buildDir`" Aegis 2>&1" | Out-String
        $exitCode = $LASTEXITCODE

        if ($exitCode -ne 0) {
            Write-Output ""
            Write-Output "Console output:"
            Write-Output $actual
            Write-Output ""
            Write-Output "Expected output fragments:"
            Write-Output $case.Expected
            throw "Program exited with code $exitCode."
        }

        Write-Output ""
        Write-Output "Console output:"
        Write-Output $actual

        try {
            Assert-ContainsInOrder -Actual $actual -Expected $case.Expected
        } catch {
            Write-Output ""
            Write-Output "Expected output fragments:"
            Write-Output $case.Expected
            throw "Test case failed: $($case.Name). $($_.Exception.Message)"
        }

        Write-Output "Result: PASS"
    }

    Write-Output ""
    Write-Output "All UI test cases passed."
} finally {
    Pop-Location
    Remove-Item -LiteralPath $buildDir -Recurse -Force -ErrorAction SilentlyContinue
}
