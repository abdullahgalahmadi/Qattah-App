/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Qatta_App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a pc
 */
public class Home extends JFrame implements ActionListener {

    String Username;
    String Pass;
    Database d;
    int idowner;

    JFrame frame = new JFrame();

    JPanel panel = new JPanel();
    JButton create = new JButton();
    JButton pay = new JButton();

    JLabel welcome = new JLabel();

    Home(String username, String pass, Database d, int idowner) {

        panel.setBounds(0, 0, 500, 125);
        panel.setBackground(new Color(72, 97, 232));

        welcome.setText("Welcome " + username);
        welcome.setBounds(125, 150, 400, 50);
        welcome.setFont(new Font("", Font.PLAIN, 40));

        create.setText("create");
        create.setBackground(new Color(72, 97, 232));
        create.setForeground(new Color(0, 0, 0));
        create.setFont(new Font(null, Font.PLAIN, 30));
        create.setFocusable(false);
        create.setBounds(150, 250, 200, 100);
        create.setVerticalTextPosition(JButton.BOTTOM);
        create.setHorizontalTextPosition(JButton.CENTER);
        create.addActionListener(this);

        pay.setText("pay");
        pay.setBackground(new Color(72, 97, 232));
        pay.setForeground(new Color(0, 0, 0));
        pay.setFont(new Font(null, Font.PLAIN, 30));
        pay.setFocusable(false);
        pay.setBounds(150, 400, 200, 100);
        pay.setVerticalTextPosition(JButton.BOTTOM);
        pay.setHorizontalTextPosition(JButton.CENTER);
        pay.addActionListener(this);

        frame.setLayout(null);
        frame.setTitle("Qattah");
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.add(panel);
        frame.add(create);
        frame.add(pay);
        frame.add(welcome);

        this.Username = username;
        this.Pass = pass;
        this.d = d;
        this.idowner = idowner;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == create) {

            frame.dispose();
            new CreatePage(Username, Pass, d, idowner);

        } else if (e.getSource() == pay) {
            frame.dispose();

            try {
                new PayPage(Username, Pass, d,idowner);
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
