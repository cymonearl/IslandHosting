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
    private SimpleStringProperty status;
    private SimpleStringProperty specs;
    private SimpleStringProperty created_at;

    public Servers() {}

    public Servers( String name, 
                    String hardware_type, int ram_gb, 
                    int storage_gb, double price_per_month, 
                    String specs, String server_location, 
                    String status) {
        this.name = new SimpleStringProperty(name);
        this.hardware_type = new SimpleStringProperty(hardware_type);
        this.ram_gb = new SimpleIntegerProperty(ram_gb);
        this.storage_gb = new SimpleIntegerProperty(storage_gb);
        this.price_per_month = new SimpleDoubleProperty(price_per_month);
        this.specs = new SimpleStringProperty(specs);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(getCurrentDate());
        newServer_id();
    }

    public int getServer_id() { return server_id.get();}
    public String getName() {return name.get();}
    public String getHardware_type() {return hardware_type.get();}
    public int getRam_gb() {return ram_gb.get();}
    public int getStorage_gb() {return storage_gb.get();}
    public String getSpecs() {return specs.get();}
    public String getStatus() {return status.get();}
    public String getCreated_at() {return created_at.get();}
    public double getPrice_per_month() {return price_per_month.get();}
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date().getTime());
        return currentDate;
    }

    public void newServer_id() {this.server_id = new SimpleIntegerProperty(getNextServerId());}
    public void setServer_id(int server_id) {this.server_id = new SimpleIntegerProperty(server_id);}
    public void setName(String name) {this.name = new SimpleStringProperty(name);}
    public void setHardware_type(String hardware_type) {this.hardware_type = new SimpleStringProperty(hardware_type);}
    public void setRam_gb(int ram_gb) {this.ram_gb = new SimpleIntegerProperty(ram_gb);}
    public void setStorage_gb(int storage_gb) {this.storage_gb = new SimpleIntegerProperty(storage_gb);}
    public void setSpecs(String specs) {this.specs = new SimpleStringProperty(specs);}
    public void setStatus(String status) {this.status = new SimpleStringProperty(status);}
    public void setCreated_at(String created_at) {this.created_at = new SimpleStringProperty(created_at);}
    public void setPrice_per_month(double price_per_month) {this.price_per_month = new SimpleDoubleProperty(price_per_month);}

    @Override
    public String toString() {
        return "Servers{" + 
                "server_id=" + server_id + 
                ", name='" + name + '\'' + 
                ", hardware_type='" + hardware_type + '\'' + 
                ", ram_gb=" + ram_gb + 
                ", storage_gb=" + storage_gb + 
                ", specs='" + specs + '\'' + 
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
                Servers server = new Servers();
                    server.setServer_id(result.getInt("server_id"));
                    server.setName(result.getString("name"));
                    server.setHardware_type(result.getString("hardware_type"));
                    server.setRam_gb(result.getInt("ram_gb"));
                    server.setStorage_gb(result.getInt("storage_gb"));
                    server.setSpecs(result.getString("specs"));
                    server.setStatus(result.getString("status"));
                    server.setCreated_at(result.getString("created_at"));
                    server.setPrice_per_month(result.getDouble("price_per_month"));
                serverList.add(server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serverList;
    }
    public Servers SELECT_SERVER(int server_id) {
        Servers server = new Servers();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM servers WHERE server_id = ?");
            statement.setInt(1, server_id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                    server.setServer_id(result.getInt("server_id"));
                    server.setName(result.getString("name"));
                    server.setHardware_type(result.getString("hardware_type"));
                    server.setRam_gb(result.getInt("ram_gb"));
                    server.setStorage_gb(result.getInt("storage_gb"));
                    server.setSpecs(result.getString("specs"));
                    server.setStatus(result.getString("status"));
                    server.setCreated_at(result.getString("created_at"));
                    server.setPrice_per_month(result.getDouble("price_per_month"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }
    public int getNextServerId() {
        int nextId = 1; // Default to 1 if the table is empty or no gaps exist
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT server_id FROM servers ORDER BY server_id ASC");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int currentId = result.getInt("server_id");
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

    public void INSERT_SERVER(Servers server) {
        String name = server.getName();
        String hardware_type = server.getHardware_type();
        int ram_gb = server.getRam_gb();
        int storage_gb = server.getStorage_gb();
        String specs = server.getSpecs();
        String status = server.getStatus();
        String created_at = server.getCreated_at().toString();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement(
                "INSERT INTO servers (server_id ,name, hardware_type, ram_gb, storage_gb, specs, status, created_at, price_per_month) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, server.getServer_id());
            statement.setString(2, name);
            statement.setString(3, hardware_type);
            statement.setInt(4, ram_gb);
            statement.setInt(5, storage_gb);
            statement.setString(6, specs);
            statement.setString(7, status);
            statement.setString(8, created_at);
            statement.setDouble(9, server.getPrice_per_month());
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
        String specs = server.getSpecs();
        String status = server.getStatus();

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement(
                "UPDATE servers SET name = ?, hardware_type = ?, ram_gb = ?, storage_gb = ?, specs = ?, status = ?, price_per_month = ? WHERE server_id = ?"  
            );
            statement.setString(1, name);
            statement.setString(2, hardware_type);
            statement.setInt(3, ram_gb);
            statement.setInt(4, storage_gb);
            statement.setString(5, specs);
            statement.setString(6, status);
            statement.setDouble(7, server.getPrice_per_month());
            statement.setInt(8, server_id);

            statement.executeUpdate();

            System.out.println("Server " + name + " has been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DELETE_SERVER(Servers server) {
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM servers WHERE server_id = ?");
            statement.setInt(1, server.getServer_id());
            statement.executeUpdate();
            System.out.println("Server " + server.getName() + " has been deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Servers> AVAILABLE_SERVERS() {
        ArrayList<Servers> serverList = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM AvailableServers;");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Servers server = new Servers();
                
                server.setServer_id(result.getInt("server_id"));
                server.setName(result.getString("name"));
                server.setHardware_type(result.getString("hardware_type"));
                server.setRam_gb(result.getInt("ram_gb"));
                server.setStorage_gb(result.getInt("storage_gb"));
                server.setSpecs(result.getString("specs"));
                server.setStatus("Available");

                serverList.add(server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serverList;
    }

    public ArrayList<Servers> AVAILABLE_SERVERS(String name) {
        ArrayList<Servers> serverList = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM AvailableServers WHERE name = ?;");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Servers server = new Servers();
                
                server.setServer_id(result.getInt("server_id"));
                server.setName(result.getString("name"));
                server.setHardware_type(result.getString("hardware_type"));
                server.setPrice_per_month(result.getDouble("price_per_month"));
                server.setRam_gb(result.getInt("ram_gb"));
                server.setStorage_gb(result.getInt("storage_gb"));
                server.setSpecs(result.getString("specs"));
                server.setStatus("Available");

                serverList.add(server);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serverList;
    }

    public ArrayList<Servers> SERVERS_OWNED(ArrayList<Orders> orders) {
        ArrayList<Servers> serverList = new ArrayList<>();
        for (Orders order : orders) {
            if (order.getStatus().equals("completed")) {
                Servers server = new Servers();
                server = new Servers().SELECT_SERVER(order.getServer_id());
                serverList.add(server);
            }
        }
        return serverList;
    }
}