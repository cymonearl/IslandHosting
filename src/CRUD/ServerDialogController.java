package CRUD;

import javafx.scene.control.*;
import javafx.collections.*;
import Tables.Servers;
public class ServerDialogController {
    public TextField nameTextField;
    public TextField hardwareTextField;
    public TextField ramTextField;
    public TextField storageTextField;
    public TextField pricer_per_monthTextField;
    public TextField specsTextField;
    public ComboBox<String> statusComboBox;

    private Servers server;
    private ObservableList<Servers> serverList;
    private boolean isNewServer = true;

    public void initialize() {
        statusComboBox.getItems().addAll("available","occupied","maintenance");
        statusComboBox.setValue("available");
    }
    public void setServer(Servers server) {

    }
    public void setServerList(ObservableList<Servers> serverList) {
        this.serverList = serverList;
    }
    public void handleSave() {}
    public void handleCancel() {}
    public void validateInput() {}
    public void validateUser() {}
    public void closeDialog() {}
}