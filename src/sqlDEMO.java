

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sqlDEMO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/island_hosting_database";
        String user = "root";
        String password = "";

        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            String sql = "show tables";
            PreparedStatement statement = connect.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) 
                System.out.println(result.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
}