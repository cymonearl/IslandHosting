package CRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class CRUDMenuOrdersController {

    @FXML
    private Scene scene;
    private Parent root;
    private Stage stage;

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
            root = FXMLLoader.load(getClass().getResource("CRUDServersMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        } 
    }
    public void Auditlogs(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("CRUDAuditLogsMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }    
    }
}