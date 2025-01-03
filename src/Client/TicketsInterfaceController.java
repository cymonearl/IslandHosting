package Client;

import java.io.IOException;
import java.util.ArrayList;

import Tables.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import Tables.SupportTicket;

public class TicketsInterfaceController {

    Users user;

    @FXML private VBox ticketsVBOX;

    public void initialize() {
        // populateTickets(new SupportTicket().SELECT_USER_SUPPORT_TICKETS(user.getUser_id()));
    }

    public void populateTickets(ArrayList<SupportTicket> tickets) {
        if (tickets.isEmpty()) {
            return;
        }
        ticketsVBOX.getChildren().clear();
        try {
            for (SupportTicket ticket : tickets) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Ticket.fxml"));
                VBox card = loader.load();
                
                ticketController controller = loader.getController();
                controller.setTicket(ticket);
                card.setUserData(controller);
                
                ticketsVBOX.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(Users user) {
        this.user = user;
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

    public void helpClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpInterface.fxml"));
            Parent root = loader.load();
    
            HelpInterfaceController controller = loader.getController();
            controller.setUser(user);
    
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
            controller.setUser(user);
    
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

    public void createIssue(ActionEvent event) {
        System.out.println("Create issue clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketDialog.fxml"));
            Parent root = loader.load();

            TicketDialogController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                
            stage.showAndWait();

            populateTickets(new SupportTicket().SELECT_USER_SUPPORT_TICKETS(user.getUser_id()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}