package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import Tables.SupportTicket;

public class ticketController {
    SupportTicket ticket;
    @FXML private Label statusTF;
    @FXML private Label subjectTF;
    @FXML private Label server_idTF;
    @FXML private Label descriptionTF;
    @FXML private Label priorityTF;
    @FXML private Label created_atTF;

    public void setTicket(SupportTicket ticket) {
        this.ticket = ticket;
        updateTicketInfo();

        if (ticket.getServer_id() == -1) {
            server_idTF.setText("Server ID:");
        }
    }

    public void updateTicketInfo() {
        subjectTF.setText(ticket.getSubject());
        server_idTF.setText(String.valueOf(ticket.getServer_id()));
        descriptionTF.setText(ticket.getDescription());
        priorityTF.setText(ticket.getPriority());
        created_atTF.setText(ticket.getCreated_at().toString());
        statusTF.setText(ticket.getStatus());
    }

    public SupportTicket getTicket() {
        return ticket;
    }
}