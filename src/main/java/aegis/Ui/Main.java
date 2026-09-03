package aegis.ui;

import java.io.IOException;

import aegis.Aegis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * A GUI for Aegis using FXML.
 */
public class Main extends Application {

    private Aegis aegis = new Aegis("data/aegis.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setAegis(aegis); // inject the Aegis instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
