import java.util.ArrayList;
import Client.LandingPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import Tables.Users;

public class LoginMenuController {

    public TextField loginTextField;
    public PasswordField passwordTextField;
    public Label confirmPasswordLabel;
    public TextField confirmPasswordTextField;
    public TextField usernameTextField;
    public TextField full_nameTextField;
    public TextField contact_numberTextField;
    public TextField addressTextField;
    public CheckBox showPasswordCheckBox;
    private TextField visiblePasswordField;

    private Parent root;
    private Stage stage;
    private ArrayList<Users> usersList = new Users().SELECT_ALL_USERS();
    private String email;
    private String password;

    public void setEP(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void loginButton(ActionEvent event) {
        email = loginTextField.getText().trim();
        password = passwordTextField.getText().trim();

        if (email.equals("admin") && password.equals("admin")) {
            navigateToScene(event, "CRUD/CRUDUsersMenu.fxml", "CRUD Menu");
        } else {
            Users user = findUserByEmailAndPassword(email, password);
            if (user != null) {
                navigateToClientLandingPage(event, user);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
            }
        }
    }

    public void registerButton(ActionEvent event) {
        if (!passwordTextField.getText().trim().isEmpty() && confirmPasswordTextField.getText().trim().isEmpty()) {
            confirmPasswordLabel.setVisible(true);
            confirmPasswordTextField.setVisible(true);
            return;
        }

        email = loginTextField.getText().trim();
        password = passwordTextField.getText().trim();
        String confirmPassword = confirmPasswordTextField.getText().trim();
        System.out.println(email + " " + password + " " + confirmPassword);

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email Error", "Invalid email address.");
            return;
        }

        if (findUserByEmail(email) != null) {
            showAlert(Alert.AlertType.ERROR, "User Exists", "User already exists.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterMenu.fxml"));
            root = loader.load();

            LoginMenuController controller = loader.getController();
            controller.setEP(email, password);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register Menu");

            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void initialize() {
        // Ensure the visiblePasswordField is created only once
        if (visiblePasswordField == null) {
            // Create the visible password field programmatically
            visiblePasswordField = new TextField();

            // Match exact specifications from FXML
            visiblePasswordField.setPrefHeight(25.0);
            visiblePasswordField.setPrefWidth(360.0);
            visiblePasswordField.setPromptText("Enter your password");
            visiblePasswordField.getStyleClass().add("textfield-design");
            visiblePasswordField.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            // Get the parent VBox of the password field
            if (passwordTextField.getParent() instanceof VBox) {
                VBox parent = (VBox) passwordTextField.getParent();

                // Add the visible field at the same index as the password field
                int index = parent.getChildren().indexOf(passwordTextField);
                parent.getChildren().add(index + 1, visiblePasswordField);

                // Initially hide the visible field
                visiblePasswordField.setManaged(false);
                visiblePasswordField.setVisible(false);
            }
        }
    }

    public void togglePasswordVisibility(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            // Show password in visiblePasswordField and hide the PasswordField
            visiblePasswordField.setText(passwordTextField.getText());
            passwordTextField.setManaged(false);
            passwordTextField.setVisible(false);
            visiblePasswordField.setManaged(true);
            visiblePasswordField.setVisible(true);
        } else {
            // Hide password and show it in PasswordField again
            passwordTextField.setText(visiblePasswordField.getText());
            passwordTextField.setManaged(true);
            passwordTextField.setVisible(true);
            visiblePasswordField.setManaged(false);
            visiblePasswordField.setVisible(false);
        }
    }

    public void createUserButton(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String fullName = full_nameTextField.getText().trim();
        String contactNumber = contact_numberTextField.getText().trim();
        String address = addressTextField.getText().trim();

        if (!validateCreateUserInput(username, fullName, contactNumber, address)) {
            return;
        }

        Users newUser = new Users(username, email, password, fullName, contactNumber, address, "active");
        System.out.println(newUser);
        if (isDuplicateUser(newUser)) {
            return;
        }

        new Users().INSERT_USER(newUser);
        usersList.add(newUser);
        navigateToClientLandingPage(event, newUser);
    }

    public void cancelButton(ActionEvent event) {
        navigateToScene(event, "LoginMenu.fxml", "Login Menu");
    }

    private Users findUserByEmailAndPassword(String email, String password) {
        for (Users user : usersList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private Users findUserByEmail(String email) {
        for (Users user : usersList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    private boolean validateCreateUserInput(String username, String fullName, String contactNumber, String address) {
        if (username.isEmpty() || fullName.isEmpty() || contactNumber.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields are required.");
            return false;
        }
        if (!contactNumber.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Contact number must contain only numbers.");
            return false;
        }
        return true;
    }

    private boolean isDuplicateUser(Users newUser) {
        for (Users user : usersList) {
            if (newUser.getUsername().equals(user.getUsername())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Entry", "Username already exists.");
                return true;
            }
            if (newUser.getFull_name().equals(user.getFull_name())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Entry", "Full name already exists.");
                return true;
            }
            if (newUser.getContact_number().equals(user.getContact_number())) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Entry", "Contact number already exists.");
                return true;
            }
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        return email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    }

    private void navigateToScene(ActionEvent event, String fxmlFile, String title) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);

            if (fxmlFile.equals("CRUD/CRUDUsersMenu.fxml")) {
                stage.setWidth(1800);
                stage.setHeight(1000);
            }

            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void navigateToClientLandingPage(ActionEvent event, Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/LandingPage.fxml"));
            Parent root = loader.load();
            LandingPageController lpc = loader.getController();
            lpc.setUser(user);
            user.LOGIN(user);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void handleException(Exception e) {
        e.printStackTrace();
    }
}
