package org.example;

import java.io.*;

public class Wallet {
    private double balance;
    private String[] transactions;
    private String[][] history;
    private final String[] documentTypes = {
            "identityCard", "photo", "bankCard", "visaCard", "drivingLicense"
    };

    public Wallet() {
        balance = 0;
        transactions = new String[0];
        history = new String[documentTypes.length][0];
    }

    public void addFunds(double amount) {
        balance += amount;
        addTransactionToHistory("Added funds: +" + amount + " €");
    }

    public void withdrawFunds(double amount) {
        if (balance >= amount) {
            balance -= amount;
            addTransactionToHistory("Withdrawal of funds: -" + amount + " €");
        } else {
            System.out.println("Insufficient balance for this withdrawal.");
        }
    }

    public void addTransactionToHistory(String transaction) {
        String[] newTransactions = new String[transactions.length + 1];
        System.arraycopy(transactions, 0, newTransactions, 0, transactions.length);
        newTransactions[transactions.length] = transaction;
        transactions = newTransactions;
    }

    public void removeTransactionFromHistory(int index) {
        if (transactions.length > index) {
            String removed = transactions[index];
            String[] newTransactions = new String[transactions.length - 1];
            System.arraycopy(transactions, 0, newTransactions, 0, index);
            System.arraycopy(transactions, index + 1, newTransactions, index, transactions.length - index - 1);
            transactions = newTransactions;
            System.out.println("Removed transaction: " + removed);
        } else {
            System.out.println("Invalid transaction index.");
        }
    }

    public void showBalance() {
        System.out.println("Current balance: " + balance + " €");
    }

    public void showTransactionHistory() {
        System.out.println("Transaction history:");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void addDocument(String type, String info) {
        int index = getDocumentTypeIndex(type);
        if (index != -1) {
            String[] documents = history[index];
            String[] newDocuments = new String[documents.length + 1];
            System.arraycopy(documents, 0, newDocuments, 0, documents.length);
            newDocuments[documents.length] = info;
            history[index] = newDocuments;

            int reference = history[index].length;
            String message = type + " added: Reference " + reference;
            addTransactionToHistory("Added " + type + ": " + message);
            System.out.println(message);
        } else {
            System.out.println("Unsupported document type.");
        }
    }

    public void removeDocument(String type, int index) {
        int typeIndex = getDocumentTypeIndex(type);
        if (typeIndex != -1 && history[typeIndex].length > index) {
            String removed = history[typeIndex][index];
            String[] documents = history[typeIndex];
            String[] newDocuments = new String[documents.length - 1];
            System.arraycopy(documents, 0, newDocuments, 0, index);
            System.arraycopy(documents, index + 1, newDocuments, index, documents.length - index - 1);
            history[typeIndex] = newDocuments;

            int reference = index + 1;
            String message = type + " removed: Reference " + reference;
            removeTransactionFromHistory(index);
            System.out.println(message);
        } else {
            System.out.println("Invalid document index or type.");
        }
    }

    public void showHistory(String type) {
        int index = getDocumentTypeIndex(type);
        if (index != -1) {
            System.out.println("History of " + type + ":");
            for (int i = 0; i < history[index].length; i++) {
                System.out.println((i + 1) + ". " + history[index][i]);
            }
        } else {
            System.out.println("Unsupported document type.");
        }
    }

    private int getDocumentTypeIndex(String type) {
        for (int i = 0; i < documentTypes.length; i++) {
            if (documentTypes[i].equals(type)) {
                return i;
            }
        }
        return -1;
    }
}

class WalletApp {
    public static void main(String[] args) {
        Wallet wallet = new Wallet();
        getPassword(wallet);
    }

    private static void getPassword(Wallet wallet) {
        try (BufferedReader br = new BufferedReader(new FileReader("password.txt"))) {
            String password = br.readLine();
            if (password != null && !password.isEmpty()) {
                System.out.println("Password retrieved!");
                authenticate(password, wallet);
            } else {
                System.out.println("No password saved.");
                createPassword(wallet);
            }
        } catch (IOException e) {
            System.out.println("No password saved.");
            createPassword(wallet);
        }
    }

    private static void createPassword(Wallet wallet) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("password.txt"))) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please create a password: ");
            String password = reader.readLine();
            bw.write(password);
            System.out.println("Password created successfully!");
            authenticate(password, wallet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void authenticate(String password, Wallet wallet) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please enter the password: ");
            String enteredPassword = reader.readLine();
            if (enteredPassword.equals(password)) {
                System.out.println("Authentication successful!");
                // UserInterface.userInterface(wallet); // Commenté car la classe UserInterface n'est pas définie
                // Vous pouvez appeler les méthodes de Wallet ici si nécessaire
            } else {
                System.out.println("Incorrect password. Please try again.");
                authenticate(password, wallet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
