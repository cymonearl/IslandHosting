package Client;

import Tables.Orders;
import Tables.Servers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class invoiceController {
    @FXML private Label order_id;
    @FXML private Label server_name;
    @FXML private Label specs;
    @FXML private Label sd;
    @FXML private Label ed;
    @FXML private Label ca;
    @FXML private Label total_amount;
    @FXML private Label status;
    @FXML private Label server_id;
    Orders order;

    public void setOrder(Orders order) {
        this.order = order;
        order_id.setText(String.valueOf("Order ID: " + order.getOrder_id()));
        server_name.setText(String.valueOf("Server Name: " + getServer(order.getServer_id()).getName()));
        specs.setText(String.format("Specs: %s Hardware %s GB RAM %s GB Storage %s",
        getServer(order.getServer_id()).getHardware_type(),
        getServer(order.getServer_id()).getRam_gb(),
        getServer(order.getServer_id()).getStorage_gb(),
        getServer(order.getServer_id()).getSpecs()));

        server_id.setText(String.valueOf("Server ID: " + order.getServer_id()));
        sd.setText(String.valueOf("Start Date: " + order.getStart_date()));
        ed.setText(String.valueOf("End Date: " + order.getEnd_date()));
        ca.setText(String.valueOf("Created At: " + order.getCreated_at()));
        total_amount.setText(String.valueOf("Total Amount: " + order.getTotal_amount()));
        status.setText(String.valueOf("Status: " + order.getStatus()));
    }

    private Servers getServer(int server_id) {
        return new Servers().SELECT_SERVER(server_id);
    }

    public Orders getOrder() { return order; }
}