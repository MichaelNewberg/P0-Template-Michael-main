package com.revature.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

public class UserDao {
    
    public User getUserByUsername(String username){
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from users where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public User createUser(UsernamePasswordAuthentication registerRequest){
        //Make use of a Try-with-resource block. Provided starting in Java 8, closes resources we open without explicitly writing code to do so.
        //This is needed for the connection object to be created, because a database can only handle so many connections at a time.
        //1. Need to write the SQL query(make a string to represet the query)
        //2. Provide relevent info to query (add username and password)
        //3. Execute query
        //4. Handle response
        try (Connection connection = ConnectionUtil.createConnection()) {
            //1.
            String sql = "insert into users values (default,?,?)";//?'s are placeholders for info provided
            //provide method with sql to be executed, and because returning a User object need to get the generated id from the user that is created
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //2.
            //Keep in mind that indexing for JDBC starts at 1 not 0.
            ps.setString(1, registerRequest.getUsername());//Username first due to table order
            ps.setString(2, registerRequest.getPassword());
            //3.
            ps.execute();//execute statement
            ResultSet rs = ps.getGeneratedKeys();//get generated id and save in result set
            //4.
            User newUser = new User();
            rs.next();//anytime you need info from a result set you need to call this once.
            int newId = rs.getInt("id");
            newUser.setId(newId);
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            return newUser;

        } catch (SQLException e) {
            System.out.println(e.getMessage());//log this for later use
            return new User();//better to return an empty User with no info than a null
        }  
    }

    // public User getSLI(){
    //     File latencyFile = new File("C:/Users/Michael/Desktop/P0-Template-Michael-main/logs/Latency.log");
    //     Scanner sc = new Scanner(latencyFile);
    //     double data = 0;
    //     while (sc.hasNext()){
    //         double x = Double.parseDouble(sc.nextLine());
    //         //System.out.println(sc.nextLine());
    //         data += x;  
    //     }
    //     sc.close();
    // //     //return "HTTP request success rate is: " + variable + "%, and the average latency for each request is: " + variable2 "ms.";
    // }

    // public static void main(String[] args) throws FileNotFoundException {
    //     File requestFile = new File("C:/Users/Michael/Desktop/P0-Template-Michael-main/logs/httpRequest.log");
    //     Scanner sc = new Scanner(requestFile);
    //     int latencyTotal = 0;
    //     while (sc.hasNext()){
    //         int y = Integer.parseInt(sc.nextLine());
    //         System.out.println(sc.nextLine());
    //         latencyTotal += y;
            
    //     }
    //     sc.close();
    //    System.out.println(latencyTotal);
    // }
}
