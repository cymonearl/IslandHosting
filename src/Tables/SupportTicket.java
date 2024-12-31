package Tables;

import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SupportTicket {
    private SimpleIntegerProperty ticket_id;
    private SimpleIntegerProperty user_id;
    private SimpleStringProperty subject;
    private SimpleStringProperty message;
    private SimpleStringProperty status;
    private SimpleStringProperty created_at;
    private SimpleStringProperty updated_at;
    
    public SupportTicket() {}
    
    public SupportTicket(int user_id, String subject, String message, String status) {
        this.user_id = new SimpleIntegerProperty(user_id);
        this.subject = new SimpleStringProperty(subject);
        this.message = new SimpleStringProperty(message);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(getCurrentDate());
        this.updated_at = new SimpleStringProperty(getCurrentDate());
        newTicket_id();
    }

    public int getTicket_id() { return ticket_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public String getSubject() { return subject.get(); }
    public String getMessage() { return message.get(); }
    public String getStatus() { return status.get(); }
    public String getCreated_at() { return created_at.get(); }
    public String getUpdated_at() { return updated_at.get(); }
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database";
    private static final String USER = "root";
    private static final String PASS = "";
    private static int ticket_id_count = 0;
    
    private static String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void newTicket_id() {
        ticket_id_count += 1;
        this.ticket_id = new SimpleIntegerProperty(ticket_id_count);
    }

    public void setTicket_id(int ticket_id) { this.ticket_id = new SimpleIntegerProperty(ticket_id); }
    public void setUser_id(int user_id) { this.user_id = new SimpleIntegerProperty(user_id); }
    public void setSubject(String subject) { this.subject = new SimpleStringProperty(subject); }
    public void setMessage(String message) { this.message = new SimpleStringProperty(message); }
    public void setStatus(String status) { this.status = new SimpleStringProperty(status); }
    public void setCreated_at(String created_at) { this.created_at = new SimpleStringProperty(created_at); }
    public void setUpdated_at(String updated_at) { this.updated_at = new SimpleStringProperty(updated_at); }

    public String toString() {
        return "SupportTicket{" +
                "ticket_id=" + ticket_id +
                ", user_id=" + user_id +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public ArrayList<SupportTicket> SELECT_ALL_SUPPORT_TICKETS() {
        ArrayList<SupportTicket> supportTickets = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM support_tickets");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                SupportTicket supportTicket = new SupportTicket();
                supportTicket.setTicket_id(result.getInt("ticket_id"));
                supportTicket.setUser_id(result.getInt("user_id"));
                supportTicket.setSubject(result.getString("subject"));
                supportTicket.setMessage(result.getString("message"));
                supportTicket.setStatus(result.getString("status"));
                supportTicket.setCreated_at(result.getString("created_at"));
                supportTicket.setUpdated_at(result.getString("updated_at"));
                supportTickets.add(supportTicket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supportTickets;
    }

    public void INSERT_SUPPORT_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO support_tickets (user_id, subject, message, status, created_at, updated_at) VALUES (?,?,?,?,?,?)");
            statement.setInt(1, supportTicket.getUser_id());
            statement.setString(2, supportTicket.getSubject());
            statement.setString(3, supportTicket.getMessage());
            statement.setString(4, supportTicket.getStatus());
            statement.setString(5, supportTicket.getCreated_at());
            statement.setString(6, supportTicket.getUpdated_at());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UPDATE_SUPPORT_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE support_tickets SET user_id = ?, subject = ?, message = ?, status = ?, created_at = ?, updated_at = ? WHERE ticket_id = ?");
            statement.setInt(1, supportTicket.getUser_id());
            statement.setString(2, supportTicket.getSubject());
            statement.setString(3, supportTicket.getMessage());
            statement.setString(4, supportTicket.getStatus());
            statement.setString(5, supportTicket.getCreated_at());
            statement.setString(6, supportTicket.getUpdated_at());
            statement.setInt(7, supportTicket.getTicket_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE_SUPPORT_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM support_tickets WHERE ticket_id = ?");
            statement.setInt(1, supportTicket.getTicket_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}