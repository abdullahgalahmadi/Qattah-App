/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Qatta_App;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author a pc
 */
public class CreatePage extends JFrame implements ActionListener {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    String Username;
    String Pass;
    Database d;
    int idowner;
    boolean ownerAvailable = true;

    JFrame frame2 = new JFrame();

    DefaultTableModel model = new DefaultTableModel();

    JLabel label = new JLabel("Qattah managment");
    JLabel label2 = new JLabel("Qatta name");
    JLabel label3 = new JLabel("Total Price");
    JLabel label4 = new JLabel("Qattah members");

    JPanel panel = new JPanel();

    JButton back = new JButton();
    JButton add = new JButton();
    JButton create = new JButton();

    TextField text = new TextField();
    TextField text2 = new TextField();
    TextField text3 = new TextField();

    JTable jTable1 = new JTable();

    CreatePage(String username, String pass, Database d, int idowner) {

        panel.setBounds(0, 0, 500, 125);
        panel.setBackground(new Color(72, 97, 232));

        label.setBounds(75, 150, 400, 50);
        label.setFont(new Font("", Font.PLAIN, 40));
        label2.setBounds(75, 230, 150, 50);
        label2.setFont(new Font("", Font.PLAIN, 20));
        label3.setBounds(75, 280, 100, 50);
        label3.setFont(new Font("", Font.PLAIN, 20));
        label4.setBounds(50, 330, 150, 50);
        label4.setFont(new Font("", Font.PLAIN, 20));

        text.setBounds(225, 243, 200, 20);
        text2.setBounds(225, 293, 200, 20);
        text3.setBounds(225, 343, 200, 20);

        create.setText("show");
        create.setBackground(new Color(72, 97, 232));
        create.setForeground(new Color(0, 0, 0));
        create.setFont(new Font(null, Font.PLAIN, 15));
        create.setFocusable(false);
        create.setBounds(200, 700, 100, 50);
        create.setVerticalTextPosition(JButton.BOTTOM);
        create.setHorizontalTextPosition(JButton.CENTER);
        create.addActionListener(this);
        
        back.setText("Home");
        back.setBackground(new Color(72, 97, 232));
        back.setForeground(new Color(0, 0, 0));
        back.setFont(new Font(null, Font.PLAIN, 15));
        back.setFocusable(false);
        back.setBounds(50, 700, 100, 50);
        back.setVerticalTextPosition(JButton.BOTTOM);
        back.setHorizontalTextPosition(JButton.CENTER);
        back.addActionListener(this);

        add.setText("add");
        add.setBackground(new Color(72, 97, 232));
        add.setForeground(new Color(0, 0, 0));
        add.setFont(new Font(null, Font.PLAIN, 15));
        add.setFocusable(false);
        add.setBounds(290, 370, 75, 50);
        add.setVerticalTextPosition(JButton.BOTTOM);
        add.setHorizontalTextPosition(JButton.CENTER);
        add.addActionListener(this);

        model.addColumn("OwnerID");
        model.addColumn("name");
        model.addColumn("Qattah name");
        model.addColumn("Qattah");

        jTable1.setPreferredSize(new Dimension(200, 200));
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.setBounds(20, 450, 440, 200);
        jTable1.setVisible(false);
        jTable1.setBackground(Color.white);
        jTable1.setModel(model);
        jTable1.setGridColor(Color.black);

        model.addRow(new Object[]{"OwnerID", "name", "Qattah name", "qatta"});

        frame2.setLayout(null);
        frame2.setTitle("Qattah");
        frame2.setSize(500, 800);
        frame2.setResizable(false);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);
        frame2.getContentPane().setBackground(new Color(255, 255, 255));
        frame2.add(label);
        frame2.add(label2);
        frame2.add(label3);
        frame2.add(label4);
        frame2.add(panel);
        frame2.add(add);
        frame2.add(create);
        frame2.add(back);
        frame2.add(text);
        frame2.add(text2);
        frame2.add(text3);
        frame2.add(jTable1);

        this.Username = username;
        this.Pass = pass;
        this.d = d;
        this.idowner = idowner;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == add) {

            try {
                if (d.personExists(text3.getText())) {
                    if (ownerAvailable) {
                        names.add(d.getName(idowner));
                        ownerAvailable = false;
                    }
                    if (!d.recordExists(d.getID(text3.getText()), text.getText())){
                    names.add(text3.getText());
                    JOptionPane.showMessageDialog(null, "memeber added");
                    }else{
                        JOptionPane.showMessageDialog(null, "name already exist");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "name does not exist");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreatePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            text3.setText("");

        } else if (e.getSource() == create) {

            for (int i = 0; i < names.size(); i++) {
                double p = Double.parseDouble(text2.getText()) / names.size();
                try {

                    if (!d.recordExists(d.getID(names.get(i)), text.getText())) {
                        int id = d.getID(names.get(i));
                        d.insert(id, text.getText(), p, idowner);
                    } else {
                        d.update(d.getID(names.get(i)), p);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(CreatePage.class.getName()).log(Level.SEVERE, null, ex);
                }

                users.add(new User(names.get(i), p));
            }

            try {
                int ud = d.getID(Username);
                d.show(text.getText(), ud, model);

                jTable1.setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(CreatePage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (e.getSource() == back){
            frame2.dispose();
            new Home(Username, Pass, d, idowner);
            
        }
    }

}
