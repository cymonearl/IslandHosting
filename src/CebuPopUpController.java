package com.example.finalalright;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

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
        if (billingCycleGroup.getSelectedToggle() == bigasRadioButton) {
            billingCycleLabel.setText("Monthly: Bigas (P 100)");
            orderSummaryText.setText("- 4 GB RAM\n" +
                    "- Budget Hardware\n" +
                    "- Unlimited Slots\n" +
                    "- 50GB SSD Storage\n" +
                    "- Up to 120 Gbps DDoS Protection\n" +
                    "- 5 MySQL Databases\n" +
                    "- Intuitive Pterodactyl Panel\n" +
                    "- Auto/Pre-Installed jars\n" +
                    "- 99.99% Uptime SLA");
        } else if (billingCycleGroup.getSelectedToggle() == kakaninRadioButton1) {
            billingCycleLabel.setText("Monthly: Kakanin (P 90)");
            orderSummaryText.setText("- 4 GB RAM\n" +
                    "- Budget Hardware\n" +
                    "- Unlimited Slots\n" +
                    "- 50GB SSD Storage\n" +
                    "- Up to 120 Gbps DDoS Protection\n" +
                    "- 5 MySQL Databases\n" +
                    "- Intuitive Pterodactyl Panel\n" +
                    "- Auto/Pre-Installed jars\n" +
                    "- 99.99% Uptime SLA\n" +
                    "- Save 10%");
        }
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
