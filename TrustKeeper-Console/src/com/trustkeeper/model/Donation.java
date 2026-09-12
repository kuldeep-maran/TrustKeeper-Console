package com.trustkeeper.model;

/**
 * Class representing a Donation record, extending Transaction.
 * Demonstrates Inheritance and Polymorphism.
 */
public class Donation extends Transaction {
    private String donorName;
    private String contact;
    private String fundType;

    public Donation() {
        super();
    }

    public Donation(String id, double amount, String date, String description, String donorName, String contact, String fundType) {
        super(id, amount, date, description);
        this.donorName = donorName;
        this.contact = contact;
        this.fundType = fundType;
    }

    // Getters and Setters
    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    @Override
    public String toCsvRow() {
        return String.format("%s,%.2f,%s,%s,%s,%s,%s",
                escapeCsv(getId()),
                getAmount(),
                escapeCsv(getDate()),
                escapeCsv(getDescription()),
                escapeCsv(donorName),
                escapeCsv(contact),
                escapeCsv(fundType));
    }

    @Override
    public String getFormattedDetails() {
        return String.format("[%s] Donation of $%.2f from '%s' (%s) | Fund: %s | Date: %s | Desc: %s",
                getId(), getAmount(), donorName, contact, fundType, getDate(), getDescription());
    }

    /**
     * Parses a CSV row into a Donation object.
     * @param csvLine Single line from donations.csv
     * @return Donation instance or null if parsing fails
     */
    public static Donation fromCsvRow(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty() || csvLine.startsWith("id")) {
            return null;
        }
        String[] parts = parseCsvLine(csvLine);
        if (parts.length >= 7) {
            try {
                String id = parts[0].trim();
                double amount = Double.parseDouble(parts[1].trim());
                String date = parts[2].trim();
                String description = parts[3].trim();
                String donorName = parts[4].trim();
                String contact = parts[5].trim();
                String fundType = parts[6].trim();
                return new Donation(id, amount, date, description, donorName, contact, fundType);
            } catch (NumberFormatException e) {
                System.err.println("Warning: Skipping malformed donation record: " + csvLine);
            }
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
}
