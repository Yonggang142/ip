public class Deadline extends Task {

    protected String end;

    public Deadline(String description, String end) {
        super(description);
        this.end = end;

    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + " (by: " + end + ")";
    }

}
