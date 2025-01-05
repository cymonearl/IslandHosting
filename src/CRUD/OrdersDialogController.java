package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import Tables.Orders;
import Tables.Views.OrderView;;
public class OrdersDialogController {
    public Label CreateOrderLabel;
    public TextField user_idTextField;
    public TextField server_idTextField;
    public TextField total_amountTextField;
    public ComboBox<String> statusComboBox;
    private CRUDMenuOrdersController controller;
    
    private OrderView orderView;
    private boolean isNewOrder = true;
    public void initialize() {
        statusComboBox.getItems().addAll("Pending", "Completed", "Cancelled", "Expired");
        statusComboBox.setValue("Pending");
    }

    public void setOrder(OrderView orderView) {
        CreateOrderLabel.setText("Update Order");
        this.orderView = orderView;
        isNewOrder = false;

        total_amountTextField.setText(String.valueOf(orderView.getTotalAmount()));
        user_idTextField.setText(String.valueOf(orderView.getUserId()));
        server_idTextField.setText(String.valueOf(orderView.getServerId()));
        statusComboBox.setValue(orderView.getStatus());
    }

    public void setController(CRUDMenuOrdersController controller) {
        this.controller = controller;
    }

    public void handleSave() {
        if (!validateInput()) {
            return;
        }
        
        String user_id = user_idTextField.getText();
        String total_amount = total_amountTextField.getText();
        String server_id = server_idTextField.getText();
        String status = statusComboBox.getValue();
        
        if (isNewOrder) {
            Orders newOrder = new Orders(user_id, server_id, total_amount, status);
            new Orders().INSERT_ORDER(newOrder);
        } else {
            orderView.setUserId(Integer.parseInt(user_idTextField.getText()));
            orderView.setServerId(Integer.parseInt(server_idTextField.getText()));
            orderView.setTotalAmount(Double.parseDouble(total_amountTextField.getText()));
            orderView.setStatus(statusComboBox.getValue());
            new Orders().UPDATE_ORDER(orderView);
        }

        controller.initialize();
        closeDialog();
    }

    public void handleCancel() {
        closeDialog();
    }

    public boolean validateInput() {
        String errorMessage = "";

        if (user_idTextField.getText().isEmpty()) {
            errorMessage += "Order ID is required!\n";
        }
        if (server_idTextField.getText().isEmpty()) {
            errorMessage += "Server ID is required!\n";
        }
        if (total_amountTextField.getText().isEmpty() || !total_amountTextField.getText().matches("\\d*(\\.\\d+)?")) {
            errorMessage += "Invalid Total Amount!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            alert("Invalid Fields", errorMessage);
            return false;
        }
    }

    public void closeDialog() {
        Stage stage = (Stage) user_idTextField.getScene().getWindow();
        stage.close();
    }

    private void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}