package ru.kata.spring.boot_security.demo.service;

import java.sql.*;

public class DropTableInDataBase {

    static final String Driver = "com.mysql.cj.jdbc.Driver" ;
    static final String url = "jdbc:mysql://localhost:3306/pp_3_1_3" ;
    static final String user = "root"  ;
    static final String pass = "root" ;

    public static void dataBase() {
        try(Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "DROP TABLE IF EXISTS user, role, users_roles \n";
            stmt.executeUpdate(sql);
            System.out.println("Table deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

