

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Tables.Users;
public class sqlDEMO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/island_hosting_database";
        String user = "root";
        String password = "";

        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connect.prepareStatement("show columns from users");
            ResultSet result = statement.executeQuery();

            for (Users userll : new Users().SELECT_ALL_USERS()) {
                System.out.println(userll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Users users = new Users();
        System.out.println(users.getCurrentTime());
    }
}