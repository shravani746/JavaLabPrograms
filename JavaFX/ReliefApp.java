import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;

import java.sql.*;

public class ReliefApp extends Application {

    static final String DB_URL = "jdbc:mysql://localhost:3306/smartaid_db";
    static final String USER = "root";
    static final String PASS = "1234";

    ObservableList<ReliefRequest> data = FXCollections.observableArrayList();
    TableView<ReliefRequest> table = new TableView<>();

    TextField tfCenter = new TextField();
    TextField tfItem = new TextField();
    TextField tfQty = new TextField();
    ComboBox<String> cbPriority = new ComboBox<>();
    DatePicker dpDate = new DatePicker();

    static class ReliefRequest {
        int id;
        String center, item, priority, date;
        int qty;

        ReliefRequest(int id, String c, String i, int q, String p, String d) {
            this.id = id;
            center = c;
            item = i;
            qty = q;
            priority = p;
            date = d;
        }
    }

    Connection connect() throws Exception {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // ✅ INPUT VALIDATION (IMPORTANT FIX)
    boolean validateInput() {
        if (tfCenter.getText().isEmpty() ||
            tfItem.getText().isEmpty() ||
            tfQty.getText().isEmpty() ||
            cbPriority.getValue() == null ||
            dpDate.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields!");
            alert.show();
            return false;
        }
        return true;
    }

    @Override
    public void start(Stage stage) {

        cbPriority.getItems().addAll("High", "Medium", "Low");

        TableColumn<ReliefRequest, String> c1 = new TableColumn<>("Center");
        c1.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(e.getValue().center));

        TableColumn<ReliefRequest, String> c2 = new TableColumn<>("Item");
        c2.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(e.getValue().item));

        TableColumn<ReliefRequest, String> c3 = new TableColumn<>("Priority");
        c3.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(e.getValue().priority));

        TableColumn<ReliefRequest, String> c4 = new TableColumn<>("Date");
        c4.setCellValueFactory(e ->
                new javafx.beans.property.SimpleStringProperty(e.getValue().date));

        table.getColumns().addAll(c1, c2, c3, c4);
        table.setItems(data);

        Button add = new Button("ADD");
        Button display = new Button("DISPLAY");
        Button edit = new Button("EDIT");
        Button delete = new Button("DELETE");

        // ✅ ADD
        add.setOnAction(e -> {
            if (!validateInput()) return;

            try (Connection con = connect()) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO relief_requests(center_name,item_required,quantity,priority,req_date) VALUES(?,?,?,?,?)");
                ps.setString(1, tfCenter.getText());
                ps.setString(2, tfItem.getText());
                ps.setInt(3, Integer.parseInt(tfQty.getText()));
                ps.setString(4, cbPriority.getValue());
                ps.setString(5, dpDate.getValue().toString());
                ps.executeUpdate();
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // DISPLAY
        display.setOnAction(e -> loadData());

        // ✅ EDIT
        edit.setOnAction(e -> {
            ReliefRequest r = table.getSelectionModel().getSelectedItem();
            if (r == null || !validateInput()) return;

            try (Connection con = connect()) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE relief_requests SET center_name=?, item_required=?, quantity=?, priority=?, req_date=? WHERE id=?");
                ps.setString(1, tfCenter.getText());
                ps.setString(2, tfItem.getText());
                ps.setInt(3, Integer.parseInt(tfQty.getText()));
                ps.setString(4, cbPriority.getValue());
                ps.setString(5, dpDate.getValue().toString());
                ps.setInt(6, r.id);
                ps.executeUpdate();
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // DELETE
        delete.setOnAction(e -> {
            ReliefRequest r = table.getSelectionModel().getSelectedItem();
            if (r == null) return;

            try (Connection con = connect()) {
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM relief_requests WHERE id=?");
                ps.setInt(1, r.id);
                ps.executeUpdate();
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Auto-fill form when row selected (BONUS, VERY USEFUL)
        table.setOnMouseClicked(e -> {
            ReliefRequest r = table.getSelectionModel().getSelectedItem();
            if (r != null) {
                tfCenter.setText(r.center);
                tfItem.setText(r.item);
                tfQty.setText(String.valueOf(r.qty));
                cbPriority.setValue(r.priority);
                dpDate.setValue(java.time.LocalDate.parse(r.date));
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Center"), tfCenter);
        form.addRow(1, new Label("Item"), tfItem);
        form.addRow(2, new Label("Quantity"), tfQty);
        form.addRow(3, new Label("Priority"), cbPriority);
        form.addRow(4, new Label("Date"), dpDate);

        HBox buttons = new HBox(10, add, display, edit, delete);

        VBox root = new VBox(15,
                new Label("SmartAid – Relief Request Manager"),
                form,
                buttons,
                table);

        root.setPadding(new javafx.geometry.Insets(20));

        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("ReliefApp");
        stage.show();
    }

    void loadData() {
        data.clear();
        try (Connection con = connect()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT * FROM relief_requests");
            while (rs.next()) {
                data.add(new ReliefRequest(
                        rs.getInt("id"),
                        rs.getString("center_name"),
                        rs.getString("item_required"),
                        rs.getInt("quantity"),
                        rs.getString("priority"),
                        rs.getString("req_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
