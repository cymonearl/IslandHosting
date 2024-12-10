package CRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

import Tables.Users;
public class CRUDMenuUsersController {

    private Scene scene;
    private Stage stage;

    @FXML private TableView<Users> usersTableView;
    @FXML private TableColumn<Users, Integer> user_id;
    @FXML private TableColumn<Users, String> username;
    @FXML private TableColumn<Users, String> email;
    @FXML private TableColumn<Users, String> full_name;
    @FXML private TableColumn<Users, String> contact_number;
    @FXML private TableColumn<Users, String> status;

    private ObservableList<Users> userList = FXCollections.observableArrayList();
    ArrayList<Users> users_ar = new ArrayList<Users>();

    public void initialize() {
        // Initialize columns
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        full_name.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        contact_number.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set items to the table
        usersTableView.setItems(userList);
        users_ar = new Users().SELECT_ALL_USERS();
        for (Users user : users_ar) {
            System.out.println("added");
            userList.add(user);
        }

    }

    public void Servers(ActionEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("CRUDServersMenu.fxml"));
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


    public void createUser() {
        showUserDialog(null);
    }

    public void deleteUser() {
        Users selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText("Are you sure you want to delete this user?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            userList.remove(selectedUser);
            } 
        } 
    }

    public void updateUser() {
        if (usersTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update User");
            alert.setHeaderText("Please select a user to update.");
            alert.showAndWait();
            return;
        }            
        Users selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        Users selectedUser_ar = new Users();
        for (Users user : users_ar) {    
            if (user.getUser_id() == selectedUser.getUser_id()) {
                selectedUser_ar = user;
            }
        }

        showUserDialog(selectedUser_ar);
    }

    public void showUserDialog(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDialog.fxml"));

            Parent root = loader.load();

            UserDialogController controller = loader.getController();
            controller.setUserList(userList);
            if (user != null)
            controller.setUser(user);

            Stage DialogStage = new Stage();
            DialogStage.setTitle(user == null ? "Create User" : "Update User");
            DialogStage.initModality(Modality.WINDOW_MODAL);
            DialogStage.initOwner(usersTableView.getScene().getWindow());
            DialogStage.setScene(new Scene(root));
            DialogStage.showAndWait();
            DialogStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}