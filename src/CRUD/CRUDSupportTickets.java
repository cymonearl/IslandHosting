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

public class CRUDSupportTickets {
    @FXML private TableView<SupportTicket> supportTicketsTable;
    @FXML private Label CRUD;
    @FXML private Button viewButton;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    boolean ticketViewMode = false;

    
    private ObservableList<SupportTicket> supportTicketList = FXCollections.observableArrayList();
    private ArrayList<SupportTicket> supportTickets_ar = new ArrayList<>(); // Initialize the list
    private FilteredList<SupportTicket> filteredData;    
    private Scene scene;
    private Stage stage;

    @FXML private TextField searchTextField;

    public void initialize() {
        CRUD.setText("CRUD");
        initializeTableColumns();
        populateAllTickets();
        createButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        viewButton.setText("User View");
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
            if (String.valueOf(ticket.getTicket_id()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(ticket.getUser_id()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (String.valueOf(ticket.getServer_id()).toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getSubject().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getDescription().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getStatus().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            } else if (ticket.getPriority().toLowerCase().contains(lowerCaseSearchText)) {
                return true;
            }
            return false; // Does not match
        });
    }
        
    public void changeView(ActionEvent event) {
        supportTicketsTable.getColumns().clear();

        if (ticketViewMode) {
            ticketViewMode = false;
            supportTicketList.clear();
            initialize();
        } else {
            supportTicketList.clear();
            ticketViewMode = true;
            initializeTableColumnsUV();
            CRUD.setText("User View");
            populateAllTickets();
            createButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            viewButton.setText("CRUD");
            filteredData = new FilteredList<>(supportTicketList, b -> true);
            supportTicketsTable.setItems(filteredData);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterTable(newValue);
            });    
        }       
    }
        
    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        TableColumn<SupportTicket, Integer> ticket_id = new TableColumn<>("Ticket ID");
        ticket_id.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));

        TableColumn<SupportTicket, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<SupportTicket, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));

        TableColumn<SupportTicket, String> title = new TableColumn<>("Subject");
        title.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<SupportTicket, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<SupportTicket, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<SupportTicket, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        TableColumn<SupportTicket, String> resolved_at = new TableColumn<>("Resolved At");
        resolved_at.setCellValueFactory(new PropertyValueFactory<>("resolved_at"));

        // Add the columns to the TableView
        supportTicketsTable.getColumns().addAll(ticket_id, user_id, server_id, title, description, status, created_at, resolved_at);

        supportTicketsTable.setItems(supportTicketList); // Set ObservableList to TableView
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumnsUV() {
        // Map Users class fields to TableView columns
        TableColumn<SupportTicket, Integer> ticket_id = new TableColumn<>("Ticket ID");
        ticket_id.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));

        TableColumn<SupportTicket, String> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<SupportTicket, String> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));

        TableColumn<SupportTicket, String> subject = new TableColumn<>("Subject"); // Use "subject" here
        subject.setCellValueFactory(new PropertyValueFactory<>("subject")); // Map to "title" property

        TableColumn<SupportTicket, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        supportTicketsTable.getColumns().addAll(ticket_id, user_id, server_id, subject, status);

        supportTicketsTable.setItems(supportTicketList); // Set ObservableList to TableView
    }

    private void populateAllTickets() {
        supportTicketList.clear();
        supportTickets_ar.clear(); // Clear the ArrayList as well
        supportTickets_ar.addAll(new SupportTicket().SELECT_ALL_SUPPORT_TICKETS()); // Populate the ArrayList
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
        SupportTicket selectedTicket = supportTicketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showAlert(Alert.AlertType.WARNING, "No Ticket Selected", "Please select a Ticket to update.");
            return;
        }

        // Find the original `SupportTicket` object in the `supportTickets_ar` list
        SupportTicket selectedTicket_original = supportTickets_ar.stream()
            .filter(ticket -> ticket.getTicket_id() == selectedTicket.getTicket_id())
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
        SupportTicket selectedTicket = supportTicketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Ticket");
            alert.setHeaderText("Are you sure you want to delete this ticket?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                
                if (new SupportTicket().DELETE_SUPPORT_TICKET(selectedTicket)) { //Remove from database first
                     // Remove from TableView and supportTickets_ar
                     supportTicketList.remove(selectedTicket);
                     supportTickets_ar.removeIf(ticket -> ticket.getTicket_id() == selectedTicket.getTicket_id());
                }
                else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Could not delete ticket from database.");
                }
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Delete Ticket", "Please select a ticket to delete.");
        }
    }

    private void showUserDialog(SupportTicket ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            SupportTicketDialogController controller = loader.getController();
            controller.setTicketList(supportTicketList);
             
            if (ticket != null) {
                controller.setTicket(ticket); // Existing ticket for editing
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