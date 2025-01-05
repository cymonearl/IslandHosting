package CRUD;

import javafx.collections.transformation.FilteredList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

import java.io.IOException;
import java.util.ArrayList;

import Tables.Payments;
import Tables.Views.PaymentView;

public class CRUDPaymentsMenuController {


    private Stage stage;
    private Scene scene;

    @FXML private TableView<PaymentView> paymentsTable;

    private ObservableList<PaymentView> paymentList = FXCollections.observableArrayList();
    private ArrayList<PaymentView> payments_ar = new ArrayList<>();
    private FilteredList<PaymentView> filteredData;
    @FXML private TextField searchTextField;
    
    public void initialize() {
        // Initialize the controller here
        initializeTableColumns();
        populateTable();
        filteredData = new FilteredList<>(paymentList, b -> true);
        paymentsTable.setItems(filteredData);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
            });
    }

    public void filterTable(String query) {
        filteredData.setPredicate(payment -> {
            if (query == null || query.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = query.toLowerCase();

            if (String.valueOf(payment.getPaymentId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(payment.getUserId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (payment.getUserName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(payment.getOrderId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(payment.getServerId()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (payment.getServerName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(payment.getAmount()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (payment.getPaymentMethod().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (payment.getTransactionId().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
    }

    @SuppressWarnings("unchecked")
    public void initializeTableColumns() {
        paymentsTable.getColumns().clear();

        TableColumn<PaymentView, Integer> payment_id = new TableColumn<>("Payment ID");
        payment_id.setCellValueFactory(new PropertyValueFactory<>("paymentId"));

        TableColumn<PaymentView, Integer> user_id = new TableColumn<>("User ID");
        user_id.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<PaymentView, Integer> user_name = new TableColumn<>("Username");
        user_name.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<PaymentView, Integer> order_id = new TableColumn<>("Order ID");
        order_id.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<PaymentView, Integer> server_id = new TableColumn<>("Server ID");
        server_id.setCellValueFactory(new PropertyValueFactory<>("serverId"));

        TableColumn<PaymentView, String> server_name = new TableColumn<>("Server Name");
        server_name.setCellValueFactory(new PropertyValueFactory<>("serverName"));

        TableColumn<PaymentView, String> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<PaymentView, String> payment_method = new TableColumn<>("Payment Method");
        payment_method.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        TableColumn<PaymentView, String> transaction_id = new TableColumn<>("Transaction ID");
        transaction_id.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<PaymentView, String> payment_status = new TableColumn<>("Payment Status");
        payment_status.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        TableColumn<PaymentView, String> payment_date = new TableColumn<>("Payment Date");
        payment_date.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        // Add the columns to the TableView
        paymentsTable.getColumns().addAll(payment_id, user_id, user_name, order_id, server_id, server_name, amount, payment_method, transaction_id, payment_status, payment_date);
        paymentsTable.setItems(paymentList);
    }

    public void populateTable() {
        paymentList.clear();
        payments_ar = new PaymentView().getPaymentsWithUserServerAndOrder();
        paymentList.addAll(payments_ar);
    }

    public void changeView(ActionEvent event) {
    
    }

    public void createPayment(ActionEvent event) {
        showPaymentDialog(null);
    }

    public void updatePayment(ActionEvent event) {
        PaymentView selectedPayment = paymentsTable.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            showAlert(Alert.AlertType.WARNING, "No Payment Selected", "Please select an Payment to update.");
            return;
        }

        PaymentView selectedPayment_orignal = payments_ar.stream()
        .filter(user -> user.getPaymentId() == selectedPayment.getPaymentId())
        .findFirst()
        .orElse(null);

        if (selectedPayment_orignal != null) {
            showPaymentDialog(selectedPayment_orignal); // Pass the selected user for editing
        }
    }

    public void deletePayment(ActionEvent event) {
        PaymentView selectedPayment = paymentsTable.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            showAlert(Alert.AlertType.WARNING, "No Payment Selected", "Please select an Payment to delete.");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Audit Logs");
            alert.setHeaderText("Are you sure you want to delete this Audit Logs?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                paymentList.remove(selectedPayment); // Remove from TableView
                new Payments().DELETE_PAYMENT(selectedPayment); // Remove from database
            }
        }
    }

    private void showPaymentDialog(PaymentView selectedPayment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PaymentDialog.fxml"));
            Parent root = loader.load();

            PaymentDialogController controller = loader.getController();
            controller.setController(this);

            if (selectedPayment != null) {
            controller.setPayment(selectedPayment); // Existing user for editing
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(selectedPayment == null ? "Create Audit Logs" : "Update Audit Logs");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
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
                stage.setHeight(310);
                stage.centerOnScreen();
            }
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public void logout(ActionEvent event) {
        try {
            navigateToScene(event, "../LoginMenu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}