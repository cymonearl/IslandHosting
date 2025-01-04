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
public class CRUDMenuOrdersController {
    @FXML private TableView<Orders> ordersTableView;
    @FXML private Label CRUD;
    @FXML private Button viewButton;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    boolean userViewMode = false;

    private ObservableList<Orders> orderList = FXCollections.observableArrayList();
    private ArrayList<Orders> orders_ar = new ArrayList<>();
    private FilteredList<Orders> filteredData;
    private Scene scene;
    private Stage stage;
    @FXML private TextField searchTextField;

    public void initialize() {
        CRUD.setText("CRUD");
        initializeTableColumns();
        populateTable();
        createButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        viewButton.setText("User View");
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
            if (String.valueOf(order.getOrder_id()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(order.getUser_id()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(order.getServer_id()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getStart_date().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getEnd_date().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(order.getTotal_amount()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
    }

    public void changeView(ActionEvent event) {
        ordersTableView.getColumns().clear();

        if (userViewMode) {
            userViewMode = false;
            orderList.clear();
            initialize();
        } else {
            orderList.clear();
            userViewMode = true;
            initializeTableColumnsUV();
            CRUD.setText("User View");
            populateTable();
            createButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            viewButton.setText("CRUD");
            filteredData = new FilteredList<>(orderList, b -> true);
            ordersTableView.setItems(filteredData);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterTable(newValue);
            });    
        }       
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        // Map Users class fields to TableView columns
        TableColumn<Orders, Integer> order_id = new TableColumn<>("Order ID");
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));

        TableColumn<Orders, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Orders, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));

        TableColumn<Orders, String> start_date = new TableColumn<>("Start Date");
        start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));

        TableColumn<Orders, String> end_date = new TableColumn<>("End Date");
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));

        TableColumn<Orders, Double> total_amount = new TableColumn<>("Total Amount");
        total_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));

        TableColumn<Orders, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Orders, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        ordersTableView.getColumns().addAll(order_id, user_id, server_id, start_date, end_date, total_amount, status, created_at);
        ordersTableView.setItems(orderList); // Set ObservableList to TableView
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumnsUV() {
        // Map Users class fields to TableView columns
        TableColumn<Orders, Integer> order_id = new TableColumn<>("Order ID");
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));

        TableColumn<Orders, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        TableColumn<Orders, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));

        TableColumn<Orders, String> start_date = new TableColumn<>("Start Date");
        start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));

        TableColumn<Orders, String> end_date = new TableColumn<>("End Date");
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));

        TableColumn<Orders, Double> total_amount = new TableColumn<>("Total Amount");
        total_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));    

        TableColumn<Orders, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Orders, String> created_at = new TableColumn<>("Created At");
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        ordersTableView.getColumns().addAll(order_id, user_id, server_id, start_date, end_date, total_amount, status, created_at);
        ordersTableView.setItems(orderList); // Set ObservableList to TableView
    }
    
    public void populateTable() {
        orders_ar = new Orders().SELECT_ALL_ORDERS();
        orderList.addAll(orders_ar);
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
        Orders selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "No Order Selected", "Please select an order to update.");
            return;
        }

        Orders selectedOrder_original = orders_ar.stream()
                .filter(user -> user.getOrder_id() == selectedOrder.getOrder_id())
                .findFirst()
                .orElse(null);

        if (selectedOrder_original != null) {
            showOrdersDialog(selectedOrder_original); // Pass the selected user for editing
        }
    }

    public void deleteOrder(ActionEvent event) {
        Orders selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
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

    private void showOrdersDialog(Orders order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            OrdersDialogController controller = loader.getController();
            controller.setOrderList(orderList);
            if (order != null) {
                controller.setOrder(order); // Existing user for editing
            }

            // Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(order == null ? "Create Order" : "Update Order");
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
