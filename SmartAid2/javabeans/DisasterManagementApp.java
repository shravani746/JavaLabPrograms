public class DisasterManagementApp {

    public static void main(String[] args) {

        DisasterReport report = new DisasterReport();

        report.setReportId(101);
        report.setDisasterType("Flood");
        report.setLocation("Kerala");
        report.setSeverityLevel(8);
        report.setReportedBy("Local Authority");

        System.out.println("Disaster Report Details");
        System.out.println("Report ID: " + report.getReportId());
        System.out.println("Disaster Type: " + report.getDisasterType());
        System.out.println("Location: " + report.getLocation());
        System.out.println("Severity Level: " + report.getSeverityLevel());
        System.out.println("Reported By: " + report.getReportedBy());
    }
}
