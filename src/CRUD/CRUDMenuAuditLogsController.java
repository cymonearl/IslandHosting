package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.util.ArrayList;

import Tables.Audit_Logs;
import Tables.Views.Audit_LogsView;

public class CRUDMenuAuditLogsController {

    @FXML private TableView<Audit_LogsView> auditLogsTable;

    private ObservableList<Audit_LogsView> auditLogsList = FXCollections.observableArrayList();
    private ArrayList<Audit_LogsView> auditLogs_ar = new ArrayList<>();

    private FilteredList<Audit_LogsView> filteredData;
    private Scene scene;
    private Stage stage;
    @FXML private TextField searchTextField;

    public void initialize() {
        initializeTableColumns();
        populateTable();
        filteredData = new FilteredList<>(auditLogsList, b -> true);
        auditLogsTable.setItems(filteredData);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
            });
        }       

    public void filterTable(String newValue) {
        filteredData.setPredicate(auditLogs -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (String.valueOf(auditLogs.getLogId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(auditLogs.getUserId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (auditLogs.getUserName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (auditLogs.getAction().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (auditLogs.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (auditLogs.getIpAddress().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            return false;
        });
    }
    
    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        auditLogsTable.getColumns().clear();
        // Map Users class fields to TableView columns
        TableColumn<Audit_LogsView, Integer> log_id = new TableColumn<>("Log ID");
        log_id.setCellValueFactory(new PropertyValueFactory<>("logId"));

        TableColumn<Audit_LogsView, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Audit_LogsView, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Audit_LogsView, String> action_type = new TableColumn<>("Action Type");
        action_type.setCellValueFactory(new PropertyValueFactory<>("action"));

        TableColumn<Audit_LogsView, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Audit_LogsView, String> ip_address = new TableColumn<>("IP Address");
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));

        TableColumn<Audit_LogsView, String> timestamp = new TableColumn<>("Timestamp");
        timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        auditLogsTable.getColumns().addAll(log_id, user_id, username, action_type, description, ip_address, timestamp);
        auditLogsTable.setItems(auditLogsList);
    }


    public void populateTable() {
        auditLogsList.clear();
        auditLogs_ar = new Audit_LogsView().getAudit_LogsWithUser();
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

    public void navigateToPayments(ActionEvent event) {
        navigateToScene(event, "CRUDPaymentsMenu.fxml");
    }

    public void openSummary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Summary.fxml"));
            Stage stage = new Stage();

            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void createAudit_logs() {
        showAudit_logsDialog(null);
    }

    public void updateAudit_logs() {
        Audit_LogsView selectedAudit_logs = auditLogsTable.getSelectionModel().getSelectedItem();
        if (selectedAudit_logs == null) {
            showAlert(Alert.AlertType.WARNING, "No Audit Logs Selected", "Please select an Audit Logs to update.");
            return;
        }

            Audit_LogsView selectedAudit_logs_original = auditLogs_ar.stream()
            .filter(user -> user.getLogId() == selectedAudit_logs.getLogId())
            .findFirst()
            .orElse(null);

            System.out.println(selectedAudit_logs_original);
        if (selectedAudit_logs_original != null) {
            showAudit_logsDialog(selectedAudit_logs_original); // Pass the selected user for editing
        }
    }

    public void deleteAudit_logs() {
        Audit_LogsView selectedAudit_logs = auditLogsTable.getSelectionModel().getSelectedItem();
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

    public void showAudit_logsDialog(Audit_LogsView audit_logs) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Audit_logsDialog.fxml"));
            Parent root = loader.load();

            Audit_logsDialogController controller = loader.getController();
            controller.getController(this);
            controller.setAudit_logs(audit_logs);

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