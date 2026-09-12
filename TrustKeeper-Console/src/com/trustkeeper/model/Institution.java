package com.trustkeeper.model;

/**
 * Class representing an Institution or Applicant record in TrustKeeper.
 */
public class Institution {
    private String name;
    private String location;
    private String applicantType;

    public Institution() {
    }

    public Institution(String name, String location, String applicantType) {
        this.name = name;
        this.location = location;
        this.applicantType = applicantType;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String toCsvRow() {
        return String.format("%s,%s,%s",
                escapeCsv(name),
                escapeCsv(location),
                escapeCsv(applicantType));
    }

    public String getFormattedDetails() {
        return String.format("Institution: %s | Location: %s | Type: %s", name, location, applicantType);
    }

    public static Institution fromCsvRow(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty() || csvLine.startsWith("name")) {
            return null;
        }
        String[] parts = parseCsvLine(csvLine);
        if (parts.length >= 3) {
            String name = parts[0].trim();
            String location = parts[1].trim();
            String applicantType = parts[2].trim();
            return new Institution(name, location, applicantType);
        }
        return null;
    }

    private static String escapeCsv(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"")) {
            return "\"" + input.replace("\"", "\"\"") + "\"";
        }
        return input;
    }

    private static String[] parseCsvLine(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString());
        return result.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return getFormattedDetails();
    }
}
