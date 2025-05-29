package Qatta_App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginNew extends JFrame implements ActionListener {

    String username;
    String password;
    int idOwner;

    Database database;

    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    JLabel label = new JLabel();

    JLabel label4 = new JLabel("Username");
    JLabel label5 = new JLabel("Password");
    JLabel label6 = new JLabel("Welcome");
    JLabel label7 = new JLabel("No account?");

    JButton button = new JButton();
    JButton button2 = new JButton();

    TextField text = new TextField();
    TextField text2 = new TextField();

    public LoginNew() throws SQLException {
        this.database = new Database();
        initializeGUI();
    }

    private void initializeGUI() {
        text.setBounds(250, 320, 200, 20);
        text2.setBounds(250, 420, 200, 20);

        panel.setBounds(0, 0, 500, 100);
        panel.setBackground(new Color(72, 97, 232));

        label4.setBounds(100, 300, 100, 50);
        label4.setFont(new Font("", Font.PLAIN, 20));
        label5.setBounds(100, 400, 100, 50);
        label5.setFont(new Font("", Font.PLAIN, 20));
        label6.setBounds(150, 150, 300, 50);
        label6.setFont(new Font("", Font.PLAIN, 50));
        label7.setBounds(210, 550, 300, 50);
        label7.setFont(new Font("", Font.PLAIN, 15));

        button.setBounds(200, 475, 100, 50);
        button.setText("Login");
        button.setBackground(new Color(72, 97, 232));
        button.setForeground(new Color(0, 0, 0));
        button.setFocusable(false);
        button.addActionListener(this);

        button2.setBounds(200, 600, 100, 50);
        button2.setText("sign up");
        button2.setBackground(new Color(72, 97, 232));
        button2.setForeground(new Color(0, 0, 0));
        button2.setFocusable(false);
        button2.addActionListener(this);

        frame.setLayout(null);
        frame.setTitle("Qattah");
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        

        panel.add(label);

        frame.add(panel);
        frame.add(button);
        frame.add(button2);
        frame.add(label4);
        frame.add(label5);
        frame.add(label6);
        frame.add(label7);
        frame.add(text);
        frame.add(text2);
    }

    private void initializeSocketConnection() throws IOException {

        Socket client = new Socket("127.0.0.1", 7897);
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(new User(username));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            this.username = text.getText();
            this.password = text2.getText();
            try {
                boolean check = database.login(username, password);
                if (check) {
                    try {
                        initializeSocketConnection();
                        idOwner = database.getID(username);
                        JOptionPane.showMessageDialog(null, "login successful");
                        frame.dispose();
                        new Home(username, password, database, idOwner);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "connection failed");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "login failed");
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == button2) {
            try {
                new signupPage();
                frame.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(LoginNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        new LoginNew();
    }
}
