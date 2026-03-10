import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentAcademicPerformance2 extends Application {
    
    static class Student{
        int id;
        String name,dept;
        double percentage;

        Student(int id,String name,String dept, double percentage) {
            this.id=id;
            this.name=name;
            this.dept=dept;
            this.percentage=percentage;
        }

        public double getPercentage(){
            return percentage;
        }

        @Override
        public String toString(){
            return "ID : " + id +
            " | Name : "+name+" | Dept : "+dept+" | Percentage : "+String.format("%.2f", percentage);
        }
    }

    ObservableList<Student> students = FXCollections.observableArrayList();
    Connection con;
    ListView<Student> listview;

    @Override
    public void start(Stage stage){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college","root","1234");

        } catch (Exception e) {
            e.printStackTrace();
        }

        TextField tid = new TextField();
        tid.setPromptText("Student ID");

        TextField tname = new TextField();
        tname.setPromptText("Name");

        TextField tDepartment = new TextField();
        tDepartment.setPromptText("Department");

        TextField tm1 = new TextField();
        tm1.setPromptText("Marks1");

        TextField tm2 = new TextField();
        tm2.setPromptText("Marks2");

        TextField tm3 = new TextField();
        tm3.setPromptText("Marks3");

        listview = new ListView<>();
        listview.setPrefHeight(300);
        listview.setItems(students);

        loadAllStudents();

        Button btnadd = new Button("Add Student");

        btnadd.setOnAction(e -> {
            try {
            int id = Integer.parseInt(tid.getText());
            String name = tname.getText();
            String dept = tDepartment.getText();
            int m1 = Integer.parseInt(tm1.getText());
            int m2 = Integer.parseInt(tm2.getText());
            int m3 = Integer.parseInt(tm3.getText());
            double percentage = (m1+m2+m3)/3.0;

            PreparedStatement ps = con.prepareStatement(
                "Insert into students values (?,?,?,?,?,?,?)"
            );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setInt(4, m1);
            ps.setInt(5, m2);
            ps.setInt(6, m3);
            ps.setDouble(7, percentage);
            ps.executeUpdate();

            loadAllStudents();

            tid.clear();tname.clear();tDepartment.clear();
            tm1.clear();tm2.clear();tm3.clear();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        Button sortbtn = new Button("sort by percentage");
        
        sortbtn.setOnAction(e ->
            students.sort(
            (s1,s2) -> Double.compare(
                s2.getPercentage(),
                s1.getPercentage()
            )
            )
        );

        Button highbtn = new Button("show > 70%");

        highbtn.setOnAction(e->
            listview.setItems(
                FXCollections.observableArrayList(
                    students.stream()
                    .filter(s->s.getPercentage()>70)
                    .collect(Collectors.toList())
                )
            )
        );

        Button statsbtn = new Button("show stats");

        statsbtn.setOnAction(e->{
            double avg = students.stream()
            .mapToDouble(Student::getPercentage)
            .average().orElse(0);

        long passed = students.stream()
        .filter(s->s.getPercentage()>=50)
        .count();

        new Alert(Alert.AlertType.INFORMATION,"Average Percentage: " + avg +
        "\nPassed Students: " + passed
    ).show();
});

    Button reset = new Button("reset");
    reset.setOnAction(e->loadAllStudents());

    VBox root = new VBox(8,
        tid,tname,tDepartment,tm1,tm2,tm3,btnadd,sortbtn,highbtn,statsbtn,reset,listview
    );

    stage.setScene(new Scene(root,560,600));
    stage.setTitle("Student Academic Performance System");
    stage.show();
    }

    void loadAllStudents(){
        students.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from students");
            while(rs.next()){
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("dept"),
                    rs.getDouble("percentage")
                ));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
