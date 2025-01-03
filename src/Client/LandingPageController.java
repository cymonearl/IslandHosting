package Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import Tables.Servers;
import Tables.Users;
import Tables.Orders;

public class LandingPageController {

    // FXML components from the UI
    @FXML private Button User;
    @FXML private Button Orders;
    @FXML private Button Server;
    @FXML private Text name;
    @FXML private ListView<String> Table_Here;
    @FXML private ImageView UserIcon;
    @FXML private Label manilaCount;
    @FXML private Label davaoCount;
    @FXML private Label cebuCount;

    private Users user;
    private ArrayList<Orders> orders;

    /**
     * Initializes the controller class.
     * This method is called after the FXML file has been loaded.
     */
    public void setUser(Users user, ArrayList<Orders> orders) {
        this.user = user;
        this.orders = orders;
        updateUI(); 
    }

    public void setUser(Users user) {
        this.user = user;
        updateUI();
    }

    public Users getUser() {
        return user;
    }
    private void updateUI() {
        if (name != null) {
            name.setText(user.getUsername());
        }
    }
    @FXML
    public void initialize() {
        // Initialization logic here
        System.out.println("LandingPage initialized!");
        
        // Example: Set default values for some UI elements
        populateTable();        
    }
    
    public void getOrders() {
        orders = new Orders().USER_ORDERS(user.getUser_id());
    }

    public void populateTable() {
        Table_Here.getItems().clear();
        int m = 0;
        int d = 0;
        int c = 0;

        ArrayList<Servers> servers = new Servers().AVAILABLE_SERVERS();
        String[] serverNames = new String[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            serverNames[i] = servers.get(i).getName();
            if (servers.get(i).getName().toLowerCase().equals("manila"))
                m++;
            if (servers.get(i).getName().toLowerCase().equals("cebu"))
                c++;
            if (servers.get(i).getName().toLowerCase().equals("davao"))
                d++;
        }

        Table_Here.getItems().addAll(serverNames);
        manilaCount.setText(String.valueOf(m));
        cebuCount.setText(String.valueOf(c));
        davaoCount.setText(String.valueOf(d));
    }

    /**
     * Handles the action when the "User" button is clicked.
     */
    @FXML
    private void onUserClick(ActionEvent event) {
        System.out.println("User button clicked!");
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

    /**
     * Handles the action when the "Orders" button is clicked.
     */
    @FXML
    private void onOrdersClick(ActionEvent event) {
        System.out.println("Orders button clicked!");
        try {
            // Load the userInterface.fxml
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceInterface.fxml"));
            Parent root = loader.load();

            ServiceInterfaceController controller = loader.getController();
            controller.setUser(user, orders);
            
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene
            stage.setScene(scene);
            stage.centerOnScreen();
            System.out.println("Navigated to User Interface!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add your logic for navigating to the Orders page or performing an action
    }

    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../LoginMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }
}
