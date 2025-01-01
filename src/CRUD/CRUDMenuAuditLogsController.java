package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.util.ArrayList;

import Tables.Audit_Logs;
public class CRUDMenuAuditLogsController {

    @FXML private TableView<Audit_Logs> auditLogsTable;
    @FXML private Label CRUD;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button viewButton;
    boolean userViewMode = false;

    private ObservableList<Audit_Logs> auditLogsList = FXCollections.observableArrayList();
    private ArrayList<Audit_Logs> auditLogs_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    public void initialize() {
        CRUD.setText("CRUD");
        initializeTableColumns();
        populateTable();
        createButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        viewButton.setText("User View");

    }

    public void changeView(ActionEvent event) {
        auditLogsTable.getColumns().clear();

        if (userViewMode) {
            userViewMode = false;
            auditLogsList.clear();
            initialize();
        } else {
            auditLogsList.clear();
            userViewMode = true;
            initializeTableColumnsUV();
            CRUD.setText("User View");
            populateTable();
            createButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            viewButton.setText("CRUD");
        }       
    }
    
    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        // Map Users class fields to TableView columns
        TableColumn<Audit_Logs, Integer> log_id = new TableColumn<>("Log ID");
        log_id.setCellValueFactory(new PropertyValueFactory<>("log_id"));

        TableColumn<Audit_Logs, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Audit_Logs, String> action_type = new TableColumn<>("Action Type");
        action_type.setCellValueFactory(new PropertyValueFactory<>("action_type"));

        TableColumn<Audit_Logs, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Audit_Logs, String> ip_address = new TableColumn<>("IP Address");
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ip_address"));

        TableColumn<Audit_Logs, String> timestamp = new TableColumn<>("Timestamp");
        timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        auditLogsTable.getColumns().addAll(log_id, user_id, action_type, description, ip_address, timestamp);
        auditLogsTable.setItems(auditLogsList);
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumnsUV() {
        // Map Users class fields to TableView columns
        TableColumn<Audit_Logs, Integer> log_id = new TableColumn<>("Log ID");
        log_id.setCellValueFactory(new PropertyValueFactory<>("log_id"));

        TableColumn<Audit_Logs, String> action_type = new TableColumn<>("Action Type");
        action_type.setCellValueFactory(new PropertyValueFactory<>("action_type"));

        TableColumn<Audit_Logs, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Audit_Logs, String> ip_address = new TableColumn<>("IP Address");
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ip_address"));

        TableColumn<Audit_Logs, String> timestamp = new TableColumn<>("Timestamp");
        timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        auditLogsTable.getColumns().addAll(log_id, action_type, description, ip_address, timestamp);
        auditLogsTable.setItems(auditLogsList);
    }


    public void populateTable() {
        auditLogs_ar = new Audit_Logs().SELECT_ALL_AUDIT_LOGS();
        auditLogsList.addAll(auditLogs_ar);
    }

    public void navigateToUsers(ActionEvent event) {
        navigateToScene(event, "CRUDUsersMenu.fxml");
    }
    
    public void navigateToServers(ActionEvent event) {
        navigateToScene(event, "CRUDServersMenu.fxml");
    }

    public void navigateToOrders(ActionEvent event) {
        navigateToScene(event, "CRUDOrdersMenu.fxml");
    }

    public void navigateToTickets(ActionEvent event) {
        navigateToScene(event, "CRUDSupportTicketsMenu.fxml");    
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
                stage.setHeight(300);
                stage.centerOnScreen();
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAudit_logs() {
        showAudit_logsDialog(null);
    }

    public void updateAudit_logs() {
        Audit_Logs selectedAudit_logs = auditLogsTable.getSelectionModel().getSelectedItem();
        if (selectedAudit_logs == null) {
            showAlert(Alert.AlertType.WARNING, "No Audit Logs Selected", "Please select an Audit Logs to update.");
            return;
        }
    }

    public void deleteAudit_logs() {
        Audit_Logs selectedAudit_logs = auditLogsTable.getSelectionModel().getSelectedItem();
        if (selectedAudit_logs == null) {
            showAlert(Alert.AlertType.WARNING, "No Audit Logs Selected", "Please select an Audit Logs to delete.");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Audit Logs");
            alert.setHeaderText("Are you sure you want to delete this Audit Logs?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                auditLogsList.remove(selectedAudit_logs); // Remove from TableView
                new Audit_Logs().DELETE_AUDIT_LOG(selectedAudit_logs); // Remove from database
            }
        }
    }

    public void showAudit_logsDialog(Audit_Logs audit_logs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Audit_logsDialog.fxml"));
            Parent root = loader.load();

            Audit_logsDialogController controller = loader.getController();
            controller.setAudit_logsList(auditLogsList);
            if (audit_logs != null) {
                controller.setAudit_logs(audit_logs); // Existing user for editing
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(audit_logs == null ? "Create Audit Logs" : "Update Audit Logs");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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