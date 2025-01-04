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
    }

    public void updateTicketInfo() {
        subjectTF.setText("Subject:" + ticket.getSubject());
        server_idTF.setText("Server ID:" + String.valueOf(ticket.getServer_id() != 0 ? ticket.getServer_id() : "N/A"));
        descriptionTF.setText(ticket.getDescription());
        priorityTF.setText("Priority: " + ticket.getPriority());
        created_atTF.setText(ticket.getCreated_at().toString());
        statusTF.setText("Status: " + ticket.getStatus());
    }

    public SupportTicket getTicket() {
        return ticket;
    }
}