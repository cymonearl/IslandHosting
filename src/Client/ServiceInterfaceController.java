package Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Tables.Orders;
import Tables.Servers;
import Tables.SupportTicket;
import Tables.Users;
public class ServiceInterfaceController {
        // Header Labels
        @FXML private Label servicesLabel;
        @FXML private Label invoiceLabel;
        @FXML private Label ticketsLabel;
        @FXML private Label helpLabel;
        @FXML private Label userLabel;
        @FXML private ImageView islandHostIcon;
        @FXML private GridPane gridPane;
        @FXML private ComboBox<String> planComboBox;
        private VBox selectedBox;
        @FXML private Label slots;
        // Service Details Labels
    
        private Users user;
    
        public void setUser(Users user) {
            this.user = user;
        }
    
        @FXML
        public void initialize() {
            // Initialization logic (if needed)
            System.out.println("Landing Page Initialized");
    
            planComboBox.getItems().addAll("Basic", "Pro", "Enterprise");
            planComboBox.setValue("Basic");
    
            // Example: Set default text for labels dynamically if needed
            servicesLabel.setText("Services");
            invoiceLabel.setText("Invoice");
            ticketsLabel.setText("Tickets");
            helpLabel.setText("Help");
            userLabel.setText("User");
    
            populateGridPane(null);
        }
    
        @FXML
        private void onInvoiceClick(MouseEvent event) {
            System.out.println("Invoice clicked!");
            // Add logic for navigating to the Invoice page
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
    
        public void populateGridPane(ActionEvent event) {
            selectedBox = null;
            ArrayList<Servers> availableServers = new Servers().AVAILABLE_SERVERS(planComboBox.getValue());
            int count = 0;
            for (Servers availableServer : availableServers) {
                if (availableServer.getStatus().equals("Available")) {
                    count++;
                }
            }
            slots.setText(String.valueOf(count) + " Slot/s Available");
            
            // Clear the grid pane
            gridPane.getChildren().clear();
        
            try {
                int column = 0;
                int row = 0;
        
                for (Servers availableServer : availableServers) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("serverCard.fxml"));
                    VBox card = loader.load();
                    
                    serverCardController controller = loader.getController();
                    controller.setServer(availableServer);
                    card.setUserData(controller);
                    
                    // Add the card to the GridPane at the specified column and row
                    card.setOnMouseClicked(this::selectOrder);
                    gridPane.add(card, column, row);
                    
                    column++;
                    // Move to the next row after filling a certain number of columns (e.g., 3 columns per row)
                    if (column == 2) {
                        column = 0;
                        row++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void selectOrder(MouseEvent event) {
            VBox clickedBox = (VBox) event.getSource();
    
            // Deselect the previously selected box (if any)
            if (selectedBox != null) {
                selectedBox.setStyle("-fx-background-color: #2c2c34; -fx-background-radius: 20; -fx-border-radius: 20;");
            }
    
            // Check if the clicked box is the same as the currently selected box
            if (selectedBox == clickedBox) {
                // Deselect the clicked box
                selectedBox.setStyle("-fx-background-color: #2c2c34; -fx-background-radius: 20; -fx-border-radius: 20;");
                handleCheckOut(null);
                selectedBox = null;
            } else {
                // Select the clicked box
                selectedBox = clickedBox;
                selectedBox.setStyle("-fx-background-color: lightblue; -fx-background-radius: 20;");
            }
        }
    
        public void handleCheckOut(ActionEvent event) {
            // Get the selected billing cycle and total due
            // Perform checkout confirmation using JOptionPane
            if (selectedBox == null) {
                Alert("Pay Order", "Please select an order to pay.");
                return;
            }
    
            serverCardController card = (serverCardController) (selectedBox.getUserData());
            Servers server = card.getServer();
            int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    "Confirm Checkout?\n\n" + "\nTotal Due: " + server.getPrice_per_month(),
                    "Checkout Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );
    
            // Handle user response
            if (confirmation == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                        null,
                        "Thank you for your purchase!\nYour order has been successfully processed.",
                        "Checkout Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
    
                // Close the pop-up window            
                Orders order = new Orders(String.valueOf(user.getUser_id()), String.valueOf(server.getServer_id()),  String.valueOf(server.getPrice_per_month()), "Pending");
                new Orders().LOG_ORDER_MADE(order);
            } else {
                // User cancelled checkout
                JOptionPane.showMessageDialog(
                        null,
                        "Checkout Cancelled.",
                        "Cancelled",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            populateGridPane(null);
        }
        
        @FXML
        private void onTicketsClick(MouseEvent event) {
            System.out.println("Tickets clicked!");
            // Add logic for navigating to the Tickets page
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
    
        @FXML
        private void onHelpClick(MouseEvent event) {
            System.out.println("Help clicked!");
            // Add logic for navigating to the Help page
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
    
        @FXML
        private void onUserClick(MouseEvent event) {
            System.out.println("User clicked!");
            // Add logic for navigating to the User page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfile.fxml"));
                Parent root = loader.load();
        
                UserProfileController controller = loader.getController();
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
        
        private void Alert(String title, String message) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.setContentText(message);
            alert.showAndWait();
    }
}
