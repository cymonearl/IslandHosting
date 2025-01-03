package Client;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

import Tables.SupportTicket;
import Tables.Users;
public class UserProfileController {
    
    @FXML TextField name;
    @FXML TextField password;
    @FXML TextField address;
    @FXML TextField contact_number;

    private Users user;

    public Users getUser() {
        return user;
    }

    public void initialize() {

    }

    public void setUser(Users user) {
        this.user = user;

        name.setText(user.getFull_name());
        password.setText(user.getPassword());
        address.setText(user.getAddress());
        contact_number.setText(user.getContact_number());
    }


    public void servicesClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceInterface.fxml"));
            Parent root = loader.load();
    
            ServiceInterfaceController controller = loader.getController();
            controller.setUser(user);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void invoiceClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InvoiceInterface.fxml"));
            Parent root = loader.load();
    
            InvoiceInterfaceController controller = loader.getController();
            controller.setUser(user);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ticketsClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketsInterface.fxml"));
            Parent root = loader.load();
    
            TicketsInterfaceController controller = loader.getController();
            controller.setUser(user);
            controller.populateTickets(new SupportTicket().SELECT_USER_SUPPORT_TICKETS(user.getUser_id()));
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void helpClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpInterface.fxml"));
            Parent root = loader.load();
    
            HelpInterfaceController controller = loader.getController();
            controller.setUser(user);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void navigateToLandingPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();

            LandingPageController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Landing Page");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
                System.out.println("Error landingPage");
        }
    }

    public void handleUpdate(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        user.setFull_name(name.getText());
        user.setPassword(password.getText());
        user.setAddress(address.getText());
        user.setContact_number(contact_number.getText());
        user.UPDATE_USER(user);
    }

    public boolean validateInput() {
        if (name.getText() == user.getUsername()) {
            return false;
        }

        if (password.getText() == user.getPassword()) {
            return false;
        }

        if (address.getText() == user.getAddress()) {
            return false;
        }

        if (contact_number.getText() == user.getContact_number()) {
            return false;
        }

        if (name.getText().isEmpty() || password.getText().isEmpty() || address.getText().isEmpty() || contact_number.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields are required.");
            return false;
        }

        if (!contact_number.getText().matches("\\d{9,}")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Contact number must be a series of digits.");
            return false;
        }

        if (!password.getText().equals(password.getText())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Passwords do not match.");
            return false;
        }        

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}