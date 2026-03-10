import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo {
    public static void main(String[] args) {
        // JDBC URL, username, and password
        String url = "jdbc:mysql://localhost:3306/Student_DB2";
        String user = "root";
        String password = "1234";

        // Establish connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM your_table")) {

            // Process results
            while (rs.next()) {
                System.out.println("Column Value: " + rs.getString("column_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
