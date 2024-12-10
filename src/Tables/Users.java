package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Users {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database"; //DB URL
    private static final String USER = "root"; //DB user
    private static final String PASS = ""; //DB password

    private SimpleIntegerProperty user_id;
    private SimpleStringProperty username;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleStringProperty full_name;
    private SimpleStringProperty contact_number;
    private SimpleStringProperty address;
    private SimpleStringProperty created_at;
    private SimpleStringProperty last_login;
    private SimpleStringProperty status;
    
    // Empty Contructor
    public Users() {}

    // Constructor
    public Users(String username, 
                String email, String password, 
                String full_name,String contact_number, 
                String address, String status) {
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.full_name = new SimpleStringProperty(full_name);
        this.contact_number = new SimpleStringProperty(contact_number);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(getCurrentDate());
        this.last_login = new SimpleStringProperty(getCurrentTime());
    }
    
    // ======= HELPERS =======
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date().getTime());
        return currentDate;
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        String currentTime = dateFormat.format(new Date().getTime());
        return currentTime;
    }
    
    // ======= GETTERS =======
    public int getUser_id() { return user_id.get(); }
    public String getUsername() { return username.get(); }
    public String getFull_name() { return full_name.get(); }
    public String getEmail() { return email.get(); }
    public String getPassword() { return password.get(); }
    public String getContact_number() { return contact_number.get(); }
    public String getAddress() { return address.get(); }
    public String getStatus() { return status.get(); }
    public String getCreated_at() { return created_at.get(); }
    public String getLast_login() { return last_login.get(); }

    // ======= SETTERS =======
    public void setUser_id(int user_id) {this.user_id = (new SimpleIntegerProperty(user_id));}
    public void setUsername(String username) {this.username = new SimpleStringProperty(username);}
    public void setFull_name(String full_name) {this.full_name = new SimpleStringProperty(full_name);}
    public void setEmail(String email) {this.email = new SimpleStringProperty(email);}
    public void setPassword(String password) {this.password = new SimpleStringProperty(password);}
    public void setContact_number(String contact_number) {this.contact_number = new SimpleStringProperty(contact_number);}
    public void setAddress(String address) {this.address = new SimpleStringProperty(address);}
    public void setStatus(String status) {this.status = new SimpleStringProperty(status);}
    public void setCreated_at(Date created_at) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleStringProperty dateString = new SimpleStringProperty(dateFormat.format(created_at));
        this.created_at = dateString;
    }
    public void setLast_login(Date last_login) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleStringProperty timeString = new SimpleStringProperty(timeFormat.format(last_login));
        this.last_login = timeString;        
    }

    public void setStatusActive() {this.status = new SimpleStringProperty("active");}
    public void setStatusInactive() {this.status = new SimpleStringProperty("inactive");}

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", full_name='" + full_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", address='" + address + '\'' +
                ", created_at=" + created_at +
                ", last_login=" + last_login +
                ", status='" + status + '\'' +
                '}';
    }

    // ======= Data Access Methods =======
    public ArrayList<Users> SELECT_ALL_USERS() {
        ArrayList<Users> userList = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM users");

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Users user = new Users();
                
                user.setUser_id(result.getInt("user_id"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password_hash"));
                user.setFull_name(result.getString("full_name"));
                user.setContact_number(result.getString("contact_number"));
                user.setAddress(result.getString("address"));
                user.setStatus(result.getString("status"));
                user.setCreated_at(result.getDate("created_at"));
                user.setLast_login(result.getDate("last_login"));
                
                userList.add(user);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }   

    public Users SELECT_USER(int user_id) { 
        Users user = new Users();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM users WHERE user_id = ?");

            statement.setInt(1, user_id);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                user.setUser_id(result.getInt("user_id"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password_hash"));
                user.setFull_name(result.getString("full_name"));
                user.setContact_number(result.getString("contact_number"));
                user.setAddress(result.getString("address"));
                user.setStatus(result.getString("status"));
                user.setCreated_at(result.getDate("created_at"));
                user.setLast_login(result.getDate("last_login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Users SELECT_USER(String email, String password) {
        Users user = new Users();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM users WHERE email = ? AND password_hash = ?");

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                user.setUser_id(result.getInt("user_id"));
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password_hash"));
                user.setFull_name(result.getString("full_name"));
                user.setContact_number(result.getString("contact_number"));
                user.setAddress(result.getString("address"));
                user.setStatus(result.getString("status"));
                user.setCreated_at(result.getDate("created_at"));
                user.setLast_login(result.getDate("last_login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private int GET_USER_ID_MAX() {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT MAX(user_id) AS max_id FROM users");

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void INSERT_USER(Users user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        String full_name = user.getFull_name();
        String contact_number = user.getContact_number();
        String address = user.getAddress();
        String status = user.getStatus();
        String created_at = user.getCreated_at();
        String last_login = user.getLast_login();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO users (user_id, username, email, password_hash, full_name, contact_number, address, last_login, created_at, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setInt(1, GET_USER_ID_MAX() + 1);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, full_name);
            statement.setString(6, contact_number);
            statement.setString(7, address);
            statement.setString(8, last_login);
            statement.setString(9, created_at);
            statement.setString(10, status);
            statement.executeUpdate();
            System.out.println("User " + username + " has been added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UPDATE_USER(Users user, int user_id) {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        String full_name = user.getFull_name();
        String contact_number = user.getContact_number();
        String address = user.getAddress();
        String status = user.getStatus();
        String created_at = user.getCreated_at();
        String last_login = user.getLast_login();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement(
            "UPDATE users SET username = ?, email = ?, password_hash = ?, full_name = ?, contact_number = ?, address = ?, last_login = ?, created_at = ?, status = ? WHERE user_id = ?"
            );
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, full_name);
            statement.setString(5, contact_number);
            statement.setString(6, address);
            statement.setString(7, last_login);
            statement.setString(8, created_at);
            statement.setString(9, status);
            statement.setInt(10, user_id);
            statement.executeUpdate();
            System.out.println("User " + username + " has been updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE_USER(int user_id) {

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM users WHERE user_id = ?");
            statement.setInt(1, user_id);

            statement.executeUpdate();
            System.out.println("User " + username + " has been deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}