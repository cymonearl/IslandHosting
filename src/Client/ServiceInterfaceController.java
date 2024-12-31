package Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import Tables.Users;
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

    private Users user;

    public void setUser(Users user) {
        this.user = user;
    }

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
    private void onHelpClick(MouseEvent event) {
        System.out.println("Help clicked!");
        // Add logic for navigating to the Help page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpInterface.fxml"));
            Parent root = loader.load();
    
            HelpInterfaceController controller = loader.getController();
            controller.setUser(user);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onUserClick(MouseEvent event) {
        System.out.println("User clicked!");
        // Add logic for navigating to the User page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfile.fxml"));
            Parent root = loader.load();
    
            UserProfileController controller = loader.getController();
            controller.setUser(user);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToLandingPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();

            LandingPageController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Landing Page");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
                System.out.println("Error landingPage");
        }
    }

    @FXML
    private void onManilaClick() {
        System.out.println("Manila clicked!");
        openPopUp("manilaPopUp.fxml", "Manila");
    }

    @FXML
    private void onCebuClick() {
        System.out.println("Cebu clicked!");
        openPopUp("cebuPopUp.fxml", "Cebu");
    }

    @FXML
    private void onDavaoClick() {
        System.out.println("Davao clicked!");
        openPopUp("davaoPopUp.fxml", "Davao");
    }

    private void openPopUp(String fxml, String title) {
        try {
            // Load the pop-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());

            // Create a new stage for the pop-up
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.centerOnScreen();
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading pop-up." + fxml + " " + title);
        }
    }
}
