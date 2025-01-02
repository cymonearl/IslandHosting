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

import javax.swing.text.html.ImageView;

import Tables.Orders;
import Tables.Servers;
import Tables.Users;

public class ManilaPopUpController {

    @FXML
    private RadioButton bigasRadioButton;

    @FXML
    private RadioButton kakaninRadioButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private Label billingCycleLabel;

    @FXML
    private Label totalDueLabel;

    @FXML
    private ImageView CancelButton;

    @FXML
    private Text orderSummaryText;

    private ToggleGroup billingToggleGroup;
    private Users user;

    // Initialize the pop-up's default values
    @FXML
    private void initialize() {
        // Create the ToggleGroup and assign it to the radio buttons
        billingToggleGroup = new ToggleGroup();
        bigasRadioButton.setToggleGroup(billingToggleGroup);
        kakaninRadioButton.setToggleGroup(billingToggleGroup);

        // Set default selection
        bigasRadioButton.setSelected(true);

        // Update labels based on the default selection
        updateBillingDetails();

        // Add a listener to update labels when selection changes
        billingToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateBillingDetails());
    }

    public void setUser(Users user) {
        this.user = user;
    }

    // Update the billing details (order summary and total due)
    private void updateBillingDetails() {
        if (billingToggleGroup.getSelectedToggle() == bigasRadioButton) {
            billingCycleLabel.setText("Monthly: Bigas (P 250)");
            orderSummaryText.setText("- 4 GB RAM\n" +
                    "- Budget Hardware\n" +
                    "- Unlimited Slots\n" +
                    "- 50GB SSD Storage\n" +
                    "- Up to 120 Gbps DDoS Protection\n" +
                    "- 5 MySQL Databases\n" +
                    "- Intuitive Pterodactyl Panel\n" +
                    "- Auto/Pre-Installed jars\n" +
                    "- 99.99% Uptime SLA");
        } else if (billingToggleGroup.getSelectedToggle() == kakaninRadioButton) {
            billingCycleLabel.setText("Monthly: Kakanin (P 225)");
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

    // Handle the "Check Out" button click
    @FXML
    private void handleCheckOut() {
        // Get the selected billing cycle and total due
        String selectedBillingCycle = billingCycleLabel.getText();
        String totalDue = billingCycleLabel.getText().contains("Bigas") ? "P 250" : "P 225";

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
            ArrayList<Servers> servers = new Servers().AVAILABLE_SERVERS(4, 50);

            if (servers.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "No available servers at the moment.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // System.out.println(totalDue);
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
    private void onCacelClick(){
        Stage stage = (Stage) checkoutButton.getScene().getWindow();
        stage.close();
    };
}
