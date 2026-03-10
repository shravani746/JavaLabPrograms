import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrationApp extends Application {

    @Override
    public void start(Stage stage) {

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();

        Button addButton = new Button("Add");

        Label status = new Label();

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String pass = passField.getText();

            if (name.isEmpty() || pass.isEmpty()) {
                status.setText("Please fill all fields!");
                return;
            }

            try {
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/testdb", "root", "1234"
                );

                PreparedStatement stmt = con.prepareStatement(
                        "INSERT INTO users(name, password) VALUES(?, ?)"
                );

                stmt.setString(1, name);
                stmt.setString(2, pass);

                stmt.executeUpdate();

                status.setText("User Added Successfully!");
                nameField.clear();
                passField.clear();

                con.close();

            } catch (Exception ex) {
                status.setText("Error: " + ex.getMessage());
            }
        });

        VBox root = new VBox(10, nameLabel, nameField, passLabel, passField, addButton, status);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Registration Form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
