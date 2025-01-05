package Client;

import Tables.Servers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class serverCardController {
    @FXML private Label server_name;
    @FXML private Label specs;
    @FXML private Label price;

    Servers server;
    public void setServer(Servers server){
        this.server = server;
        server_name.setText(server.getName());
        specs.setText(String.format("- Hardware: %s\n- Ram: %s GB\n- Storage: %s GB\n- %s",
        server.getHardware_type(),
        server.getRam_gb(),
        server.getStorage_gb(),
        server.getSpecs())
        );
        price.setText(String.valueOf("PHP " + server.getPrice_per_month()));
    }

    public Servers getServer() {return server;}
}