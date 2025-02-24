/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.polyit.edusys.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nothi
 */
public class XJbdc {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=EduSys;encrypt=true;trustServerCertificate=true";
    static String user = "sa";
    static String pass = "songlong";
    static{
        try {
            Class.forName(driver);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException{
        Connection conn = DriverManager.getConnection(dbUrl, user, pass);
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt =  conn.prepareCall(sql);//PROC
        }else{
            stmt = conn.prepareStatement(sql);//SQL
        }
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i+1, args[i]);
        }
        return stmt;
    }
    public static ResultSet query(String sql, Object... args) throws SQLException{
        PreparedStatement stmt = XJbdc.getStmt(sql, args);
        return stmt.executeQuery();
    }
    public static Object value(String sql, Object... args){
        try {
            ResultSet rs = XJbdc.query(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static int update(String sql, Object... args){
        try {
            PreparedStatement stmt = XJbdc.getStmt(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
