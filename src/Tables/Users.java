package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class Users {
    private SimpleIntegerProperty user_id;
    private SimpleStringProperty username;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleStringProperty full_name;
    private SimpleStringProperty contact_number;
    private SimpleStringProperty address;
    private SimpleIntegerProperty created_at;
    private SimpleIntegerProperty last_login;
    private SimpleStringProperty status;

    public enum Status {
        ACTIVE, 
        INACTIVE, 
        SUSPENDED
    }

    public Users(int user_id, String username, 
                String email, String password, 
                String full_name,String contact_number, 
                String address, String status, 
                int created_at, int last_login) {
        this.user_id = new SimpleIntegerProperty(user_id);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.full_name = new SimpleStringProperty(full_name);
        this.contact_number = new SimpleStringProperty(contact_number);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleIntegerProperty(created_at);
        this.last_login = new SimpleIntegerProperty(last_login);
    }

    
    public int getUser_id() { return user_id.get(); }
    public String getUsername() { return username.get(); }
    public String getFull_name() { return full_name.get(); }
    public String getEmail() { return email.get(); }
    public String getPassword() { return password.get(); }
    public String getContact_number() { return contact_number.get(); }
    public String getAddress() { return address.get(); }
    public String getStatus() { return status.get(); }
    public int getCreated_at() { return created_at.get(); }
    public int getLast_login() { return last_login.get(); }

    public void setUser_id(int user_id) {this.user_id.set(user_id);}
    public void setUsername(String username) {this.username.set(username);}
    public void setFull_name(String full_name) {this.full_name.set(full_name);}
    public void setEmail(String email) {this.email.set(email);}
    public void setPassword(String password) {this.password.set(password);}
    public void setContact_number(String contact_number) {this.contact_number.set(contact_number);}
    public void setAddress(String address) {this.address.set(address);}
    public void setStatus(String status) {this.status.set(status);}
    public void setCreated_at(int created_at) {this.created_at.set(created_at);}
    public void setLast_login(int last_login) {this.last_login.set(last_login);}
}