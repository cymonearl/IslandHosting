package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;

import Tables.Payments;

public class PaymentDialogController {
    public Label CreatePaymentLabel;
    public TextField UserIDField;
    public TextField AmountField;
    public TextField PaymentMethodField;
    public ComboBox<String> StatusComboBox;
    private Payments payment;
    private ObservableList<Payments> paymentList;
    private boolean isNewPayment = true;

    public void initialize() {
        StatusComboBox.getItems().addAll("pending","completed","failed","refunded");
        StatusComboBox.setValue("pending");
    }

    public void setPayment(Payments payment) {
        CreatePaymentLabel.setText("Update Payment");
        this.payment = payment;
        isNewPayment = false;

        UserIDField.setText(String.valueOf(payment.getUser_id()));
        AmountField.setText(payment.getAmount());
        PaymentMethodField.setText(payment.getPayment_method());
        StatusComboBox.setValue(payment.getPayment_status());
    }

    public void setPaymentList(ObservableList<Payments> paymentList) {
        this.paymentList = paymentList;
    }

    public void handleSavePayment() {
        if (!validateInput()) {
            return;
        }
        
        String paymentID = UserIDField.getText();
        String amount = AmountField.getText();
        String paymentMethod = PaymentMethodField.getText();
        String paymentStatus = StatusComboBox.getValue();
        if (isNewPayment) {
            Payments payment = new Payments(Integer.parseInt(paymentID), amount, paymentMethod, paymentStatus);
            new Payments().INSERT_PAYMENT(payment);
            paymentList.add(payment);
        } else {
            payment.setUser_id(Integer.parseInt(paymentID));
            payment.setAmount(amount);
            payment.setPayment_method(paymentMethod);
            payment.setPayment_status(paymentStatus);
            Payments newPayment = payment;
            paymentList.set(paymentList.indexOf(payment), newPayment);
            System.out.println(newPayment);
            new Payments().UPDATE_PAYMENT(newPayment);
        }
        closeDialog();
    }

    public boolean validateInput() {
        String errorMessage = "";

        String paymentID = UserIDField.getText();
        String amount = AmountField.getText();
        String paymentMethod = PaymentMethodField.getText();
        String paymentStatus = StatusComboBox.getValue();

        if (paymentID.isEmpty()) {
            errorMessage += "User ID is required!\n";
        }
        if (amount.isEmpty() || !amount.matches("\\d*(\\.\\d+)?")) {
            errorMessage += "Invalid Amount!\n";
        }
        if (paymentMethod.isEmpty()) {
            errorMessage += "Payment Method is required!\n";
        }
        if (paymentStatus.isEmpty()) {
            errorMessage += "Payment Status is required!\n";
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