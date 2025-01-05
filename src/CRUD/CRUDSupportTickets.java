package CRUD;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import java.util.*;

import Tables.SupportTicket;
import Tables.Views.SupportTicketView;

public class CRUDSupportTickets {
    @FXML private TableView<SupportTicketView> supportTicketsTable;
    
    private ObservableList<SupportTicketView> supportTicketList = FXCollections.observableArrayList();
    private ArrayList<SupportTicketView> supportTickets_ar = new ArrayList<>(); // Initialize the list
    private FilteredList<SupportTicketView> filteredData;    
    private Scene scene;
    private Stage stage;

    @FXML private TextField searchTextField;

    public void initialize() {
        initializeTableColumns();
        populateAllTickets();
        filteredData = new FilteredList<>(supportTicketList, b -> true);
        supportTicketsTable.setItems(filteredData);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    private void filterTable(String searchText) {
        filteredData.setPredicate(ticket -> {
            // If search text is empty, show all tickets
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            // Convert search text to lowercase for case-insensitive search
            String lowerCaseSearchText = searchText.toLowerCase();

            // Check if any of the ticket's properties contain the search text
            if (String.valueOf(ticket.getTicketId()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(ticket.getUserId()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getUserName().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(ticket.getServerId()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (Objects.toString(ticket.getServerName(), "").toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getSubject().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getStatus().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            }

            return false; // Does not match
        });
    }
        
    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        supportTicketsTable.getColumns().clear();

        TableColumn<SupportTicketView, Integer> ticket_id = new TableColumn<>("Ticket ID");
        ticket_id.setCellValueFactory(new PropertyValueFactory<>("ticketId"));

        TableColumn<SupportTicketView, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<SupportTicketView, String> user_name = new TableColumn<>("Username");
        user_name.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<SupportTicketView, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("serverId"));

        TableColumn<SupportTicketView, String> server_name = new TableColumn<>("Server Name");
        server_name.setCellValueFactory(new PropertyValueFactory<>("serverName"));

        TableColumn<SupportTicketView, String> title = new TableColumn<>("Subject");
        title.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<SupportTicketView, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<SupportTicketView, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<SupportTicketView, String> resolved_at = new TableColumn<>("Resolved At");
        resolved_at.setCellValueFactory(new PropertyValueFactory<>("resolvedAt"));

        // Add the columns to the TableView
        supportTicketsTable.getColumns().addAll(ticket_id, user_id, user_name, server_id, server_name, title, status, created_at, resolved_at);

        supportTicketsTable.setItems(supportTicketList); // Set ObservableList to TableView
    }

    private void populateAllTickets() {
        supportTicketList.clear();
        supportTickets_ar = new SupportTicketView().getSupportTicketsWithUserAndServer(); // Populate the ArrayList
        supportTicketList.addAll(supportTickets_ar); // Populate the ObservableList
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

    public void createTicket() {
        showUserDialog(null); // Passing null for a new ticket creation
    }

    public void updateTicket() {
        SupportTicketView selectedTicket = supportTicketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showAlert(Alert.AlertType.WARNING, "No Ticket Selected", "Please select a Ticket to update.");
            return;
        }

        // Find the original `SupportTicket` object in the `supportTickets_ar` list
        SupportTicketView selectedTicket_original = supportTickets_ar.stream()
            .filter(ticket -> ticket.getTicketId() == selectedTicket.getTicketId())
            .findFirst()
            .orElse(null);
    
        if (selectedTicket_original != null) {
            showUserDialog(selectedTicket_original); // Pass the selected ticket for editing
        } else {
            // Handle the case where the selected ticket is not found in the original list (shouldn't happen normally)
            showAlert(Alert.AlertType.ERROR, "Error", "Could not find the selected ticket in the original list.");
        }
    }

    public void deleteTicket() {
        SupportTicketView selectedTicket = supportTicketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Ticket");
            alert.setHeaderText("Are you sure you want to delete this ticket?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                
                if (new SupportTicket().DELETE_SUPPORT_TICKET(selectedTicket)) { //Remove from database first
                     // Remove from TableView and supportTickets_ar
                     supportTicketList.remove(selectedTicket);
                     supportTickets_ar.removeIf(ticket -> ticket.getTicketId() == selectedTicket.getTicketId());
                }
                else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Could not delete ticket from database.");
                }
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Delete Ticket", "Please select a ticket to delete.");
        }
    }

    private void showUserDialog(SupportTicketView ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            SupportTicketDialogController controller = loader.getController();
            controller.setController(this);
             
            if (ticket != null) {
                controller.setTicket(ticket); // Existing user for editing
            }

            // Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(ticket == null ? "Create Support Ticket" : "Update Support Ticket");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(supportTicketsTable.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.setAlwaysOnTop(true);
            dialogStage.setResizable(false);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();


            // Refresh data in the table after closing the dialog
            populateAllTickets();


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