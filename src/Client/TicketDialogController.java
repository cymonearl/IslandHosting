package Client;

import Tables.SupportTicket;
import Tables.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class TicketDialogController {
    @FXML private TextField server_idTextField;
    @FXML private TextField subjectTextField;
    @FXML private TextField descriptionTextField;
    @FXML private ComboBox<String> priorityComboBox;
    private Users user;

    public void setUser(Users user) {
        this.user = user;
    }

    public void initialize() {
        priorityComboBox.getItems().addAll("Low", "Medium", "High");
        priorityComboBox.setValue("Low");
    }

    public void handleSave(ActionEvent event) {
        if (validateInput()) {
            return;
        }

        int server_id = 0;
        if (!server_idTextField.getText().isEmpty()) 
            server_id = Integer.parseInt(server_idTextField.getText().trim());
        String title = subjectTextField.getText().trim();
        String description = descriptionTextField.getText().trim();
        String priority = priorityComboBox.getValue();
  
        System.out.println(server_id);
        SupportTicket newTicket = new SupportTicket(user.getUser_id() ,server_id, title, description, "Open", priority);

        if (new SupportTicket().INSERT_SUPPORT_TICKET(newTicket)) {
            closeDialog();
        } else {
            // Handle database insertion error (e.g., show an alert)
            alert("Database Error", "Failed to create the support ticket.");
        }
      }  

      private boolean validateInput() {
        String errorMessage = "";
    
        // Validate server_id
        if (!server_idTextField.getText().isEmpty()) {
            try {
                Integer.parseInt(server_idTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid server ID (must be an integer)!\n";
            }
        }

        // Validate title
        if (subjectTextField.getText() == null || subjectTextField.getText().trim().isEmpty()) {
            errorMessage += "No valid title!\n";
        }

        // Validate description
        if (descriptionTextField.getText() == null || descriptionTextField.getText().trim().isEmpty()) {
            errorMessage += "No valid description!\n";
        }

        // Validate priority
        if (priorityComboBox.getValue() == null) {
            errorMessage += "No valid priority selected!\n";
        }

        if (errorMessage.isEmpty()) {
            return false; // No errors
        } else {
            alert("Invalid Fields", errorMessage);
            return true; // Errors found
        }
    }
    public void handleCancel() {
        closeDialog();
    }

    public void closeDialog() {
        Stage stage = (Stage) server_idTextField.getScene().getWindow();
        stage.close();
    }

    private void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}