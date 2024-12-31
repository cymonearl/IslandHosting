package CRUD;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import java.util.*;

import Tables.SupportTicket;

public class CRUDSupportTickets {
    @FXML private TableView<SupportTicket> supportTicketsTable;
    @FXML private Label CRUD;
    @FXML private Button viewButton;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    boolean userViewMode = false;

    
    private ObservableList<SupportTicket> supportTicketList = FXCollections.observableArrayList();
    private ArrayList<SupportTicket> supportTickets_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    public void initialize() {
        CRUD.setText("CRUD");
        initializeTableColumns();
        populateAllTickets();
        createButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        viewButton.setText("User View");
    }
        
    public void changeView(ActionEvent event) {
        supportTicketsTable.getColumns().clear();

        if (userViewMode) {
            userViewMode = false;
            supportTicketList.clear();
            initialize();
        } else {
            supportTicketList.clear();
            userViewMode = true;
            initializeTableColumnsUV();
            CRUD.setText("User View");
            populateAllTickets();
            createButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            viewButton.setText("CRUD");
        }       
    }
        
    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        TableColumn<SupportTicket, Integer> user_id = new TableColumn<>("Ticket ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));

        TableColumn<SupportTicket, String> username = new TableColumn<>("User ID");
        username.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<SupportTicket, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<SupportTicket, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<SupportTicket, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<SupportTicket, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        TableColumn<SupportTicket, String> updated_at = new TableColumn<>("Updated At");
        updated_at.setCellValueFactory(new PropertyValueFactory<>("updated_at"));

        // Add the columns to the TableView
        supportTicketsTable.getColumns().addAll(user_id, username, title, description, status, created_at, updated_at);

        supportTicketsTable.setItems(supportTicketList); // Set ObservableList to TableView
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumnsUV() {
        // Map Users class fields to TableView columns
        TableColumn<SupportTicket, Integer> ticket_id = new TableColumn<>("Ticket ID");
        ticket_id.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));

        TableColumn<SupportTicket, String> subject = new TableColumn<>("Subject");
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<SupportTicket, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        supportTicketsTable.getColumns().addAll(ticket_id, subject, status);

        supportTicketsTable.setItems(supportTicketList); // Set ObservableList to TableView
    }

    private void populateAllTickets() {
        supportTicketList.clear();
        supportTicketList.addAll(new SupportTicket().SELECT_ALL_SUPPORT_TICKETS());
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