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
    @FXML private TableColumn<Audit_Logs, Integer> log_id;
    @FXML private TableColumn<Audit_Logs, Integer> user_id;
    @FXML private TableColumn<Audit_Logs, String> action_type;
    @FXML private TableColumn<Audit_Logs, String> description;
    @FXML private TableColumn<Audit_Logs, String> ip_address;

    private ObservableList<Audit_Logs> auditLogsList = FXCollections.observableArrayList();
    private ArrayList<Audit_Logs> auditLogs_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    public void initialize() {
        initializeTableColumns();
        populateTable();    
    }
    
    public void initializeTableColumns() {
        log_id.setCellValueFactory(new PropertyValueFactory<>("log_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        action_type.setCellValueFactory(new PropertyValueFactory<>("action_type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ip_address.setCellValueFactory(new PropertyValueFactory<>("ip_address"));
        auditLogsTable.setItems(auditLogsList);
    }

    public void populateTable() {
        auditLogs_ar = new Audit_Logs().SELECT_ALL_AUDIT_LOGS();
        for (Audit_Logs audit_log : auditLogs_ar) {
            auditLogsList.add(audit_log);
        }
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
    
    public void navigateToScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Double width = stage.getWidth();
            Double height = stage.getHeight();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}