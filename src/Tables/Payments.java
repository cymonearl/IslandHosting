package Tables;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.sql.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Payments {
    private SimpleIntegerProperty payment_id;
    private SimpleIntegerProperty order_id;
    private SimpleStringProperty amount;
    private SimpleStringProperty payment_method;
    private SimpleStringProperty transaction_id;
    private SimpleStringProperty payment_status;
    private SimpleStringProperty payment_date;

    public Payments() {}

    public Payments(int order_id , String amount, String payment_method, String payment_status) {
        this.order_id = new SimpleIntegerProperty(order_id );
        this.amount = new SimpleStringProperty(amount);
        this.payment_method = new SimpleStringProperty(payment_method);
        this.payment_id = new SimpleIntegerProperty(getNextPaymentId());
        this.payment_status = new SimpleStringProperty(payment_status);
        this.payment_date = new SimpleStringProperty(getCurrentDate());
        this.transaction_id = new SimpleStringProperty(generateAlphanumericTransactionId());
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database";
    private static final String USER = "root";
    private static final String PASS = "";

    public int getPayment_id() { return payment_id.get(); }
    public int getOrder_id() { return order_id.get(); }
    public String getAmount() { return amount.get(); }
    public String getPayment_method() { return payment_method.get(); }
    public String getTransaction_id() { return transaction_id.get(); }
    public String getPayment_status() { return payment_status.get(); }
    public String getPayment_date() { return payment_date.get(); }

    private static String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        return sdf.format(date);
    }

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateAlphanumericTransactionId() {
        StringBuilder transactionId = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC.length());
            transactionId.append(ALPHANUMERIC.charAt(index));
        }
        return transactionId.toString();
    }

    public void setPayment_id(int payment_id) { this.payment_id = new SimpleIntegerProperty(payment_id); }
    public void setUser_id(int order_id ) { this.order_id = new SimpleIntegerProperty(order_id ); }
    public void setAmount(String amount) { this.amount = new SimpleStringProperty(amount); }
    public void setPayment_method(String payment_method) { this.payment_method = new SimpleStringProperty(payment_method); }
    public void setTransaction_id(String transaction_id) { this.transaction_id = new SimpleStringProperty(transaction_id); }
    public void setPayment_status(String payment_status) { this.payment_status = new SimpleStringProperty(payment_status); }
    public void setPayment_date(String payment_date) { this.payment_date = new SimpleStringProperty(payment_date); }

    public String toString() {
        return "Payments{" +
                "payment_id=" + payment_id +
                ", order_id =" + order_id +
                ", amount='" + amount + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", payment_date='" + payment_date + '\'' +
                '}';
    }

    private int getNextPaymentId() {
        int nextId = 1; // Default to 1 if the table is empty or no gaps exist
    
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT payment_id FROM payments ORDER BY payment_id ASC");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int currentId = result.getInt("payment_id");
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

    public ArrayList<Payments> SELECT_ALL_PAYMENTS() {
        ArrayList<Payments> payments = new ArrayList<>(); 
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM payments");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Payments payment = new Payments();
                payment.setPayment_id(result.getInt("payment_id"));
                payment.setUser_id(result.getInt("order_id"));
                payment.setAmount(result.getString("amount"));
                payment.setPayment_method(result.getString("payment_method"));
                payment.setTransaction_id(result.getString("transaction_id"));
                payment.setPayment_status(result.getString("payment_status"));
                payment.setPayment_date(result.getString("payment_date"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public void INSERT_PAYMENT(Payments payment) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO payments (order_id, amount, payment_method, transaction_id, payment_status, payment_date, payment_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, payment.getOrder_id());
            statement.setString(2, payment.getAmount());
            statement.setString(3, payment.getPayment_method());
            statement.setString(4, payment.getTransaction_id());
            statement.setString(5, payment.getPayment_status());
            statement.setString(6, payment.getPayment_date());
            statement.setInt(7, payment.getPayment_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UPDATE_PAYMENT(Payments payment) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE payments SET order_id = ?, amount = ?, payment_method = ?, transaction_id = ?, payment_status = ?, payment_date = ? WHERE payment_id = ?");
            statement.setInt(1, payment.getOrder_id());
            statement.setString(2, payment.getAmount());
            statement.setString(3, payment.getPayment_method());
            statement.setString(4, payment.getTransaction_id());
            statement.setString(5, payment.getPayment_status());
            statement.setString(6, payment.getPayment_date());
            statement.setInt(7, payment.getPayment_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE_PAYMENT(Payments payment) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM payments WHERE payment_id = ?");
            statement.setInt(1, payment.getPayment_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void PAY_ORDER(Payments payment) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            CallableStatement statement = connect.prepareCall("{call payOrder(?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, payment.getPayment_id());
            statement.setInt(2, payment.getOrder_id());
            statement.setDouble(3, Double.parseDouble(payment.getAmount()));
            statement.setString(4, payment.getPayment_method());
            statement.setString(5, payment.getTransaction_id());
            statement.setString(6, "completed");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}