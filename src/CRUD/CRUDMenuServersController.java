package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    @FXML private Label CRUD;
    @FXML private Button viewButton;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    boolean userViewMode = false;

    // Data and Stage Properties
    private ObservableList<Servers> serverList = FXCollections.observableArrayList();
    private ArrayList<Servers> servers_ar = new ArrayList<>();
    private FilteredList<Servers> filteredData;
    private Scene scene;
    private Stage stage;

    @FXML private TextField searchTextField;

    // ========= Initialization =========
    public void initialize() {
        CRUD.setText("CRUD");
        initializeTableColumns();
        populateTable();
        createButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        viewButton.setText("User View");
        filteredData = new FilteredList<>(serverList, b -> true);
        serversTableView.setItems(filteredData);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    public void filterTable(String searchText) {
        filteredData.setPredicate(server -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            String lowerCaseSearchText = searchText.toLowerCase();
            if (String.valueOf(server.getServer_id()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (server.getName().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (server.getHardware_type().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(server.getRam_gb()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(server.getStorage_gb()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(server.getPrice_per_month()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(server.getStatus()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (server.getServer_location().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (server.getSpecs().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (server.getStatus().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            }
            return false;
            
        });
        serversTableView.setItems(filteredData);
    }

    public void changeView(ActionEvent event) {
        serversTableView.getColumns().clear();

        if (userViewMode) {
            userViewMode = false;
            serverList.clear();
            initialize();
        } else {
            serverList.clear();
            userViewMode = true;
            initializeTableColumnsUV();
            CRUD.setText("User View");
            populateAvailableServers();
            createButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            viewButton.setText("CRUD");
            filteredData = new FilteredList<>(serverList, b -> true);
            serversTableView.setItems(filteredData);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterTable(newValue);
            });    
        }       
    }

    @SuppressWarnings("unchecked")
    private void initializeTableColumns() {
        // Map Users class fields to TableView columns
        TableColumn<Servers, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));

        TableColumn<Servers, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Servers, String> hardware_type = new TableColumn<>("Hardware Type");
        hardware_type.setCellValueFactory(new PropertyValueFactory<>("hardware_type"));

        TableColumn<Servers, Integer> ram_gb = new TableColumn<>("RAM (GB)");
        ram_gb.setCellValueFactory(new PropertyValueFactory<>("ram_gb"));

        TableColumn<Servers, Integer> storage_gb = new TableColumn<>("Storage (GB)");
        storage_gb.setCellValueFactory(new PropertyValueFactory<>("storage_gb"));

        TableColumn<Servers, Double> price_per_month = new TableColumn<>("Price Per Month");
        price_per_month.setCellValueFactory(new PropertyValueFactory<>("price_per_month"));

        TableColumn<Servers, String> specs = new TableColumn<>("Specs");
        specs.setCellValueFactory(new PropertyValueFactory<>("specs"));

        TableColumn<Servers, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Servers, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        serversTableView.getColumns().addAll(server_id, name, hardware_type, ram_gb, storage_gb, price_per_month, specs, status, created_at);
        serversTableView.setItems(serverList); // Set ObservableList to TableView
    }

    @SuppressWarnings("unchecked")
    private void initializeTableColumnsUV() {
        // Map Users class fields to TableView columns
        TableColumn<Servers, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));
        
        TableColumn<Servers, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Servers, String> hardware_type = new TableColumn<>("Hardware Type");
        hardware_type.setCellValueFactory(new PropertyValueFactory<>("hardware_type"));
        
        TableColumn<Servers, Integer> ram_gb = new TableColumn<>("RAM (GB)");
        ram_gb.setCellValueFactory(new PropertyValueFactory<>("ram_gb"));
        
        TableColumn<Servers, Integer> storage_gb = new TableColumn<>("Storage (GB)");
        storage_gb.setCellValueFactory(new PropertyValueFactory<>("storage_gb"));
        
        TableColumn<Servers, Double> price_per_month = new TableColumn<>("Price Per Month");
        price_per_month.setCellValueFactory(new PropertyValueFactory<>("price_per_month"));
        
        TableColumn<Servers, String> specs = new TableColumn<>("Specs");
        specs.setCellValueFactory(new PropertyValueFactory<>("specs"));
        
        TableColumn<Servers, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        serversTableView.getColumns().addAll(server_id, name, hardware_type, ram_gb, storage_gb, price_per_month, specs, status);
        serversTableView.setItems(serverList); // Set ObservableList to TableView
    }

    private void populateTable() {
        servers_ar = new Servers().SELECT_ALL_SERVERS();
        serverList.addAll(servers_ar);
    }

    private void populateAvailableServers() {
        servers_ar = new Servers().AVAILABLE_SERVERS();
        serverList.addAll(servers_ar);
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

    public void navigateToTickets(ActionEvent event) {
        navigateToScene(event, "CRUDSupportTicketsMenu.fxml");    
    }

    public void navigateToPayments(ActionEvent event) {
        navigateToScene(event, "CRUDPaymentsMenu.fxml");
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
            stage.centerOnScreen();
            if (fxmlFile.equals("../LoginMenu.fxml")) {
                stage.setWidth(650);
                stage.setHeight(310);
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