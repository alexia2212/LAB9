package com.example.lab9_base.Dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class BaseDao {
    public Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        String user = "root";
        String pass = "123456";
        String url = "jdbc:mysql://localhost:3306/lab9";
        return DriverManager.getConnection(url, user, pass);
    }
}

