/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import Database_Connector.Connector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author HP
 */
public class Match_Result {
    public void MR_Start(JComboBox cb,Connector cnn){
//        cb.removeAllItems();
//        int round = cnn.new Match().getLastestRound();
//        System.out.println(round);
//        for(int i=1;i<=round;i++){
//            cb.addItem(Integer.toString(i));
//        }
    try{
        cb.removeAllItems();
        String sql = "SELECT MAX(Round) as vong FROM matchgame WHERE Result IS NOT NULL";
        Statement st = cnn.conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int round=0;
        if(rs.next()){
            round = Integer.parseInt(rs.getString("vong"));
        }
        cb.removeAllItems();
        System.out.println(round);
        for(int i=1;i<=round;i++){
           cb.addItem(Integer.toString(i));
        }
    }catch(Exception e){
        System.out.println("Error Match Result");
    }
    
    }
    public void Round_Choose(JTable tab,int round,Connector cnn){
        try{
            tab.removeAll();
            String header[] = {"Match ID", "Home Team", "Away Team","Result","Round"};
            DefaultTableModel tblModel = new DefaultTableModel(header, 0);
            String sql = "SELECT * FROM matchgame where Round = ?";
            PreparedStatement st = cnn.conn.prepareStatement(sql);
            st.setInt(1, round);
            ResultSet rs = st.executeQuery();
            Vector data = null;
            tblModel.setRowCount(0);
            while(rs.next()){
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
                while(rs1.next()){
                    if(temp1.compareTo(rs1.getString("clubid"))==0){
                        temp1 = rs1.getString("name");
                    }else{
                        temp2 = rs1.getString("name");
                    }
                }
                data.add(temp1);
                data.add(temp2);
                data.add(rs.getString("result"));
                data.add(rs.getString("round"));
                tblModel.addRow(data);
                st1.close();
                rs1.close();
                
            }
            tab.setModel(tblModel);
            rs.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public void Match_Detail(Connector conn,String match_id,String home,String away,JTextArea ht,JTextArea at,JTextArea scer){
        String sql = "SELECT fp.`name` as Name, cl.`name` as CLUB, mp.`intime` as TimeIn\n" +
"FROM (`matchplayer` as mp JOIN `footballplayer` as fp on mp.`playerid` = fp.`playerid`) join `club` as cl on fp.`clubid` = cl.`clubid`\n" +
"WHERE mp.`matchid` = ? \n" +
"ORDER BY TimeIn";
        try{
            PreparedStatement st = conn.conn.prepareStatement(sql);
            st.setString(1, match_id);
            ResultSet rs = st.executeQuery();
            String htxi="";
            String atxi = "";
            int h_count=0;
            int a_count=0;
            while(rs.next()){
                if(rs.getString("club").compareTo(home)==0){
                    htxi+=rs.getString("name");
                    if(h_count<11){
                        htxi+="\n";
                        h_count++;
                    }
                    else{
                        htxi+="    (SUB)\n";
                    }
                }else{
                    atxi+=rs.getString("name");
                    if(a_count<11){
                        atxi+="\n";
                        a_count++;
                    }
                    else{
                        atxi+="    (SUB)\n";
                    }
                }
            }
            ht.setText(htxi);
            at.setText(atxi);
            st.close();
            rs.close();
            sql = "SELECT fp.`name` as Name, cl.`name` as CLUB\n" +
"FROM (`goal` as goal JOIN `footballplayer` as fp on goal.`playerid` = fp.`playerid`) join `club` as cl on fp.`clubid` = cl.`clubid`\n" +
"WHERE goal.`MatchID` = ? \n" +
"ORDER BY `goal`.`Time`";
            PreparedStatement st1 = conn.conn.prepareStatement(sql);
            st1.setString(1, match_id);
            ResultSet rs1 = st1.executeQuery();
            String goal = "";
            while (rs1.next()) {                
                goal+=(rs1.getString("name")+"  ("+rs1.getString("club")+")\n");
            }
            scer.setText(goal);
            st1.close();
            rs1.close();
        }catch(Exception e){
            System.out.println("Main.Match_Result.Match_Detail()");
            System.out.println(e.toString());
        }
        
        
    }   
}
