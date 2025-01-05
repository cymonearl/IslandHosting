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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Tables.Audit_Logs;
import Tables.Orders;
import Tables.SupportTicket;
import Tables.Users;
public class InvoiceInterfaceController {

    private Users user;
    @FXML private VBox invoiceVBOX;
    @FXML private Label totalLabel;
    private VBox selectedBox;
    double total;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private HBox bottomHBOX;

    public void initialize() {
        statusComboBox.getItems().addAll("pending", "completed", "cancelled");
        statusComboBox.setValue("pending");
    }

    public void selectFilter(ActionEvent event) {
        String selected = statusComboBox.getValue();
        populateOrders(populateByFilter(selected));

        if (selected.equals("cancelled") || selected.equals("completed"))
        bottomHBOX.setVisible(false);
        else
        bottomHBOX.setVisible(true);
    }

    public void setUser(Users user) {
        this.user = user;

        populateOrders(populateByFilter("pending"));
    }

    public void populateOrders(ArrayList<Orders> orders)  {
        invoiceVBOX.getChildren().clear();
        if (orders.isEmpty()) {
            return;
        }
        
        total = 0;
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

    public ArrayList<Orders> populateByFilter(String status) {
        ArrayList<Orders> orders = new ArrayList<>();
        for (Orders order : new Orders().SELECT_ORDER_ID(user.getUser_id())) {
            if (order.getStatus() != "cancelled") {
                if (order.getStatus().equals(status)) {
                    orders.add(order);
                }                
            }
        }
        return orders;
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

                order.CANCEL_ORDER(order);
                new Audit_Logs().INSERT_AUDIT_LOG(new Audit_Logs(user.getUser_id(), "Cancel Order", "Cancelled order with order id " + order.getOrder_id(), "127.0.0.1"));
                invoiceVBOX.getChildren().remove(selectedBox);
                selectedBox = null;
            }
        }
    }

    public void pay(ActionEvent event) {
        if (selectedBox == null) {
            Alert("Pay Order", "Please select an order to pay.");
            return;
        }
        
        invoiceController controller = ((invoiceController) selectedBox.getUserData());
        Orders order = controller.getOrder();

        if (order.getStatus().equals("completed")) {
            Alert("Pay Order", "You cannot pay for an order that has already been completed.");
            return;
        }

        if (!Alert("Pay Order", "Are you sure you want to pay this order?")) {
            return;
        }

        selectedBox = null;
        System.out.println(order);
        showPayPopup(order);
    }

    public void showPayPopup(Orders order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payPopup.fxml"));
            Parent root = loader.load();
    
            payPopupController controller = loader.getController();
            controller.setPayment(String.valueOf(order.getTotal_amount()), order.getOrder_id());
            controller.setController(this);
    
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            pay(null);
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