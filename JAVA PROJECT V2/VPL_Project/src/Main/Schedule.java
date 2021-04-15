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
public class Schedule {

    public void scheduleStart(Connector cnn, JComboBox coBox) {
        try {
            coBox.removeAllItems();
            String sql = "SELECT MAX(Round) as vong FROM matchgame";
            Statement st = cnn.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int round = 0;
            if (rs.next()) {
                round = Integer.parseInt(rs.getString("vong"));
            }
            System.out.println(round);
            for (int i = 1; i <= round; i++) {
                coBox.addItem(Integer.toString(i));
            }
        } catch (Exception e) {
            System.out.println("Fail load db schedule");
            e.printStackTrace();
        }
    }

    public void loadDataMatchGame(Connector cnn, JTable tab, int round) {
        try {
            tab.removeAll();
            String header[] = {"Match ID", "Club Home", " Club Away", "Time", "Round"};
            DefaultTableModel tblModel = new DefaultTableModel(header, 0);
            String sql = "SELECT * FROM matchgame where Round = ?";
            PreparedStatement st = cnn.conn.prepareStatement(sql);
            st.setInt(1, round);
            ResultSet rs = st.executeQuery();
            Vector data = null;
            tblModel.setRowCount(0);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("matchid"));
                // them ten doi chu nha
                String temp1 = rs.getString("clubidhome");
                String temp2 = rs.getString("clubidaway");
                sql = "SELECT * FROM `club` WHERE (`ClubID`= ?) OR (`ClubID`= ?)";
                PreparedStatement st1 = cnn.conn.prepareStatement(sql);
                st1.setString(1, temp1);
                st1.setString(2, temp2);
                ResultSet rs1 = st1.executeQuery();
                while (rs1.next()) {
                    if (temp1.compareTo(rs1.getString("clubid")) == 0) {
                        temp1 = rs1.getString("name");
                    } else {
                        temp2 = rs1.getString("name");
                    }
                }
                data.add(temp1);
                data.add(temp2);
                String[] temp3Time=rs.getString("time").split("-");
                String timeMain=temp3Time[2]+"-"+temp3Time[1]+"-"+temp3Time[0];
                data.add(timeMain);
                data.add(rs.getString("round"));
                tblModel.addRow(data);
                st1.close();
                rs1.close();

            }
            tab.setModel(tblModel);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
