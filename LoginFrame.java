import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public LoginFrame() {

        setTitle("Online Reservation System");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Login");
        title.setBounds(170, 20, 100, 30);
        add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 110, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 150, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(140, 160, 100, 30);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {

                    Connection con = DBConnection.getConnection();

                    PreparedStatement ps = con.prepareStatement(
                            "SELECT * FROM users WHERE username=? AND password=?"
                    );

                    ps.setString(1, username);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Login Successful"
                        );

                        rs.close();
                        ps.close();
                        con.close();

                        dispose();

                        new ReservationFrame();

                    } else {

                        JOptionPane.showMessageDialog(
                                null,
                                "Invalid Username or Password"
                        );
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}