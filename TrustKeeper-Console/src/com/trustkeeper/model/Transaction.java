package com.trustkeeper.model;

/**
 * Abstract base class representing a generic financial transaction in TrustKeeper.
 * Demonstrates Object-Oriented principles of abstraction and encapsulation.
 */
public abstract class Transaction {
    private String id;
    private double amount;
    private String date;
    private String description;

    /**
     * Default Constructor
     */
    public Transaction() {
    }

    /**
     * Parameterized Constructor
     *
     * @param id          Unique transaction identifier
     * @param amount      Transaction financial amount
     * @param date        Transaction date (YYYY-MM-DD)
     * @param description Brief transaction description
     */
    public Transaction(String id, double amount, String date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters (Encapsulation)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Abstract method to convert transaction object into CSV string format.
     * Must be implemented by subclasses.
     * @return CSV formatted string
     */
    public abstract String toCsvRow();

    /**
     * Abstract method to display formatted representation of transaction details.
     * @return Formatted multi-line string for console UI
     */
    public abstract String getFormattedDetails();

    @Override
    public String toString() {
        return String.format("ID: %s | Date: %s | Amount: $%.2f | Description: %s", id, date, amount, description);
    }
}
