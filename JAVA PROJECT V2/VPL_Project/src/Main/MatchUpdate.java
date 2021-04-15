/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Database_Connector.Connector;
import java.io.FileReader;
import java.sql.*;
import java.util.Vector;
import org.json.simple.parser.*;
import org.json.simple.*;
import java.io.FileReader;

/**
 *
 * @author miste
 */
public class MatchUpdate {

    public void round_update(String url,Connector conn){
        JSONParser parser = new JSONParser();
        try{
            String sql;
            PreparedStatement st;
            Object obj = parser.parse(new FileReader(url));
            JSONObject jsonObject = (JSONObject) obj;
            String temp_s = (String)jsonObject.get("round");
            int round = (Integer.parseInt(temp_s));
            temp_s = (String)jsonObject.get("season");
            int season = (Integer.parseInt(temp_s));
            JSONArray match_list = (JSONArray)jsonObject.get("match");
            for(int i=0;i<match_list.size();i++){
                JSONObject match =(JSONObject)match_list.get(i);
                String result = (String)match.get("result");
                int home_score,away_score;
                String[] score = result.split("-");
                home_score = Integer.parseInt(score[0]);
                away_score = Integer.parseInt(score[1]);
                String match_id = (String)match.get("matchid");
                String home_id = (String)match.get("homeid");
                String away_id = (String)match.get("awayid");
                
                // update match result
                sql = "UPDATE `matchgame` SET`Result`=? WHERE `MatchID`=?";
                st = conn.conn.prepareStatement(sql);
                st.setString(1, result);
                st.setString(2, match_id);
                System.out.println(st.toString());
                st.execute();
                st.close();
                
                // update home team status:
                sql = "SELECT * FROM `clubstatus` WHERE `ClubID`=? AND `Round`=?";
                st = conn.conn.prepareStatement(sql);
                st.setString(1, home_id);
                st.setInt(2, round-1);
                ResultSet rs1 = st.executeQuery();
                while(rs1.next()){
                    sql = "INSERT INTO `clubstatus`(`ClubID`, `Season`, `Round`, `Win`, `Loose`, `Draw`, `Goal`, `GoalLoose`, `Point`) VALUES (?,?,?,?,?,?,?,?,?)";
                    st = conn.conn.prepareStatement(sql);
                    st.setString(1, home_id);
                    st.setInt(2, season);
                    st.setInt(3, round);
                    st.setInt(7, rs1.getInt(7)+home_score);
                    st.setInt(8, rs1.getInt(8)+away_score);
                    if(home_score==away_score){
                        st.setInt(4, rs1.getInt(4));
                        st.setInt(5, rs1.getInt(5));
                        st.setInt(6, rs1.getInt(6)+1);
                        st.setInt(9, rs1.getInt(9)+1);
                    }else if(home_score>away_score){
                        st.setInt(4, rs1.getInt(4)+1);
                        st.setInt(5, rs1.getInt(5));
                        st.setInt(6, rs1.getInt(6));
                        st.setInt(9, rs1.getInt(9)+3);
                    }else{
                        st.setInt(4, rs1.getInt(4));
                        st.setInt(5, rs1.getInt(5)+1);
                        st.setInt(6, rs1.getInt(6));
                        st.setInt(9, rs1.getInt(9));
                    }
                    System.out.println(st.toString());
                    st.execute();
                    st.close();
                }
                
                // update away team status:
                sql = "SELECT * FROM `clubstatus` WHERE `ClubID`=? AND `Round`=?";
                st = conn.conn.prepareStatement(sql);
                st.setString(1, away_id);
                st.setInt(2, round-1);
                ResultSet rs2 = st.executeQuery();
                while(rs2.next()){
                    sql = "INSERT INTO `clubstatus`(`ClubID`, `Season`, `Round`, `Win`, `Loose`, `Draw`, `Goal`, `GoalLoose`, `Point`) VALUES (?,?,?,?,?,?,?,?,?)";
                    st = conn.conn.prepareStatement(sql);
                    st.setString(1, away_id);
                    st.setInt(2, season);
                    st.setInt(3, round);
                    st.setInt(7, rs2.getInt(7)+away_score);
                    st.setInt(8, rs2.getInt(8)+home_score);
                    if(home_score==away_score){
                        st.setInt(4, rs2.getInt(4));
                        st.setInt(5, rs2.getInt(5));
                        st.setInt(6, rs2.getInt(6)+1);
                        st.setInt(9, rs2.getInt(9)+1);
                    }else if(home_score<away_score){
                        st.setInt(4, rs2.getInt(4)+1);
                        st.setInt(5, rs2.getInt(5));
                        st.setInt(6, rs2.getInt(6));
                        st.setInt(9, rs2.getInt(9)+3);
                    }else{
                        st.setInt(4, rs2.getInt(4));
                        st.setInt(5, rs2.getInt(5)+1);
                        st.setInt(6, rs2.getInt(6));
                        st.setInt(9, rs2.getInt(9));
                    }
                    System.out.println(st.toString());
                    st.execute();
                    st.close();
                }
                
                //update match player:
                JSONArray player_list = (JSONArray)match.get("player");
                for(int z=0;z<player_list.size();z++){
                    JSONObject temp =(JSONObject)player_list.get(z);
                    sql = "INSERT INTO `matchplayer`(`MatchID`, `PlayerID`, `InTime`, `OutTime`) VALUES (?,?,?,?)";
                    st = conn.conn.prepareStatement(sql);
                    st.setString(1, match_id);
                    st.setString(2,(String)temp.get("playerid"));
                    st.setString(3,(String)temp.get("timein"));
                    st.setString(4,(String)temp.get("timeout"));
                    System.out.println(st.toString());
                    st.execute();
                    st.close();
                }
                
                // update goal:
                JSONArray goal_list = (JSONArray)match.get("goal");
                for(int z=0;z<goal_list.size();z++){
                    JSONObject temp =(JSONObject)goal_list.get(z);
                    sql = "INSERT INTO `goal`(`MatchID`, `PlayerID`, `Time`, `OG`) VALUES (?,?,?,'00:00:00')";
                    st = conn.conn.prepareStatement(sql);
                    st.setString(1, match_id);
                    st.setString(2,(String)temp.get("player"));
                    st.setString(3,(String)temp.get("time"));
                    System.out.println(st.toString());
                    st.execute();
                    st.close();
                    
                }
                System.out.println("done");
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
