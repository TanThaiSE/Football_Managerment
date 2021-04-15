/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_Connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author HP
 */
public class Connector {
    public Connection conn = null;
    public Connector(String url){
        try{
            String dbURL = url;
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(dbURL, username, password);
            if(conn != null){
                System.out.println("Database connected...");
            }
        }catch(SQLException err){
            System.out.println("Can't connect to database...");
        }
    }
    public class FBPlayer{
        ResultSet getByID(String player_id){
            try{
                ResultSet rs = null;
                String sql = "select* from footballplayer where playerid='"+player_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        ResultSet getByClubID(String club_id){
            try{
                ResultSet rs = null;
                String sql = "select* from footballplayer where ClubID='"+club_id+"';";
                System.out.println(sql);
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
    }
    
    public class Club{
        ResultSet getByID(String id){
            try{
                ResultSet rs = null;
                String sql = "select* from club where ClubID='"+id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        public String getNamebyID(String id){
            try{
                ResultSet rs = null;
                String sql = "select * from club where ClubID = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, id);
                System.out.println(st.toString());
                rs = st.executeQuery(sql);
                st.close();
                System.out.println(rs.toString());
                return rs.getString("name");
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
    }
    
    public class ClubMG{
        ResultSet getByClubID(String club_id){
            try{
                ResultSet rs = null;
                String sql = "select* from clubmanager where ClubID='"+club_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        
        ResultSet getByID(String id){
            try{
                ResultSet rs = null;
                String sql = "select* from clubmanager where cmid='"+id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
    }
    
    public class Coach{
        ResultSet getByClubID(String club_id){
            try{
                ResultSet rs = null;
                String sql = "select* from coach where ClubID='"+club_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        
        ResultSet getByID(String id){
            try{
                ResultSet rs = null;
                String sql = "select* from coach where CoachID='"+id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
    }
    
    public class Stadium{
        ResultSet getByID(String stadium_id){
            try{
                ResultSet rs = null;
                String sql = "select* from stadium where StadiumID='"+stadium_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
    }
    
    public class Registration_Club{
        ResultSet getByClubID(String club_id){
            try{
                ResultSet rs = null;
                String sql = "select* from registrationclub where ClubID='"+club_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        ResultSet getBySeason(int season){
            try{
                ResultSet rs = null;
                String sql = "select* from registrationclub where Season="+Integer.toString(season)+";";
                System.out.println(sql);
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
    }
    
    public class Club_Status{
        ResultSet getByClub_Season(String club_id,int season){
            try{
                ResultSet rs = null;
                String sql = "select* from clubstatus where ClubID='"+club_id+"' and Season="+Integer.toString(season)+";";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        ResultSet getBySeason_Round(int season,int round){
            try{
                ResultSet rs = null;
                String sql = "select* from clubstatus where Season="+Integer.toString(season)+" and round="+Integer.toString(round)+";";
                System.out.println(sql);
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
    }
    
    public class Match{
        ResultSet getByID(String match_id){
            try{
                ResultSet rs = null;
                String sql = "select* from matchgame where MatchID='"+match_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        ResultSet getBySeason(int season){
            try{
                ResultSet rs = null;
                String sql = "select* from matchgame where Season="+Integer.toString(season)+";";
                System.out.println(sql);
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
        public ResultSet getByRound(int round){
            try{
                ResultSet rs = null;
                String sql = "select* from matchgame where Round="+Integer.toString(round);
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
        public Integer getLastestRound(){
            try{
                ResultSet rs = null;
                String sql = "SELECT MAX(Round) as vong FROM matchgame WHERE Result IS NOT NULL";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                int re;
                if(rs.next()){
                    re =  Integer.parseInt(rs.getString("vong"));
                }
                else{
                    re =  0;
                }
                System.out.println(re);
                return re;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
    }
    
    public class Match_Player{
        ResultSet getByMatchID(String match_id){
              try{
                ResultSet rs = null;
                String sql = "select* from matchplayer where MatchID='"+match_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }
        }
    }
    
    public class Goal{
        ResultSet getByMatchID(String match_id){
            try{
                ResultSet rs = null;
                String sql = "select* from goal where MatchID='"+match_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
        
        ResultSet getByPlayerID(String player_id){
            try{
                ResultSet rs = null;
                String sql = "select* from goal where PlayerID='"+player_id+"';";
                Statement st = conn.createStatement();
                rs = st.executeQuery(sql);
                st.close();
                return rs;
            }catch(SQLException err){
                System.out.println("Query Executed fail...");
                return null;
            }  
        }
    }
}
