import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StudentInfoSystemSwing {
    private JFrame frame;
    private JComboBox<String> courseDropdown;
    private JTextField nameField, contactField, studentIdField;
    private JTextArea studentDetails;
    private String studentRecords = ""; 

    public StudentInfoSystemSwing() {
        frame = new JFrame("Student Information System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 1));

        JLabel idLabel = new JLabel("Student ID:");
        studentIdField = new JTextField();

        JLabel nameLabel = new JLabel("Student Name:");
        nameField = new JTextField();

        JLabel contactLabel = new JLabel("Contact Number:");
        contactField = new JTextField();

        JLabel courseLabel = new JLabel("Select Course:");
        String[] courses = {"Data Structures", "Mathematics", "Java", "FSD", "English"};
        courseDropdown = new JComboBox<>(courses);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitAction());

        studentDetails = new JTextArea();
        studentDetails.setEditable(false);

        frame.add(idLabel);
        frame.add(studentIdField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(contactLabel);
        frame.add(contactField);
        frame.add(courseLabel);
        frame.add(courseDropdown);
        frame.add(submitButton);
        frame.add(new JScrollPane(studentDetails));

        frame.setVisible(true);
    }

    class SubmitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String studentId = studentIdField.getText().trim();
            String studentName = nameField.getText().trim();
            String contactNumber = contactField.getText().trim();
            String selectedCourse = (String) courseDropdown.getSelectedItem();

            if (studentId.isEmpty() || studentName.isEmpty() || contactNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter all details!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPhoneNumber(contactNumber)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid 10-digit phone number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String studentInfo = "ID: " + studentId + " | Name: " + studentName + " | Course: " + selectedCourse + " | Contact: " + contactNumber;
            studentRecords += studentInfo + "\n";

            studentDetails.setText(studentRecords);
            JOptionPane.showMessageDialog(frame, "Student Information Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) return false;
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        new StudentInfoSystemSwing();
    }
}
