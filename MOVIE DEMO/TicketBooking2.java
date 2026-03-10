import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class TicketBooking2 extends JFrame implements ActionListener{
    
    JTextField[] fields = new JTextField[7];
    JButton book, cancel,update,display;
    Connection con;

    TicketBooking2(){
        setTitle("Ticket Booking system!");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9,2,10,10));

        String[] labels = {"Movie ID:","Movie Name:","Duration:","Theatre Name:","Ratings:","Language:","Number Of Tickets"};
        for(int i=0;i<labels.length;i++){
            add(new JLabel(labels[i]));
            fields[i]=new JTextField();
            add(fields[i]);
        }

        book=new JButton("Book Ticket");
        cancel=new JButton("Cancel Booking");
        update=new JButton("Update Booking Details");
        display=new JButton("Display all Bookings");

        add(book);add(cancel);add(update);add(display);

        book.addActionListener(this);
        cancel.addActionListener(this);
        update.addActionListener(this);
        display.addActionListener(this);

        connectDatabase();

        setVisible(true);
    }

    void connectDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "1234");
            JOptionPane.showMessageDialog(this, "Database Connected Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Connection Error : " + e.getMessage());
        }
    }

    void executeUpdate(String query, String successMessage, boolean isDelete ){
        try (PreparedStatement stmt = con.prepareStatement(query)){
            if(isDelete){
                stmt.setInt(1, Integer.parseInt(fields[0].getText()));
            }else{
                for(int i=0;i<fields.length;i++){
                    if(i==0 || i==6){
                        stmt.setInt(i+1, Integer.parseInt(fields[i].getText()));
                    }
                    else{
                        stmt.setString(i+1, fields[i].getText());
                    }
                }
            }
            int rowsAffected = stmt.executeUpdate();
            JOptionPane.showMessageDialog(this,rowsAffected>0 ? successMessage : "No Records Found");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fill all the required details");
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==book){
            executeUpdate("Insert into movies values (?,?,?,?,?,?,?)", "Ticket Booked", false);
        }else if(e.getSource()==cancel){
            executeUpdate("delete from movies where movieid=?", "Booking canceled", true);
        }else if(e.getSource()==update){
            executeUpdate("update movies set moviename=? where movieid=?", "Booking updated", false);
        }else if(e.getSource()==display){
            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("Select *from movies")){
                StringBuilder data = new StringBuilder();
                while(rs.next()){
                    data.append("Movie ID: ").append(rs.getString(1)).append("Movie Name : ").append(rs.getString(2)).append("\n");
                }
                JOptionPane.showConfirmDialog(this, data.toString());
            } catch (Exception ex) {
                JOptionPane.showConfirmDialog(this, "Error : " + ex.getMessage());
            }
        }
    }

    public static void  main(String[] args){
        new TicketBooking2();
    }
}