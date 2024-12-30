package Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceInterfaceController {

    // Header Labels
    @FXML
    private Label servicesLabel;

    @FXML
    private Label invoiceLabel;

    @FXML
    private Label ticketsLabel;

    @FXML
    private Label helpLabel;

    @FXML
    private Label userLabel;

    @FXML
    private ImageView islandHostIcon;

    // Service Details Labels
    @FXML
    private Label manilaLabel;

    @FXML
    private VBox Manila;

    @FXML
    private  VBox Davao;

    @FXML
    private  VBox Cebu;

    @FXML
    private Label davaoLabel;

    @FXML
    private Label cebuLabel;

    @FXML
    public void initialize() {
        // Initialization logic (if needed)
        System.out.println("Landing Page Initialized");

        // Example: Set default text for labels dynamically if needed
        servicesLabel.setText("Services");
        invoiceLabel.setText("Invoice");
        ticketsLabel.setText("Tickets");
        helpLabel.setText("Help");
        userLabel.setText("User");

        manilaLabel.setText("Manila");
        davaoLabel.setText("Davao");
        cebuLabel.setText("Cebu");
    }

    // Example Methods for Navigation or Event Handling

    @FXML
    private void onServicesClick() {
        System.out.println("Services clicked!");
        // Add logic for navigating to the Services page
    }

    @FXML
    private void onInvoiceClick() {
        System.out.println("Invoice clicked!");
        // Add logic for navigating to the Invoice page
    }

    @FXML
    private void onTicketsClick() {
        System.out.println("Tickets clicked!");
        // Add logic for navigating to the Tickets page
    }

    @FXML
    private void onHelpClick() {
        System.out.println("Help clicked!");
        // Add logic for navigating to the Help page
    }

    @FXML
    private void onUserClick() {
        System.out.println("User clicked!");
        // Add logic for navigating to the User page
    }

    @FXML
    private void onIslandHostClick(){
        try {
            // Load the Manila pop-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Manila Pop-Up");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Manila pop-up.");
        }
    }

    @FXML
    private void onManilaClick() {
        System.out.println("Manila clicked!");
        try {
            // Load the Manila pop-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manilaPopUp.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Manila Pop-Up");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Manila pop-up.");
        }
    }

    @FXML
    private void onCebuClick() {
        System.out.println("Cebu clicked!");
        try {
            // Load the Manila pop-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cebuPopUp.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Cebu Pop-Up");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Cebu pop-up.");
        }
    }

    @FXML
    private void onDavaoClick() {
        System.out.println("Davao clicked!");
        try {
            // Load the Manila pop-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("davaoPopUp.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Davao Pop-Up");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Davao pop-up.");
        }
    }
}
