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
    }
}