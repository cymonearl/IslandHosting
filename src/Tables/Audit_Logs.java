package Tables;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Audit_Logs {
    private SimpleIntegerProperty log_id;
    private SimpleIntegerProperty user_id;
    private SimpleStringProperty action_type;
    private SimpleStringProperty description;
    private SimpleStringProperty ip_address;
    private SimpleStringProperty timestamp;

    public Audit_Logs() {}

    public Audit_Logs(int user_id, 
                    String action_type, String description, 
                    String ip_address) {
        this.user_id = new SimpleIntegerProperty(user_id);
        this.action_type = new SimpleStringProperty(action_type);
        this.description = new SimpleStringProperty(description);
        this.ip_address = new SimpleStringProperty(ip_address);
        this.timestamp = new SimpleStringProperty(getCurrentTime());
        newLog_id();
    }

    public int getLog_id() { return log_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public String getAction_type() { return action_type.get(); }
    public String getDescription() { return description.get(); }
    public String getIp_address() { return ip_address.get(); }
    public String getTimestamp() { return timestamp.get(); }

    public void newLog_id() { this.log_id = new SimpleIntegerProperty(GET_LOG_ID_MAX() + 1); }
    public void setLog_id(int log_id) { this.log_id = new SimpleIntegerProperty(log_id); }
    public void setUser_id(int user_id) { this.user_id = new SimpleIntegerProperty(user_id); }
    public void setAction_type(String action_type) { this.action_type = new SimpleStringProperty(action_type); }
    public void setDescription(String description) { this.description = new SimpleStringProperty(description); }
    public void setIp_address(String ip_address) { this.ip_address = new SimpleStringProperty(ip_address); }
    public void setCreated_at(String timestamp) { this.timestamp = new SimpleStringProperty(timestamp); }
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        String currentTime = dateFormat.format(new Date().getTime());
        return currentTime;
    }

    @Override
    public String toString() {
        return "Audit_Logs{" +
                "log_id=" + log_id +
                ", user_id=" + user_id +
                ", action_type='" + action_type + '\'' +
                ", description='" + description + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database"; //DB URL
    private static final String USER = "root"; //DB user
    private static final String PASS = ""; //DB password

    public ArrayList<Audit_Logs> SELECT_ALL_AUDIT_LOGS() {
        ArrayList<Audit_Logs> audit_logs = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM audit_logs");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Audit_Logs audit_log = new Audit_Logs();
                audit_log.setLog_id(result.getInt("log_id"));
                audit_log.setUser_id(result.getInt("user_id"));
                audit_log.setAction_type(result.getString("action_type"));
                audit_log.setDescription(result.getString("description"));
                audit_log.setIp_address(result.getString("ip_address"));
                audit_log.setCreated_at(result.getString("timestamp"));
                audit_logs.add(audit_log);
            }
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return audit_logs;
    }
    public Audit_Logs SELECT_AUDIT_LOG_ID() {
        Audit_Logs audit_log = new Audit_Logs();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM audit_logs WHERE log_id = ?");
            statement.setInt(1, log_id.get());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                audit_log.setLog_id(result.getInt("log_id"));
                audit_log.setUser_id(result.getInt("user_id"));
                audit_log.setAction_type(result.getString("action_type"));
                audit_log.setDescription(result.getString("description"));
                audit_log.setIp_address(result.getString("ip_address"));
                audit_log.setCreated_at(result.getString("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audit_log;
    }
    public int GET_LOG_ID_MAX() {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT MAX(log_id) FROM audit_logs");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                System.out.println(result.getInt(1) + 1);
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void INSERT_AUDIT_LOG(Audit_Logs audit_log) {
        try {
            System.out.println(audit_log);
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO audit_logs (log_id, user_id, action_type, description, ip_address, timestamp) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, audit_log.getLog_id());
            statement.setInt(2, audit_log.getUser_id());
            statement.setString(3, audit_log.getAction_type());
            statement.setString(4, audit_log.getDescription());
            statement.setString(5, audit_log.getIp_address());
            statement.setString(6, audit_log.getTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UPDATE_AUDIT_LOG(Audit_Logs audit_log) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE audit_logs SET user_id = ?, action_type = ?, description = ?, ip_address = ?, timestamp = ? WHERE log_id = ?");
            statement.setInt(1, audit_log.getUser_id());
            statement.setString(2, audit_log.getAction_type());
            statement.setString(3, audit_log.getDescription());
            statement.setString(4, audit_log.getIp_address());
            statement.setString(5, audit_log.getTimestamp());
            statement.setInt(6, audit_log.getLog_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DELETE_AUDIT_LOG(Audit_Logs audit_log) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM audit_logs WHERE log_id = ?");
            statement.setInt(1, audit_log.getLog_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}