import javax.swing.*;
import javax.swing.ImageIcon; 

class LabelExample {
    public static void main(String args[]) {
        JFrame f = new JFrame("Label Example");
        JLabel l1, l2;

        l1 = new JLabel("First Label.");
        l1.setBounds(50, 50, 100, 30);
        l1.setHorizontalAlignment(SwingConstants.CENTER); 

        l2 = new JLabel("Second Label.");
        l2.setBounds(50, 100, 100, 30);

        // Load an image icon (replace "image.png" with your image file)
        ImageIcon icon = new ImageIcon("Screenshot 2024-10-12 143515.png"); 
// Make sure the image is in the project directory or provide a full path.
        l2.setIcon(icon);
        l2.setText("Image Label"); 
        l2.setHorizontalAlignment(SwingConstants.LEFT); 
        l2.setIconTextGap(10); 


        f.add(l1);
        f.add(l2);

        f.setSize(300, 300);
        f.setLayout(null);
        f.setVisible(true);
    }
}