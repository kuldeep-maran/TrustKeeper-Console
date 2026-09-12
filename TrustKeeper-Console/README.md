# TrustKeeper Console

TrustKeeper Console is a production-ready, object-oriented Java console application designed for managing trust finances (donations and expenses) and applicant institution records with local CSV file persistence.

---

## 🌟 Key Features

* **Donation Management**: Record donations categorized by fund type (*General Fund*, *Future Renovation Savings*, etc.).
* **Expense Tracking**: Record maintenance expenses categorized by department (*Cleaning*, *Pujari Salary*, *Renovation*, etc.).
* **Institution Registration**: Track partner institutions, educational trusts, and charitable applicants.
* **Financial Analytics & Summary**: View real-time totals for donations, maintenance expenses, and overall solvent net balance.
* **Category Filtering**: Search and filter expense records instantly by keyword.
* **Local CSV File Persistence**: All data is automatically saved to `./data/donations.csv`, `./data/expenses.csv`, and `./data/institutions.csv`.

---

## 🛠️ Object-Oriented Design

Built following standard Java package conventions (`com.trustkeeper`):
* `model`: `Transaction` (abstract base class), `Donation`, `Expense`, `Institution`
* `service`: `TrustManager` (in-memory `ArrayList` collections & calculation logic)
* `util`: `FileManager` (CSV reading/writing with `BufferedReader` and `FileWriter`)
* `main`: `Main` (Interactive console menu loop with scanner input validation)

---

## 🚀 How to Run

### Method 1: Double-Click (Windows File Explorer)
Simply double-click **`run.bat`** in the project folder!

### Method 2: Command Line (PowerShell / Terminal)
```powershell
# 1. Compile Java source files
javac -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName

# 2. Launch the application
java -cp bin com.trustkeeper.main.Main
```
