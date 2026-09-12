# TrustKeeper Console - Trust Financial & Applicant Management System

**TrustKeeper Console** is a production-ready, console-based Java application built to manage non-profit trust finances (donations and maintenance expenses) and applicant institution records. The application utilizes pure Java SE object-oriented principles, modular multi-package design, robust input validation, and local CSV file persistence.

---

## 📋 Table of Contents
- [Project Overview](#-project-overview)
- [Key Features](#-key-features)
- [Software Architecture & OOP Principles](#-software-architecture--oop-principles)
- [Directory Structure](#-directory-structure)
- [Environment Setup & Prerequisites](#-environment-setup--prerequisites)
- [Installation & Configuration](#-installation--configuration)
- [Execution Instructions](#-execution-instructions)
- [Data Persistence Specification](#-data-persistence-specification)
- [Error Handling & Reliability](#-error-handling--reliability)

---

## 💡 Project Overview
Managing non-profit trust accounts requires accurate tracking of incoming funds (donations earmarked for general or specific purposes) against operational expenses (cleaning, salaries, renovation). **TrustKeeper Console** provides a lightweight, dependency-free solution with persistent local file storage (`.csv`), automated ID generation, real-time balance calculations, category filtering, and an interactive console menu interface.

---

## ✨ Key Features
1. **Donation Recording**: Track incoming donations with donor name, contact details, date, amount, and fund category (*General Fund*, *Future Renovation Savings*, or custom funds).
2. **Maintenance Expense Tracking**: Log operational expenses categorized by department (*Cleaning*, *Pujari Salary*, *Renovation*, *Utilities*).
3. **Institution & Applicant Registration**: Register partner institutions, educational trusts, and charitable applicants with location details.
4. **Financial Analytics & Solvency Summary**: Instantly view total donations, total expenses, net solvency balance, and fund/category breakdowns.
5. **Expense Filtering**: Filter maintenance expenses by category or keyword with subtotal calculations.
6. **Data Persistence**: Automatically reads from and writes to `./data/*.csv` files on every transaction so data persists across application restarts.

---

## 🏛️ Software Architecture & OOP Principles

The project strictly follows standard Java package conventions (`com.trustkeeper`):

* **`com.trustkeeper.model`**: Core domain entities.
  * **Abstraction**: `Transaction.java` is an abstract base class holding `id`, `amount`, `date`, `description`, and abstract methods `toCsvRow()` and `getFormattedDetails()`.
  * **Inheritance & Polymorphism**: `Donation.java` and `Expense.java` extend `Transaction`, providing specialized attributes (`donorName`, `contact`, `fundType`, `category`) and overriding formatting/CSV serialization logic.
  * **Encapsulation**: Private attributes accessible exclusively via getters and setters.
  * `Institution.java`: Data model for institution/applicant records.

* **`com.trustkeeper.service`**: Business logic layer.
  * `TrustManager.java`: Manages `ArrayList<Donation>`, `ArrayList<Expense>`, and `ArrayList<Institution>` collections. Handles total calculations (Donations - Expenses), category filtering, auto-generating unique transaction IDs (`DON-1001`, `EXP-1001`), and synchronization with storage.

* **`com.trustkeeper.util`**: File I/O utility layer.
  * `FileManager.java`: Uses Java's `BufferedReader`, `BufferedWriter`, and `FileWriter` to read and write CSV files. Automatically creates missing `./data/` directories and CSV headers.

* **`com.trustkeeper.main`**: Application entry point.
  * `Main.java`: Interactive `while(true)` console menu loop powered by `Scanner` and a `switch` navigation system.

---

## 📁 Directory Structure

```
TrustKeeper-Console/
├── data/                         # Local CSV persistent data folder
│   ├── donations.csv             # Persistent donation records
│   ├── expenses.csv              # Persistent maintenance expense records
│   └── institutions.csv          # Persistent institution applicant records
├── src/                          # Java source code directory
│   └── com/
│       └── trustkeeper/
│           ├── main/
│           │   └── Main.java     # Console menu & user interface
│           ├── model/
│           │   ├── Donation.java
│           │   ├── Expense.java
│           │   ├── Institution.java
│           │   └── Transaction.java
│           ├── service/
│           │   └── TrustManager.java
│           └── util/
│               └── FileManager.java
├── bin/                          # Generated Java bytecode (.class files)
├── run.bat                       # Windows double-click launcher script
├── .gitignore                    # Git version control ignore rules
└── README.md                     # Project documentation
```

---

## ⚙️ Environment Setup & Prerequisites

### 1. Java Development Kit (JDK)
* **Required Version**: Java SE 11, 17, 21, or 26+.
* **Verification**: Open a terminal/command prompt and verify Java is installed:

```bash
javac -version
java -version
```

*If `javac` is not recognized, install the JDK from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [Adoptium Temurin OpenJDK](https://adoptium.net/) and ensure Java is added to your system `PATH`.*

---

## 🔧 Installation & Configuration

1. **Download / Clone the Repository**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/TrustKeeper-Console.git
   cd TrustKeeper-Console
   ```
   *(Or download and extract the project ZIP file to any directory on your computer).*

2. **No External Dependencies Required**:
   The project is built entirely using standard Java SE libraries (`java.io`, `java.util`, `java.time`, `java.nio`). No Maven, Gradle, or third-party JARs are required.

---

## 🚀 Execution Instructions

### Method 1: Double-Click Launcher (Windows)
1. Open **Windows File Explorer**.
2. Navigate to the project root folder (`TrustKeeper-Console`).
3. Double-click **`run.bat`**.
*(The script will automatically create the `bin/` directory, compile all `.java` files, and launch the interactive console application).*

### Method 2: Manual Terminal / PowerShell Execution

1. Open PowerShell or Command Prompt in the project root directory:
   ```powershell
   cd path/to/TrustKeeper-Console
   ```

2. **Compile the Java source files**:
   ```powershell
   # PowerShell
   if (-not (Test-Path bin)) { New-Item -ItemType Directory -Path bin }
   javac -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
   ```
   ```cmd
   :: Command Prompt (CMD)
   mkdir bin
   javac -d bin -sourcepath src src/com/trustkeeper/*/*.java
   ```

3. **Run the Application**:
   ```powershell
   java -cp bin com.trustkeeper.main.Main
   ```

---

## 💾 Data Persistence Specification

Data is stored in human-readable CSV format inside the `data/` folder:

* **`data/donations.csv`**: `id,amount,date,description,donorName,contact,fundType`
* **`data/expenses.csv`**: `id,amount,date,description,category`
* **`data/institutions.csv`**: `name,location,applicantType`

*Files and directories are initialized automatically upon app launch if missing.*

---

## 🛡️ Error Handling & Reliability
* **Input Validation**: Uses robust input readers preventing `InputMismatchException` or `NumberFormatException` when numeric inputs are expected.
* **Date Parsing**: Accepts `YYYY-MM-DD` inputs or defaults gracefully to the current date if left empty.
* **CSV Escaping**: Commas and quotes within user text descriptions are escaped properly to maintain CSV structural integrity.
* **Fault-Tolerant I/O**: Try-with-resources blocks ensure file readers and writers are closed safely without memory leaks.
