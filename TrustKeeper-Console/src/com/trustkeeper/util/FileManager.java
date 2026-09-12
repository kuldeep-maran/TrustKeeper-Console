package com.trustkeeper.util;

import com.trustkeeper.model.Donation;
import com.trustkeeper.model.Expense;
import com.trustkeeper.model.Institution;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for local file persistence using BufferedReader and FileWriter.
 * Interacts directly with the data/ folder to read and write CSV records.
 */
public class FileManager {

    private static final String DATA_DIR = "data";
    private static final String DONATIONS_FILE = DATA_DIR + File.separator + "donations.csv";
    private static final String EXPENSES_FILE = DATA_DIR + File.separator + "expenses.csv";
    private static final String INSTITUTIONS_FILE = DATA_DIR + File.separator + "institutions.csv";

    private static final String DONATIONS_HEADER = "id,amount,date,description,donorName,contact,fundType";
    private static final String EXPENSES_HEADER = "id,amount,date,description,category";
    private static final String INSTITUTIONS_HEADER = "name,location,applicantType";

    /**
     * Initializes the data directory and missing CSV files with appropriate headers.
     */
    public static void initializeFiles() {
        try {
            Path dirPath = Paths.get(DATA_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            createFileWithHeaderIfMissing(DONATIONS_FILE, DONATIONS_HEADER);
            createFileWithHeaderIfMissing(EXPENSES_FILE, EXPENSES_HEADER);
            createFileWithHeaderIfMissing(INSTITUTIONS_FILE, INSTITUTIONS_HEADER);
        } catch (IOException e) {
            System.err.println("Error initializing data directory or files: " + e.getMessage());
        }
    }

    private static void createFileWithHeaderIfMissing(String filePath, String header) {
        File file = new File(filePath);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(header);
            } catch (IOException e) {
                System.err.println("Failed to create file: " + filePath + " | " + e.getMessage());
            }
        }
    }

    /**
     * Loads donations from donations.csv using BufferedReader.
     * @return List of Donation objects
     */
    public static List<Donation> loadDonations() {
        initializeFiles();
        List<Donation> list = new ArrayList<>();
        File file = new File(DONATIONS_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Donation donation = Donation.fromCsvRow(line);
                if (donation != null) {
                    list.add(donation);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading donations file: " + e.getMessage());
        }
        return list;
    }

    /**
     * Saves donations list to donations.csv using FileWriter / BufferedWriter.
     * @param donations List of Donation objects
     */
    public static void saveDonations(List<Donation> donations) {
        initializeFiles();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DONATIONS_FILE))) {
            writer.write(DONATIONS_HEADER);
            writer.newLine();
            for (Donation d : donations) {
                writer.write(d.toCsvRow());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing donations file: " + e.getMessage());
        }
    }

    /**
     * Loads expenses from expenses.csv using BufferedReader.
     * @return List of Expense objects
     */
    public static List<Expense> loadExpenses() {
        initializeFiles();
        List<Expense> list = new ArrayList<>();
        File file = new File(EXPENSES_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Expense expense = Expense.fromCsvRow(line);
                if (expense != null) {
                    list.add(expense);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading expenses file: " + e.getMessage());
        }
        return list;
    }

    /**
     * Saves expenses list to expenses.csv using FileWriter / BufferedWriter.
     * @param expenses List of Expense objects
     */
    public static void saveExpenses(List<Expense> expenses) {
        initializeFiles();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSES_FILE))) {
            writer.write(EXPENSES_HEADER);
            writer.newLine();
            for (Expense e : expenses) {
                writer.write(e.toCsvRow());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing expenses file: " + e.getMessage());
        }
    }

    /**
     * Loads institutions from institutions.csv using BufferedReader.
     * @return List of Institution objects
     */
    public static List<Institution> loadInstitutions() {
        initializeFiles();
        List<Institution> list = new ArrayList<>();
        File file = new File(INSTITUTIONS_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Institution inst = Institution.fromCsvRow(line);
                if (inst != null) {
                    list.add(inst);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading institutions file: " + e.getMessage());
        }
        return list;
    }

    /**
     * Saves institutions list to institutions.csv using FileWriter / BufferedWriter.
     * @param institutions List of Institution objects
     */
    public static void saveInstitutions(List<Institution> institutions) {
        initializeFiles();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INSTITUTIONS_FILE))) {
            writer.write(INSTITUTIONS_HEADER);
            writer.newLine();
            for (Institution inst : institutions) {
                writer.write(inst.toCsvRow());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing institutions file: " + e.getMessage());
        }
    }
}
