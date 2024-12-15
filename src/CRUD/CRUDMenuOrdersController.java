package CRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

import Tables.Orders;
public class CRUDMenuOrdersController {
    @FXML private TableView<Orders> ordersTableView;
    @FXML private TableColumn<Orders, Integer> order_id;
    @FXML private TableColumn<Orders, Integer> user_id;
    @FXML private TableColumn<Orders, String> server_id;
    @FXML private TableColumn<Orders, String> start_date;
    @FXML private TableColumn<Orders, String> end_date;
    @FXML private TableColumn<Orders, Double> total_amount;
    @FXML private TableColumn<Orders, String> status;
    @FXML private TableColumn<Orders, String> created_at;

    private ObservableList<Orders> orderList = FXCollections.observableArrayList();
    private ArrayList<Orders> orders_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    public void initialize() {
        initializeTableColumns();
        populateTable();
    }

    public void initializeTableColumns() {
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));
        start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        total_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        ordersTableView.setItems(orderList); // Set ObservableList to TableView
    }
    
    public void populateTable() {
        orders_ar = new Orders().SELECT_ALL_ORDERS();
        for (Orders order : orders_ar) {
            orderList.add(order);
        }
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

    public void navigateToScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Double width = stage.getWidth();
            Double height = stage.getHeight();
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
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
}
