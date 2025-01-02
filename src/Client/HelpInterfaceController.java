package Client;

import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import Tables.Orders;
import Tables.SupportTicket;
import Tables.Users;
public class HelpInterfaceController {

    Users user;
    ArrayList<Orders> orders;

    public void setUser(Users user, ArrayList<Orders> orders) {
        this.user = user;
        this.orders = orders;
    }

    public void servicesClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceInterface.fxml"));
            Parent root = loader.load();
    
            ServiceInterfaceController controller = loader.getController();
            controller.setUser(user, orders);
    
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
            controller.setUser(user, orders);
    
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
            controller.setUser(user, orders);
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
            controller.setUser(user, orders);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UserClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfile.fxml"));
            Parent root = loader.load();
    
            UserProfileController controller = loader.getController();
            controller.setUser(user, orders);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
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
            controller.setUser(user, orders);

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
}