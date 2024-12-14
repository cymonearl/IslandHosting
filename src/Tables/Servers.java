package Tables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
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

    public Servers() {}

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
        this.created_at = new SimpleStringProperty(getCurrentDate());
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
    public String getCreated_at() {return created_at.get();}
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date().getTime());
        return currentDate;
    }

    public void setServer_id(int server_id) {this.server_id.set(server_id);}
    public void setName(String name) {this.name.set(name);}
    public void setHardware_type(String hardware_type) {this.hardware_type.set(hardware_type);}
    public void setRam_gb(int ram_gb) {this.ram_gb.set(ram_gb);}
    public void setStorage_gb(int storage_gb) {this.storage_gb.set(storage_gb);}
    public void setPrice_per_month(double price_per_month) {this.price_per_month.set(price_per_month);}
    public void setSpecs(String specs) {this.specs.set(specs);}
    public void setServer_location(String server_location) {this.server_location.set(server_location);}
    public void setStatus(String status) {this.status.set(status);}
    public void setCreated_at(Date created_at) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleStringProperty dateString = new SimpleStringProperty(dateFormat.format(created_at));
        this.created_at = dateString;
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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database"; //DB URL
    private static final String USER = "root"; //DB user
    private static final String PASS = ""; //DB password

    // ======= Data Access Methods =======
    public ArrayList<Servers> SELECT_ALL_SERVERS() {
        ArrayList<Servers> serverList = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM servers");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Servers server = new Servers(
                        result.getInt("server_id"),
                        result.getString("name"),
                        result.getString("hardware_type"),
                        result.getInt("ram_gb"),
                        result.getInt("storage_gb"),
                        result.getDouble("price_per_month"),
                        result.getString("specs"),
                        result.getString("location"),
                        result.getString("status")
                );
                serverList.add(server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serverList;
    }
    public Servers SELECT_SERVER(int server_id) {
        Servers server = null;
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM servers WHERE server_id = ?");
            statement.setInt(1, server_id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                server = new Servers(
                        result.getInt("server_id"),
                        result.getString("name"),
                        result.getString("hardware_type"),
                        result.getInt("ram_gb"),
                        result.getInt("storage_gb"),
                        result.getDouble("price_per_month"),
                        result.getString("specs"),
                        result.getString("location"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }
    public Servers SELECT_SERVER(String name) {
        Servers server = null;
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM servers WHERE name = ?");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                server = new Servers(
                        result.getInt("server_id"),
                        result.getString("name"),
                        result.getString("hardware_type"),
                        result.getInt("ram_gb"),
                        result.getInt("storage_gb"),
                        result.getDouble("price_per_month"),
                        result.getString("specs"),
                        result.getString("location"),
                        result.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }
    public void INSERT_SERVER(Servers server) {
        int server_id = server.getServer_id();
        String name = server.getName();
        String hardware_type = server.getHardware_type();
        int ram_gb = server.getRam_gb();
        int storage_gb = server.getStorage_gb();
        double price_per_month = server.getPrice_per_month();
        String specs = server.getSpecs();
        String server_location = server.getServer_location();
        String status = server.getStatus();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("INSERT INTO servers (server_id, name, hardware_type, ram_gb, storage_gb, price_per_month, specs, location, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, server_id);
            statement.setString(2, name);
            statement.setString(3, hardware_type);
            statement.setInt(4, ram_gb);
            statement.setInt(5, storage_gb);
            statement.setDouble(6, price_per_month);
            statement.setString(7, specs);
            statement.setString(8, server_location);
            statement.setString(9, status);
            statement.executeUpdate();
            System.out.println("Server " + name + " has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UPDATE_SERVER(Servers server) {
        int server_id = server.getServer_id();
        String name = server.getName();
        String hardware_type = server.getHardware_type();
        int ram_gb = server.getRam_gb();
        int storage_gb = server.getStorage_gb();
        double price_per_month = server.getPrice_per_month();
        String specs = server.getSpecs();
        String server_location = server.getServer_location();
        String status = server.getStatus();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement(
                "UPDATE servers SET name = ?, hardware_type = ?, ram_gb = ?, storage_gb = ?, price_per_month = ?, specs = ?, location = ?, status = ? WHERE server_id = ?"  
            );
            statement.setString(1, name);
            statement.setString(2, hardware_type);
            statement.setInt(3, ram_gb);
            statement.setInt(4, storage_gb);
            statement.setDouble(5, price_per_month);
            statement.setString(6, specs);
            statement.setString(7, server_location);
            statement.setString(8, status);
            statement.setInt(9, server_id);
            statement.executeUpdate();
            System.out.println("Server " + name + " has been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DELETE_SERVER(int server_id) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM servers WHERE server_id = ?");
            statement.setInt(1, server_id);
            statement.executeUpdate();
            System.out.println("Server " + server_id + " has been deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}