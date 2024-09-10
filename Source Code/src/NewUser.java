import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class NewUser extends JFrame implements ActionListener {

    JLabel heading, fnameLbl, lnameLbl, ageLbl, genederLbl, dobLbl, unameLbl, passLbl;
    JTextField firstName;
    JTextField lastName;
    JTextField age;
    static JTextField userName;
    JTextField passWord;
    JDateChooser dob;
    JComboBox gender;
    JButton addBtn, cancelBtn;
    NewUser() {

        getContentPane().setBackground(new Color(241, 238, 220));
        setLayout(null);

        // Main Title
        heading = new JLabel("New User");
        heading.setBounds(358, 30, 484, 108);
        heading.setFont(new Font("Papyrus", Font.BOLD, 100));
        add(heading);

        // First Name Label
        fnameLbl = new JLabel("First Name");
        fnameLbl.setBounds(120, 200, 160, 60);
        fnameLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(fnameLbl);

        // First Name Box
        firstName = new JTextField();
        firstName.setBounds(280, 200, 250, 30);
        firstName.setFont(new Font("Goudy Old Style", Font.PLAIN, 20));
        add(firstName);

        // Last Name Label
        lnameLbl = new JLabel("Last Name");
        lnameLbl.setBounds(650, 200, 140, 60);
        lnameLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(lnameLbl);

        // Last Name Box
        lastName = new JTextField();
        lastName.setBounds(820, 200, 250, 30);
        lastName.setFont(new Font("Goudy Old Style", Font.PLAIN, 20));
        add(lastName);


        // Age Label
        ageLbl = new JLabel("Age");
        ageLbl.setBounds(120, 300, 55, 60);
        ageLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(ageLbl);

        // Age Box
        age = new JTextField();
        age.setBounds(180, 300, 180, 30);
        age.setFont(new Font("Goudy Old Style", Font.PLAIN, 20));
        add(age);

        // Gender Label
        genederLbl = new JLabel("Gender");
        genederLbl.setBounds(460, 300, 140, 60);
        genederLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(genederLbl);

        // Gender Selection Pane
        String[] courses = {"Male", "Female", "Transgender", "Gay", "Lesbian", "Bisexual", "Queer"};
        gender = new JComboBox(courses);
        gender.setBackground(Color.WHITE);
        gender.setBounds(570, 300, 140, 30);
        add(gender);

        // Date of Birth Label
        dobLbl = new JLabel("DOB");
        dobLbl.setBounds(820, 300, 140, 60);
        dobLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(dobLbl);

        // Date Of Birth
        dob = new JDateChooser();
        dob.setBounds(900, 300, 170, 30);
        add(dob);


        // Username Label
        unameLbl = new JLabel("Username");
        unameLbl.setBounds(120, 400, 140, 60);
        unameLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(unameLbl);

        // Username Box
        userName = new JTextField();
        userName.setBounds(280, 400, 250, 30);
        userName.setFont(new Font("Goudy Old Style", Font.PLAIN, 20));
        add(userName);

        // Password Label
        passLbl = new JLabel("Password");
        passLbl.setBounds(650, 400, 140, 60);
        passLbl.setFont(new Font("Gabriola", Font.BOLD, 40));
        add(passLbl);

        // Password Box
        passWord = new JTextField();
        passWord.setBounds(820, 400, 250, 30);
        passWord.setFont(new Font("Goudy Old Style", Font.PLAIN, 20));
        add(passWord);

        // Add Button
        addBtn = new JButton("Add");
        addBtn.setBounds(300, 520, 200, 35);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Maiandra GD", Font.PLAIN, 20));
        addBtn.addActionListener(this);
        add(addBtn);

        // Cancel Button
        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(700, 520, 200, 35);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(new Font("Maiandra GD", Font.PLAIN, 20));
        cancelBtn.addActionListener(this);
        add(cancelBtn);

        // Defaults
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1214, 650);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            dispose();
            new FrontPage();
        } else {
            ConnectDB obj = new ConnectDB();

            try {
                String name = this.firstName.getText() + " " + this.lastName.getText();
                int age = Integer.parseInt(this.age.getText());
                Date dob = this.dob.getDate();
                String gender = (String) this.gender.getSelectedItem();
                String userName = this.userName.getText();
                String passWord = this.passWord.getText();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dobFormatted = formatter.format(dob);

                // Check if the username already exists
                String checkQuery = "SELECT * FROM users WHERE Username = ?";
                obj.pst = obj.cn.prepareStatement(checkQuery);
                obj.pst.setString(1, userName);
                ResultSet rs = obj.pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Username already taken!");
                } else {
                    String query = "INSERT INTO users (Name, Age, DateOfBirth, Gender, Username, Password) VALUES (?, ?, ?, ?, ?, ?);";
                    obj.pst = obj.cn.prepareStatement(query);
                    obj.pst.setString(1, name);
                    obj.pst.setInt(2, age);
                    obj.pst.setString(3, dobFormatted);
                    obj.pst.setString(4, gender);
                    obj.pst.setString(5, userName);
                    obj.pst.setString(6, passWord);

                    int rowsAffected = obj.pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Data added successfully!!");
                        dispose();
                        new CreateFolder();
                        new Login();
                    } else {
                        JOptionPane.showMessageDialog(null, "Something went wrong");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        }
    }
}