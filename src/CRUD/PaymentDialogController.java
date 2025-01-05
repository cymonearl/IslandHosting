package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;

import Tables.Payments;
import Tables.Views.PaymentView;

public class PaymentDialogController {
    public Label CreatePaymentLabel;
    public TextField order_idTF;
    public TextField AmountField;
    public ComboBox<String> PaymentMethodComboBox;
    public ComboBox<String> StatusComboBox;
    private PaymentView payment;
    private CRUDPaymentsMenuController controller;
    private boolean isNewPayment = true;

    public void initialize() {
        StatusComboBox.getItems().addAll("pending","completed","failed","refunded");
        StatusComboBox.setValue("pending");

        PaymentMethodComboBox.getItems().addAll("Credit Card", "Debit Card", "PayPal", "GCash");
        PaymentMethodComboBox.setValue("Credit Card");
    }

    public void setPayment(PaymentView payment) {
        CreatePaymentLabel.setText("Update Payment");
        this.payment = payment;
        isNewPayment = false;

        order_idTF.setText(String.valueOf(payment.getOrderId()));
        AmountField.setText(String.valueOf(payment.getAmount()));
        PaymentMethodComboBox.setValue(payment.getPaymentMethod());
        StatusComboBox.setValue(payment.getPaymentStatus());
    }

    public void setController(CRUDPaymentsMenuController controller) {
        this.controller = controller;
    }

    public void handleSavePayment() {
        if (!validateInput()) {
            return;
        }
        
        String order = order_idTF.getText();
        String amount = AmountField.getText();
        String paymentMethod = PaymentMethodComboBox.getValue();
        String paymentStatus = StatusComboBox.getValue();
        if (isNewPayment) {
            Payments payment = new Payments(Integer.parseInt(order), amount, paymentMethod, paymentStatus);
            new Payments().INSERT_PAYMENT(payment);
        } else {
            payment.setOrderId(Integer.parseInt(order));
            payment.setAmount(Double.parseDouble(amount));
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus(paymentStatus);
            new Payments().UPDATE_PAYMENT(payment);
        }
        closeDialog();
        controller.initialize();
    }

    public boolean validateInput() {
        String errorMessage = "";

        String order_id = order_idTF.getText();
        String amount = AmountField.getText();

        if (order_id.isEmpty()) {
            errorMessage += "Order ID is required!\n";
        }
        if (amount.isEmpty() || !amount.matches("\\d*(\\.\\d+)?")) {
            errorMessage += "Invalid Amount!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            alert("Invalid Fields", errorMessage);
            return false;
        }
    }

    public void closeDialog() {
        Stage stage = (Stage) CreatePaymentLabel.getScene().getWindow();
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