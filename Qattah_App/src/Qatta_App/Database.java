package Qatta_App;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Database {

    String url = "jdbc:postgresql://localhost:5432/Qatta";
    String username = "Abdullah";
    String password = "1234";
    Connection connection = DriverManager.getConnection(url, username, password);
    Statement st = connection.createStatement();

    public Database() throws SQLException {

    }

    public boolean login(String username, String pass) throws SQLException {
        String query = "SELECT password FROM login WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String checkPass = rs.getString(1);
                    boolean passwordsMatch = checkPass.equals(pass);
                    return passwordsMatch;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean signup(String username, String pass) throws SQLException {
        int id = (DBTotal() + 1) * 111;
        String query = "INSERT INTO login (\"ID\", username, password) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, pass);

            ps.executeUpdate();
        }
        return true;
    }

    public int DBTotal() throws SQLException {

        String query = "SELECT COUNT(*) FROM login ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count;
                }
            }
        }
        return 0;

    }

    public int getID(String username) throws SQLException {

        String query = ("SELECT \"ID\" FROM login WHERE username = ?");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int checkID = rs.getInt(1);
                    return checkID;
                } else {
                    return 0;
                }
            }
        }

    }

    public void insert(int ID, String Qname, double Price, int IDowner) throws SQLException {
        if (!recordExists(ID, Qname)) {
            String query = "INSERT INTO qattah_info (userid, qname, qprice,idowner) VALUES(?,?,?,?)";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, ID);
                ps.setString(2, Qname);
                ps.setDouble(3, Price);
                ps.setInt(4, IDowner);
                ps.executeUpdate();
            }
        } else {
            System.out.println("Record already exists: ID = " + ID + ", Name = " + Qname);
        }
    }

    public boolean recordExists(int ID, String Qname) throws SQLException {
        String query = "SELECT COUNT(*) FROM qattah_info WHERE userid = ? AND qname = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ID);
            ps.setString(2, Qname);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public void update(int ID, double price) throws SQLException {

        String query = "UPDATE qattah_info SET qprice = ? WHERE userid = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, price);
            ps.setInt(2, ID);
            ps.executeUpdate();
        }

    }

    public String getName(int ID) throws SQLException {

        String query = ("SELECT username FROM login WHERE \"ID\" = ?");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String checkname = rs.getString(1);
                    return checkname;
                } else {
                    return "";
                }
            }
        }

    }

    public void deleteRow(int userID, String qname) throws SQLException {

        String query = "DELETE FROM qattah_info WHERE userid = ? AND qname = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, userID);
            ps.setString(2, qname);

            ps.executeUpdate();
        }
    }

    public void show(String qattaName, int ud, DefaultTableModel model) throws SQLException {
        String query = "SELECT userid, qname, qprice ,idowner FROM qattah_info WHERE qname = ? AND idowner = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, qattaName);
            ps.setInt(2, ud);
            try (ResultSet rs = ps.executeQuery()) {
                model.setRowCount(1);
                while (rs.next()) {
                    int x = rs.getInt("userid");
                    String name = getName(x);
                    String y = rs.getString("qname");
                    double z = rs.getDouble("qprice");
                    int a = rs.getInt("idowner");
                    model.addRow(new Object[]{a, name, y, z});
                }
            }
        }

    }

    public void showqatta(int userid, DefaultTableModel model) throws SQLException {
        String query = "SELECT  qname, qprice ,idowner FROM qattah_info WHERE userid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userid);
            try (ResultSet rs = ps.executeQuery()) {
                model.setRowCount(1);
                while (rs.next()) {
                    String y = rs.getString("qname");
                    double z = rs.getDouble("qprice");
                    int a = rs.getInt("idowner");
                    model.addRow(new Object[]{a, y, z});
                }
            }
        }

    }

    public boolean personExists(String name) throws SQLException {
        String query = "SELECT COUNT(*) FROM login WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

}
