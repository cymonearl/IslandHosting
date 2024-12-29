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
import java.util.Date;
import Tables.Users;

public class CRUDMenuUsersController {

    // UI Components
    @FXML private TableView<Users> usersTableView;
    @FXML private TableColumn<Users, Integer> user_id;
    @FXML private TableColumn<Users, String> username;
    @FXML private TableColumn<Users, String> email;
    @FXML private TableColumn<Users, String> full_name;
    @FXML private TableColumn<Users, String> contact_number;
    @FXML private TableColumn<Users, String> status;
    @FXML private TableColumn<Users, Date> created_at;
    @FXML private TableColumn<Users, Date> last_login;

    // Data and Stage Properties
    private ObservableList<Users> userList = FXCollections.observableArrayList();
    private ArrayList<Users> users_ar = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    // ========= Initialization =========
    public void initialize() {
        initializeTableColumns();
        populateTable();
    }

    private void initializeTableColumns() {
        // Map Users class fields to TableView columns
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        full_name.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        contact_number.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        last_login.setCellValueFactory(new PropertyValueFactory<>("last_login"));
        usersTableView.setItems(userList); // Set ObservableList to TableView
    }

    private void populateTable() {
        users_ar = new Users().SELECT_ALL_USERS();
        for (Users user : users_ar) {
            userList.add(user);
        }
    }

    // ========= Navigation Methods =========
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

    // ========= CRUD Functions =========
    public void createUser() {
        showUserDialog(null); // Passing null for a new user creation
    }

    public void updateUser() {
        Users selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.INFORMATION, "Update User", "Please select a user to update.");
            return;
        }

        // Find the original `Users` object in the `users_ar` list
        Users selectedUser_original = users_ar.stream()
            .filter(user -> user.getUser_id() == selectedUser.getUser_id())
            .findFirst()
            .orElse(null);

        if (selectedUser_original != null) {
            showUserDialog(selectedUser_original); // Pass the selected user for editing
        }
    }

    public void deleteUser() {
        Users selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText("Are you sure you want to delete this user?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                userList.remove(selectedUser); // Remove from TableView
                new Users().DELETE_USER(selectedUser); // Remove from database
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Delete User", "Please select a user to delete.");
        }
    }

    private void showUserDialog(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDialog.fxml"));
            Parent root = loader.load();

            // Pass data to UserDialogController
            UserDialogController controller = loader.getController();
            controller.setUserList(userList);
            if (user != null) {
                controller.setUser(user); // Existing user for editing
            }

            // Show the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(user == null ? "Create User" : "Update User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(usersTableView.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.setAlwaysOnTop(true);
            dialogStage.setResizable(false);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========= Utility Methods =========
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