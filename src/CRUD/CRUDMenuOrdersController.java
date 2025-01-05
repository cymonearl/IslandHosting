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

import Tables.Orders;
import Tables.Views.OrderView;
public class CRUDMenuOrdersController {
    @FXML private TableView<OrderView> ordersTableView;

    private ObservableList<OrderView> orderList = FXCollections.observableArrayList();
    private ArrayList<OrderView> orderView_ar = new ArrayList<>();
    private FilteredList<OrderView> filteredData;

    private Scene scene;
    private Stage stage;
    @FXML private TextField searchTextField;

    public void initialize() {
        initializeTableColumns();
        populateTable();
        filteredData = new FilteredList<>(orderList, b -> true);
        ordersTableView.setItems(filteredData);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    public void filterTable(String searchText) {
        filteredData.setPredicate(order -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = searchText.toLowerCase();

            if (String.valueOf(order.getOrderId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(order.getUserId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getUserName().toLowerCase().contains(lowerCaseFilter)) {
                return true;                
            } else if (String.valueOf(order.getServerId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getServerName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getStartDate().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getEndDate().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(order.getTotalAmount()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getCreatedAt().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        ordersTableView.getColumns().clear();
        // Map Users class fields to TableView columns
        TableColumn<OrderView, Integer> order_id = new TableColumn<>("Order ID");
        order_id.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<OrderView, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<OrderView, Integer> user_name = new TableColumn<>("Username");
        user_name.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<OrderView, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("serverId"));

        TableColumn<OrderView, String> server_name = new TableColumn<>("Server Name");
        server_name.setCellValueFactory(new PropertyValueFactory<>("serverName"));

        TableColumn<OrderView, String> start_date = new TableColumn<>("Start Date");
        start_date.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<OrderView, String> end_date = new TableColumn<>("End Date");
        end_date.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<OrderView, Double> total_amount = new TableColumn<>("Total Amount");
        total_amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        TableColumn<OrderView, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<OrderView, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Add the columns to the TableView
        ordersTableView.getColumns().addAll(order_id, user_id, user_name, server_id, server_name, start_date, end_date, total_amount, status, created_at);
        ordersTableView.setItems(orderList); // Set ObservableList to TableView
    }

    public void populateTable() {
        orderList.clear();
        orderView_ar = new OrderView().getOrderView();
        orderList.addAll(orderView_ar);
    }

    public void navigateToUsers(ActionEvent event) {
        navigateToScene(event, "CRUDUsersMenu.fxml");
    }

    public void navigateToServers(ActionEvent event) {
        navigateToScene(event, "CRUDServersMenu.fxml");
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

    public void createOrder(ActionEvent event) {
        showOrdersDialog(null);
    }

    public void updateOrder(ActionEvent event) {
        OrderView selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "No Order Selected", "Please select an order to update.");
            return;
        }

        OrderView selectedOrder_original = orderView_ar.stream()
                .filter(user -> user.getOrderId() == selectedOrder.getOrderId())
                .findFirst()
                .orElse(null);

        if (selectedOrder_original != null) {
            showOrdersDialog(selectedOrder_original); // Pass the selected user for editing
        }
    }

    public void deleteOrder(ActionEvent event) {
        OrderView selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "No Order Selected", "Please select an order to delete.");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Order");
            alert.setHeaderText("Are you sure you want to delete this order?");
            if (alert.showAndWait().get() == ButtonType.OK) {
            orderList.remove(selectedOrder); // Remove the user from the TableView
            new Orders().DELETE_ORDER(selectedOrder); // Delete the user from the database
            }
        }
    }

    private void showOrdersDialog(OrderView orderView) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            OrdersDialogController controller = loader.getController();
            controller.setController(this);
            if (orderView != null) {
                controller.setOrder(orderView); // Existing user for editing
            }

            // Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(orderView == null ? "Create Order" : "Update Order");
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
