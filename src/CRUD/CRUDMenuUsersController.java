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
import java.util.Date;
import Tables.Users;

public class CRUDMenuUsersController {

    // UI Components
    @FXML private TableView<Users> usersTableView;
    @FXML private Label CRUD;
    @FXML private Button viewButton;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    boolean userViewMode = false;

    // Data and Stage Properties
    private ObservableList<Users> userList = FXCollections.observableArrayList();
    private ArrayList<Users> users_ar = new ArrayList<>();
    private FilteredList<Users> filteredData;
    private Scene scene;
    private Stage stage;
    @FXML private TextField searchTextField;

    // ========= Initialization =========
    public void initialize() {
            CRUD.setText("CRUD");
            initializeTableColumns();
            populateAllUsers();
            createButton.setVisible(true);
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
            viewButton.setText("User View");
            filteredData = new FilteredList<>(userList, b -> true);
            usersTableView.setItems(filteredData);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterTable(newValue);
            });    
        }
        
        public void changeView(ActionEvent event) {
            usersTableView.getColumns().clear();

            if (userViewMode) {
                userViewMode = false;
                userList.clear();
                initialize();
            } else {
                userList.clear();
                userViewMode = true;
                initializeTableColumnsUV();
                CRUD.setText("User View");
                populateActiveUsers();
                createButton.setVisible(false);
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                viewButton.setText("CRUD");
                filteredData = new FilteredList<>(userList, b -> true);
                usersTableView.setItems(filteredData);
                searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filterTable(newValue);
                });    
            }       
        }
        
        public void filterTable(String searchText) {
            filteredData.setPredicate(user -> {
                // If search text is empty, show all users
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }
    
                // Convert search text to lowercase for case-insensitive search
                String lowerCaseSearchText = searchText.toLowerCase();
    
                // Check if any of the user's properties contain the search text
                if (String.valueOf(user.getUser_id()).toLowerCase().contains(lowerCaseSearchText)) {
                    return true;
                } else if (user.getUsername().toLowerCase().contains(lowerCaseSearchText)) {
                    return true;
                } else if (user.getEmail().toLowerCase().contains(lowerCaseSearchText)) {
                    return true;
                } else if (user.getFull_name().toLowerCase().contains(lowerCaseSearchText)) {
                    return true;
                } else if (user.getContact_number().toLowerCase().contains(lowerCaseSearchText)) {
                    return true;
                } else if (user.getStatus().toLowerCase().contains(lowerCaseSearchText)) {
                    return true;
                }
                return false; // Does not match
            });
        }

        @SuppressWarnings("unchecked")
        private void initializeTableColumns() {
            // Define CRUD columns
            TableColumn<Users, Integer> user_id = new TableColumn<>("User ID");
            user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    
            TableColumn<Users, String> username = new TableColumn<>("Username");
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
    
            TableColumn<Users, String> email = new TableColumn<>("Email");
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
    
            TableColumn<Users, String> full_name = new TableColumn<>("Full Name");
            full_name.setCellValueFactory(new PropertyValueFactory<>("full_name"));
    
            TableColumn<Users, String> contact_number = new TableColumn<>("Contact Number");
            contact_number.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
    
            TableColumn<Users, String> status = new TableColumn<>("Status");
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
    
            TableColumn<Users, Date> created_at = new TableColumn<>("Created At");
            created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    
            TableColumn<Users, Date> last_login = new TableColumn<>("Last Login");
            last_login.setCellValueFactory(new PropertyValueFactory<>("last_login"));
    
            // Add the columns to the TableView
            usersTableView.getColumns().addAll(user_id, username, email, full_name, contact_number, status, created_at, last_login);
    
            usersTableView.setItems(userList); // Set ObservableList to TableView
        }
    
        @SuppressWarnings("unchecked")
        private void initializeTableColumnsUV() {
            // Define User View columns
            TableColumn<Users, Integer> user_id = new TableColumn<>("User ID");
            user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    
            TableColumn<Users, String> username = new TableColumn<>("Username");
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
    
            TableColumn<Users, String> email = new TableColumn<>("Email");
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
    
            TableColumn<Users, String> full_name = new TableColumn<>("Full Name");
            full_name.setCellValueFactory(new PropertyValueFactory<>("full_name"));
    
            TableColumn<Users, String> contact_number = new TableColumn<>("Contact Number");
            contact_number.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
    
            TableColumn<Users, String> status = new TableColumn<>("Status");
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            
            // Add User View specific columns to the TableView
            usersTableView.getColumns().addAll(user_id, username, email, full_name, contact_number, status);
    
            usersTableView.setItems(userList); // Set ObservableList to TableView
        }


    private void populateAllUsers() {
        users_ar = new Users().SELECT_ALL_USERS();
        userList.addAll(users_ar);
    }

    private void populateActiveUsers() {
        users_ar = new Users().SELECT_ACTIVE_USERS();
        userList.addAll(users_ar);
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

    public void navigateToTickets(ActionEvent event) {
        navigateToScene(event, "CRUDSupportTicketsMenu.fxml");    
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

    // ========= CRUD Functions =========
    public void createUser() {
        showUserDialog(null); // Passing null for a new user creation
    }

    public void updateUser() {
        Users selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No User Selected", "Please select a user to update.");
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
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete User");
                alert.setHeaderText("Deleting this User will delete all associated data. Are you sure you want to delete this user?");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    new Users().DELETE_USER(selectedUser); // Remove from database                    
                    new Users().DELETE_OTHER_DATA(selectedUser.getUser_id());
                    System.out.println(selectedUser.getUser_id());
                    userList.remove(selectedUser); // Remove from TableView
                }
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