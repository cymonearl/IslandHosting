package CRUD;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;

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

    public void initialize() {
        statusComboBox.getItems().addAll("Active", "Inactive");
        statusComboBox.setValue("Active");
    }

    public void setUser(Users user) {
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
        if (!validateInput()) {
            return;
        }

        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String full_name = full_nameTextField.getText();
        String contact_number = contact_numberTextField.getText();
        String address = addressTextField.getText();
        String status = statusComboBox.getValue();

        int UserId = 0;

        if (isNewUser) {
            userList.add(new Users(UserId, username, email, password, full_name, contact_number, address, status, 2024, 20241231));
        } else {
            user.setUser_id(UserId);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setFull_name(full_name);    
            user.setContact_number(contact_number);
            user.setAddress(address);
            user.setStatus(status);
            Users updateUser = user;
            userList.set(userList.indexOf(user), updateUser);
        }
        closeDialog();
    }

    public void handleCancel() {
        closeDialog();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    public void closeDialog() {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.close();
    }
}