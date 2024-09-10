// The front page from where the user will enter or exit the system

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrontPage extends JFrame implements ActionListener {
    JButton enter, exit;
    FrontPage() {

        getContentPane().setBackground(new Color(0, 0, 0));
        setLayout(null);

        // Main heading
        JLabel title = new JLabel("Image Gallery");
        title.setBounds(350, 30, 300, 60);
        title.setFont(new Font("Monotype Corsiva", Font.PLAIN, 60));
        title.setForeground(Color.WHITE);
        add(title);

        // Image
        ImageIcon bg1 = new ImageIcon(ClassLoader.getSystemResource("Images/bg.jpg"));
        Image image = bg1.getImage().getScaledInstance(1000, 400, Image.SCALE_DEFAULT);
        ImageIcon bg2 = new ImageIcon(image);
        JLabel bg_final = new JLabel(bg2);
        bg_final.setBounds(0, 0, 985, 400);
        add(bg_final);

        // enter Button
        enter = new JButton("Enter");
        enter.setBounds(230, 120, 200, 33);
        enter.setBackground(Color.BLACK);
        enter.setForeground(Color.WHITE);
        enter.setFont(new Font("Maiandra GD",Font.PLAIN, 30));
        enter.setBorderPainted(false);
        enter.setContentAreaFilled(false);
        enter.setFocusPainted(false);
        enter.addActionListener(this);
        bg_final.add(enter);

        // Exit Button
        exit = new JButton("Exit");
        exit.setBounds(570, 120, 200, 30);
        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.WHITE);
        exit.setFont(new Font("Maiandra GD",Font.PLAIN, 30));
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.addActionListener(this);
        bg_final.add(exit);

        // Defaults
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            new Login();
            dispose();
        } else {
            dispose();
        }
    }
}
