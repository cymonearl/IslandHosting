package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.sql.*;
import java.util.ArrayList;

public class Views {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database";
    private static final String USER = "root";
    private static final String PASS = "";

    // Class for OrdersWithUserAndServer view
    public static class OrderView {
        private SimpleIntegerProperty orderId;
        private SimpleIntegerProperty userId;
        private SimpleStringProperty userName;
        private SimpleIntegerProperty serverId;
        private SimpleStringProperty serverName;
        private SimpleStringProperty startDate; // Changed to String
        private SimpleStringProperty endDate;   // Changed to String
        private SimpleDoubleProperty totalAmount;
        private SimpleStringProperty status;
        private SimpleStringProperty createdAt; // Changed to String

        public OrderView() {}

        // Constructor with String dates
        public OrderView(int orderId, int userId, String userName, int serverId, String serverName,
                         String startDate, String endDate, double totalAmount, String status, String createdAt) {
            this.orderId = new SimpleIntegerProperty(orderId);
            this.userId = new SimpleIntegerProperty(userId);
            this.userName = new SimpleStringProperty(userName);
            this.serverId = new SimpleIntegerProperty(serverId);
            this.serverName = new SimpleStringProperty(serverName);
            this.startDate = new SimpleStringProperty(startDate);
            this.endDate = new SimpleStringProperty(endDate);
            this.totalAmount = new SimpleDoubleProperty(totalAmount);
            this.status = new SimpleStringProperty(status);
            this.createdAt = new SimpleStringProperty(createdAt);
        }

        // Setters
        public void setOrderId(int orderId) { this.orderId = new SimpleIntegerProperty(orderId); }
        public void setUserId(int userId) { this.userId = new SimpleIntegerProperty(userId); }
        public void setUserName(String userName) { this.userName = new SimpleStringProperty(userName); }
        public void setServerId(int serverId) { this.serverId = new SimpleIntegerProperty(serverId); }
        public void setServerName(String serverName) { this.serverName = new SimpleStringProperty(serverName); }
        public void setStartDate(String startDate) { this.startDate = new SimpleStringProperty(startDate); }
        public void setEndDate(String endDate) { this.endDate = new SimpleStringProperty(endDate); }
        public void setTotalAmount(double totalAmount) { this.totalAmount = new SimpleDoubleProperty(totalAmount); }
        public void setStatus(String status) { this.status = new SimpleStringProperty(status); }
        public void setCreatedAt(String createdAt) { this.createdAt = new SimpleStringProperty(createdAt); }

        // Getters
        public int getOrderId() { return orderId.get(); }
        public int getUserId() { return userId.get(); }
        public String getUserName() { return userName.get(); }
        public int getServerId() { return serverId.get(); }
        public String getServerName() { return serverName.get(); }
        public String getStartDate() { return startDate.get(); } // Returns String
        public String getEndDate() { return endDate.get(); }     // Returns String
        public double getTotalAmount() { return totalAmount.get(); }
        public String getStatus() { return status.get(); }
        public String getCreatedAt() { return createdAt.get(); } // Returns String

        public String toString() {
            return "Order ID: " + orderId.get() +
                    ", User ID: " + userId.get() +
                    ", User Name: " + userName.get() +
                    ", Server ID: " + serverId.get() +
                    ", Server Name: " + serverName.get() +
                    ", Start Date: " + startDate.get() +
                    ", End Date: " + endDate.get() +
                    ", Total Amount: " + totalAmount.get() +
                    ", Status: " + status.get() +
                    ", Created At: " + createdAt.get();
        }

        public ArrayList<OrderView> getOrderView() {
            ArrayList<OrderView> orderViews = new ArrayList<>();
            try {
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM OrdersWithUserAndServer");

                while (resultSet.next()) {
                    OrderView orderView = new OrderView();
                    orderView.setOrderId(resultSet.getInt("order_id"));
                    orderView.setUserId(resultSet.getInt("user_id"));
                    orderView.setUserName(resultSet.getString("user_name"));
                    orderView.setServerId(resultSet.getInt("server_id"));
                    orderView.setServerName(resultSet.getString("server_name"));
                    orderView.setStartDate(resultSet.getString("start_date"));
                    orderView.setEndDate(resultSet.getString("end_date"));
                    orderView.setTotalAmount(resultSet.getDouble("total_amount"));
                    orderView.setStatus(resultSet.getString("status"));
                    orderView.setCreatedAt(resultSet.getString("created_at"));

                    orderViews.add(orderView);
                }
            return orderViews; 
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    // Class for SupportTicketsWithUserAndServer view
    public static class SupportTicketView {
        private  SimpleIntegerProperty ticketId;
        private  SimpleIntegerProperty userId;
        private  SimpleStringProperty userName;
        private  SimpleIntegerProperty serverId;
        private  SimpleStringProperty serverName;
        private  SimpleStringProperty subject;
        private  SimpleStringProperty description;
        private  SimpleStringProperty priority;
        private  SimpleStringProperty status;
        private  SimpleStringProperty createdAt; // Changed to String
        private  SimpleStringProperty resolvedAt; // Changed to String

        public SupportTicketView() {}

        // Constructor with String dates
        public SupportTicketView(int ticketId, int userId, String userName, int serverId, String serverName,
                                 String subject, String description, String priority, String status,
                                 String createdAt, String resolvedAt) {
            this.ticketId = new SimpleIntegerProperty(ticketId);
            this.userId = new SimpleIntegerProperty(userId);
            this.userName = new SimpleStringProperty(userName);
            this.serverId = new SimpleIntegerProperty(serverId);
            this.serverName = new SimpleStringProperty(serverName);
            this.subject = new SimpleStringProperty(subject);
            this.description = new SimpleStringProperty(description);
            this.priority = new SimpleStringProperty(priority);
            this.status = new SimpleStringProperty(status);
            this.createdAt = new SimpleStringProperty(createdAt);
            this.resolvedAt = new SimpleStringProperty(resolvedAt);
        }

        // Getters
        public int getTicketId() { return ticketId.get(); }
        public int getUserId() { return userId.get(); }
        public String getUserName() { return userName.get(); }
        public int getServerId() { return serverId.get(); }
        public String getServerName() { return serverName.get(); }
        public String getSubject() { return subject.get(); }
        public String getDescription() { return description.get(); }
        public String getPriority() { return priority.get(); }
        public String getStatus() { return status.get(); }
        public String getCreatedAt() { return createdAt.get(); } // Returns String
        public String getResolvedAt() { return resolvedAt.get(); } // Returns String

        // Property Getters (for JavaFX TableView)
        public SimpleIntegerProperty ticketIdProperty() { return ticketId; }
        public SimpleIntegerProperty userIdProperty() { return userId; }
        public SimpleStringProperty userNameProperty() { return userName; }
        public SimpleIntegerProperty serverIdProperty() { return serverId; }
        public SimpleStringProperty serverNameProperty() { return serverName; }
        public SimpleStringProperty subjectProperty() { return subject; }
        public SimpleStringProperty descriptionProperty() { return description; }
        public SimpleStringProperty priorityProperty() { return priority; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleStringProperty createdAtProperty() { return createdAt; } // Returns StringProperty
        public SimpleStringProperty resolvedAtProperty() { return resolvedAt; } // Returns StringProperty

        // Setters
        public void setTicketId(int ticketId) { this.ticketId = new SimpleIntegerProperty(ticketId); }
        public void setUserId(int userId) { this.userId = new SimpleIntegerProperty(userId); }
        public void setUserName(String userName) { this.userName = new SimpleStringProperty(userName); }
        public void setServerId(int serverId) { this.serverId = new SimpleIntegerProperty(serverId); }
        public void setServerName(String serverName) { this.serverName = new SimpleStringProperty(serverName); }
        public void setSubject(String subject) { this.subject = new SimpleStringProperty(subject); }
        public void setDescription(String description) { this.description = new SimpleStringProperty(description); }
        public void setPriority(String priority) { this.priority = new SimpleStringProperty(priority); }
        public void setStatus(String status) { this.status = new SimpleStringProperty(status); }
        public void setCreatedAt(String createdAt) { this.createdAt = new SimpleStringProperty(createdAt); } // Sets String
        public void setResolvedAt(String resolvedAt) { this.resolvedAt = new SimpleStringProperty(resolvedAt); } // Sets String

        public String toString() {
            return "Ticket ID: " + ticketId.get() +
                    ", User ID: " + userId.get() +
                    ", User Name: " + userName.get() +
                    ", Server ID: " + serverId.get() +
                    ", Server Name: " + serverName.get() +
                    ", Subject: " + subject.get() +
                    ", Description: " + description.get() +
                    ", Priority: " + priority.get() +
                    ", Status: " + status.get() +
                    ", Created At: " + createdAt.get() +
                    ", Resolved At: " + resolvedAt.get();
        }

        public ArrayList<SupportTicketView> getSupportTicketsWithUserAndServer() {
            ArrayList<SupportTicketView> supportTickets = new ArrayList<>(); // Create an ArrayList of SupportTicketView objects
            try {
                Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM SupportTicketsWithUserAndServer");
                while (resultSet.next()) {
                    int ticketId = resultSet.getInt("ticket_id");
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_name");
                    int serverId = resultSet.getInt("server_id");
                    String serverName = resultSet.getString("server_name");
                    String subject = resultSet.getString("subject");
                    String description = resultSet.getString("description");
                    String priority = resultSet.getString("priority");
                    String status = resultSet.getString("status");
                    String createdAt = resultSet.getString("created_at");
                    String resolvedAt = resultSet.getString("resolved_at");
                    SupportTicketView supportTicketView = new SupportTicketView(ticketId, userId, userName, serverId, serverName, subject, description, priority, status, createdAt, resolvedAt);
                    supportTickets.add(supportTicketView);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return supportTickets;
        }
    }    

    // Class for PaymentsWithUserServerAndOrder view
    public static class PaymentView {
        private  SimpleIntegerProperty paymentId;
        private  SimpleIntegerProperty orderId;
        private  SimpleIntegerProperty userId;
        private  SimpleStringProperty userName;
        private  SimpleIntegerProperty serverId;
        private  SimpleStringProperty serverName;
        private  SimpleDoubleProperty amount;
        private  SimpleStringProperty paymentMethod;
        private  SimpleStringProperty transactionId;
        private  SimpleStringProperty paymentStatus;
        private  SimpleStringProperty paymentDate; // Changed to String

        public PaymentView() {}

        // Constructor with String date
        public PaymentView(int paymentId, int orderId, int userId, String userName, int serverId, String serverName,
                           double amount, String paymentMethod, String transactionId, String paymentStatus, String paymentDate) {
            this.paymentId = new SimpleIntegerProperty(paymentId);
            this.orderId = new SimpleIntegerProperty(orderId);
            this.userId = new SimpleIntegerProperty(userId);
            this.userName = new SimpleStringProperty(userName);
            this.serverId = new SimpleIntegerProperty(serverId);
            this.serverName = new SimpleStringProperty(serverName);
            this.amount = new SimpleDoubleProperty(amount);
            this.paymentMethod = new SimpleStringProperty(paymentMethod);
            this.transactionId = new SimpleStringProperty(transactionId);
            this.paymentStatus = new SimpleStringProperty(paymentStatus);
            this.paymentDate = new SimpleStringProperty(paymentDate);
        }

        // Getters
        public int getPaymentId() { return paymentId.get(); }
        public int getOrderId() { return orderId.get(); }
        public int getUserId() { return userId.get(); }
        public String getUserName() { return userName.get(); }
        public int getServerId() { return serverId.get(); }
        public String getServerName() { return serverName.get(); }
        public double getAmount() { return amount.get(); }
        public String getPaymentMethod() { return paymentMethod.get(); }
        public String getTransactionId() { return transactionId.get(); }
        public String getPaymentStatus() { return paymentStatus.get(); }
        public String getPaymentDate() { return paymentDate.get(); } // Returns String

        // Setters
        public void setPaymentId(int paymentId) { this.paymentId = new SimpleIntegerProperty(paymentId); }
        public void setOrderId(int orderId) { this.orderId = new SimpleIntegerProperty(orderId); }
        public void setUserId(int userId) { this.userId = new SimpleIntegerProperty(userId); }
        public void setUserName(String userName) { this.userName = new SimpleStringProperty(userName); }
        public void setServerId(int serverId) { this.serverId = new SimpleIntegerProperty(serverId); }
        public void setServerName(String serverName) { this.serverName = new SimpleStringProperty(serverName); }
        public void setAmount(double amount) { this.amount = new SimpleDoubleProperty(amount); }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = new SimpleStringProperty(paymentMethod); }
        public void setTransactionId(String transactionId) { this.transactionId = new SimpleStringProperty(transactionId); }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = new SimpleStringProperty(paymentStatus); }
        public void setPaymentDate(String paymentDate) { this.paymentDate = new SimpleStringProperty(paymentDate); }

        // Property Getters (for JavaFX TableView)
        public SimpleIntegerProperty paymentIdProperty() { return paymentId; }
        public SimpleIntegerProperty orderIdProperty() { return orderId; }
        public SimpleIntegerProperty userIdProperty() { return userId; }
        public SimpleStringProperty userNameProperty() { return userName; }
        public SimpleIntegerProperty serverIdProperty() { return serverId; }
        public SimpleStringProperty serverNameProperty() { return serverName; }
        public SimpleDoubleProperty amountProperty() { return amount; }
        public SimpleStringProperty paymentMethodProperty() { return paymentMethod; }
        public SimpleStringProperty transactionIdProperty() { return transactionId; }
        public SimpleStringProperty paymentStatusProperty() { return paymentStatus; }
        public SimpleStringProperty paymentDateProperty() { return paymentDate; } // Returns StringProperty

        public ArrayList<PaymentView> getPaymentsWithUserServerAndOrder() { 
            ArrayList<PaymentView> payments = new ArrayList<>(); // Create an ArrayList of PaymentView objects
            try {
                Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM PaymentsWithUserServerAndOrder");
                while (resultSet.next()) {
                    int paymentId = resultSet.getInt("payment_id");
                    int orderId = resultSet.getInt("order_id");
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_name");
                    int serverId = resultSet.getInt("server_id");
                    String serverName = resultSet.getString("server_name");
                    double amount = resultSet.getDouble("amount");
                    String paymentMethod = resultSet.getString("payment_method");
                    String transactionId = resultSet.getString("transaction_id");
                    String paymentStatus = resultSet.getString("payment_status");
                    String paymentDate = resultSet.getString("payment_date"); // Changed to String
                    PaymentView paymentView = new PaymentView(paymentId, orderId, userId, userName, serverId, serverName, amount, paymentMethod, transactionId, paymentStatus, paymentDate);
                    payments.add(paymentView);
                }   
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return payments;
        } // Method to retrieve PaymentsWithUserServerAndOrder
    }

    public static class Audit_LogsView {
        private  SimpleIntegerProperty logId;
        private  SimpleIntegerProperty userId;
        private  SimpleStringProperty userName;
        private  SimpleStringProperty action;
        private  SimpleStringProperty description;
        private  SimpleStringProperty timestamp;
        private  SimpleStringProperty ipAddress;

        public Audit_LogsView() {}

        public Audit_LogsView(int logId, int userId, String userName, String action, String description, String timestamp, String ipAddress) {
            this.logId = new SimpleIntegerProperty(logId);
            this.userId = new SimpleIntegerProperty(userId);
            this.userName = new SimpleStringProperty(userName);
            this.action = new SimpleStringProperty(action);
            this.description = new SimpleStringProperty(description);
            this.timestamp = new SimpleStringProperty(timestamp);
            this.ipAddress = new SimpleStringProperty(ipAddress);
        }

        public int getLogId() { return logId.get(); }
        public int getUserId() { return userId.get(); }
        public String getUserName() { return userName.get(); }
        public String getAction() { return action.get(); }
        public String getDescription() { return description.get(); }
        public String getTimestamp() { return timestamp.get(); }
        public String getIpAddress() { return ipAddress.get(); }

        public SimpleIntegerProperty logIdProperty() { return logId; }
        public SimpleIntegerProperty userIdProperty() { return userId; }
        public SimpleStringProperty userNameProperty() { return userName; }
        public SimpleStringProperty actionProperty() { return action; }
        public SimpleStringProperty descriptionProperty() { return description; }
        public SimpleStringProperty timestampProperty() { return timestamp; }
        public SimpleStringProperty ipAddressProperty() { return ipAddress; }

        public void setLogId(int logId) { this.logId = new SimpleIntegerProperty(logId); }
        public void setUserId(int userId) { this.userId = new SimpleIntegerProperty(userId); }
        public void setUserName(String userName) { this.userName = new SimpleStringProperty(userName); }
        public void setAction(String action) { this.action = new SimpleStringProperty(action); }
        public void setDescription(String description) { this.description = new SimpleStringProperty(description); }
        public void setTimestamp(String timestamp) { this.timestamp = new SimpleStringProperty(timestamp); }
        public void setIpAddress(String ipAddress) { this.ipAddress = new SimpleStringProperty(ipAddress); }

        public String toString() {
            return "Log ID: " + logId.get() +
                    ", User ID: " + userId.get() +
                    ", User Name: " + userName.get() +
                    ", Action: " + action.get() +
                    ", Description: " + description.get() +
                    ", Timestamp: " + timestamp.get() +
                    ", IP Address: " + ipAddress.get();
        }

        public ArrayList<Audit_LogsView> getAudit_LogsWithUser() {
            ArrayList<Audit_LogsView> audit_LogsList = new ArrayList<>();
            try {
                Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Audit_LogsWithUser");
                while (resultSet.next()) {
                    int logId = resultSet.getInt("log_id");
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_name"); 
                    String action = resultSet.getString("action_type");
                    String description = resultSet.getString("description");
                    String createdAt = resultSet.getString("timestamp");
                    String ipAddress = resultSet.getString("ip_address");
                    Audit_LogsView audit_LogsView = new Audit_LogsView(logId, userId, userName, action, description, createdAt, ipAddress);
                    audit_LogsList.add(audit_LogsView);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return audit_LogsList;
        }
    }
}