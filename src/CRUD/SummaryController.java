package CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class SummaryController {

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextArea reportArea;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/island_hosting_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @FXML
    public void handleOrders() {
        generateReport("Orders");
    }

    @FXML
    public void handleSupportTickets() {
        generateReport("Support Tickets");
    }

    @FXML
    public void handlePayments() {
        generateReport("Payments");
    }

    @FXML
    public void handleAuditLogs() {
        generateReport("Audit Logs");
    }

    private void generateReport(String reportType) {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        if (fromDate == null || toDate == null) {
            reportArea.setText("Please select both From and To dates.");
            return;
        }

        String query = getQueryForReportType(reportType);
        if (query == null) {
            reportArea.setText("Invalid report type selected.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, fromDate.toString());
            preparedStatement.setString(2, toDate.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder reportBuilder = new StringBuilder();
            int totalRow = 0;
            int totalAmount = 0;

            if (reportType.equals("Orders")) {
                reportBuilder.append("Order ID - User Name - Total Amount\n");
            } else if (reportType.equals("Support Tickets")) {
                reportBuilder.append("Ticket ID - Priority - Status - Subject\n");
            } else if (reportType.equals("Payments")) {
                reportBuilder.append("Payment ID - Amount - Payment Method\n");
            } else if (reportType.equals("Audit Logs")) {
                reportBuilder.append("Log ID - User Name - Action Type\n");
            }

            while (resultSet.next()) {
                // Customize the fields based on the query
                reportBuilder.append(resultSet.getString(1)).append(" - ")
                             .append(resultSet.getString(2)).append(" - ");
                if (reportType.equals("Orders")) {
                    reportBuilder.append(resultSet.getString("user_name")).append(" - ")
                                 .append(resultSet.getString("total_amount"));
                                 totalAmount += resultSet.getDouble("total_amount");
                                 totalRow++;
                } else if (reportType.equals("Support Tickets")) {
                    reportBuilder.append(resultSet.getString("priority")).append(" - ")
                                 .append(resultSet.getString("status")).append(" - ")
                                 .append(resultSet.getString("subject"));
                                
                                 totalRow++;
                } else if (reportType.equals("Payments")) {
                    reportBuilder.append(resultSet.getString("amount")).append(" - ")
                                 .append(resultSet.getString("payment_method"));

                                 totalRow++;
                                 totalAmount += resultSet.getDouble("amount");
                } else if (reportType.equals("Audit Logs")) {
                    reportBuilder.append(resultSet.getString("user_name")).append(" - ")
                                 .append(resultSet.getString("action_type"));

                                 totalRow++;
                }
                reportBuilder.append("\n");
            }
            
            if (reportType.equals("Orders")) {
                reportBuilder.append("\nTotal Orders: ").append(totalRow).append("\n")
                .append("Total Accumulated Amount: ").append(totalAmount);
            } else if (reportType.equals("Support Tickets")) {
                reportBuilder.append("\nTotal Support Tickets: ").append(totalRow);
            } else if (reportType.equals("Payments")) {
                reportBuilder.append("\nTotal Payments: ").append(totalRow);
            } else if (reportType.equals("Audit Logs")) {
                reportBuilder.append("\nTotal Audit Logs: ").append(totalRow);
            }

            reportArea.setText(reportBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            reportArea.setText("Error generating report: " + e.getMessage());
        }
    }

    private String getQueryForReportType(String reportType) {
        switch (reportType) {
            case "Orders":
                return "SELECT order_id, start_date, user_name, total_amount FROM OrdersWithUserAndServer WHERE start_date BETWEEN ? AND ? ORDER BY start_date DESC";
            case "Support Tickets":
                return "SELECT ticket_id, created_at, priority, status, subject, description FROM SupportTicketsWithUserAndServer WHERE created_at BETWEEN ? AND ? ORDER BY created_at DESC";
            case "Payments":
                return "SELECT payment_id, payment_date, amount, payment_method, user_name FROM PaymentsWithUserServerAndOrder WHERE payment_date BETWEEN ? AND ? ORDER BY payment_date DESC";
            case "Audit Logs":
                return "SELECT log_id, timestamp, user_name, action_type FROM Audit_LogsWithUser WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
            default:
                return null;
        }
    }
}