import java.sql.*;
import java.util.Scanner;

class StudentDatabase {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/student_db"; 
        String user = "root"; 
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL successfully!");

            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter department: ");
            String department = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = scanner.nextInt();

            String insertQuery = "INSERT INTO students (name, department, age) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.setString(2, department);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("Record Inserted Successfully!");

            System.out.print("Enter new age for " + name + ": ");
            int newAge = scanner.nextInt();
            String updateQuery = "UPDATE students SET age = ? WHERE name = ?";
            PreparedStatement pstmtUpdate = con.prepareStatement(updateQuery);
            pstmtUpdate.setInt(1, newAge);
            pstmtUpdate.setString(2, name);
            pstmtUpdate.executeUpdate();
            System.out.println("Record Updated Successfully!");

            System.out.println("===== Student Records =====");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("department") + " " + rs.getInt("age"));
            }

            pstmt.close();
            pstmtUpdate.close();
            stmt.close();
            con.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
