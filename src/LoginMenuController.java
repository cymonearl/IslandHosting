import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import Tables.Users;

public class LoginMenuController {

    // FXML Components
    public TextField loginTextField;
    public TextField passwordTextField;
    public Label confirmPasswordLabel;
    public TextField confirmPasswordTextField;
    public TextField usernameTextField;
    public TextField full_nameTextField;
    public TextField contact_numberTextField;
    public TextField addressTextField;

    // Class-level properties
    private Scene scene;
    private Parent root;
    private Stage stage;
    private ArrayList<Users> users_ar = new Users().SELECT_ALL_USERS();
    public String email, password;

    // ========= Event Handlers =========

    // Login button handler
    public void loginButton(ActionEvent event) {
        String email = loginTextField.getText();
        String password = passwordTextField.getText();
        
        System.out.println(email);
        System.out.println(password);

        if (email.equals("admin") && password.equals("admin")) {
            try {
                loadScene(event, "CRUD/CRUDUsersMenu.fxml", "CRUD Menu");
            } catch (Exception e) {
                handleError(e);
            }
        } else if (validateUser(email, password)) {
            try {
                // TODO: Implement Client Menu navigation
                loadScene(event, "Client/LandingPage.fxml", "Client Menu");
                System.out.println();
            } catch (Exception e) {
                handleError(e);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "User Not Found", "Email or Password is incorrect");
        }
    }

    // Register button handler
    public void registerButton(ActionEvent event) {
        email = loginTextField.getText();
        password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Invalid email address");
        } else if (validateUser(email, password)) {
            showAlert(Alert.AlertType.ERROR, "Duplicate User", "User already exists");
        } else if (confirmPasswordLabel.isVisible() && confirmPassword.equals(password)) {
            try {
                loadScene(event, "RegisterMenu.fxml", "Register Menu");
            } catch (Exception e) {
                handleError(e);
            }
        } else if (confirmPasswordLabel.isVisible() && !confirmPassword.equals(password)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match");
        }

        if (!passwordTextField.getText().isEmpty()) {
            confirmPasswordLabel.setVisible(true);
            confirmPasswordTextField.setVisible(true);
        }
    }

    // Create user button handler
    public void createUserButton(ActionEvent event) {
        String username = usernameTextField.getText();
        String fullName = full_nameTextField.getText();
        String contactNumber = contact_numberTextField.getText();
        String address = addressTextField.getText();

        Users user = new Users(username, email, password, fullName, contactNumber, address, "active");
        if (!validateInput()) return;

        if (validateUser(user)) {
            try {
                // TODO: Implement Client Menu navigation
                System.exit(0);
            } catch (Exception e) {
                handleError(e);
            }
        }
    }

    // Cancel button handler
    public void cancelButton(ActionEvent event) {
        try {
            loadScene(event, "LoginMenu.fxml", "Login Menu");
        } catch (Exception e) {
            handleError(e);
        }
    }

    // ========= Validation Methods =========

    // Validate user on email and password
    private boolean validateUser(String email, String password) {
        for (Users user : users_ar) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Validate user for duplicate information
    private boolean validateUser(Users user) {
        for (Users u : users_ar) {
            if (!u.getUsername().equals(user.getUsername())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Username", "Username already exists");
                return false;
            } else if (!u.getFull_name().equals(user.getFull_name())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Full Name", "Full Name already exists");
                return false;
            } else if (!u.getContact_number().equals(user.getContact_number())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Contact Number", "Contact number already exists");
                return false;
            } else if (!u.getAddress().equals(user.getAddress())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Address", "Address already exists");
                return false;
            }
        }
        return true;
    }

    // Validate input fields
    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (usernameTextField.getText().isEmpty()) {
            errorMessage.append("Username is required!\n");
        }
        if (full_nameTextField.getText().isEmpty()) {
            errorMessage.append("Full name is required!\n");
        }
        if (contact_numberTextField.getText().isEmpty() || !contact_numberTextField.getText().matches("\\d+")) {
            errorMessage.append("Invalid contact number!\n");
        }
        if (addressTextField.getText().isEmpty()) {
            errorMessage.append("Address is required!\n");
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Fields", "Please correct invalid fields\n" + errorMessage.toString());
            return false;
        }
    }

    // ========= Utility Methods =========

    // Load a new FXML scene
    private void loadScene(ActionEvent event, String fxmlFile, String title) throws java.io.IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        if ("CRUD/CRUDUsersMenu.fxml".equals(fxmlFile)) {
            stage.setWidth(1600);
            stage.setHeight(1000);
            stage.centerOnScreen();
        }
        stage.show();
        stage.centerOnScreen();
    }

    // Show alert dialog
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    // Handle exceptions
    private void handleError(Exception e) {
        System.err.println(e);
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        return email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    }
}