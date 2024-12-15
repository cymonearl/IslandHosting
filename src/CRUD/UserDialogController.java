package CRUD;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.fxml.FXML;

import Tables.Users;

public class UserDialogController {
    public TextField usernameTextField;
    public TextField emailTextField;
    public TextField passwordTextField;
    public TextField full_nameTextField;
    public TextField contact_numberTextField;
    public TextField addressTextField;
    public ComboBox<String> statusComboBox;

    private Users user;
    private ObservableList<Users> userList;
    private boolean isNewUser = true;


    @FXML public Label CreateUserLabel;

    public void initialize() {
        statusComboBox.getItems().addAll("active", "inactive");
        statusComboBox.setValue("inactive");
    }

    public void setUser(Users user) {
        CreateUserLabel.setText("Update User");
        this.user = user;
        isNewUser = false;

        usernameTextField.setText(user.getUsername());
        emailTextField.setText(user.getEmail());
        passwordTextField.setText(user.getPassword());
        full_nameTextField.setText(user.getFull_name());
        contact_numberTextField.setText(user.getContact_number());
        addressTextField.setText(user.getAddress());
    }

    public void setUserList(ObservableList<Users> userList) {
        this.userList = userList;
    }

    public void handleSave() {
        if (!validateInput())
            return;
            
            String username = usernameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            String full_name = full_nameTextField.getText();
            String contact_number = contact_numberTextField.getText();
            String address = addressTextField.getText();
            String status = statusComboBox.getValue();
            
        if (isNewUser) {
            if (!validateUser())
                return;
            new Users().INSERT_USER(new Users(username, email, password, full_name, contact_number, address, status));
            userList.add(new Users(username, email, password, full_name, contact_number, address, status));
        } else {
            user.setUsername(username);
            user.setEmail(email);                                
            user.setPassword(password);
            user.setFull_name(full_name);    
            user.setContact_number(contact_number);
            user.setAddress(address);
            user.setStatus(status);
            Users updateUser = user;
            userList.set(userList.indexOf(user), updateUser);
            new Users().UPDATE_USER(updateUser);
        }
        closeDialog();
    }

    public void handleCancel() {
        closeDialog();
    }

    public boolean validateUser() {
        for (Users user : userList) {
            if (user.getUsername().equals(usernameTextField.getText())) {
                alert("Duplicate Username", "Username already exists.");
                return false;
            } else if (user.getEmail().equals(emailTextField.getText())) {
                return false;
            } else if (user.getContact_number().equals(contact_numberTextField.getText())) {
                alert("Duplicate Contact Number", "Contact number already exists.");
                return false;
            } else if (user.getFull_name().equals(full_nameTextField.getText())) {
                alert("Duplicate Full Name", "Full name already exists.");                
                return false;
            }
        }
        return true;
    }

    public boolean validateInput() {
        String errorMessage = "";
        
        if (usernameTextField.getText().isEmpty()) {
            errorMessage += "Username is required!\n";
        }
        if (emailTextField.getText().isEmpty() || !emailTextField.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            errorMessage += "Invalid email address!\n";
        }
        if (full_nameTextField.getText().isEmpty()) {
            errorMessage += "Full name is required!\n";
        }
        if (contact_numberTextField.getText().isEmpty() || !contact_numberTextField.getText().matches("\\d+")) {
            errorMessage += "Invalid contact number!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            alert("Invalid Fields", errorMessage);
            return false;
        }
    }

    public void closeDialog() {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.close();
    }

    private void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}