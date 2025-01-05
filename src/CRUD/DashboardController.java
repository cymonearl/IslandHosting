package CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class DashboardController {

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextArea reportArea;

    // Handler for Orders button
    @FXML
    public void handleOrders() {
        generateReport("Orders");
    }

    // Handler for Support Tickets button
    @FXML
    public void handleSupportTickets() {
        generateReport("Support Tickets");
    }

    // Handler for Payments button
    @FXML
    public void handlePayments() {
        generateReport("Payments");
    }

    // Handler for Audit Logs button
    @FXML
    public void handleAuditLogs() {
        generateReport("Audit Logs");
    }

    // Generic report generator
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

        // Simulate data fetching (replace this with actual database or logic calls)
        String report = String.format("Summary for %s\nFrom: %s\nTo: %s\n\nTotal: %d items.",
                reportType, fromDate, toDate, (int) (Math.random() * 100)); // Random count for demonstration

        reportArea.setText(report);
    }
}
