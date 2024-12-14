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
    private ObservableList<Servers> userList = FXCollections.observableArrayList();
    private ArrayList<Servers> servers_ar = new Servers().SELECT_ALL_SERVERS();
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
        serversTableView.setItems(userList); // Set ObservableList to TableView
    }

    private void populateTable() {
            // TODO: Fetch servers from database and populate table
        for (Servers server : servers_ar) {
            userList.add(server);
            System.out.println(server);
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
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========= CRUD Functions =========
    private void showServerDialog(Servers servers) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            ServerDialogController controller = loader.getController();
            controller.setServerList(userList);
            if (servers != null) {
                controller.setServer(servers); // Existing user for editing
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