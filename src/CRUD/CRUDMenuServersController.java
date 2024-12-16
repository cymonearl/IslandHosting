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
    @FXML private TableColumn<Servers, String> name;
    @FXML private TableColumn<Servers, String> hardware_type;
    @FXML private TableColumn<Servers, Integer> ram_gb;
    @FXML private TableColumn<Servers, Integer> storage_gb;
    @FXML private TableColumn<Servers, Double> price_per_month;
    @FXML private TableColumn<Servers, String> specs;
    @FXML private TableColumn<Servers, String> status;
    @FXML private TableColumn<Servers, String> created_at;

    // Data and Stage Properties
    private ObservableList<Servers> serverList = FXCollections.observableArrayList();
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
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        hardware_type.setCellValueFactory(new PropertyValueFactory<>("hardware_type"));
        ram_gb.setCellValueFactory(new PropertyValueFactory<>("ram_gb"));
        storage_gb.setCellValueFactory(new PropertyValueFactory<>("storage_gb"));
        price_per_month.setCellValueFactory(new PropertyValueFactory<>("price_per_month"));
        specs.setCellValueFactory(new PropertyValueFactory<>("specs"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        serversTableView.setItems(serverList); // Set ObservableList to TableView
    }

    private void populateTable() {
            servers_ar = new Servers().SELECT_ALL_SERVERS();
        for (Servers server : servers_ar) {
            serverList.add(server);
        }
    }

    // ========= Navigation Methods =========
    public void navigateToUsers(ActionEvent event) {
        navigateToScene(event, "CRUDUsersMenu.fxml");
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
            Double width = stage.getWidth();
            Double height = stage.getHeight();
            scene = new Scene(loader.load());
            stage.setScene(scene);
            if (!fxmlFile.equals("../LoginMenu.fxml")) {
                stage.setWidth(width);
                stage.setHeight(height);
            } else {
                stage.centerOnScreen();
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========= CRUD Functions =========
    public void createServer(ActionEvent event) {
        showServerDialog(null); // Pass null to create a new user
    }

    public void updateServer(ActionEvent event) {
        Servers selectedServer = serversTableView.getSelectionModel().getSelectedItem();
        if (selectedServer == null) {
            showAlert(Alert.AlertType.WARNING, "No User Selected", "Please select a user to update.");
            return;
        }

        // Find the original `Users` object in the `users_ar` list
        Servers selectedServer_original = servers_ar.stream()
                .filter(user -> user.getServer_id() == selectedServer.getServer_id())
                .findFirst()
                .orElse(null);

        if (selectedServer_original != null) {
            showServerDialog(selectedServer_original); // Pass the selected user for editing
        }
    }

    public void deleteServer(ActionEvent event) {
        Servers selectedServer = serversTableView.getSelectionModel().getSelectedItem();
        if (selectedServer == null) {
            showAlert(Alert.AlertType.WARNING, "No User Selected", "Please select a user to delete.");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText("Are you sure you want to delete this user?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                serverList.remove(selectedServer); // Remove from TableView
                new Servers().DELETE_SERVER(selectedServer); // Remove from database
            }
        }
    }
    private void showServerDialog(Servers servers) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            ServerDialogController controller = loader.getController();
            controller.setServerList(serverList);
            if (servers != null) {
                controller.setServer(servers); // Existing user for editing
            }

            // Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(servers == null ? "Create Server" : "Update Server");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(serversTableView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
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

    public void logout(ActionEvent event) {
        try {
            navigateToScene(event, "../LoginMenu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}