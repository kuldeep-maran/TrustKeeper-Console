package com.trustkeeper.model;

/**
 * Class representing an Expense record, extending Transaction.
 * Demonstrates Inheritance and Polymorphism.
 */
public class Expense extends Transaction {
    private String category;

    public Expense() {
        super();
    }

    public Expense(String id, double amount, String date, String description, String category) {
        super(id, amount, date, description);
        this.category = category;
    }

    // Getter and Setter
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toCsvRow() {
        return String.format("%s,%.2f,%s,%s,%s",
                escapeCsv(getId()),
                getAmount(),
                escapeCsv(getDate()),
                escapeCsv(getDescription()),
                escapeCsv(category));
    }

    @Override
    public String getFormattedDetails() {
        return String.format("[%s] Expense of $%.2f | Category: %s | Date: %s | Desc: %s",
                getId(), getAmount(), category, getDate(), getDescription());
    }

    /**
     * Parses a CSV row into an Expense object.
     * @param csvLine Single line from expenses.csv
     * @return Expense instance or null if parsing fails
     */
    public static Expense fromCsvRow(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty() || csvLine.startsWith("id")) {
            return null;
        }
        String[] parts = parseCsvLine(csvLine);
        if (parts.length >= 5) {
            try {
                String id = parts[0].trim();
                double amount = Double.parseDouble(parts[1].trim());
                String date = parts[2].trim();
                String description = parts[3].trim();
                String category = parts[4].trim();
                return new Expense(id, amount, date, description, category);
            } catch (NumberFormatException e) {
                System.err.println("Warning: Skipping malformed expense record: " + csvLine);
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
