package Client;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import Tables.Orders;
import Tables.Payments;
import Tables.SupportTicket;
import Tables.Users;
public class InvoiceInterfaceController {

    private Users user;
    private ArrayList<Orders> orders;
    @FXML private ComboBox<String> paymentComboBox;
    @FXML private VBox invoiceVBOX;
    @FXML private Label totalLabel;
    private VBox selectedBox;
    double total;

    public void initialize() {
        paymentComboBox.getItems().addAll("Paypal", "Credit Card", "Debit Card", "GCash");
        paymentComboBox.setValue("Paypal");
    }

    public void setUser(Users user) {
        this.user = user;
        this.orders = new Orders().SELECT_ORDER_ID(user.getUser_id());

        populateOrders(orders);
    }

    public void populateOrders(ArrayList<Orders> orders)  {
        if (orders.isEmpty()) {
            return;
        }
        
        total = 0;
        invoiceVBOX.getChildren().clear();
        try {
            for (Orders order : orders) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("invoice.fxml"));
                VBox card = loader.load();
                
                invoiceController controller = loader.getController();
                controller.setOrder(order);
                card.setUserData(controller);
                
                card.setOnMouseClicked(this::selectOrder);
                invoiceVBOX.getChildren().add(card);
                if (order.getStatus().equals("pending")) {
                    total += order.getTotal_amount();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        totalLabel.setText(String.valueOf("Total Due: " + total));
    }

    public void cancelOrder(ActionEvent event) {
        if (selectedBox != null) {
            invoiceController controller = (invoiceController) selectedBox.getUserData();
            if (controller != null) {
                Orders order = controller.getOrder();
                if (order.getStatus().equals("completed")) {
                    Alert("Cancel Order", "You cannot cancel an order that has already been completed.");
                    return;
                }

                if (!Alert("Cancel Order", "Are you sure you want to cancel this order?")) {
                    return;
                }

                order.DELETE_ORDER(order);
                invoiceVBOX.getChildren().remove(selectedBox);
                selectedBox = null;
            }
        }
    }

    public void pay(ActionEvent event) {
        if (!checkOrderStatus()) {
            Alert("Pay Order", "No pending orders.");
            return;
        }

        if (!Alert("Pay Order", "Are you sure you want to pay this order?")) {
            return;
        }

        Payments payment = new Payments(user.getUser_id(), String.valueOf(total), paymentComboBox.getValue(), "completed");
        System.out.println(payment);
        new Payments().PAY_ORDER(payment);

        completeOrder();
        populateOrders(new Orders().SELECT_ORDER_ID(user.getUser_id()));
    }

    private void completeOrder() {
        ArrayList<Orders> orders = new Orders().SELECT_ORDER_ID(user.getUser_id());
        new Orders().COMPLETE_ORDER(orders);
        totalLabel.setText("Total Due: 0");
    }

    private boolean checkOrderStatus() {
        ArrayList<Orders> orders = new Orders().SELECT_ORDER_ID(user.getUser_id());
        for (Orders order : orders) {
            if (order.getStatus().equals("pending")) {
                return true;
            }            
        }
        return false;
    }

    public void selectOrder(MouseEvent event) {
        VBox clickedBox = (VBox) event.getSource();

        // Deselect the previously selected box (if any)
        if (selectedBox != null) {
            selectedBox.setStyle("-fx-background-color: gray; -fx-background-radius: 20; -fx-border-radius: 20;");
        }

        // Check if the clicked box is the same as the currently selected box
        if (selectedBox == clickedBox) {
            // Deselect the clicked box
            selectedBox.setStyle("-fx-background-color: gray; -fx-background-radius: 20; -fx-border-radius: 20;");
            selectedBox = null;
        } else {
            // Select the clicked box
            selectedBox = clickedBox;
            selectedBox.setStyle("-fx-background-color: lightblue; -fx-background-radius: 20;");
        }
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

    public boolean Alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }
}