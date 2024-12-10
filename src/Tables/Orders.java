package Tables;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Orders {
    private SimpleIntegerProperty order_id;
    private SimpleIntegerProperty user_id;
    private SimpleStringProperty server_id;
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

    public Orders(String order_id, String user_id, 
                String server_id, String start_date, 
                String end_date, String total_amount, 
                String status, String created_at) {
        this.order_id = new SimpleIntegerProperty(Integer.parseInt(order_id));
        this.user_id = new SimpleIntegerProperty(Integer.parseInt(user_id));
        this.server_id = new SimpleStringProperty(server_id);
        this.start_date = new SimpleStringProperty(start_date);
        this.end_date = new SimpleStringProperty(end_date);
        this.total_amount = new SimpleDoubleProperty(Double.parseDouble(total_amount));
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(created_at);
    }

    public int getOrder_id() { return order_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public String getServer_id() { return server_id.get(); }
    public String getStart_date() { return start_date.get(); }
    public String getEnd_date() { return end_date.get(); }
    public double getTotal_amount() { return total_amount.get(); }
    public String getStatus() { return status.get(); }
    public String getCreated_at() { return created_at.get(); }

    public void setOrder_id(int order_id) { this.order_id.set(order_id); }
    public void setUser_id(int user_id) { this.user_id.set(user_id); }
    public void setServer_id(String server_id) { this.server_id.set(server_id); }
    public void setStart_date(String start_date) { this.start_date.set(start_date); }
    public void setEnd_date(String end_date) { this.end_date.set(end_date); }
    public void setTotal_amount(double total_amount) { this.total_amount.set(total_amount); }
    public void setStatus(String status) { this.status.set(status); }
    public void setCreated_at(String created_at) { this.created_at.set(created_at); }

    public ArrayList<Orders> SELECT_ALL_ORDERS() {}
    public Servers SELECT_ORDER_ID(int user_id) {}
    public void INSERT_ORDER() {}
    public void UPDATE_ORDER() {}
    public void DELETE_ORDER() {}
}