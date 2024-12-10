import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
public class LoginMenuController {

    private Scene scene;
    private Parent root;
    private Stage stage;

    public TextField loginTextField;
    public TextField passwordTextField;
    public void loginButton(ActionEvent event) {
        String email = loginTextField.getText();
        String password = passwordTextField.getText();
        System.out.println(email);
        System.out.println(password);

        if (email.equals("admin") && password.equals("admin"))
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
        else
        System.exit(0);
    }
    
    public Label confirmPasswordLabel;
    public TextField confirmPasswordTextField;
    public String email, password;
    public void registerButton(ActionEvent event) {
        email = loginTextField.getText();
        password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if (confirmPasswordLabel.isVisible() && confirmPassword.equals(password)) {
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
        }

        confirmPasswordLabel.setVisible(true);
        confirmPasswordTextField.setVisible(true);
    }
}