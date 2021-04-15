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
 * @author miste
 */
public class PlayerSearch {

    public void loadDatabase(Connector cnn, JTable tab, String name) {
        try {
            tab.removeAll();
            String header[] = {"ID Cầu thủ", "Tên cầu thủ", "CLB", "Quốc tịch", "Ngày sinh", "Vị trí", "Địa chỉ", "Ngày kí HĐ", "Ngày hết hạn"};
            DefaultTableModel tblModel = new DefaultTableModel(header, 0);
            String sql = "SELECT * FROM `footballplayer` where name LIKE \'%" + name + "%\'";
            System.out.println(name);
            PreparedStatement st = cnn.conn.prepareStatement(sql);
            //st.setString(1, name);
            ResultSet rs = st.executeQuery();
            tblModel.setRowCount(0);
            Vector data = null;
          
            //rs.next();

            while (rs.next()) {
//                System.out.println(data.get(0).toString());
                
                data = new Vector();
                data.add(rs.getString("playerid"));
                data.add(rs.getString("name"));
                data.add(rs.getString("clubid"));
                data.add(rs.getString("nationality"));
                data.add(rs.getString("birthday"));
                data.add(rs.getString("position"));
                data.add(rs.getString("salary"));
                data.add(rs.getString("address"));

                String[] hiredate = rs.getString("hiredate").split("-");
                String hiredate1 = hiredate[2] + "-" + hiredate[1] + "-" + hiredate[0];
                data.add(hiredate1);

                String[] expirationdate = rs.getString("expirationdate").split("-");
                String expirationdate1 = expirationdate[2] + "-" + expirationdate[1] + "-" + expirationdate[0];
                data.add(expirationdate1);
                
                tblModel.addRow(data);
                
            }
         
            tab.setModel(tblModel);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
