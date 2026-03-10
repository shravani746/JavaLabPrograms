import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.*;

public class SmartAidCombinedApp extends Application {

    Connection con;
    Statement stmt;
    ResultSet rs;

    TextField tfCenter = new TextField();
    TextField tfLocation = new TextField();
    TextField tfItem = new TextField();
    TextField tfQty = new TextField();

    ComboBox<String> cbPriority = new ComboBox<>();
    DatePicker dpDate = new DatePicker();

    ImageView imageView = new ImageView();

    // DB Connection
    void connectDB() throws Exception {
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/smartaid_db",
            "root",
            "1234"
        );

        stmt = con.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE
        );

        rs = stmt.executeQuery(
            "SELECT c.center_name, c.location, r.item_name, r.quantity, r.priority, r.resource_date, r.resource_image " +
            "FROM relief_centers c JOIN relief_resources r ON c.center_id = r.center_id"
        );
    }

    void showRecord() throws Exception {
        tfCenter.setText(rs.getString("center_name"));
        tfLocation.setText(rs.getString("location"));
        tfItem.setText(rs.getString("item_name"));
        tfQty.setText(String.valueOf(rs.getInt("quantity")));
        cbPriority.setValue(rs.getString("priority"));
        dpDate.setValue(java.time.LocalDate.parse(rs.getString("resource_date")));

        Blob blob = rs.getBlob("resource_image");
        if (blob != null) {
            InputStream is = blob.getBinaryStream();
            imageView.setImage(new Image(is));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        connectDB();
        rs.first();
        showRecord();

        cbPriority.getItems().addAll("High", "Medium", "Low");

        // Buttons (LAB 3 + LAB 4)
        Button add = new Button("ADD");
        Button display = new Button("DISPLAY");
        Button edit = new Button("EDIT");
        Button delete = new Button("DELETE");

        Button first = new Button("First");
        Button next = new Button("Next");
        Button prev = new Button("Previous");
        Button last = new Button("Last");

        // Navigation (LAB 4)
        first.setOnAction(e -> { try { rs.first(); showRecord(); } catch (Exception ex) {} });
        next.setOnAction(e -> { try { rs.next(); showRecord(); } catch (Exception ex) {} });
        prev.setOnAction(e -> { try { rs.previous(); showRecord(); } catch (Exception ex) {} });
        last.setOnAction(e -> { try { rs.last(); showRecord(); } catch (Exception ex) {} });

        // EDIT using Updatable ResultSet (LAB 4)
        edit.setOnAction(e -> {
            try {
                rs.updateInt("quantity", Integer.parseInt(tfQty.getText()));
                rs.updateString("priority", cbPriority.getValue());
                rs.updateRow();
            } catch (Exception ex) {}
        });

        // ResultSetMetaData (LAB 4)
        ResultSetMetaData meta = rs.getMetaData();
        System.out.println("Total Columns: " + meta.getColumnCount());
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.println(meta.getColumnName(i) + " - " + meta.getColumnTypeName(i));
        }

        imageView.setFitWidth(140);
        imageView.setFitHeight(140);

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);

        form.addRow(0, new Label("Center"), tfCenter);
        form.addRow(1, new Label("Location"), tfLocation);
        form.addRow(2, new Label("Item"), tfItem);
        form.addRow(3, new Label("Quantity"), tfQty);
        form.addRow(4, new Label("Priority"), cbPriority);
        form.addRow(5, new Label("Date"), dpDate);
        form.addRow(6, new Label("Image"), imageView);

        HBox crud = new HBox(10, add, display, edit, delete);
        HBox nav = new HBox(10, first, prev, next, last);

        VBox root = new VBox(15,
            new Label("SmartAid – Relief Resource Manager"),
            form,
            crud,
            nav
        );

        root.setPadding(new javafx.geometry.Insets(20));
        stage.setScene(new Scene(root, 720, 550));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
