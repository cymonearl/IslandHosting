package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import Tables.Servers;
public class ServerDialogController {
    public Label CreateServerLabel;
    public TextField hardware_typeTextField;
    public TextField ram_gbTextField;
    public TextField storage_gbTextField;
    public TextField price_per_monthTextField;
    public TextField server_locationTextField;
    public TextField additional_specsTextField;
    public ComboBox<String> statusComboBox;
    public ComboBox<String> server_nameComboBox;

    private Servers server;
    private ObservableList<Servers> serverList;
    private boolean isNewServer = true;

    public void initialize() {
        statusComboBox.getItems().addAll("available","occupied","maintenance");
        statusComboBox.setValue("available");

        server_nameComboBox.getItems().addAll("Basic", "Pro", "Enterprise");
        server_nameComboBox.setValue("Basic");
    }
    public void setServer(Servers server) {
        CreateServerLabel.setText("Update Server");
        this.server = server;
        isNewServer = false;

        server_nameComboBox.setValue(server.getName());
        hardware_typeTextField.setText(server.getHardware_type());
        ram_gbTextField.setText(String.valueOf(server.getRam_gb()));
        storage_gbTextField.setText(String.valueOf(server.getStorage_gb()));
        price_per_monthTextField.setText(String.valueOf(server.getPrice_per_month()));
        additional_specsTextField.setText(server.getSpecs());
        statusComboBox.setValue(server.getStatus());
    }
    public void setServerList(ObservableList<Servers> serverList) {
        this.serverList = serverList;
    }
    public void handleSave() {
        if (!validateInput()) {
            return;
        }

        String name = server_nameComboBox.getValue();
        String hardware_type = hardware_typeTextField.getText();
        int ram_gb = Integer.parseInt(ram_gbTextField.getText());
        int storage_gb = Integer.parseInt(storage_gbTextField.getText());
        double price_per_month = Double.parseDouble(price_per_monthTextField.getText());
        String specs = additional_specsTextField.getText();
        String status = statusComboBox.getValue();

        if (isNewServer) {
            Servers newServer = new Servers(name, hardware_type, ram_gb, storage_gb, price_per_month, specs, "server_location", status);
            new Servers().INSERT_SERVER(newServer);
            serverList.add(newServer);
        } else {
            server.setName(name);
            server.setHardware_type(hardware_type);
            server.setRam_gb(ram_gb);
            server.setStorage_gb(storage_gb);
            server.setPrice_per_month(price_per_month);
            server.setSpecs(specs);
            server.setStatus(status);
            
            Servers updateServer = server;
            serverList.set(serverList.indexOf(server), updateServer);
            new Servers().UPDATE_SERVER(updateServer);
        }
        closeDialog();
    }
    public void handleCancel() {
        closeDialog();
    }
    public boolean validateInput() {
        String errorMessage = "";

        if (hardware_typeTextField.getText().isEmpty()) {
            errorMessage += "Hardware type is required!\n";
        }
        if (ram_gbTextField.getText().isEmpty() || !ram_gbTextField.getText().matches("\\d+")) {
            errorMessage += "Invalid RAM GB!\n";
        }
        if (storage_gbTextField.getText().isEmpty() || !storage_gbTextField.getText().matches("\\d+")) {
            errorMessage += "Invalid Storage GB!\n";
        }
        if (price_per_monthTextField.getText().isEmpty() || !price_per_monthTextField.getText().matches("\\d*(\\.\\d+)?")) {
            errorMessage += "Invalid Price per month!\n";
        }
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            alert("Validation Error", errorMessage);
            return false;
        }
    }
    public void closeDialog() {
        Stage stage = (Stage) server_nameComboBox.getScene().getWindow();
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