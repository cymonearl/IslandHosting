package Client;

import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import Tables.Users;
public class HelpInterfaceController {

    Users user;

    public void setUser(Users user) {
        this.user = user;
    }

    public void servicesClicked() {
        navigateToPage("ServicesInterface.fxml", "Services");
    }

    public void invoiceClicked() {

    }

    public void ticketsClicked() {

    }
    
    public void helpClicked() {
        navigateToPage("HelpInterface.fxml", "Help");
    }

    public void UserClicked() {
        navigateToPage("UserInterface.fxml", "User");
    }

    private void navigateToPage(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}