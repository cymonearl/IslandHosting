package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sqlDEMO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/dbmssample";
        String user = "root";
        String password = "";

        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            // String sql = "SELECT * FROM celebrity";
            // PreparedStatement statement = connect.prepareStatement(sql);
            // ResultSet result = statement.executeQuery();
            // System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
}