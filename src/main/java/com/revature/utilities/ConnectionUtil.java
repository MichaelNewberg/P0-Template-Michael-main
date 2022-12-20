package com.revature.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//This connection Util class contains the method needed to create a connection to the database. 
//It requires a properties file with the necessary data to connect to your database in order to work.

public class ConnectionUtil {
    
    public static Connection createConnection(){
        try {
            //creates a Java object to represent the properties
            Properties prop = new Properties();
            //loads properties from the properties file into the Java object
            prop.load(new FileInputStream("src/main/resources/db-properties.properties"));
            //use property values in object above to provide Java with needed info to connect to the database.
            return DriverManager.getConnection(
                prop.getProperty("URL"), 
                prop.getProperty("USERNAME"), 
                prop.getProperty("PASSWORD")
            );
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        // use this to confirm you set up your environment variables correctly
        //done correctly if you get something like "org.postgresql.jdbc.PgConnection@"
        System.out.println(createConnection());
    }

}
