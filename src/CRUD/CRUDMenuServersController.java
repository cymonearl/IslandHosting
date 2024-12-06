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

import Tables.Servers;

public class CRUDMenuServersController {

    private Scene scene;
    private Parent root;
    private Stage stage;

    @FXML private TableView<Servers> serversTableView;
    @FXML private TableColumn<Servers, Integer> server_id;
    @FXML private TableColumn<Servers, String> name;
    @FXML private TableColumn<Servers, String> hardware_type;
    @FXML private TableColumn<Servers, Integer> ram_gb;
    @FXML private TableColumn<Servers, String> specs;
    @FXML private TableColumn<Servers, Integer> storage_gb;
    @FXML private TableColumn<Servers, Double> price_per_month;
    @FXML private TableColumn<Servers, String> server_location;
    @FXML private TableColumn<Servers, String> status;
    
    private ObservableList<Servers> serverList = FXCollections.observableArrayList();
    
    public void initialize() {
        server_id.setCellValueFactory(new PropertyValueFactory<>("server_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        hardware_type.setCellValueFactory(new PropertyValueFactory<>("hardware_type"));
        ram_gb.setCellValueFactory(new PropertyValueFactory<>("ram_gb"));
        specs.setCellValueFactory(new PropertyValueFactory<>("specs"));
        storage_gb.setCellValueFactory(new PropertyValueFactory<>("storage_gb"));
        price_per_month.setCellValueFactory(new PropertyValueFactory<>("price_per_month"));
        server_location.setCellValueFactory(new PropertyValueFactory<>("server_location"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        serversTableView.setItems(serverList);
        serverList.add(new Servers(1, "Server 1", "Hardware Type 1", 8, 100, 50.00, "Specs 1", "Location 1", "Active", 2024));
    }
    
    public void Users(ActionEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDUsersMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void Orders(ActionEvent event) {
        System.out.println("what");
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDOrdersMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Auditlogs(ActionEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDAuditLogsMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }   catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void createUser(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("CreateWindow.fxml"));
            stage = new Stage();            
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void deleteUser(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText("Are you sure you want to delete this user?");
        alert.showAndWait();
    }

    public void updateUser(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("CreateWindow.fxml"));
            stage = new Stage();            
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void createUserCancel(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void createUserSave(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void createUserUpdate(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}