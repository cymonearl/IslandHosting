package CRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class CRUDMenuAuditLogsController {
    
    private Scene scene;
    private Stage stage;

    public void Orders(ActionEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDOrdersMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Users(ActionEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDUsersMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Servers(ActionEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDServersMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }
    }
}