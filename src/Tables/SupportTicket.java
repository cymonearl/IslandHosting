package Tables;

import java.sql.*;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SupportTicket {
    private SimpleIntegerProperty ticket_id;
    private SimpleIntegerProperty user_id;
    private SimpleIntegerProperty server_id;
    private SimpleStringProperty subject;
    private SimpleStringProperty description;
    private SimpleStringProperty status;
    private SimpleStringProperty priority;
    private SimpleStringProperty created_at;
    private SimpleStringProperty resolved_at;

    public SupportTicket() {}

    public SupportTicket(int user_id, String subject, String description, String status, String priority) {
        this.ticket_id = new SimpleIntegerProperty(getNextTicketId()); // Automatically assign the next ticket ID
        this.user_id = new SimpleIntegerProperty(user_id);
        this.subject = new SimpleStringProperty(subject);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
        this.created_at = new SimpleStringProperty(getCurrentDate());
        this.resolved_at = new SimpleStringProperty("");
    }

    public SupportTicket(int user_id, int server_id, String subject, String description, String status, String priority) {
        this.ticket_id = new SimpleIntegerProperty(getNextTicketId()); // Automatically assign the next ticket ID
        this.user_id = new SimpleIntegerProperty(user_id);
        this.server_id = new SimpleIntegerProperty(server_id);
        this.subject = new SimpleStringProperty(subject);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
        this.created_at = new SimpleStringProperty(getCurrentDate());
        this.resolved_at = new SimpleStringProperty("");
    }

    public int getTicket_id() { return ticket_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public int getServer_id() { return server_id.get(); }
    public String getSubject() { return subject.get(); }
    public String getDescription() { return description.get(); }
    public String getStatus() { return status.get(); }
    public String getPriority() { return priority.get(); }
    public String getCreated_at() { return created_at.get(); }
    public String getResolved_at() { return resolved_at.get(); }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database";
    private static final String USER = "root";
    private static final String PASS = "";

    private static String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void setTicket_id(int ticket_id) { this.ticket_id = new SimpleIntegerProperty(ticket_id); }
    public void setUser_id(int user_id) { this.user_id = new SimpleIntegerProperty(user_id); }
    public void setServer_id(int server_id) { this.server_id = new SimpleIntegerProperty(server_id); }
    public void setSubject(String subject) { this.subject = new SimpleStringProperty(subject); }
    public void setDescription(String description) { this.description = new SimpleStringProperty(description); }
    public void setStatus(String status) { this.status = new SimpleStringProperty(status); }
    public void setPriority(String priority) { this.priority = new SimpleStringProperty(priority); }
    public void setCreated_at(String created_at) { this.created_at = new SimpleStringProperty(created_at); }
    public void setResolved_at(String resolved_at) { this.resolved_at = new SimpleStringProperty(resolved_at); }

    public String toString() {
        return "SupportTicket{" +
                "ticket_id=" + ticket_id +
                ", user_id=" + user_id +
                ", server_id=" + server_id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", resolved_at='" + resolved_at + '\'' +
                ", resolved_at='" + resolved_at + '\'' +
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
                supportTicket.setServer_id(result.getInt("server_id"));
                supportTicket.setSubject(result.getString("subject"));
                supportTicket.setDescription(result.getString("description"));
                supportTicket.setStatus(result.getString("status"));
                supportTicket.setPriority(result.getString("priority"));
                supportTicket.setCreated_at(result.getString("created_at"));
                supportTicket.setResolved_at(result.getString("resolved_at"));
                supportTickets.add(supportTicket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supportTickets;
    }

    public boolean INSERT_SUPPORT_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO support_tickets (ticket_id, user_id, server_id, subject, description, status, priority) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, supportTicket.getTicket_id());
            statement.setInt(2, supportTicket.getUser_id());
            statement.setInt(3, supportTicket.getServer_id());
            statement.setString(4, supportTicket.getSubject());
            statement.setString(5, supportTicket.getDescription());
            statement.setString(6, supportTicket.getStatus());
            statement.setString(7, supportTicket.getPriority());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean UPDATE_SUPPORT_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE support_tickets SET user_id = ?, server_id = ?, subject = ?, description = ?, status = ?, priority = ? WHERE ticket_id = ?");
            statement.setInt(1, supportTicket.getUser_id());
            statement.setInt(2, supportTicket.getServer_id());
            statement.setString(3, supportTicket.getSubject());
            statement.setString(4, supportTicket.getDescription());
            statement.setString(5, supportTicket.getStatus());
            statement.setString(6, supportTicket.getPriority());
            statement.setInt(7, supportTicket.getTicket_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean DELETE_SUPPORT_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM support_tickets WHERE ticket_id = ?");
            statement.setInt(1, supportTicket.getTicket_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void RESOLVE_TICKET(SupportTicket supportTicket) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE support_tickets SET resolved_at = ?, status = 'resolved' WHERE ticket_id = ?");
            statement.setString(1, getCurrentDate());
            statement.setInt(2, supportTicket.getTicket_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNextTicketId() {
        int nextId = 1; // Default to 1 if the table is empty or no gaps exist

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT ticket_id FROM support_tickets ORDER BY ticket_id ASC");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int currentId = result.getInt("ticket_id");
                if (currentId != nextId) {
                    return nextId;
                }
                nextId++;
            }
            result.close();
            statement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }
}