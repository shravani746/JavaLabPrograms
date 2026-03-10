import javax.swing.*; 
import java.awt.Color;
   
public class ButtonExample {  
public static void main(String[] args) {  
    JFrame f=new JFrame("Button Example");  
    JButton b=new JButton("Click Here");  
    b.setBounds(150,150,150,150);  
    f.add(b);  
    f.setSize(600,400);  
    f.setLayout(null); 
    b.setBackground(Color.BLUE);
    b.setForeground(Color.RED);
    f.setVisible(true);   
}  
}