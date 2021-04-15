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
 * @author HP
 */
public class Leader_Board {
    public void LB_Start(Connector conn,JComboBox op){
        try{
               String sql = "SELECT MAX(`round`) as vong FROM `clubstatus`";
               PreparedStatement st = conn.conn.prepareStatement(sql);
               ResultSet rs = st.executeQuery();
               op.removeAllItems();
               if(rs.next()){
                   int temp = Integer.parseInt(rs.getString("vong"));
                   for(int i=0;i<=temp;i++){
                       op.addItem(Integer.toString(i));
                   }
               }else{
                   op.addItem("0");
               }
        }catch(Exception e){
            System.out.println("Database Error...");
        }
    }
    
    public void Load_LB(Connector conn,int round,JTable tab){
        try{
            ResultSet rs1=null;
            String header[] = {"  ", "Club", "Win","Draw","Lose","GF","GA","GD","Points"};
            DefaultTableModel tblModel = new DefaultTableModel(header, 0);
            String sql = "SELECT * FROM `clubstatus` WHERE `Round`=? ORDER BY -`Point`,(`GoalLoose`-`Goal`),`ClubID`";
            // Tạo đối tượng thực thi câu lệnh Select
            PreparedStatement st = conn.conn.prepareStatement(sql);
            st.setInt(1,round);
            // Thực thi
            ResultSet rs = st.executeQuery();
            Vector data = null;
            tblModel.setRowCount(0);
            // Trong khi chưa hết dữ liệu
            int temp = 1;
            while (rs.next()) {
            data = new Vector();
            data.add(temp);
            temp++;
            sql = "SELECT * FROM `club` WHERE `ClubID` =?";
            st = conn.conn.prepareStatement(sql);
            st.setString(1, rs.getString("ClubID"));
            rs1 = st.executeQuery();
            if(rs1.next()){
                data.add(rs1.getString("name"));
            }
            data.add(rs.getString("win"));
            data.add(rs.getString("Draw"));
            data.add(rs.getString("Loose"));
            data.add(rs.getString("Goal"));
            data.add(rs.getString("GoalLoose"));
            data.add(Integer.parseInt(rs.getString("Goal"))-Integer.parseInt(rs.getString("GoalLoose")));
            data.add(rs.getString("Point"));
            // Thêm một dòng vào table model
            tblModel.addRow(data);
            }
            tab.setModel(tblModel); // Thêm dữ liệu vào table
            st.close();
            rs.close();
            rs1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
