package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import Tables.Audit_Logs;
public class Audit_logsDialogController {
    public Label Audit_logsOrderLabel;
    public TextField user_idTextField;
    public TextField action_typeTextField;
    public TextField descriptionTextField;
    public TextField ip_addressTextField;
    
    private Audit_Logs audit_logs;
    private ObservableList<Audit_Logs> audit_logsList;
    private boolean isNewAudit_logs = true;
    public void initialize() {
        
    }

    public void setAudit_logs(Audit_Logs audit_logs) {
        this.audit_logs = audit_logs;
        this.isNewAudit_logs = false;

        user_idTextField.setText(String.valueOf(audit_logs.getUser_id()));
        action_typeTextField.setText(audit_logs.getAction_type());
        descriptionTextField.setText(audit_logs.getDescription());
        ip_addressTextField.setText(audit_logs.getIp_address());
    }

    public void setAudit_logsList(ObservableList<Audit_Logs> audit_logsList) {
        this.audit_logsList = audit_logsList;
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
            audit_logsList.add(new Audit_Logs(user_id, action_type, description, ip_address));
        } else {
            audit_logs.setUser_id(Integer.parseInt(user_idTextField.getText()));
            audit_logs.setAction_type(action_typeTextField.getText());
            audit_logs.setDescription(descriptionTextField.getText());
            audit_logs.setIp_address(ip_addressTextField.getText());
            Audit_Logs updateAudit_Logs = audit_logs;
            audit_logsList.set(audit_logsList.indexOf(audit_logs), updateAudit_Logs);
            new Audit_Logs().UPDATE_AUDIT_LOG(updateAudit_Logs);
        }
        closeDialog();
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
