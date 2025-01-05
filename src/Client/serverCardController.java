package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class serverCardController {

    @FXML
    private VBox Manila;

    @FXML
    private Label planLabel, descriptionLabel, priceLabel, availableLabel;

    @FXML
    private HBox planHBox;

    /**
     * Called when the Manila VBox is clicked.
     */
    @FXML
    private void onManilaClick() {
        // Add functionality for when the plan is clicked.
        System.out.println("Manila Plan clicked!");
        // Example: You could open a detailed view or log the event.
    }

    /**
     * Updates the plan name.
     *
     * @param planName The new plan name.
     */
    public void setPlanName(String planName) {
        planLabel.setText(planName); // set the new plan name
    }

    /**
     * Updates the plan description.
     *
     * @param description The new plan description.
     */
    public void setPlanDescription(String description) {
        descriptionLabel.setText(description); // set the new plan description
    }

    /**
     * Updates the plan price.
     *
     * @param price The new price.
     */
    public void setPlanPrice(String price) {
        priceLabel.setText(price); // set the new price of the plan
    }

    /**
     * Updates the availability status.
     *
     * @param availability The new availability status.
     */
    public void setPlanAvailability(String availability) {
        availableLabel.setText(availability); // set the availability (0, 1, 2,...)
    }

    /**
     * Sets the background color of the HBox (`planHBox`).
     *
     * @param color The color to set (e.g., "red", "#FF5733").
     */
    public void setPlanHBoxColor(String color) {
        planHBox.setStyle("-fx-background-color: " + color + ";"); // set the color of the HBox
    }

    /**
     * Initializes the controller.
     * This method can be used to set up default values or configurations.
     */
    @FXML
    public void initialize() {
        // Example: Set default values for the labels if needed.
        System.out.println("PlanController initialized.");
    }

    @FXML
    public void initialize(String planName, String description, String price, String availability, String color) {
        planLabel.setText(planName);
        descriptionLabel.setText(description);
        priceLabel.setText(price);
        availableLabel.setText(availability);

        // Use default color (red) if the color parameter is null or empty
        if (color != null && !color.isEmpty()) {
            setPlanHBoxColor(color);
        }

        System.out.println("Custom initialize called with values and color: " + color);
    }
}
