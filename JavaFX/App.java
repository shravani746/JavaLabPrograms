import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Apps {

   
    public void start(Stage stage) {
        Label label = new Label("Hello JavaFX!");

        Scene scene = new Scene(label, 500, 500);

        //stage.setTitle("Simple JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}