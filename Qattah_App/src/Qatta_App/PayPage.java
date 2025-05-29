/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Qatta_App;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author a pc
 */
public class PayPage extends JFrame implements ActionListener {

    String Username;
    String Pass;
    Database d;
    String qattaname;
    int idowner;

    JFrame frame = new JFrame();

    JPanel panel = new JPanel();

    JButton pay = new JButton();
    
    JButton back = new JButton();

    JLabel q = new JLabel();

    DefaultTableModel model = new DefaultTableModel();

    JTable jTable1 = new JTable();

    PayPage(String username, String pass, Database d, int idowner) throws SQLException {

        panel.setBounds(0, 0, 500, 125);
        panel.setBackground(new Color(72, 97, 232));

        q.setText("Do you want to pay?");
        q.setBounds(75, 150, 400, 50);
        q.setFont(new Font("", Font.PLAIN, 30));

        pay.setText("pay");
        pay.setBackground(new Color(72, 97, 232));
        pay.setForeground(new Color(0, 0, 0));
        pay.setFont(new Font(null, Font.PLAIN, 15));
        pay.setFocusable(false);
        pay.setBounds(200, 600, 100, 50);
        pay.setVerticalTextPosition(JButton.BOTTOM);
        pay.setHorizontalTextPosition(JButton.CENTER);
        pay.addActionListener(this);
        
        back.setText("Home");
        back.setBackground(new Color(72, 97, 232));
        back.setForeground(new Color(0, 0, 0));
        back.setFont(new Font(null, Font.PLAIN, 15));
        back.setFocusable(false);
        back.setBounds(50, 600, 100, 50);
        back.setVerticalTextPosition(JButton.BOTTOM);
        back.setHorizontalTextPosition(JButton.CENTER);
        back.addActionListener(this);


        model.addColumn("Owner");
        model.addColumn("Qatta name");
        model.addColumn("Total");

        jTable1.setPreferredSize(new Dimension(400, 200));
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.setBounds(50, 350, 400, 200);
        jTable1.setVisible(false);
        jTable1.setBackground(Color.white);
        jTable1.setModel(model);
        jTable1.setGridColor(Color.black);

        model.addRow(new Object[]{"Owner", "Qattah name", "qatta"});
        jTable1.setVisible(true);

        d.showqatta(d.getID(username), model);

        frame.setLayout(null);
        frame.setTitle("Qattah");
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.add(panel);
        frame.add(pay);
        frame.add(back);
        pay.setVisible(true);
        frame.add(q);
        frame.add(jTable1);

        this.Username = username;
        this.Pass = pass;
        this.d = d;
        this.idowner = idowner;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == pay) {

            try {
                int id = d.getID(Username);

                int[] selectedRows = jTable1.getSelectedRows();
                for (int selectedRow : selectedRows) {
                    String qattaname = jTable1.getValueAt(selectedRow, 1).toString();

                    try {
                        d.deleteRow(id, qattaname);
                    } catch (SQLException ex) {
                        Logger.getLogger(PayPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                JOptionPane.showMessageDialog(null, "Payment successful");
                frame.dispose();
                new Home(Username, Pass, d, id);
            } catch (SQLException ex) {
                Logger.getLogger(PayPage.class.getName()).log(Level.SEVERE, null, ex);
            }

            jTable1.setVisible(false);
            pay.setEnabled(false);
        }else if (e.getSource() == back){
            frame.dispose();
            new Home(Username, Pass, d, idowner);
            
        }
    }

}
