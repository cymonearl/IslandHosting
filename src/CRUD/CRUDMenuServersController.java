package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import Tables.Servers;

public class CRUDMenuServersController {

    // UI Components
    @FXML private TableView<Servers> serversTableView;
    @FXML private TableColumn<Servers, Integer> server_id;

    // Data and Stage Properties
    private ObservableList<Servers> userList = FXCollections.observableArrayList();
    private ArrayList<Servers> servers_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    // ========= Initialization =========
    public void initialize() {
        initializeTableColumns();
        populateTable();
    }

    private void initializeTableColumns() {
        // Map Users class fields to TableView columns

    }

    private void populateTable() {
            // TODO: Fetch servers from database and populate table
    }

    // ========= Navigation Methods =========
    public void navigateToServers(ActionEvent event) {
        navigateToScene(event, "CRUDServersMenu.fxml");
    }

    public void navigateToOrders(ActionEvent event) {
        navigateToScene(event, "CRUDOrdersMenu.fxml");
    }

    public void navigateToAuditLogs(ActionEvent event) {
        navigateToScene(event, "CRUDAuditLogsMenu.fxml");
    }

    private void navigateToScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========= CRUD Functions =========
    private void showUserDialog(Servers servers) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            UserDialogController controller = loader.getController();
            controller.setUserList(userList);
            if (servers != null) {
                controller.setUser(servers); // Existing user for editing
            }

            // Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(servers == null ? "Create User" : "Update User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(serversTableView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========= Utility Methods =========
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}