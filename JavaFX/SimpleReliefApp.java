import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

import java.io.*;
import java.sql.*;

public class SimpleReliefApp extends Application {

    Connection con;
    Statement st;
    ResultSet rs;

    TextField tfName = new TextField();
    TextField tfLocation = new TextField();
    TextField tfResource = new TextField();
    TextField tfQuantity = new TextField();

    TextArea metaArea = new TextArea();
    ImageView imageView = new ImageView();
    Label status = new Label();

    void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/simple_relief?useSSL=false&serverTimezone=UTC",
            "root",
            "1234"
        );

        st = con.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE
        );
    }

    void loadData() throws Exception {
        rs = st.executeQuery(
            "SELECT c.name, c.location, c.photo, r.resource, r.quantity " +
            "FROM centers c JOIN resources r ON c.id = r.center_id"
            
        );

        loadMetaData();   // Load metadata first
        rs.first();       // Then move cursor
        showRecord();     // Then display first record
    }

    void loadMetaData() throws Exception {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        StringBuilder sb = new StringBuilder();
        sb.append("Total Columns: ").append(cols).append("\n\n");

        for (int i = 1; i <= cols; i++) {
            sb.append(meta.getColumnName(i))
              .append(" - ")
              .append(meta.getColumnTypeName(i))
              .append("\n");
        }

        metaArea.setText(sb.toString());
    }

    void showRecord() throws Exception {
        tfName.setText(rs.getString("name"));
        tfLocation.setText(rs.getString("location"));
        tfResource.setText(rs.getString("resource"));
        tfQuantity.setText(String.valueOf(rs.getInt("quantity")));

        Blob b = rs.getBlob("photo");
        if (b != null) {
            InputStream is = b.getBinaryStream();
            imageView.setImage(new Image(is));
        } else {
            imageView.setImage(null);
        }
    }

    @Override
    public void start(Stage stage) {

        try {
            connect();
            loadData();
            status.setText("Data Loaded Successfully");
        } catch (Exception e) {
            status.setText("Database Error");
            e.printStackTrace();
        }

        Button first = new Button("First");
        Button prev = new Button("Previous");
        Button next = new Button("Next");
        Button last = new Button("Last");
        Button update = new Button("Update Quantity");

        first.setOnAction(e -> move(() -> rs.first()));
        prev.setOnAction(e -> move(() -> rs.previous()));
        next.setOnAction(e -> move(() -> rs.next()));
        last.setOnAction(e -> move(() -> rs.last()));

        update.setOnAction(e -> {
            try {
                rs.updateInt("quantity", Integer.parseInt(tfQuantity.getText()));
                rs.updateRow();
                status.setText("Quantity Updated in Database");
            } catch (Exception ex) {
                status.setText("Update Failed");
            }
        });

        imageView.setFitWidth(140);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(true);

        metaArea.setEditable(false);
        metaArea.setPrefRowCount(6);
        metaArea.setWrapText(true);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.addRow(0, new Label("Center Name"), tfName);
        form.addRow(1, new Label("Location"), tfLocation);
        form.addRow(2, new Label("Resource"), tfResource);
        form.addRow(3, new Label("Quantity"), tfQuantity);
        form.addRow(4, new Label("Photo"), imageView);

        HBox nav = new HBox(10, first, prev, next, last, update);
        nav.setAlignment(Pos.CENTER);

        VBox root = new VBox(12,
                new Label("Relief Center Management System"),
                form,
                nav,
                new Label("ResultSet MetaData"),
                metaArea,
                status
        );

        root.setPadding(new Insets(15));

        stage.setScene(new Scene(root, 650, 560));
        stage.setTitle("Simple Relief App - Lab 4");
        stage.show();
    }

    void move(DBAction action) {
        try {
            action.run();
            showRecord();
        } catch (Exception e) {
            status.setText("No more records");
        }
    }

    interface DBAction {
        void run() throws Exception;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
