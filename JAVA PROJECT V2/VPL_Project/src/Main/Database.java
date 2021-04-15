/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author HP
 */
public class Database {
    public void load_season(Vector<String> url,Vector<String> name){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("C:\\Users\\HP\\Desktop\\database\\season.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get("Season");
            for(int i=0;i<arr.size();i++){
                JSONObject temp = (JSONObject)arr.get(i);
                url.add((String)temp.get("url"));
                name.add((String)temp.get("name"));
            }
            name.add("NEW SEASON");
        }catch(Exception e){
            System.out.println("Load season FIle Error");
        }
    };
    
    
    public static String to_num(int n){
        if(n<10){
            return "00"+Integer.toString(n);
        }
        if(n<100){
            return "0"+Integer.toString(n);
        }
        return Integer.toString(n);
    }

    public static Connection create_batabase(String season) {
        try {
            String dbURL = "jdbc:mysql://localhost:3306";
            String username = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            String sql = "create database vpl" + season;
            System.out.println(sql);
            Statement st = conn.createStatement();
            st.execute(sql);
            st.close();
            conn.close();
            System.out.println("create database success");
            dbURL += ("/vpl" + season);
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("database connected...");
            // tao bang stadium:
            sql = "CREATE TABLE `stadium` (\n"
                    + "  `StadiumID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Name` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Address` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `NumberofSeats` int(11) NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `stadium`\n"
                    + "  ADD PRIMARY KEY (`StadiumID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang club
            sql = "CREATE TABLE `club` (\n"
                    + "  `ClubID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Name` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Stadium` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `club`\n"
                    + "  ADD PRIMARY KEY (`ClubID`),\n"
                    + "  ADD FOREIGN KEY (`Stadium`) REFERENCES stadium(`StadiumID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang club_manager:
            sql = "CREATE TABLE `clubmanager` (\n"
                    + "  `CMID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Name` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `ClubID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Nationality` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Birthday` date NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `clubmanager`\n"
                    + "  ADD PRIMARY KEY (`CMID`),\n"
                    + "  ADD FOREIGN KEY (`ClubID`) REFERENCES club(`ClubID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang football player:
            sql = "CREATE TABLE `footballplayer` (\n"
                    + "  `PlayerID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Name` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `ClubID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Nationality` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Birthday` date NOT NULL,\n"
                    + "  `Position` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Salary` float NOT NULL,\n"
                    + "  `Address` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `HireDate` date NOT NULL,\n"
                    + "  `ExpirationDate` date NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `footballplayer`\n"
                    + "  ADD PRIMARY KEY (`PlayerID`),\n"
                    + "  ADD FOREIGN KEY (`ClubID`) REFERENCES club(`ClubID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang coach:
            sql = "CREATE TABLE `coach` (\n"
                    + "  `CoachID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Name` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `ClubID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Nationality` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Birthday` date NOT NULL,\n"
                    + "  `Salary` float NOT NULL,\n"
                    + "  `Address` varchar(70) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `HireDate` date NOT NULL,\n"
                    + "  `ExpirationDate` date NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `coach`\n"
                    + "  ADD PRIMARY KEY (`CoachID`),\n"
                    + "  ADD FOREIGN KEY (`ClubID`) REFERENCES club(`ClubID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang resigned club:
            sql = "CREATE TABLE `registrationclub` (\n"
                    + "  `ClubID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Season` int(11) NOT NULL,\n"
                    + "  `RegistrationDate` date NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `registrationclub`\n"
                    + "  ADD PRIMARY KEY (`ClubID`,`Season`),\n"
                    + "  ADD FOREIGN KEY (`ClubID`) REFERENCES club(`ClubID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang club status:
            sql = "CREATE TABLE `clubstatus` (\n"
                    + "  `ClubID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Season` int(11) NOT NULL,\n"
                    + "  `Round` int(11) NOT NULL,\n"
                    + "  `Win` int(11) NOT NULL,\n"
                    + "  `Loose` int(11) NOT NULL,\n"
                    + "  `Draw` int(11) NOT NULL,\n"
                    + "  `Goal` int(11) NOT NULL,\n"
                    + "  `GoalLoose` int(11) NOT NULL,\n"
                    + "  `Point` int(11) NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `clubstatus`\n"
                    + "  ADD PRIMARY KEY (`ClubID`,`Season`,`Round`),\n"
                    + "  ADD FOREIGN KEY (`ClubID`,`Season`) REFERENCES registrationclub(`ClubID`,`Season`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang match game:
            sql = "CREATE TABLE `matchgame` (\n"
                    + "  `MatchID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `ClubIDHome` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Season` int(11) NOT NULL,\n"
                    + "  `ClubIDAway` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Time` date NOT NULL,\n"
                    + "  `Round` int(11) NOT NULL,\n"
                    + "  `Result` varchar(70) COLLATE utf8mb4_vietnamese_ci\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `matchgame`\n"
                    + "  ADD PRIMARY KEY (`MatchID`),\n"
                    + "  ADD FOREIGN KEY (`ClubIDHome`,`Season`) REFERENCES registrationclub(`ClubID`,`Season`),\n"
                    + "  ADD FOREIGN KEY (`ClubIDAway`,`Season`) REFERENCES registrationclub(`ClubID`,`Season`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang match player:
            sql = "CREATE TABLE `matchplayer` (\n"
                    + "  `MatchID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `PlayerID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `InTime` time NOT NULL,\n"
                    + "  `OutTime` time NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `matchplayer`\n"
                    + "  ADD PRIMARY KEY (`MatchID`,`PlayerID`),\n"
                    + "  ADD FOREIGN KEY (`PlayerID`) REFERENCES footballplayer(`PlayerID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();

            // tao bang goal:
            sql = "CREATE TABLE `goal` (\n"
                    + "  `MatchID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `PlayerID` varchar(6) COLLATE utf8mb4_vietnamese_ci NOT NULL,\n"
                    + "  `Time` time NOT NULL,\n"
                    + "  `OG` time NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            sql = "ALTER TABLE `goal`\n"
                    + "  ADD PRIMARY KEY (`MatchID`,`PlayerID`,`Time`),\n"
                    + "  ADD FOREIGN KEY (`MatchID`,`PlayerID`) REFERENCES matchplayer(`MatchID`,`PlayerID`)";
            st = conn.createStatement();
            st.execute(sql);
            st.close();
            return conn;
        } catch (SQLException err) {
            err.printStackTrace();
            return null;
        }
    }
    
    public String new_database(String url){
        JSONParser parser = new JSONParser();
        Random rd = new Random();
        try {
            Object obj = parser.parse(new FileReader(url));

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;
            
            String name = (String) jsonObject.get("Season");
            System.out.println("Season: "+name);
            Connection conn = create_batabase(name);
//            String result = "jdbc:mysql://localhost:3306/vpl"+name;
//            String season_name = name.replace('_', '-');
            String result = name;
            int season = Integer.parseInt(name.split("_")[0]);
            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray club_list = (JSONArray) jsonObject.get("Club");

            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            /*Iterator<JSONObject> iterator = club_list.iterator();
            while (iterator.hasNext()) {
                String club_name = (String) iterator.get("Name");
                
            }*/
            int player_count = 0;
            int n = club_list.size();
            String[] club_id = new String[n];
            for(int i=0;i<club_list.size();i++){
                System.out.println("add club");
                JSONObject temp = (JSONObject)club_list.get(i);
                String club_name = (String)temp.get("Name");
                String stadium = (String)temp.get("Stadium");
                
                
                // them data stadium:
                String st_id = "STD"+Integer.toString(i);
                String sql = "INSERT INTO `stadium`(`StadiumID`, `Name`, `Address`, `NumberofSeats`) VALUES (?,?,\"abc\",10000)";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, st_id);
                st.setString(2, stadium);
                st.execute();
                st.close();
                
                // them data club:
                String cl_id = "CLUB"+Integer.toString(i);
                sql = "INSERT INTO `club`(`ClubID`, `Name`, `Stadium`) VALUES (?,?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, cl_id);
                st.setString(2, club_name);
                st.setString(3, st_id);
                st.execute();
                st.close();
                club_id[i] = cl_id;
                
                
                // them data resigned club:
                sql = "INSERT INTO `registrationclub`(`ClubID`, `Season`, `RegistrationDate`) VALUES (?,?,NOW())";
                st = conn.prepareStatement(sql);
                st.setString(1, cl_id);
                st.setInt(2, season);
                st.execute();
                st.close();
                
                
                // them club status:
                sql = "INSERT INTO `clubstatus`(`ClubID`, `Season`, `Round`, `Win`, `Loose`, `Draw`, `Goal`, `GoalLoose`, `Point`) VALUES (?,?,0,0,0,0,0,0,0)";
                st = conn.prepareStatement(sql);
                st.setString(1, cl_id);
                st.setInt(2, season);
                st.execute();
                st.close();
                
                
                
                // them data football player
                JSONArray players = (JSONArray) temp.get("Player");
                for(int z=0;z<players.size();z++){
                    JSONObject player = (JSONObject)players.get(z);
                    name = (String)player.get("name");
                    String pos = (String)player.get("position");
                    String dob = (String)player.get("dob");
                    sql = "INSERT INTO `footballplayer`(`PlayerID`, `Name`, `ClubID`, `Nationality`, `Birthday`, `Position`, `Salary`, `Address`, `HireDate`, `ExpirationDate`) VALUES (?,?,?,\"Việt Nam\",?,?,123456,\"abcxyz\",\"2020-01-01\",\"2021-01-01\")";
                    st = conn.prepareStatement(sql);
                    String pl_id = "FP"+to_num(player_count);
                    player_count++;
                    st.setString(1, pl_id);
                    st.setString(2, name);
                    st.setString(3, cl_id);
                    st.setString(4, dob);
                    st.setString(5, pos);
                    st.execute();
                    st.close();
                }
                
                
                // them data hlv:
                sql = "INSERT INTO `coach`(`CoachID`, `Name`, `ClubID`, `Nationality`, `Birthday`, `Salary`, `Address`, `HireDate`, `ExpirationDate`) VALUES (?,?,?,\"Viet Nam\",\"2000-05-04\",123456,\"abc xyz\",\"2020-01-01\",\"2021-01-01\")";
                st = conn.prepareStatement(sql);
                st.setString(1, "Coa"+to_num(i));
                st.setString(2, (String)temp.get("Coach"));
                st.setString(3, cl_id);
                st.execute();
                st.close();
                
                
                // them data club manager:
                sql = "INSERT INTO `clubmanager`(`CMID`, `Name`, `ClubID`, `Nationality`, `Birthday`) VALUES (?,?,?,\"Viet Nam\",\"2000-05-04\")";
                st = conn.prepareStatement(sql);
                st.setString(1, "CM"+to_num(i));
                st.setString(2, (String)temp.get("ClubManager"));
                st.setString(3, cl_id);
                st.execute();
                st.close();
            }
            
            
            // them match game:
            int match_count=0;
            PreparedStatement st;
            String sql;
            JSONArray date_list = (JSONArray) jsonObject.get("Match_Day");
            for(int i=0;i<n-1;i++){
                for(int z=0;z<club_id.length;){
                    String home;
                    String away;
                    int t = rd.nextInt(10);
                    int date;
                    if(t%2==0){
                        home = club_id[z];
                        away = club_id[z+1];
                        date = i*2;
                    }else{
                        home = club_id[z+1];
                        away = club_id[z];
                        date = i*2+1;
                    }
                    z+=2;
                    sql = "INSERT INTO `matchgame`(`MatchID`, `ClubIDHome`, `Season`, `ClubIDAway`, `Time`, `Round`, `Result`) VALUES (?,?,?,?,?,?,NULL)";
                    st = conn.prepareStatement(sql);
                    st.setString(1, "MAT"+to_num(match_count));
                    st.setString(2, home);
                    st.setInt(3, season);
                    st.setString(4, away);
                    st.setString(5, (String)date_list.get(date));
                    st.setInt(6, i+1);
                    st.execute();
                    st.close();
                    st = conn.prepareStatement(sql);
                    st.setString(1, "MAT"+to_num(match_count+(n/2)*(n-1)));
                    st.setString(2, away);
                    st.setInt(3, season);
                    st.setString(4, home);
                    st.setString(5, (String)date_list.get(date+(n-1)*2));
                    st.setInt(6, i+n);
                    st.execute();
                    st.close();
                    match_count++;
                }
                String temp = club_id[1];
                for(int z=1;z<n-1;z++){
                    club_id[z] = club_id[z+1];
                }
                club_id[n-1]=temp;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
