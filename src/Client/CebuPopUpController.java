package Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Tables.Orders;
import Tables.Servers;
import Tables.Users;

public class CebuPopUpController {

    @FXML
    private RadioButton bigasRadioButton;

    @FXML
    private RadioButton kakaninRadioButton1;

    @FXML
    private Button checkoutButton;

    @FXML
    private Text orderSummaryText;

    @FXML
    private Label billingCycleLabel;

    private ToggleGroup billingCycleGroup;

    private Users user;
    private ArrayList<Servers> servers = new Servers().AVAILABLE_SERVERS();

    public void setUser(Users user) {
        this.user = user;
    }

    @FXML
    private void initialize() {
        // Create the ToggleGroup and assign it to the radio buttons
        billingCycleGroup = new ToggleGroup();
        bigasRadioButton.setToggleGroup(billingCycleGroup);
        kakaninRadioButton1.setToggleGroup(billingCycleGroup);

        // Set default selection
        bigasRadioButton.setSelected(true);

        // Update labels based on the default selection
        updateBillingDetails();

        // Add a listener to update labels when selection changes
        billingCycleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateBillingDetails());
    }

    // Update the billing details (order summary and total due)
    private void updateBillingDetails() {
        Servers server = getInfo("cebu");
        if (billingCycleGroup.getSelectedToggle() == bigasRadioButton) {
            billingCycleLabel.setText("Monthly: " + server.getPrice_per_month());
            orderSummaryText.setText(
            String.format("- Hardware: %s\n- Ram: %s GB\n- Storage: %s GB\n- %s",
                server.getHardware_type(),
                server.getRam_gb(),
                server.getStorage_gb(),
                server.getSpecs())
            );
        } else if (billingCycleGroup.getSelectedToggle() == kakaninRadioButton1) {
            billingCycleLabel.setText("Monthly: " + (server.getPrice_per_month() - server.getPrice_per_month() * 0.1));
            orderSummaryText.setText(
            String.format("- %s\n\n- Hardware: %s\n- Ram: %s GB\n- Storage: %s GB\n- %s",
        "First Time User Discount, applied for first time server only",
                server.getHardware_type(),
                server.getRam_gb(),
                server.getStorage_gb(),
                server.getSpecs())
            );
        }
    }

    private Servers getInfo(String name) {
        for (Servers server : servers) {
            if (server.getName().toLowerCase().equals(name)) {
                return server;
            }
        }
        return null;
    }

    @FXML
    private void handleCheckOut() {
        // Get the selected billing cycle and total due
        String selectedBillingCycle = billingCycleLabel.getText();
        String totalDue = billingCycleLabel.getText().contains("Bigas") ? "P 100" : "P 90";

        // Perform checkout confirmation using JOptionPane
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Confirm Checkout?\n\nBilling Cycle: " + selectedBillingCycle + "\nTotal Due: " + totalDue,
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
                        ArrayList<Servers> servers = new Servers().AVAILABLE_SERVERS(1, 50);

            if (servers.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "No available servers at the moment.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Orders order = new Orders(String.valueOf(user.getUser_id()), String.valueOf(servers.getFirst()),  totalDue, "Pending");
            // // new Orders().INSERT_ORDER(order);
            // System.out.println(order);
            // ServiceInterfaceController controller = new ServiceInterfaceController();
            // controller.setUser(user, order);
            Stage stage = (Stage) checkoutButton.getScene().getWindow();
            stage.close();
        } else {
            // User cancelled checkout
            JOptionPane.showMessageDialog(
                    null,
                    "Checkout Cancelled.",
                    "Cancelled",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    @FXML
    private void onCancelClick() {
        // Close the pop-up window
        Stage stage = (Stage) checkoutButton.getScene().getWindow();
        stage.close();
    }
}
