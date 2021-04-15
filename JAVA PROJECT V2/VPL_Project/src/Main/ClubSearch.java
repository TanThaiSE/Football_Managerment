/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Database_Connector.Connector;
import java.sql.*;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TanThai
 */
public class ClubSearch {

    public void clubSearchStart(Connector cnn, JComboBox coBox) {
        try {
            coBox.removeAllItems();
            String sql = "SELECT * FROM club";
            Statement st = cnn.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String nameClub = rs.getString("name");
                coBox.addItem(nameClub);
//                System.out.println(rs.getString("name"));
            }

        } catch (Exception e) {
            System.out.println("Fail load db CLUB");
            e.printStackTrace();
        }
    }

    public void loadDataClubCManaCoachStadium(Connector cnn, JTable tab, String nameClub) {
        try {
            tab.removeAll();
            String header[] = {"Club ID", "Club Manager", "Coach", "Stadium"};
            DefaultTableModel tblModel = new DefaultTableModel(header, 0);
            tblModel.setRowCount(0);
            Vector data = new Vector();

            String sql = "SELECT * FROM club where name = ?";
            PreparedStatement st = cnn.conn.prepareStatement(sql);
            st.setString(1, nameClub);
            ResultSet rs = st.executeQuery();
            rs.next();
            String clubId = rs.getString("clubid"); //lấy clubid
            String stadiumId = rs.getString("stadium"); //lấy stadiumid
            data.add(clubId); //xong clubid
            st.close();
            rs.close();

            sql = "SELECT * FROM `stadium` where stadiumid=?";
            PreparedStatement st1 = cnn.conn.prepareStatement(sql);
            st1.setString(1, stadiumId);
            ResultSet rs1 = st1.executeQuery();
            rs1.next();
            String stadiumName = rs1.getString("name"); //lấy tên stadium
            data.add(stadiumName); //Xong nameStadium
            st1.close();
            rs1.close();

            sql = "SELECT * FROM `clubmanager` where clubid=?";
            PreparedStatement st2 = cnn.conn.prepareStatement(sql);
            st2.setString(1, clubId);
            ResultSet rs2 = st2.executeQuery();
            rs2.next();
            String nameManager = rs2.getString("name"); //lấy tên manager
            data.add(nameManager); //xong nameManager
            st2.close();
            rs2.close();

            sql = "SELECT * FROM `coach` where clubid=?";
            PreparedStatement st3 = cnn.conn.prepareStatement(sql);
            st3.setString(1, clubId);
            ResultSet rs3 = st3.executeQuery();
            rs3.next();
            String nameCoach = rs3.getString("name"); //lấy tên coach
            data.add(nameCoach); //xong nameCoach
            st3.close();
            rs3.close();
            tblModel.addRow(data);
            tab.setModel(tblModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
