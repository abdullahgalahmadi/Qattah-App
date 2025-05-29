/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Qatta_App;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author a pc
 */
public class signupPage extends JFrame implements ActionListener {

    String username;
    String password;
    int idOwner;
    Database database;

    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    JLabel label = new JLabel();

    JLabel label4 = new JLabel("Username");
    JLabel label5 = new JLabel("Password");

    JButton button = new JButton();
    JButton button2 = new JButton();

    TextField text = new TextField();
    TextField text2 = new TextField();

    public signupPage() throws SQLException {
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
        frame.add(button2);
        frame.add(label4);
        frame.add(label5);
        frame.add(text);
        frame.add(text2);
    }

    private void initializeSocketConnection() throws IOException {

        Socket client = new Socket("127.0.0.1", 7897);
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(new User(text.getText()));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button2) {

            try {
                database.signup(text.getText(), text2.getText());
                idOwner = database.getID(text.getText());
                initializeSocketConnection();
                new Home(text.getText(), text2.getText(), database, idOwner);
                JOptionPane.showMessageDialog(null, "sign up successful");
                frame.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "connection failed");
            } catch (SQLException ex) {
                Logger.getLogger(signupPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
