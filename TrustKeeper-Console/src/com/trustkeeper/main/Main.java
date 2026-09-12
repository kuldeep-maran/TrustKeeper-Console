package com.trustkeeper.main;

import com.trustkeeper.model.Donation;
import com.trustkeeper.model.Expense;
import com.trustkeeper.model.Institution;
import com.trustkeeper.service.TrustManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point for TrustKeeper Console.
 * Provides an interactive console menu driven by Scanner and switch-case navigation.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TrustManager trustManager = new TrustManager();

    public static void main(String[] args) {
        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Select an option (1-7): ", 1, 7);

            switch (choice) {
                case 1:
                    handleRecordDonation();
                    break;
                case 2:
                    handleRecordExpense();
                    break;
                case 3:
                    handleRegisterInstitution();
                    break;
                case 4:
                    handleViewFinancialSummary();
                    break;
                case 5:
                    handleFilterExpenses();
                    break;
                case 6:
                    handleViewAllRecords();
                    break;
                case 7:
                    handleSaveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please choose between 1 and 7.");
            }
            if (running) {
                System.out.println("\nPress Enter to return to the main menu...");
                scanner.nextLine();
            }
        }
    }

    private static void printBanner() {
        System.out.println("==================================================================");
        System.out.println("                   TRUSTKEEPER CONSOLE SYSTEM                    ");
        System.out.println("          Multi-Package Financial & Applicant Management         ");
        System.out.println("==================================================================");
    }

    private static void printMenu() {
        System.out.println("\n----------------------- MAIN NAVIGATION -----------------------");
        System.out.println("  [1] Record Donation");
        System.out.println("  [2] Record Maintenance Expense");
        System.out.println("  [3] Register Institution / Applicant");
        System.out.println("  [4] View Financial Summary");
        System.out.println("  [5] Filter Expenses by Category");
        System.out.println("  [6] View All Stored Records");
        System.out.println("  [7] Save Data & Exit");
        System.out.println("---------------------------------------------------------------");
    }

    // --- Action Handlers ---

    private static void handleRecordDonation() {
        System.out.println("\n>>> RECORD NEW DONATION <<<");

        String id = trustManager.generateNextDonationId();
        System.out.println("Generated Transaction ID: " + id);

        double amount = readDoubleInput("Enter Donation Amount ($): ", 0.01, Double.MAX_VALUE);
        String date = readDateInput("Enter Date (YYYY-MM-DD) [Press Enter for Today]: ");
        String donorName = readNonEmptyString("Enter Donor Name: ");
        String contact = readNonEmptyString("Enter Contact Info (Phone/Email): ");

        System.out.println("Select Fund Type:");
        System.out.println("  1. General Fund");
        System.out.println("  2. Future Renovation Savings");
        System.out.println("  3. Custom Fund");
        int fundChoice = readIntInput("Choice (1-3): ", 1, 3);
        String fundType;
        if (fundChoice == 1) {
            fundType = "General Fund";
        } else if (fundChoice == 2) {
            fundType = "Future Renovation Savings";
        } else {
            fundType = readNonEmptyString("Enter Custom Fund Type Name: ");
        }

        String description = readStringWithDefault("Enter Description [Optional]: ", "Donation to " + fundType);

        Donation donation = new Donation(id, amount, date, description, donorName, contact, fundType);
        trustManager.addDonation(donation);

        System.out.println("\n[SUCCESS] Donation recorded and saved successfully!");
        System.out.println(donation.getFormattedDetails());
    }

    private static void handleRecordExpense() {
        System.out.println("\n>>> RECORD MAINTENANCE EXPENSE <<<");

        String id = trustManager.generateNextExpenseId();
        System.out.println("Generated Transaction ID: " + id);

        double amount = readDoubleInput("Enter Expense Amount ($): ", 0.01, Double.MAX_VALUE);
        String date = readDateInput("Enter Date (YYYY-MM-DD) [Press Enter for Today]: ");

        System.out.println("Select Category:");
        System.out.println("  1. Cleaning");
        System.out.println("  2. Pujari Salary");
        System.out.println("  3. Renovation");
        System.out.println("  4. Utilities / Bills");
        System.out.println("  5. Other Category");
        int catChoice = readIntInput("Choice (1-5): ", 1, 5);
        String category;
        switch (catChoice) {
            case 1: category = "Cleaning"; break;
            case 2: category = "Pujari Salary"; break;
            case 3: category = "Renovation"; break;
            case 4: category = "Utilities"; break;
            default: category = readNonEmptyString("Enter Custom Category Name: "); break;
        }

        String description = readNonEmptyString("Enter Expense Description: ");

        Expense expense = new Expense(id, amount, date, description, category);
        trustManager.addExpense(expense);

        System.out.println("\n[SUCCESS] Maintenance expense recorded and saved successfully!");
        System.out.println(expense.getFormattedDetails());
    }

    private static void handleRegisterInstitution() {
        System.out.println("\n>>> REGISTER INSTITUTION / APPLICANT RECORD <<<");

        String name = readNonEmptyString("Enter Institution / Organization Name: ");
        String location = readNonEmptyString("Enter Location (City/State): ");
        
        System.out.println("Select Applicant Type:");
        System.out.println("  1. Educational Trust");
        System.out.println("  2. Medical Society");
        System.out.println("  3. Charitable Foundation");
        System.out.println("  4. Other");
        int typeChoice = readIntInput("Choice (1-4): ", 1, 4);
        String applicantType;
        switch (typeChoice) {
            case 1: applicantType = "Educational Trust"; break;
            case 2: applicantType = "Medical Society"; break;
            case 3: applicantType = "Charitable Foundation"; break;
            default: applicantType = readNonEmptyString("Enter Custom Applicant Type: "); break;
        }

        Institution inst = new Institution(name, location, applicantType);
        trustManager.addInstitution(inst);

        System.out.println("\n[SUCCESS] Institution registered and persisted!");
        System.out.println(inst.getFormattedDetails());
    }

    private static void handleViewFinancialSummary() {
        System.out.println("\n==================================================================");
        System.out.println("                    TRUST FINANCIAL SUMMARY                       ");
        System.out.println("==================================================================");

        double totalDonations = trustManager.calculateTotalDonations();
        double totalExpenses = trustManager.calculateTotalExpenses();
        double netBalance = trustManager.calculateTotalBalance();

        System.out.printf("  Total Donations Received : $%,12.2f (%d records)%n", totalDonations, trustManager.getDonations().size());
        System.out.printf("  Total Maintenance Expenses: $%,12.2f (%d records)%n", totalExpenses, trustManager.getExpenses().size());
        System.out.println("  --------------------------------------------------------------");
        System.out.printf("  NET TRUST BALANCE        : $%,12.2f %s%n", netBalance, (netBalance >= 0 ? "[SOLVENT]" : "[DEFICIT!]"));
        System.out.println("==================================================================");

        System.out.println("\nBreakdown by Fund Type:");
        List<Donation> donations = trustManager.getDonations();
        donations.stream()
                .map(Donation::getFundType)
                .distinct()
                .forEach(fund -> {
                    double fundTotal = donations.stream()
                            .filter(d -> fund.equalsIgnoreCase(d.getFundType()))
                            .mapToDouble(Donation::getAmount)
                            .sum();
                    System.out.printf("  - %-30s : $%,10.2f%n", fund, fundTotal);
                });

        System.out.println("\nBreakdown by Expense Category:");
        List<Expense> expenses = trustManager.getExpenses();
        expenses.stream()
                .map(Expense::getCategory)
                .distinct()
                .forEach(cat -> {
                    double catTotal = expenses.stream()
                            .filter(e -> cat.equalsIgnoreCase(e.getCategory()))
                            .mapToDouble(Expense::getAmount)
                            .sum();
                    System.out.printf("  - %-30s : $%,10.2f%n", cat, catTotal);
                });
    }

    private static void handleFilterExpenses() {
        System.out.println("\n>>> FILTER EXPENSES BY CATEGORY <<<");
        System.out.print("Enter category or keyword to search (e.g. Cleaning, Pujari, Renovation): ");
        String category = scanner.nextLine();

        List<Expense> filtered = trustManager.filterExpensesByCategory(category);

        if (filtered.isEmpty()) {
            System.out.println("No expense records match category: '" + category + "'");
        } else {
            System.out.printf("\nFound %d matching expense record(s):%n", filtered.size());
            System.out.println("------------------------------------------------------------------");
            double total = 0.0;
            for (Expense e : filtered) {
                System.out.println(" " + e.getFormattedDetails());
                total += e.getAmount();
            }
            System.out.println("------------------------------------------------------------------");
            System.out.printf("Subtotal for Category '%s': $%.2f%n", category, total);
        }
    }

    private static void handleViewAllRecords() {
        System.out.println("\n==================================================================");
        System.out.println("                       ALL STORED RECORDS                         ");
        System.out.println("==================================================================");

        System.out.println("\n--- DONATIONS (" + trustManager.getDonations().size() + ") ---");
        if (trustManager.getDonations().isEmpty()) {
            System.out.println("  No donation records found.");
        } else {
            for (Donation d : trustManager.getDonations()) {
                System.out.println("  " + d.getFormattedDetails());
            }
        }

        System.out.println("\n--- EXPENSES (" + trustManager.getExpenses().size() + ") ---");
        if (trustManager.getExpenses().isEmpty()) {
            System.out.println("  No expense records found.");
        } else {
            for (Expense e : trustManager.getExpenses()) {
                System.out.println("  " + e.getFormattedDetails());
            }
        }

        System.out.println("\n--- REGISTERED INSTITUTIONS (" + trustManager.getInstitutions().size() + ") ---");
        if (trustManager.getInstitutions().isEmpty()) {
            System.out.println("  No registered institutions found.");
        } else {
            for (Institution i : trustManager.getInstitutions()) {
                System.out.println("  " + i.getFormattedDetails());
            }
        }
    }

    private static void handleSaveAndExit() {
        System.out.println("\nSaving data files to disk...");
        trustManager.saveAllData();
        System.out.println("[SAVED] Data successfully written to data/donations.csv, data/expenses.csv, and data/institutions.csv.");
        System.out.println("Thank you for using TrustKeeper Console. Goodbye!");
    }

    // --- Input Validation Utility Helpers ---

    private static int readIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int val = Integer.parseInt(input.trim());
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.printf("Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double readDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                double val = Double.parseDouble(input.trim());
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.printf("Please enter a value >= %.2f.%n", min);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Example valid input: 2500.50");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private static String readStringWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        if (input == null || input.trim().isEmpty()) {
            return defaultValue;
        }
        return input.trim();
    }

    private static String readDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return LocalDate.now().toString();
            }
            try {
                LocalDate parsed = LocalDate.parse(input);
                return parsed.toString();
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Expected YYYY-MM-DD (e.g. 2026-09-15) or leave empty for today.");
            }
        }
    }
}
