import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentInfoSwings {
    public static void main(String[] args) {
        // Create Frame
        JFrame frame = new JFrame("Student Information System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Labels & Input Fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(5);


        JButton submitButton = new JButton("Submit");

        // Text Area for Output
        JTextArea outputArea = new JTextArea(8, 30);
        outputArea.setEditable(false);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(idLabel);
        frame.add(idField);
        frame.add(ageLabel);
        frame.add(ageField);
        frame.add(submitButton);
        frame.add(new JScrollPane(outputArea));

        // Button Action
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String id = idField.getText().trim();
                String age = ageField.getText().trim();

                if (name.isEmpty() || id.isEmpty() || age.isEmpty()) {
                    outputArea.setText("Error: All fields must be filled.");
                    return;
                }

                outputArea.setText("Student Info:\nName: " + name + "\nID: " + id + "\nAge: " + age);
            }
        });

        // Show frame
        frame.setVisible(true);
    }
}
