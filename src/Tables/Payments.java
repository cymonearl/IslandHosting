package Tables;

import java.util.ArrayList;
import java.sql.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Payments {
    private SimpleIntegerProperty payment_id;
    private SimpleIntegerProperty user_id;
    private SimpleStringProperty amount;
    private SimpleStringProperty payment_method;
    private SimpleIntegerProperty transaction_id;
    private SimpleStringProperty payment_status;
    private SimpleStringProperty payment_date;

    public Payments() {}

    public Payments(int user_id, String amount, String payment_method, String payment_status) {
        this.user_id = new SimpleIntegerProperty(user_id);
        this.amount = new SimpleStringProperty(amount);
        this.payment_method = new SimpleStringProperty(payment_method);
        newTransaction_id();
        this.payment_status = new SimpleStringProperty(payment_status);
        this.payment_date = new SimpleStringProperty(getCurrentDate());
        newPayment_id();
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database";
    private static final String USER = "root";
    private static final String PASS = "";
    private static int payment_id_count = 0;
    private static int transaction_id_count = 1000;

    private void newPayment_id() {
        payment_id_count += 1;
        this.payment_id = new SimpleIntegerProperty(payment_id_count);
    }

    private void newTransaction_id() {
        transaction_id_count += 1;
        this.transaction_id = new SimpleIntegerProperty(transaction_id_count);
    }

    public int getPayment_id() { return payment_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public String getAmount() { return amount.get(); }
    public String getPayment_method() { return payment_method.get(); }
    public int getTransaction_id() { return transaction_id.get(); }
    public String getPayment_status() { return payment_status.get(); }
    public String getPayment_date() { return payment_date.get(); }

    private static String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void setPayment_id(int payment_id) { this.payment_id = new SimpleIntegerProperty(payment_id); }
    public void setUser_id(int user_id) { this.user_id = new SimpleIntegerProperty(user_id); }
    public void setAmount(String amount) { this.amount = new SimpleStringProperty(amount); }
    public void setPayment_method(String payment_method) { this.payment_method = new SimpleStringProperty(payment_method); }
    public void setTransaction_id(int transaction_id) { this.transaction_id = new SimpleIntegerProperty(transaction_id); }
    public void setPayment_status(String payment_status) { this.payment_status = new SimpleStringProperty(payment_status); }
    public void setPayment_date(String payment_date) { this.payment_date = new SimpleStringProperty(payment_date); }

    public String toString() {
        return "Payments{" +
                "payment_id=" + payment_id +
                ", user_id=" + user_id +
                ", amount='" + amount + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", payment_date='" + payment_date + '\'' +
                '}';
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
                payment.setUser_id(result.getInt("user_id"));
                payment.setAmount(result.getString("amount"));
                payment.setPayment_method(result.getString("payment_method"));
                payment.setTransaction_id(result.getInt("transaction_id"));
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
            PreparedStatement statement = connect.prepareStatement("INSERT INTO payments (user_id, amount, payment_method, transaction_id, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, payment.getUser_id());
            statement.setString(2, payment.getAmount());
            statement.setString(3, payment.getPayment_method());
            statement.setInt(4, payment.getTransaction_id());
            statement.setString(5, payment.getPayment_status());
            statement.setString(6, payment.getPayment_date());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UPDATE_PAYMENT(Payments payment) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE payments SET user_id = ?, amount = ?, payment_method = ?, transaction_id = ?, payment_status = ?, payment_date = ? WHERE payment_id = ?");
            statement.setInt(1, payment.getUser_id());
            statement.setString(2, payment.getAmount());
            statement.setString(3, payment.getPayment_method());
            statement.setInt(4, payment.getTransaction_id());
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
}