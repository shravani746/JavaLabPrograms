import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ticketBooking extends JFrame implements ActionListener{

    JTextField[] fields = new JTextField[7];
    JButton displayBtn,bookBtn,cancelBtn,updateBtn;
    Connection con;

    ticketBooking(){
        setTitle("Movie ticket booking system");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9,2,10,10));

        String[] labels = {"ID:","Name:","Duration:","lang:","theatre:","ratings","no of tickets"};
        for(int i=0; i <labels.length;i++){
            add(new JLabel(labels[i]));
            fields[i]= new JTextField();
            add(fields[i]);
        }

        bookBtn = new JButton("book");
        updateBtn=new JButton("Update");
        cancelBtn=new JButton("cancel");
        displayBtn=new JButton("Display");

        add(bookBtn);add(updateBtn);add(cancelBtn);add(displayBtn);

        bookBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        displayBtn.addActionListener(this);

        connectDatabase();
        setVisible(true);
    }

    void connectDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets","root","1234");
            JOptionPane.showMessageDialog(this,"Database connected Succesfully");
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"connection error " + e.getMessage());
        }
    }

    void executeUpdate(String query, String successMessage, boolean isDelete ){
        try(PreparedStatement stmt = con.prepareStatement(query)){
            if(isDelete){
                stmt.setInt(1,Integer.parseInt(fields[0].getText()));
            }else{
                for(int i = 0;i<fields.length;i++){
                    if(i==0 || i==6){
                        stmt.setInt(i+1,Integer.parseInt(fields[i].getText()));
                    }else{
                        stmt.setString(i+1,fields[i].getText());
                    }
                }
            }
            int rowsAffected = stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, rowsAffected > 0 ? successMessage : "No Records Found");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"all the required fields must be filled : ");
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == bookBtn){
            executeUpdate("insert into movies values (?,?,?,?,?,?,?)","Movie booked successfully",false);
        }else if(e.getSource()==cancelBtn){
            executeUpdate("delete from movies where movieid=?","Booking canceled",true);
        }else if(e.getSource()==updateBtn){
            executeUpdate("update movies set moviename=?,duration=?,language=?,theatrename=?,rating=?,numoftickets=? where movieid=? ","Movie uodated successfully",false);
        }else if(e.getSource()== displayBtn){
            try(Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("Select *from movies")){
                StringBuilder data = new StringBuilder();
                while(rs.next()){
                    data.append("Movie ID: ").append(rs.getString(1)).append("Movie Name: ").append(rs.getString(2)).append("\n");
                }
                JOptionPane.showMessageDialog(this, data.toString());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args){
        new ticketBooking();
    }
}