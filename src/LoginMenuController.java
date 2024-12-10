import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;

import Tables.Users;
public class LoginMenuController {

    private Scene scene;
    private Parent root;
    private Stage stage;

    public TextField loginTextField;
    public TextField passwordTextField;

    private ArrayList<Users> users_ar = new Users().SELECT_ALL_USERS();

    public void loginButton(ActionEvent event) {
        String email = loginTextField.getText();
        String password = passwordTextField.getText();
        System.out.println(email);
        System.out.println(password);

        if (email.equals("admin") && password.equals("admin")) {
            try {
                root = FXMLLoader.load(getClass().getResource("CRUD/CRUDUsersMenu.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("CRUD Menu");
                stage.show();
                stage.centerOnScreen();
            } catch(Exception e) {
                System.err.println(e);
            } 
        }
        else if (validateUser(email, password)) {
            try {
                // TODO: Open Client Menu\
                System.out.println();
            } catch(Exception e) {
                System.err.println(e);
            }
        }
        else {
            System.out.println("Email or Password is incorrect");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Not Found");
            alert.setHeaderText("Email or Password is incorrect");
            alert.showAndWait();            
        }
    }

    private boolean validateUser(String email, String password) {
        for (Users user : users_ar) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateUser(Users user) {
        for (Users u : users_ar) {
            if (!u.getUsername().equals(user.getUsername())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Username Number");
                alert.setHeaderText("Username already exists");
                alert.showAndWait();
                return false;
            } else if (!u.getFull_name().equals(user.getFull_name())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Full Name");
                alert.setHeaderText("Full Name already exists");
                alert.showAndWait();
                return false;
            } else if (!u.getContact_number().equals(user.getContact_number())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Contact Number");
                alert.setHeaderText("Contact number already exists");
                alert.showAndWait();
                return false;
            } else if (!u.getAddress().equals(user.getAddress())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Address");
                alert.setHeaderText("Address already exists");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    private boolean validateInput() {
        String errorMessage = "";
        
        if (usernameTextField.getText().isEmpty()) {
            errorMessage += "Username is required!\n";
        }
        if (full_nameTextField.getText().isEmpty()) {
            errorMessage += "Full name is required!\n";
        }
        if (contact_numberTextField.getText().isEmpty() || !contact_numberTextField.getText().matches("\\d+")) {
            errorMessage += "Invalid contact number!\n";
        }
        if (addressTextField.getText().isEmpty()) {
            errorMessage += "Address is required!\n";
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

    
    public Label confirmPasswordLabel;
    public TextField confirmPasswordTextField;
    public String email, password;
    public void registerButton(ActionEvent event) {
        email = loginTextField.getText();
        password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if (loginTextField.getText().isEmpty() || !loginTextField.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText("Invalid email address");
            alert.showAndWait();
        } 
        else if (validateUser(email, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Duplicate User");
            alert.setHeaderText("User already exists");
            alert.showAndWait();
        }
        else if (confirmPasswordLabel.isVisible() && confirmPassword.equals(password)) {
            try {
                root = FXMLLoader.load(getClass().getResource("RegisterMenu.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Register Menu");
                stage.show();
                stage.centerOnScreen();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (confirmPasswordLabel.isVisible() && !confirmPassword.equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText("Passwords do not match");
            alert.showAndWait();
        }

        if (!passwordTextField.getText().isEmpty()) {
            confirmPasswordLabel.setVisible(true);
            confirmPasswordTextField.setVisible(true);
        }
    }

    public TextField usernameTextField;
    public TextField full_nameTextField;
    public TextField contact_numberTextField;
    public TextField addressTextField;    
    public void createUserButton(ActionEvent event) {
        String username = usernameTextField.getText();
        String full_name = full_nameTextField.getText();
        String contact_number = contact_numberTextField.getText();
        String address = addressTextField.getText();

        Users user = new Users(username, email, password, full_name, contact_number, address, "active");
        if (validateInput()) {return;} 
        else if (validateUser(user)) {
            try {
                // TODO: Open Client Menu
                System.exit(0);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public void cancelButton(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login Menu");
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
