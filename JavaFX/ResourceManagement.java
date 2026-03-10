import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

public class ResourceManagement extends Application {

    // Database variables
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    // Controls
    TextField txtId = new TextField();
    TextField txtName = new TextField();
    TextField txtQuantity = new TextField();
    TextField txtUnit = new TextField();
    TextField txtPriority = new TextField();

    ComboBox<String> cmbCategory = new ComboBox<>();
    TextArea txtLocation = new TextArea();

    Label lblStatus = new Label();

    Button btnAdd = new Button("ADD");
    Button btnDisplay = new Button("DISPLAY");
    Button btnEdit = new Button("EDIT");
    Button btnDelete = new Button("DELETE");
    Button btnClear = new Button("CLEAR");

    @Override
    public void start(Stage stage) {

        connectDB();

        Label title = new Label("RESOURCE MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial Black", 24));
        title.setTextFill(Color.DARKBLUE);

        cmbCategory.getItems().addAll(
                "Food",
                "Medical Supplies",
                "Water",
                "Clothing",
                "Equipment",
                "Manpower",
                "Funds"
        );

        txtLocation.setPrefRowCount(3);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setStyle("-fx-background-color:#e3f2fd; -fx-border-color:#1565c0;");

        grid.add(new Label("Resource ID"), 0, 0);
        grid.add(txtId, 1, 0);

        grid.add(new Label("Resource Name"), 0, 1);
        grid.add(txtName, 1, 1);

        grid.add(new Label("Category"), 0, 2);
        grid.add(cmbCategory, 1, 2);

        grid.add(new Label("Quantity"), 0, 3);
        grid.add(txtQuantity, 1, 3);

        grid.add(new Label("Unit"), 0, 4);
        grid.add(txtUnit, 1, 4);

        grid.add(new Label("Priority Level"), 0, 5);
        grid.add(txtPriority, 1, 5);

        grid.add(new Label("Storage Location"), 0, 6);
        grid.add(txtLocation, 1, 6);

        String style = "-fx-background-color:#1976d2; -fx-text-fill:white; -fx-font-weight:bold; -fx-pref-width:100;";
        btnAdd.setStyle(style);
        btnDisplay.setStyle(style);
        btnEdit.setStyle(style);
        btnDelete.setStyle(style);
        btnClear.setStyle(style);

        HBox buttons = new HBox(15, btnAdd, btnDisplay, btnEdit, btnDelete, btnClear);
        buttons.setAlignment(Pos.CENTER);

        lblStatus.setFont(new Font(14));
        lblStatus.setTextFill(Color.DARKGREEN);

        VBox root = new VBox(20, title, grid, buttons, lblStatus);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color:linear-gradient(to bottom,#e3f2fd,#bbdefb);");

        btnAdd.setOnAction(e -> addResource());
        btnDisplay.setOnAction(e -> displayResource());
        btnEdit.setOnAction(e -> editResource());
        btnDelete.setOnAction(e -> deleteResource());
        btnClear.setOnAction(e -> clearFields());

        stage.setScene(new Scene(root, 650, 720));
        stage.setTitle("Resource Management System");
        stage.show();
    }

    void connectDB() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/resourcedb?useSSL=false&serverTimezone=UTC",
                "root",
                "1234"
        );

        lblStatus.setText("Database Connected Successfully");
    } catch (Exception e) {
        e.printStackTrace();
        lblStatus.setText("Database Not Connected: " + e.getMessage());
    }
}


    void addResource() {
    try {
        if (cmbCategory.getValue() == null) {
            lblStatus.setText("Please select a category");
            return;
        }

        pst = con.prepareStatement(
            "INSERT INTO resources " +
            "(resource_id, resource_name, category, quantity, unit, priority_level, storage_location) " +
            "VALUES (?,?,?,?,?,?,?)"
        );

        pst.setInt(1, Integer.parseInt(txtId.getText()));
        pst.setString(2, txtName.getText());
        pst.setString(3, cmbCategory.getValue());
        pst.setInt(4, Integer.parseInt(txtQuantity.getText()));
        pst.setString(5, txtUnit.getText());
        pst.setInt(6, Integer.parseInt(txtPriority.getText()));
        pst.setString(7, txtLocation.getText());

        pst.executeUpdate();
        lblStatus.setText("Resource Added Successfully");

    } catch (SQLIntegrityConstraintViolationException ex) {
        lblStatus.setText("Resource ID already exists");
    } catch (NumberFormatException ex) {
        lblStatus.setText("Enter valid numeric values");
    } catch (Exception e) {
        e.printStackTrace();
        lblStatus.setText("Add Error: " + e.getMessage());
    }
}


    void displayResource() {
        try {
            pst = con.prepareStatement("SELECT * FROM resources WHERE resource_id=?");
            pst.setInt(1, Integer.parseInt(txtId.getText()));
            rs = pst.executeQuery();

            if (rs.next()) {
                txtName.setText(rs.getString("resource_name"));
                cmbCategory.setValue(rs.getString("category"));
                txtQuantity.setText(rs.getString("quantity"));
                txtUnit.setText(rs.getString("unit"));
                txtPriority.setText(rs.getString("priority_level"));
                txtLocation.setText(rs.getString("storage_location"));
                lblStatus.setText("Record Displayed");
            } else {
                lblStatus.setText("Record Not Found");
            }
        } catch (Exception e) {
            lblStatus.setText("Display Error");
        }
    }

    void editResource() {
        try {
            pst = con.prepareStatement(
                    "UPDATE resources SET resource_name=?, category=?, quantity=?, unit=?, priority_level=?, storage_location=? WHERE resource_id=?"
            );

            pst.setString(1, txtName.getText());
            pst.setString(2, cmbCategory.getValue());
            pst.setInt(3, Integer.parseInt(txtQuantity.getText()));
            pst.setString(4, txtUnit.getText());
            pst.setInt(5, Integer.parseInt(txtPriority.getText()));
            pst.setString(6, txtLocation.getText());
            pst.setInt(7, Integer.parseInt(txtId.getText()));

            pst.executeUpdate();
            lblStatus.setText("Resource Updated Successfully");
        } catch (Exception e) {
            lblStatus.setText("Edit Error");
        }
    }

    void deleteResource() {
        try {
            pst = con.prepareStatement("DELETE FROM resources WHERE resource_id=?");
            pst.setInt(1, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            lblStatus.setText("Resource Deleted");
            clearFields();
        } catch (Exception e) {
            lblStatus.setText("Delete Error");
        }
    }

    void clearFields() {
        txtId.clear();
        txtName.clear();
        txtQuantity.clear();
        txtUnit.clear();
        txtPriority.clear();
        cmbCategory.setValue(null);
        txtLocation.clear();
        lblStatus.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
