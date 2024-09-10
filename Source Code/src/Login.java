// This class will show the Login UI

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    public static String username;
    JButton lgBtn, clrBtn, newUserBtn;
    JLabel unameLbl, passLbl, newUserLbl;
    JTextField uname, pass;
//    String username, password;

    public Login() {

        getContentPane().setBackground(new Color(227, 197, 227));
        setLayout(null);

        // Logo
        ImageIcon bg1 = new ImageIcon(ClassLoader.getSystemResource("Images/login_logo.png"));
        Image image = bg1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon bg2 = new ImageIcon(image);
        JLabel bg_final = new JLabel(bg2);
        bg_final.setBounds(0, 0, 400, 275);
        add(bg_final);

        // Username
        unameLbl = new JLabel("Username:");
        unameLbl.setBounds(50, 285, 100, 30);
        add(unameLbl);

        // Password
        passLbl = new JLabel("Password:");
        passLbl.setBounds(50, 335, 100, 30);
        add(passLbl);

        // Username Box
        uname = new JTextField();
        uname.setBounds(160, 285, 180, 30);
        add(uname);

        // Password box
        pass = new JTextField();
        pass.setBounds(160, 335, 180, 30);
        add(pass);

        // Login Button
        lgBtn = new JButton("Login");
        lgBtn.setBounds(65, 410, 100, 30);
        lgBtn.setFocusPainted(false);
        lgBtn.addActionListener(this);
        add(lgBtn);

        // Clear Button
        clrBtn = new JButton("Clear");
        clrBtn.setBounds(235, 410, 100, 30);
        clrBtn.setFocusPainted(false);
        clrBtn.addActionListener(this);
        add(clrBtn);

        // New User label
        newUserLbl = new JLabel("New User?");
        newUserLbl.setBounds(130, 480, 62, 10);
        add(newUserLbl);

        // New User Button
        newUserBtn = new JButton("Click Here");
        newUserBtn.setBounds(180, 472, 94, 26);
        newUserBtn.setBackground(Color.BLACK);
        newUserBtn.setBorderPainted(false);
        newUserBtn.setContentAreaFilled(false);
        newUserBtn.setFocusPainted(false);
        newUserBtn.addActionListener(this);
        add(newUserBtn);

        // Defaults
        setSize(400, 530);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lgBtn) {
            try {
                username = uname.getText();
                String password = pass.getText();

                if (username.equals("admin") && password.equals("admin")) {
                    new ViewUsers();
                    JOptionPane.showMessageDialog(null, "Welcome Admin!");
                } else {
                    ConnectDB obj = new ConnectDB();

                    String query = "SELECT * FROM login WHERE Username = ? AND Password = ?";

                    obj.pst = obj.cn.prepareStatement(query);
                    obj.pst.setString(1, username);
                    obj.pst.setString(2, password);

                    ResultSet rs = obj.pst.executeQuery();  // preparedStatement class

                    if (rs.next()) {
                        new ViewImage();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    }
                }
            } catch (Exception damnIt) {
                System.out.println("Damn It!! Something went Wrong");
                System.out.println(damnIt);
            }
        } else if (e.getSource() == clrBtn) {
            uname.setText("");
            pass.setText("");
        } else {
            new NewUser();
            dispose();
        }
    }
}
