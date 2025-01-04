package CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.collections.*;
import Tables.Servers;

public class ServerDialogController {
    public Label CreateServerLabel;
    @FXML
    public ComboBox<String> planComboBox; // ComboBox for plans
//    public TextField server_nameTextField;
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
        // Populate the plan combo box
        planComboBox.setItems(FXCollections.observableArrayList("Plan 1", "Plan 2", "Plan 3"));
        planComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> populateFieldsBasedOnPlan(newValue));

        // Set default value for statusComboBox
        statusComboBox.setItems(FXCollections.observableArrayList("available", "occupied", "maintenance"));
        statusComboBox.setValue("available");

        // Set the fields as non-editable
        hardware_typeTextField.setEditable(false);
        ram_gbTextField.setEditable(false);
        storage_gbTextField.setEditable(false);
        price_per_monthTextField.setEditable(false);
        server_locationTextField.setEditable(false);
        additional_specsTextField.setEditable(false);
    }

    private void populateFieldsBasedOnPlan(String plan) {
        if (plan == null) return;

        switch (plan) {
            case "Plan 1":
//                server_nameTextField.setText("Plan 1");
                hardware_typeTextField.setText("Budget hardware");
                ram_gbTextField.setText("4");
                storage_gbTextField.setText("50");
                price_per_monthTextField.setText("225");
                server_locationTextField.setText("Default Location 1");
                additional_specsTextField.setText("Default Specs 1");
                break;

            case "Plan 2":
//                server_nameTextField.setText("Plan 2");
                hardware_typeTextField.setText("Budget hardware");
                ram_gbTextField.setText("2");
                storage_gbTextField.setText("50");
                price_per_monthTextField.setText("175");
                server_locationTextField.setText("Default Location 2");
                additional_specsTextField.setText("Default Specs 2");
                break;

            case "Plan 3":
//                server_nameTextField.setText("Plan 3");
                hardware_typeTextField.setText("Budget hardware");
                ram_gbTextField.setText("1");
                storage_gbTextField.setText("50");
                price_per_monthTextField.setText("125");
                server_locationTextField.setText("Default Location 3");
                additional_specsTextField.setText("Default Specs 3");
                break;

            default:
                clearFields();
                break;
        }
    }

    private void clearFields() {
//        server_nameTextField.clear();
        hardware_typeTextField.clear();
        ram_gbTextField.clear();
        storage_gbTextField.clear();
        price_per_monthTextField.clear();
        server_locationTextField.clear();
        additional_specsTextField.clear();
    }

    public void setServer(Servers server) {
        CreateServerLabel.setText("Update Server");
        this.server = server;
        isNewServer = false;

//        server_nameTextField.setText(server.getName());
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

        String name = planComboBox.getValue();
        String hardware_type = hardware_typeTextField.getText();
        int ram_gb = Integer.parseInt(ram_gbTextField.getText());
        int storage_gb = Integer.parseInt(storage_gbTextField.getText());
        double price_per_month = Double.parseDouble(price_per_monthTextField.getText());
        String specs = additional_specsTextField.getText();
        String server_location = server_locationTextField.getText();
        String status = statusComboBox.getValue();

        if (isNewServer) {
            Servers newServer = new Servers(name, hardware_type, ram_gb, storage_gb, price_per_month, specs, server_location, status);
            serverList.add(newServer);
            new Servers().INSERT_SERVER(newServer);
        } else {
            server.setName(name);
            server.setHardware_type(hardware_type);
            server.setRam_gb(ram_gb);
            server.setStorage_gb(storage_gb);
            server.setPrice_per_month(price_per_month);
            server.setSpecs(specs);
            server.setStatus(status);
            server.setServer_location(server_location);
            serverList.set(serverList.indexOf(server), server);
            new Servers().UPDATE_SERVER(server);
        }

        closeDialog();
    }

    public void handleCancel() {
        closeDialog();
    }

    public boolean validateInput() {
        String errorMessage = "";

//        if (server_nameTextField.getText().isEmpty()) {
//            errorMessage += "Server name is required!\n";
//        }
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
        Stage stage = (Stage) CreateServerLabel.getScene().getWindow();
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
