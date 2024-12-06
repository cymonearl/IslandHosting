package Tables;

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
    private SimpleIntegerProperty created_at;

    public enum Status {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE
    }

    public Servers(int server_id, String name, 
                    String hardware_type, int ram_gb, 
                    int storage_gb, double price_per_month, 
                    String specs, String server_location, 
                    String status, int created_at) {
        this.server_id = new SimpleIntegerProperty(server_id);
        this.name = new SimpleStringProperty(name);
        this.hardware_type = new SimpleStringProperty(hardware_type);
        this.ram_gb = new SimpleIntegerProperty(ram_gb);
        this.storage_gb = new SimpleIntegerProperty(storage_gb);
        this.price_per_month = new SimpleDoubleProperty(price_per_month);
        this.specs = new SimpleStringProperty(specs);
        this.server_location = new SimpleStringProperty(server_location);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleIntegerProperty(created_at);
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
    public int getCreated_at() {return created_at.get();}

    public void setServer_id(int server_id) {this.server_id.set(server_id);}
    public void setName(String name) {this.name.set(name);}
    public void setHardware_type(String hardware_type) {this.hardware_type.set(hardware_type);}
    public void setRam_gb(int ram_gb) {this.ram_gb.set(ram_gb);}
    public void setStorage_gb(int storage_gb) {this.storage_gb.set(storage_gb);}
    public void setPrice_per_month(double price_per_month) {this.price_per_month.set(price_per_month);}
    public void setSpecs(String specs) {this.specs.set(specs);}
    public void setServer_location(String server_location) {this.server_location.set(server_location);}
    public void setStatus(String status) {this.status.set(status);}
    public void setCreated_at(int created_at) {this.created_at.set(created_at);}
}