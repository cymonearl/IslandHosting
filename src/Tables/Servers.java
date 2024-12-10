package Tables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Servers {
    private SimpleIntegerProperty server_id;
    private SimpleStringProperty name;
    private SimpleStringProperty hardware_type;
    private SimpleIntegerProperty ram_gb;
    private SimpleIntegerProperty storage_gb;
    private SimpleDoubleProperty price_per_month;
    private SimpleStringProperty server_location;
    private SimpleStringProperty status;
    private SimpleStringProperty specs;
    private SimpleStringProperty created_at;

    public enum Status {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE
    }

    public Servers( int server_id, String name, 
                    String hardware_type, int ram_gb, 
                    int storage_gb, double price_per_month, 
                    String specs, String server_location, 
                    String status) {
        this.server_id = new SimpleIntegerProperty(server_id);
        this.name = new SimpleStringProperty(name);
        this.hardware_type = new SimpleStringProperty(hardware_type);
        this.ram_gb = new SimpleIntegerProperty(ram_gb);
        this.storage_gb = new SimpleIntegerProperty(storage_gb);
        this.price_per_month = new SimpleDoubleProperty(price_per_month);
        this.specs = new SimpleStringProperty(specs);
        this.server_location = new SimpleStringProperty(server_location);
        this.status = new SimpleStringProperty(status);
        SimpleDateFormat created_at = new SimpleDateFormat("yyyy-MM-dd");
        created_at = getCurrentDate();
        this.created_at = new SimpleStringProperty(created_at.format(new Date()));
    }

    public int getServer_id() { return server_id.get();}
    public String getName() {return name.get();}
    public String getHardware_type() {return hardware_type.get();}
    public int getRam_gb() {return ram_gb.get();}
    public int getStorage_gb() {return storage_gb.get();}
    public double getPrice_per_month() {return price_per_month.get();}
    public String getSpecs() {return specs.get();}
    public String getServer_location() {return server_location.get();}
    public String getStatus() {return status.get();}
    public String getCreated_at() { return created_at.get(); }

    public void setServer_id(int server_id) {this.server_id.set(server_id);}
    public void setName(String name) {this.name.set(name);}
    public void setHardware_type(String hardware_type) {this.hardware_type.set(hardware_type);}
    public void setRam_gb(int ram_gb) {this.ram_gb.set(ram_gb);}
    public void setStorage_gb(int storage_gb) {this.storage_gb.set(storage_gb);}
    public void setPrice_per_month(double price_per_month) {this.price_per_month.set(price_per_month);}
    public void setSpecs(String specs) {this.specs.set(specs);}
    public void setServer_location(String server_location) {this.server_location.set(server_location);}
    public void setStatus(String status) {this.status.set(status);}
    private SimpleDateFormat getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        System.out.println(currentDate);

        return dateFormat;
    }

    @Override
    public String toString() {
        return "Servers{" + 
                "server_id=" + server_id + 
                ", name='" + name + '\'' + 
                ", hardware_type='" + hardware_type + '\'' + 
                ", ram_gb=" + ram_gb + 
                ", storage_gb=" + storage_gb + 
                ", price_per_month=" + price_per_month + 
                ", specs='" + specs + '\'' + 
                ", server_location='" + server_location + '\'' + 
                ", status='" + status + '\'' + 
                ", created_at=" + created_at + 
                '}';
    }

    // ======= Data Access Methods =======
    public ArrayList<Servers> SELECT_ALL_SERVERS() {}
    public Servers SELECT_SERVER(int server_id) {}
    public Servers SELECT_SERVER(String name) {}
    public void INSERT_SERVER(String name, String hardware_type, int ram_gb, int storage_gb, double price_per_month, String specs, String server_location) {}
    public void UPDATE_SERVER(int server_id, String name, String hardware_type, int ram_gb, int storage_gb, double price_per_month, String specs, String server_location) {}
    public void DELETE_SERVER(int server_id) {}
}