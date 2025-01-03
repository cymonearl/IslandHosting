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
import java.util.ArrayList;

import Tables.Orders;
import Tables.Servers;
import Tables.SupportTicket;
import Tables.Users;
public class ServiceInterfaceController {

    // Header Labels
    @FXML private Label servicesLabel;
    @FXML private Label invoiceLabel;
    @FXML private Label ticketsLabel;
    @FXML private Label helpLabel;
    @FXML private Label userLabel;
    @FXML private ImageView islandHostIcon;
    // Service Details Labels
    @FXML private Label manilaLabel;
    @FXML private VBox Manila;
    @FXML private  VBox Davao;
    @FXML private  VBox Cebu;
    @FXML private Label davaoLabel;
    @FXML private Label cebuLabel;
    @FXML private Label davaoCount;
    @FXML private Label manilaCount;
    @FXML private Label cebuCount;

    private Users user;
    private ArrayList<Orders> orders;
    private ArrayList<Servers> servers = new Servers().AVAILABLE_SERVERS();

    public void setUser(Users user, ArrayList<Orders> orders) {
        this.user = user;
        this.orders = orders;
    }

    public void setUser(Users user, Orders order) {
        this.user = user;
        this.orders.add(order);
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

        countStocks();
    }

    private void countStocks() {
        int m = 0, c = 0, d = 0;

        for (Servers server : servers) {
            if (server.getName().toLowerCase().equals("manila"))
                m++;
            if (server.getName().toLowerCase().equals("davao"))
                d++;
            if (server.getName().toLowerCase().equals("cebu"))
                c++;            
        }

        manilaCount.setText(String.valueOf(m) + " Available");
        davaoCount.setText(String.valueOf(d) + " Available");
        cebuCount.setText(String.valueOf(c) + " Available");
    }

    @FXML
    private void onInvoiceClick(MouseEvent event) {
        System.out.println("Invoice clicked!");
        // Add logic for navigating to the Invoice page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InvoiceInterface.fxml"));
            Parent root = loader.load();
    
            InvoiceInterfaceController controller = loader.getController();
            controller.setUser(user, orders);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onTicketsClick(MouseEvent event) {
        System.out.println("Tickets clicked!");
        // Add logic for navigating to the Tickets page
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketsInterface.fxml"));
            Parent root = loader.load();
    
            TicketsInterfaceController controller = loader.getController();
            controller.setUser(user, orders);
            controller.populateTickets(new SupportTicket().SELECT_USER_SUPPORT_TICKETS(user.getUser_id()));
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHelpClick(MouseEvent event) {
        System.out.println("Help clicked!");
        // Add logic for navigating to the Help page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpInterface.fxml"));
            Parent root = loader.load();
    
            HelpInterfaceController controller = loader.getController();
            controller.setUser(user, orders);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void invoiceClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InvoiceInterface.fxml"));
            Parent root = loader.load();
    
            InvoiceInterfaceController controller = loader.getController();
            controller.setUser(user, orders);
    
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
            controller.setUser(user, orders);
    
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
            controller.setUser(user, orders);

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
        try {
            System.out.println("Manila clicked!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manilaPopUp.fxml"));
            Scene scene = new Scene(loader.load());
    
            // Create a new stage for the pop-up
            ManilaPopUpController controller = loader.getController();
            controller.setUser(user);
    
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Manila");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.centerOnScreen();
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCebuClick() {
        System.out.println("Cebu clicked!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cebuPopUp.fxml"));
            Scene scene = new Scene(loader.load());
    
            // Create a new stage for the pop-up
            CebuPopUpController controller = loader.getController();
            controller.setUser(user);
    
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Cebu");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.centerOnScreen();
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDavaoClick() {
        System.out.println("Davao clicked!");
        try {
            System.out.println("Manila clicked!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("davaoPopUp.fxml"));
            Scene scene = new Scene(loader.load());
    
            // Create a new stage for the pop-up
            DavaoPopUpController controller = loader.getController();
            controller.setUser(user);
    
            Stage popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.setTitle("Davao");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it a modal dialog
            popupStage.centerOnScreen();
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPopUp(String fxml, String title) {
        try {
            // Load the pop-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());

            // Create a new stage for the pop-up
            ManilaPopUpController controller = loader.getController();
            controller.setUser(user);

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
