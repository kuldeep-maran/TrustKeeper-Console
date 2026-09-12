package com.trustkeeper.service;

import com.trustkeeper.model.Donation;
import com.trustkeeper.model.Expense;
import com.trustkeeper.model.Institution;
import com.trustkeeper.util.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic service class for TrustKeeper.
 * Manages in-memory collections of Donations, Expenses, and Institutions,
 * calculates financial totals, filters data, and interacts with persistent storage.
 */
public class TrustManager {
    private final ArrayList<Donation> donations;
    private final ArrayList<Expense> expenses;
    private final ArrayList<Institution> institutions;

    public TrustManager() {
        this.donations = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.institutions = new ArrayList<>();
        loadAllData();
    }

    /**
     * Loads all records from disk storage into memory.
     */
    public void loadAllData() {
        donations.clear();
        expenses.clear();
        institutions.clear();

        donations.addAll(FileManager.loadDonations());
        expenses.addAll(FileManager.loadExpenses());
        institutions.addAll(FileManager.loadInstitutions());
    }

    /**
     * Persists all current in-memory collections to disk.
     */
    public void saveAllData() {
        FileManager.saveDonations(donations);
        FileManager.saveExpenses(expenses);
        FileManager.saveInstitutions(institutions);
    }

    // --- Record Management ---

    public void addDonation(Donation donation) {
        if (donation != null) {
            donations.add(donation);
            FileManager.saveDonations(donations);
        }
    }

    public void addExpense(Expense expense) {
        if (expense != null) {
            expenses.add(expense);
            FileManager.saveExpenses(expenses);
        }
    }

    public void addInstitution(Institution institution) {
        if (institution != null) {
            institutions.add(institution);
            FileManager.saveInstitutions(institutions);
        }
    }

    // --- ID Auto-Generation ---

    public String generateNextDonationId() {
        int max = 1000;
        for (Donation d : donations) {
            if (d.getId() != null && d.getId().startsWith("DON-")) {
                try {
                    int num = Integer.parseInt(d.getId().substring(4));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return "DON-" + (max + 1);
    }

    public String generateNextExpenseId() {
        int max = 1000;
        for (Expense e : expenses) {
            if (e.getId() != null && e.getId().startsWith("EXP-")) {
                try {
                    int num = Integer.parseInt(e.getId().substring(4));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return "EXP-" + (max + 1);
    }

    // --- Financial Calculations ---

    /**
     * Calculates the total amount of all recorded donations.
     * @return Sum of donation amounts
     */
    public double calculateTotalDonations() {
        double total = 0.0;
        for (Donation d : donations) {
            total += d.getAmount();
        }
        return total;
    }

    /**
     * Calculates the total amount of all recorded maintenance expenses.
     * @return Sum of expense amounts
     */
    public double calculateTotalExpenses() {
        double total = 0.0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    /**
     * Calculates the net total balance (Total Donations minus Total Expenses).
     * @return Net balance
     */
    public double calculateTotalBalance() {
        return calculateTotalDonations() - calculateTotalExpenses();
    }

    // --- Filtering Methods ---

    /**
     * Filters expenses by category name (case-insensitive substring match).
     * @param category Search category
     * @return List of matching Expense records
     */
    public List<Expense> filterExpensesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>(expenses);
        }
        String searchTerm = category.toLowerCase().trim();
        return expenses.stream()
                .filter(e -> e.getCategory() != null && e.getCategory().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Filters donations by fund type (case-insensitive substring match).
     * @param fundType Search fund type
     * @return List of matching Donation records
     */
    public List<Donation> filterDonationsByFundType(String fundType) {
        if (fundType == null || fundType.trim().isEmpty()) {
            return new ArrayList<>(donations);
        }
        String searchTerm = fundType.toLowerCase().trim();
        return donations.stream()
                .filter(d -> d.getFundType() != null && d.getFundType().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    // --- Getters for Collections ---

    public ArrayList<Donation> getDonations() {
        return donations;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public ArrayList<Institution> getInstitutions() {
        return institutions;
    }
}
