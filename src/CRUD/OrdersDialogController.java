package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import Tables.Orders;
public class OrdersDialogController {
    public Label CreateOrderLabel;
    public TextField user_idTextField;
    public TextField server_idTextField;
    public TextField total_amountTextField;
    public ComboBox<String> statusComboBox;
    
    private Orders order;
    private ObservableList<Orders> orderList;
    private boolean isNewOrder = true;
    public void initialize() {
        statusComboBox.getItems().addAll("Pending", "Completed", "Cancelled", "Expired");
        statusComboBox.setValue("Pending");
    }

    public void setOrder(Orders order) {
        CreateOrderLabel.setText("Update Order");
        this.order = order;
        isNewOrder = false;

        total_amountTextField.setText(String.valueOf(order.getTotal_amount()));
        user_idTextField.setText(String.valueOf(order.getUser_id()));
        server_idTextField.setText(String.valueOf(order.getServer_id()));
        statusComboBox.setValue(order.getStatus());
    }

    public void setOrderList(ObservableList<Orders> orderList) {
        this.orderList = orderList;
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
            new Orders().INSERT_ORDER(new Orders(user_id, server_id, total_amount, status));
            orderList.add(new Orders(user_id, server_id, total_amount, status));
        } else {
            order.setUser_id(Integer.parseInt(user_idTextField.getText()));
            order.setServer_id(Integer.parseInt(server_idTextField.getText()));
            order.setTotal_amount(Double.parseDouble(total_amountTextField.getText()));
            order.setStatus(statusComboBox.getValue());
            Orders updateOrder = order;
            orderList.set(orderList.indexOf(order), updateOrder);
            new Orders().UPDATE_ORDER(updateOrder);
        }
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