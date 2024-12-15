package CRUD;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import Tables.Servers;
public class ServerDialogController {
    public Label CreateServerLabel;
    public TextField server_nameTextField;
    public TextField hardware_typeTextField;
    public TextField ram_gbTextField;
    public TextField storage_gbTextField;
    public TextField price_per_monthTextField;
    public TextField server_locationTextField;
    public TextField additional_specsTextField;
    public ComboBox<String> statusComboBox;

    private Servers server;
    private ObservableList<Servers> serverList;
    private boolean isNewServer = true;

    public void initialize() {
        statusComboBox.getItems().addAll("available","occupied","maintenance");
        statusComboBox.setValue("available");
    }
    public void setServer(Servers server) {
        CreateServerLabel.setText("Update Server");
        this.server = server;
        isNewServer = false;

        server_nameTextField.setText(server.getName());
        hardware_typeTextField.setText(server.getHardware_type());
        ram_gbTextField.setText(String.valueOf(server.getRam_gb()));
        storage_gbTextField.setText(String.valueOf(server.getStorage_gb()));
        price_per_monthTextField.setText(String.valueOf(server.getPrice_per_month()));
        server_locationTextField.setText(server.getServer_location());
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

        String name = server_nameTextField.getText();
        String hardware_type = hardware_typeTextField.getText();
        int ram_gb = Integer.parseInt(ram_gbTextField.getText());
        int storage_gb = Integer.parseInt(storage_gbTextField.getText());
        double price_per_month = Double.parseDouble(price_per_monthTextField.getText());
        String specs = additional_specsTextField.getText();
        String server_location = server_locationTextField.getText();
        String status = statusComboBox.getValue();

        if (isNewServer) {
            if (!validateServer())
                return;
            new Servers().INSERT_SERVER(new Servers(name, hardware_type, ram_gb, storage_gb, price_per_month, specs, server_location,status));
            serverList.add(new Servers(name, hardware_type, ram_gb, storage_gb, price_per_month, specs, server_location,status));
        } else {
            server.setName(name);
            server.setHardware_type(hardware_type);
            server.setRam_gb(ram_gb);
            server.setStorage_gb(storage_gb);
            server.setPrice_per_month(price_per_month);
            server.setSpecs(specs);
            server.setStatus(status);
            server.setServer_location(server_location);
            Servers updateServer = server;
            serverList.set(serverList.indexOf(server), updateServer);
            new Servers().UPDATE_SERVER(server);
        }
        closeDialog();
    }
    public void handleCancel() {
        closeDialog();
    }
    public boolean validateServer() {
        for (Servers server : serverList) {
            if (server.getName().equals(server_nameTextField.getText())) {
                System.out.println("Server name already exists.");
                alert("Duplicate Server Name", "Server name already exists.");
                return false;
            }
        }
        return true;
    }
    public boolean validateInput() {
        String errorMessage = "";

        if (server_nameTextField.getText().isEmpty()) {
            errorMessage += "Server name is required!\n";
        }
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
        if (server_locationTextField.getText().isEmpty()) {
            errorMessage += "Server location is required!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            alert("Validation Error", errorMessage);
            return false;
        }
    }
    public void closeDialog() {
        Stage stage = (Stage) server_nameTextField.getScene().getWindow();
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