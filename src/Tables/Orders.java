package Tables;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Orders {
    private SimpleIntegerProperty order_id;
    private SimpleIntegerProperty user_id;
    private SimpleIntegerProperty server_id;
    private SimpleStringProperty start_date;
    private SimpleStringProperty end_date;
    private SimpleDoubleProperty total_amount;
    private SimpleStringProperty status;
    private SimpleStringProperty created_at;
    
    public enum Status { 
        ACTIVE,
        PENDING,
        CANCELLED,
        EXPIRED
    }

    public Orders() {}

    public Orders(String user_id,
                String server_id, String total_amount, 
                String status) {
        this.user_id = new SimpleIntegerProperty(Integer.parseInt(user_id));
        this.server_id = new SimpleIntegerProperty(Integer.parseInt(server_id));
        this.start_date = new SimpleStringProperty(getCurrentDate());
        this.end_date = new SimpleStringProperty(getNext12Months(start_date));
        this.total_amount = new SimpleDoubleProperty(Double.parseDouble(total_amount));
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(getCurrentTime());
        this.order_id = new SimpleIntegerProperty(getNextOrderId());
        newOrder_id();
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date().getTime());
        return currentTime;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date().getTime());
        return currentDate;
    }

    private String getNext12Months(SimpleStringProperty date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date.getValue()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 1);
        return dateFormat.format(calendar.getTime());
    }

    public int getOrder_id() { return order_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public int getServer_id() { return server_id.get(); }
    public String getStart_date() { return start_date.get(); }
    public String getEnd_date() { return end_date.get(); }
    public double getTotal_amount() { return total_amount.get(); }
    public String getStatus() { return status.get(); }
    public String getCreated_at() { return created_at.get(); }

    public void newOrder_id() { this.order_id = new SimpleIntegerProperty(getNextOrderId()); }
    public void setOrder_id(int order_id) { this.order_id = new SimpleIntegerProperty(order_id); }
    public void setUser_id(int user_id) { this.user_id = new SimpleIntegerProperty(user_id); }
    public void setServer_id(int server_id) { this.server_id = new SimpleIntegerProperty(server_id); }
    public void setStart_date(String start_date) { this.start_date = new SimpleStringProperty(start_date); }
    public void setEnd_date(String end_date) { this.end_date = new SimpleStringProperty(end_date); }
    public void setTotal_amount(double total_amount) { this.total_amount = new SimpleDoubleProperty(total_amount); }
    public void setStatus(String status) { this.status = new SimpleStringProperty(status); }
    public void setCreated_at(String created_at) { this.created_at = new SimpleStringProperty(created_at); }


    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database"; //DB URL
    private static final String USER = "root"; //DB user
    private static final String PASS = ""; //DB password

    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", user_id=" + user_id +
                ", server_id=" + server_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", total_amount=" + total_amount +
                ", status=" + status +
                ", created_at=" + created_at +
                '}';
    }

    public ArrayList<Orders> SELECT_ALL_ORDERS() {
        ArrayList<Orders> orders = new ArrayList<>();
        Orders order = null;
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM orders");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                order = new Orders();

                order.setOrder_id(result.getInt("order_id"));
                order.setUser_id(result.getInt("user_id"));
                order.setServer_id(result.getInt("server_id"));
                order.setStart_date(result.getString("start_date"));
                order.setEnd_date(result.getString("end_date"));
                order.setTotal_amount(result.getDouble("total_amount"));
                order.setStatus(result.getString("status"));
                order.setCreated_at(result.getString("created_at"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    public ArrayList<Orders> SELECT_ORDER_ID(int user_id) {
        ArrayList<Orders> orders = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM orders WHERE user_id = ?");
            statement.setInt(1, user_id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Orders order = new Orders();

                order.setOrder_id(result.getInt("order_id"));
                order.setUser_id(result.getInt("user_id"));
                order.setServer_id(result.getInt("server_id"));
                order.setStart_date(result.getString("start_date"));
                order.setEnd_date(result.getString("end_date"));
                order.setTotal_amount(result.getDouble("total_amount"));
                order.setStatus(result.getString("status"));
                order.setCreated_at(result.getString("created_at"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    public int getNextOrderId() {
        int nextId = 1; // Default to 1 if the table is empty or no gaps exist
    
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT order_id FROM orders ORDER BY order_id ASC");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int currentId = result.getInt("order_id");
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

    public void INSERT_ORDER(Orders order) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO orders (order_id ,user_id, server_id, start_date, end_date, total_amount, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, order.getOrder_id());
            statement.setInt(2, order.getUser_id());
            statement.setInt(3, order.getServer_id());
            statement.setString(4, order.getStart_date());
            statement.setString(5, order.getEnd_date());
            statement.setDouble(6, order.getTotal_amount());
            statement.setString(7, order.getStatus());
            statement.setString(8, order.getCreated_at());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UPDATE_ORDER(Orders order) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("UPDATE orders SET user_id = ?, server_id = ?, start_date = ?, end_date = ?, total_amount = ?, status = ?, created_at = ? WHERE order_id = ?");
            statement.setInt(1, order.getUser_id());
            statement.setInt(2, order.getServer_id());
            statement.setString(3, order.getStart_date());
            statement.setString(4, order.getEnd_date());
            statement.setDouble(5, order.getTotal_amount());
            statement.setString(6, order.getStatus());
            statement.setString(7, order.getCreated_at());
            statement.setInt(8, order.getOrder_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DELETE_ORDER(Orders order) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM orders WHERE order_id = ?");
            statement.setInt(1, order.getOrder_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }            
    }   

    public void LOG_ORDER_MADE(Orders order) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            CallableStatement statement = connect.prepareCall("{call logOrderMade(?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, new Audit_Logs().getNextLogId());
            statement.setInt(2, order.getUser_id());
            statement.setInt(3, order.getOrder_id());
            statement.setInt(4, order.getServer_id());
            statement.setString(5, order.getStart_date());
            statement.setString(6, order.getEnd_date());
            statement.setDouble(7, order.getTotal_amount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void COMPLETE_ORDER(ArrayList<Orders> orders) {
        for (Orders order : orders) {
            try {
                Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement statement = connect.prepareStatement("UPDATE orders SET status = ? WHERE status = ? AND order_id = ?");
                statement.setString(1, "Completed");
                statement.setString(2, "Pending");
                statement.setInt(3, order.getOrder_id());
                statement.executeUpdate();
            }   catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}