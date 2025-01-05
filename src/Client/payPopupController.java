package Client;

import Tables.Payments;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class payPopupController {
    @FXML private ComboBox<String> payment_method;
    @FXML private TextField amount;
    private int order_id;
    private InvoiceInterfaceController controller;
    
    public void initialize() {
        payment_method.getItems().addAll("GCash", "PayPal", "Credit Card", "Debit Card", "PayMaya");
        payment_method.setValue("GCash");
    }

    public void setPayment(String amount, int order_id) {
        this.amount.setText(amount);
        this.order_id = order_id;
    }

    public void handleSave() {
        new Payments().PAY_ORDER(new Payments(order_id , amount.getText(), payment_method.getValue(), "completed"));
        controller.populateOrders(controller.populateByFilter("pending"));
        closeDialog();
    }

    public void setController(InvoiceInterfaceController controller) {
        this.controller = controller;
    }

    public void closeDialog() {
        Stage stage = (Stage) payment_method.getScene().getWindow();
        stage.close();
    }
}
