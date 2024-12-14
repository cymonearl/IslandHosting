package Tables;

import java.util.ArrayList;
import java.sql.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Audit_Logs {
    private SimpleIntegerProperty log_id;
    private SimpleIntegerProperty user_id;
    private SimpleStringProperty action_type;
    private SimpleStringProperty description;
    private SimpleStringProperty ip_address;

    public Audit_Logs(int log_id, int user_id, 
                    String action_type, String description, 
                    String ip_address) {
        this.log_id = new SimpleIntegerProperty(log_id);
        this.user_id = new SimpleIntegerProperty(user_id);
        this.action_type = new SimpleStringProperty(action_type);
        this.description = new SimpleStringProperty(description);
        this.ip_address = new SimpleStringProperty(ip_address);
    }

    public int getLog_id() { return log_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public String getAction_type() { return action_type.get(); }
    public String getDescription() { return description.get(); }
    public String getIp_address() { return ip_address.get(); }

    public void setLog_id(int log_id) { this.log_id.set(log_id); }
    public void setUser_id(int user_id) { this.user_id.set(user_id); }
    public void setAction_type(String action_type) { this.action_type.set(action_type); }
    public void setDescription(String description) { this.description.set(description); }
    public void setIp_address(String ip_address) { this.ip_address.set(ip_address); }

    @Override
    public String toString() {
        return "Audit_Logs{" +
                "log_id=" + log_id +
                ", user_id=" + user_id +
                ", action_type='" + action_type + '\'' +
                ", description='" + description + '\'' +
                ", ip_address='" + ip_address + '\'' +
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
                Audit_Logs audit_log = new Audit_Logs(
                        result.getInt("log_id"),
                        result.getInt("user_id"),
                        result.getString("action_type"),
                        result.getString("description"),
                        result.getString("ip_address")
                );
                audit_logs.add(audit_log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audit_logs;
    }
    public Audit_Logs SELECT_AUDIT_LOG_ID() {
        Audit_Logs audit_log = null;
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM audit_logs WHERE log_id = ?");
            statement.setInt(1, log_id.get());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                audit_log = new Audit_Logs(
                        result.getInt("log_id"),
                        result.getInt("user_id"),
                        result.getString("action_type"),
                        result.getString("description"),
                        result.getString("ip_address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audit_log;
    }
    public void INSERT_AUDIT_LOG() {}
    public void UPDATE_AUDIT_LOG() {}
    public void DELETE_AUDIT_LOG() {}
}