import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.*;

public class SimpleReliefApp extends Application {

    Connection con;
    Statement st;
    ResultSet rs;

    TextField tfName = new TextField();
    TextField tfLocation = new TextField();
    TextField tfResource = new TextField();
    TextField tfQuantity = new TextField();

    ImageView imageView = new ImageView();

    void connect() throws Exception {
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/simple_relief",
            "root",
            "1234"
        );

        st = con.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE
        );

        rs = st.executeQuery(
            "SELECT c.name, c.location, c.photo, r.resource, r.quantity " +
            "FROM centers c JOIN resources r ON c.id = r.center_id"
        );
    }

    void show() throws Exception {
        tfName.setText(rs.getString("name"));
        tfLocation.setText(rs.getString("location"));
        tfResource.setText(rs.getString("resource"));
        tfQuantity.setText(String.valueOf(rs.getInt("quantity")));

        Blob b = rs.getBlob("photo");
        if (b != null) {
            InputStream is = b.getBinaryStream();
            imageView.setImage(new Image(is));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        connect();
        rs.first();
        show();

        Button first = new Button("First");
        Button next = new Button("Next");
        Button prev = new Button("Previous");
        Button last = new Button("Last");
        Button update = new Button("Update Qty");

        first.setOnAction(e -> { try { rs.first(); show(); } catch (Exception ex) {} });
        next.setOnAction(e -> { try { rs.next(); show(); } catch (Exception ex) {} });
        prev.setOnAction(e -> { try { rs.previous(); show(); } catch (Exception ex) {} });
        last.setOnAction(e -> { try { rs.last(); show(); } catch (Exception ex) {} });

        update.setOnAction(e -> {
            try {
                rs.updateInt("quantity", Integer.parseInt(tfQuantity.getText()));
                rs.updateRow();
            } catch (Exception ex) {}
        });

        // Metadata
        ResultSetMetaData meta = rs.getMetaData();
        System.out.println("Columns: " + meta.getColumnCount());
        for (int i = 1; i <= meta.getColumnCount(); i++)
            System.out.println(meta.getColumnName(i));

        imageView.setFitWidth(120);
        imageView.setFitHeight(120);

        GridPane g = new GridPane();
        g.setVgap(8);
        g.setHgap(8);

        g.addRow(0, new Label("Center"), tfName);
        g.addRow(1, new Label("Location"), tfLocation);
        g.addRow(2, new Label("Resource"), tfResource);
        g.addRow(3, new Label("Quantity"), tfQuantity);
        g.addRow(4, new Label("Photo"), imageView);

        HBox nav = new HBox(8, first, prev, next, last, update);

        VBox root = new VBox(12,
            new Label("Simple Relief Center Viewer"),
            g,
            nav
        );

        root.setPadding(new javafx.geometry.Insets(15));
        stage.setScene(new Scene(root, 600, 420));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
