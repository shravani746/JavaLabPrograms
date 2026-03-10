import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.stream.Collectors;

public class StudentAcademicPerformance extends Application {

    // -------- STUDENT MODEL --------
    static class Student {
        int id;
        String name, dept;
        double percentage;

        Student(int id, String name, String dept, double percentage) {
            this.id = id;
            this.name = name;
            this.dept = dept;
            this.percentage = percentage;
        }

        public double getPercentage() {
            return percentage;
        }

        @Override
        public String toString() {
            return "ID: " + id +
                   " | Name: " + name +
                   " | Dept: " + dept +
                   " | %: " + String.format("%.2f", percentage);
        }
    }

    ObservableList<Student> students = FXCollections.observableArrayList();
    Connection con;
    ListView<Student> listView;

    @Override
    public void start(Stage stage) {

        // -------- JDBC CONNECTION --------
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college",
                "root",
                "1234"   // <-- YOUR MYSQL PASSWORD
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // -------- INPUT FIELDS --------
        TextField tfId = new TextField();
        tfId.setPromptText("Student ID");

        TextField tfName = new TextField();
        tfName.setPromptText("Name");

        TextField tfDept = new TextField();
        tfDept.setPromptText("Department");

        TextField tfM1 = new TextField();
        tfM1.setPromptText("Marks 1");

        TextField tfM2 = new TextField();
        tfM2.setPromptText("Marks 2");

        TextField tfM3 = new TextField();
        tfM3.setPromptText("Marks 3");

        // -------- LIST VIEW (DISPLAY RECORDS FROM SELECT) --------
        listView = new ListView<>();
        listView.setPrefHeight(250);
        listView.setItems(students);

        // -------- LOAD ALL RECORDS USING SELECT --------
        loadAllStudents();

        // -------- ADD STUDENT (INSERT + SELECT) --------
        Button addBtn = new Button("Add Student");

        addBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(tfId.getText());
                String name = tfName.getText();
                String dept = tfDept.getText();
                int m1 = Integer.parseInt(tfM1.getText());
                int m2 = Integer.parseInt(tfM2.getText());
                int m3 = Integer.parseInt(tfM3.getText());

                double percentage = (m1 + m2 + m3) / 3.0;

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students VALUES (?,?,?,?,?,?,?)"
                );
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, dept);
                ps.setInt(4, m1);
                ps.setInt(5, m2);
                ps.setInt(6, m3);
                ps.setDouble(7, percentage);
                ps.executeUpdate();

                loadAllStudents(); // <-- SELECT after INSERT

                tfId.clear(); tfName.clear(); tfDept.clear();
                tfM1.clear(); tfM2.clear(); tfM3.clear();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // -------- LAMBDA: SORT DESCENDING --------
        Button sortBtn = new Button("Sort by Percentage");

        sortBtn.setOnAction(e ->
            students.sort(
                (s1, s2) -> Double.compare(
                    s2.getPercentage(),
                    s1.getPercentage()
                )
            )
        );

        // -------- STREAM: FILTER > 70 --------
        Button highBtn = new Button("Show > 70%");

        highBtn.setOnAction(e ->
            listView.setItems(
                FXCollections.observableArrayList(
                    students.stream()
                            .filter(s -> s.getPercentage() > 70)
                            .collect(Collectors.toList())
                )
            )
        );

        // -------- STREAM: AVG + COUNT --------
        Button statsBtn = new Button("Show Statistics");

        statsBtn.setOnAction(e -> {
            double avg = students.stream()
                    .mapToDouble(Student::getPercentage)
                    .average()
                    .orElse(0);

            long passed = students.stream()
                    .filter(s -> s.getPercentage() >= 50)
                    .count();

            new Alert(Alert.AlertType.INFORMATION,
                "Average Percentage: " + avg +
                "\nPassed Students: " + passed
            ).show();
        });

        // -------- SHOW ALL (RESET AFTER FILTER) --------
        Button showAllBtn = new Button("Show All");

        showAllBtn.setOnAction(e -> loadAllStudents());

        VBox root = new VBox(8,
            tfId, tfName, tfDept, tfM1, tfM2, tfM3,
            addBtn, sortBtn, highBtn, statsBtn, showAllBtn,
            listView
        );

        stage.setScene(new Scene(root, 540, 680));
        stage.setTitle("Student Academic Performance System");
        stage.show();
    }

    // -------- SELECT METHOD (CORE REQUIREMENT) --------
    void loadAllStudents() {
        students.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                students.add(
                    new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("dept"),
                        rs.getDouble("percentage")
                    )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
