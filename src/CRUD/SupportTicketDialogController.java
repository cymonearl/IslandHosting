package CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import Tables.SupportTicket;

public class SupportTicketDialogController {
    @FXML private TextField user_idTextField;
    @FXML private TextField server_idTextField;
    @FXML private TextField subjectTextField;
    @FXML private TextField descriptionTextField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> priorityComboBox;

    private SupportTicket supportTicket;
    
    private ObservableList<SupportTicket> supportTicketList;
    private boolean isNewSupportTicket = true;
    @FXML private Label CreateSupportTicketLabel;

    public void initialize() {
        statusComboBox.getItems().addAll("Open", "Closed", "In_Progress", "Resolved");
        statusComboBox.setValue("Open");

        priorityComboBox.getItems().addAll("Low", "Medium", "High");
        priorityComboBox.setValue("Low");
    }

    public void setTicketList(ObservableList<SupportTicket> supportTicketList) {
        this.supportTicketList = supportTicketList;
    }

    public void setTicket(SupportTicket supportTicket) {
        this.supportTicket = supportTicket;
        if (supportTicket != null) {
            isNewSupportTicket = false;
        }

        if (!isNewSupportTicket) {
            // Existing ticket - populate fields
            user_idTextField.setText(String.valueOf(supportTicket.getUser_id()));
            server_idTextField.setText(String.valueOf(supportTicket.getServer_id()));
            subjectTextField.setText(supportTicket.getSubject());
            descriptionTextField.setText(supportTicket.getDescription());
            statusComboBox.setValue(supportTicket.getStatus());
            priorityComboBox.setValue(supportTicket.getPriority());
             CreateSupportTicketLabel.setText("Update Support Ticket");
        } else {
            // New ticket - reset fields if needed 
            user_idTextField.clear();
            server_idTextField.clear();
            subjectTextField.clear();
            descriptionTextField.clear();
            statusComboBox.setValue("Open");
            priorityComboBox.setValue("Low");
            CreateSupportTicketLabel.setText("Create Support Ticket");
        }
    }

    public void handleSave() {
        if (validateInput()) {
            return;
        }

        int server_id = 0;
        int user_id = Integer.parseInt(user_idTextField.getText());
        if (!server_idTextField.getText().isEmpty()) 
            server_id = Integer.parseInt(server_idTextField.getText());
        String title = subjectTextField.getText();
        String description = descriptionTextField.getText();
        String status = statusComboBox.getValue();
        String priority = priorityComboBox.getValue();

        if (isNewSupportTicket) {
            // Create a new SupportTicket object
            SupportTicket newTicket = new SupportTicket(user_id, server_id, title, description, status, priority);

            // Insert into the database
            if (new SupportTicket().INSERT_SUPPORT_TICKET(newTicket)) {
                // Add to the ObservableList ONLY if the database insert is successful
                supportTicketList.add(newTicket);
                closeDialog();  // Close the dialog after successful creation
            } else {
                // Handle database insertion error (e.g., show an alert)
                alert("Database Error", "Failed to create the support ticket.");
            }
        } else {
            // Update the existing SupportTicket object
            supportTicket.setUser_id(Integer.parseInt(user_idTextField.getText()));
            supportTicket.setServer_id(Integer.parseInt(server_idTextField.getText()));
            supportTicket.setSubject(subjectTextField.getText());
            supportTicket.setDescription(descriptionTextField.getText());
            supportTicket.setStatus(statusComboBox.getValue());
            supportTicket.setPriority(priorityComboBox.getValue());

            // Update in the database
            if (new SupportTicket().UPDATE_SUPPORT_TICKET(supportTicket)) {
                // Update the ObservableList only if the database update is successful
                SupportTicket newST = supportTicket;
                int index = -1;
                for (int i = 0; i < supportTicketList.size(); i++) {
                    if (supportTicketList.get(i).getTicket_id() == newST.getTicket_id()) {
                        index = i;
                        break;
                    }
                }

                if (index != -1) {
                    supportTicketList.set(index, newST);
                }

                closeDialog(); // Close the dialog after successful update
            } else {
                // Handle database update error (e.g., show an alert)
                alert("Database Error", "Failed to update the support ticket.");
            }
        } 
    }

    private boolean validateInput() {
        String errorMessage = "";
        
        // Validate user_id
        try {
            Integer.parseInt(user_idTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "No valid user ID (must be an integer)!\n";
        }

        // Validate title
        if (subjectTextField.getText() == null || subjectTextField.getText().trim().isEmpty()) {
            errorMessage += "No valid title!\n";
        }

        // Validate description
        if (descriptionTextField.getText() == null || descriptionTextField.getText().trim().isEmpty()) {
            errorMessage += "No valid description!\n";
        }

        // Validate status
        if (statusComboBox.getValue() == null) {
            errorMessage += "No valid status selected!\n";
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
    

    public void deleteTicket() {
        new SupportTicket().DELETE_SUPPORT_TICKET(supportTicket);
    }

    public void handleCancel() {
        closeDialog();
    }

    public void closeDialog() {
        Stage stage = (Stage) user_idTextField.getScene().getWindow();
        stage.close();
    }

    private void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}