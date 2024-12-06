package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Orders {
    private SimpleIntegerProperty order_id;
    private SimpleIntegerProperty user_id;
    private SimpleIntegerProperty server_id;
    private SimpleIntegerProperty start_date;
    private SimpleIntegerProperty end_date;
    private SimpleDoubleProperty total_amount;
    private SimpleStringProperty status;
    private SimpleIntegerProperty created_at;
    
    public enum Status { 
        ACTIVE,
        PENDING,
        CANCELLED,
        EXPIRED
    }

    public Orders(String order_id, String user_id, String server_id, String start_date, String end_date, String total_amount, String status, String created_at) {
        this.order_id = new SimpleIntegerProperty(Integer.parseInt(order_id));
        this.user_id = new SimpleIntegerProperty(Integer.parseInt(user_id));
        this.server_id = new SimpleIntegerProperty(Integer.parseInt(server_id));
        this.start_date = new SimpleIntegerProperty(Integer.parseInt(start_date));
        this.end_date = new SimpleIntegerProperty(Integer.parseInt(end_date));
        this.total_amount = new SimpleDoubleProperty(Double.parseDouble(total_amount));
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleIntegerProperty(Integer.parseInt(created_at));
    }

    public int getOrder_id() { return order_id.get(); }
    public int getUser_id() { return user_id.get(); }
    public int getServer_id() { return server_id.get(); }
    public int getStart_date() { return start_date.get(); }
    public int getEnd_date() { return end_date.get(); }
    public double getTotal_amount() { return total_amount.get(); }
    public String getStatus() { return status.get(); }
    public int getCreated_at() { return created_at.get(); }

    public void setOrder_id(int order_id) { this.order_id.set(order_id); }
    public void setUser_id(int user_id) { this.user_id.set(user_id); }
    public void setServer_id(int server_id) { this.server_id.set(server_id); }
    public void setStart_date(int start_date) { this.start_date.set(start_date); }
    public void setEnd_date(int end_date) { this.end_date.set(end_date); }
    public void setTotal_amount(double total_amount) { this.total_amount.set(total_amount); }
    public void setStatus(String status) { this.status.set(status); }
    public void setCreated_at(int created_at) { this.created_at.set(created_at); }
}