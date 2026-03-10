import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MovieTicketBooking extends JFrame implements ActionListener {
    JTextField[] fields = new JTextField[7];
    JButton bookBtn, cancelBtn, updateBtn, displayBtn;
    Connection con;

    MovieTicketBooking() {
        setTitle("Movie Ticket Booking System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));

        String[] labels = {"Movie ID:", "Movie Name:", "Duration:", "Language:",
                            "Theater Name:", "Rating:", "Number of Tickets:"};
        for (int i = 0; i < labels.length; i++) {
            add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            add(fields[i]);
        }

        bookBtn = new JButton("Book");
        cancelBtn = new JButton("Cancel");
        updateBtn = new JButton("Update");
        displayBtn = new JButton("Display");

        add(bookBtn); add(cancelBtn);
        add(updateBtn); add(displayBtn);

        bookBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        displayBtn.addActionListener(this);

        connectDatabase();
        setVisible(true);
    }

    void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "1234");
            JOptionPane.showMessageDialog(this, "Database Connected Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Error: " + e.getMessage());
        }
    }

    void executeUpdate(String query, String successMessage, boolean isDelete) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            if (isDelete) {
                stmt.setInt(1, Integer.parseInt(fields[0].getText()));
            } else {
                for (int i = 0; i < fields.length; i++) {
                    if (i == 0 || i == 6) {
                        stmt.setInt(i + 1, Integer.parseInt(fields[i].getText()));
                    } else {
                        stmt.setString(i + 1, fields[i].getText());
                    }
                }
            }
            int rowsAffected = stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, rowsAffected > 0 ? successMessage : "No record found.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookBtn) {
            executeUpdate("INSERT INTO movies VALUES (?, ?, ?, ?, ?, ?, ?)", "Movie Booked Successfully!", false);
        } else if (e.getSource() == cancelBtn) {
            executeUpdate("DELETE FROM movies WHERE movieid=?", "Booking Canceled!", true);
        } else if (e.getSource() == updateBtn) {
            executeUpdate("UPDATE movies SET moviename=?, duration=?, language=?, theatername=?, rating=?, numoftickets=? WHERE movieid=?", "Movie Updated Successfully!", false);
        } else if (e.getSource() == displayBtn) {
            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {
                StringBuilder data = new StringBuilder();
                while (rs.next()) {
                    data.append("Movie ID: ").append(rs.getInt(1)).append(", Movie Name: ")
                        .append(rs.getString(2)).append(", Duration: ").append(rs.getString(3))
                        .append(", Language: ").append(rs.getString(4)).append(", Theater: ")
                        .append(rs.getString(5)).append(", Rating: ").append(rs.getString(6))
                        .append(", Tickets: ").append(rs.getInt(7)).append("\n");
                }
                JOptionPane.showMessageDialog(this, data.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new MovieTicketBooking();
    }

    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    // }
     
}
