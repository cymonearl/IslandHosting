package com.example.finalalright;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController {

    // FXML components from the UI
    @FXML
    private Button User;

    @FXML
    private Button Orders;

    @FXML
    private Button Server;

    @FXML
    private Label BalanceLabelHere;

    @FXML
    private ListView<String> Table_Here;

    @FXML
    private ImageView UserIcon;

    /**
     * Initializes the controller class.
     * This method is called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Initialization logic here
        System.out.println("LandingPage initialized!");

        // Example: Set default values for some UI elements
        BalanceLabelHere.setText("₱0.00");
        Table_Here.getItems().addAll("Service 1", "Service 2", "Service 3");
    }

    /**
     * Handles the action when the "User" button is clicked.
     */
    @FXML
    private void onUserClick(ActionEvent event) {
    }

    /**
     * Handles the action when the "Orders" button is clicked.
     */
    @FXML
    private void onOrdersClick(ActionEvent event) {
        System.out.println("Orders button clicked!");
        try {
            // Load the userInterface.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finalalright/ServiceInterface.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);

            System.out.println("Navigated to User Interface!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add your logic for navigating to the Orders page or performing an action
    }

    /**
     * Handles the action when the "Server" button is clicked.
     */
    @FXML
    private void onServerClick(ActionEvent event) {
        System.out.println("Server button clicked!");
        // Add your logic for navigating to the Server page or performing an action
    }

}
