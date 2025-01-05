package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import Tables.Audit_Logs;
import Tables.Views.Audit_LogsView;
public class Audit_logsDialogController {
    public Label Audit_logsOrderLabel;
    public TextField user_idTextField;
    public TextField action_typeTextField;
    public TextField descriptionTextField;
    public TextField ip_addressTextField;
    private CRUDMenuAuditLogsController controller;
    
    private Audit_LogsView audit_logs;
    private boolean isNewAudit_logs = true;
    public void initialize() {
        
    }

    public void getController(CRUDMenuAuditLogsController controller) {
        this.controller = controller;
    }

    public void setAudit_logs(Audit_LogsView audit_logs) {
        this.audit_logs = audit_logs;
        this.isNewAudit_logs = false;

        user_idTextField.setText(String.valueOf(audit_logs.getUserId()));
        action_typeTextField.setText(audit_logs.getAction());
        descriptionTextField.setText(audit_logs.getDescription());
        ip_addressTextField.setText(audit_logs.getIpAddress());
    }

    public void handleSave() {
        System.out.println("save");
        if (validateInput()) {
            return;
        }

        int user_id = Integer.parseInt(user_idTextField.getText());
        String action_type = action_typeTextField.getText();
        String description = descriptionTextField.getText();
        String ip_address = ip_addressTextField.getText();

        if (isNewAudit_logs) {  
            new Audit_Logs().INSERT_AUDIT_LOG(new Audit_Logs(user_id, action_type, description, ip_address));
        } else {
            audit_logs.setUserId(Integer.parseInt(user_idTextField.getText()));
            audit_logs.setAction(action_typeTextField.getText());
            audit_logs.setDescription(descriptionTextField.getText());
            audit_logs.setIpAddress(ip_addressTextField.getText());
            new Audit_Logs().UPDATE_AUDIT_LOG(audit_logs);
        }
        closeDialog();
        controller.initialize();
    }

    public void handleCancel() {
        closeDialog();
    }

    public boolean validateInput() {
        String errorMessage = "";
        if (user_idTextField.getText().isEmpty()) {
            errorMessage += "User ID is required!\n";
        }
        if (action_typeTextField.getText().isEmpty()) {
            errorMessage += "Action Type is required!\n";
        }
        if (descriptionTextField.getText().isEmpty()) {
            errorMessage += "Description is required!\n";
        }
        if (ip_addressTextField.getText().isEmpty()) {
            errorMessage += "IP Address is required!\n";
        }
        if (!errorMessage.isEmpty()) {
            alert("Invalid Fields", errorMessage);
            return true;
        } else {
            return false;
        }
    }

    public void closeDialog() {
        Stage stage = (Stage) Audit_logsOrderLabel.getScene().getWindow();
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
