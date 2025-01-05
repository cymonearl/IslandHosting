package CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class DashboardController {

    @FXML
    private DatePicker fromDatePicker, toDatePicker;


    @FXML
    private TextArea reportArea;

    @FXML
    private Button ordersButton, paymentsButton, supportTicketsButton, auditLogsButton;

    @FXML
    public void initialize() {
        // Assign button click handlers
        ordersButton.setOnAction(event -> handleOrders());
        supportTicketsButton.setOnAction(event -> handleSupportTickets());
        paymentsButton.setOnAction(event -> handlePayments());
        auditLogsButton.setOnAction(event -> handleAuditLogs());

        System.out.println("DashboardController initialized!");
    }

    private void handleOrders() {
        generateReport("Orders");
    }

    private void handleSupportTickets() {
        generateReport("Support Tickets");
    }

    private void handlePayments() {
        generateReport("Payments");
    }

    private void handleAuditLogs() {
        generateReport("Audit Logs");
    }

    private void generateReport(String reportType) {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        if (fromDate == null || toDate == null) {
            reportArea.setText("Please select both From and To dates.");
            return;
        }

        if (toDate.isBefore(fromDate)) {
            reportArea.setText("Invalid date range. 'To Date' must be after 'From Date'.");
            return;
        }

        // Simulate data fetching (replace with actual database or logic calls)
        String report = String.format(
                "Summary for %s\nFrom: %s\nTo: %s\n\nTotal: %d items.",
                reportType, fromDate, toDate, (int) (Math.random() * 100) // Random count for demonstration
        );

        reportArea.setText(report);
    }
}
