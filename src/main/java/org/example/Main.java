package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
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
                userInterface(wallet);
            } else {
                System.out.println("Incorrect password. Please try again.");
                authenticate(password, wallet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void userInterface(Wallet wallet) {
        Scanner scanner = new Scanner(System.in);
        final Runnable[] menu = new Runnable[1]; // Déclarer un tableau contenant la référence à Runnable

        menu[0] = () -> {
            System.out.println("\n1. Add funds");
            System.out.println("2. Withdraw funds");
            System.out.println("3. Show balance");
            System.out.println("4. Show transaction history");
            System.out.println("5. Add a document");
            System.out.println("6. Remove a document");
            System.out.println("7. Show document history");
            System.out.println("8. Quit");

            System.out.print("Enter your choice number: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter the amount to add: ");
                    double addAmount = Double.parseDouble(scanner.nextLine());
                    wallet.addFunds(addAmount);
                    menu[0].run();
                    break;
                case "2":
                    System.out.print("Enter the amount to withdraw: ");
                    double withdrawAmount = Double.parseDouble(scanner.nextLine());
                    wallet.withdrawFunds(withdrawAmount);
                    menu[0].run();
                    break;
                case "3":
                    wallet.showBalance();
                    menu[0].run();
                    break;
                case "4":
                    wallet.showTransactionHistory();
                    menu[0].run();
                    break;
                case "5":
                    System.out.print("Enter the type of document to add: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter information for " + type + ": ");
                    String info = scanner.nextLine();
                    wallet.addDocument(type, info);
                    menu[0].run();
                    break;
                case "6":
                    System.out.print("Enter the type of document to remove: ");
                    String removeType = scanner.nextLine();
                    System.out.print("Enter the index of the document to remove: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    wallet.removeDocument(removeType, index - 1);
                    menu[0].run();
                    break;
                case "7":
                    System.out.print("Enter the type of document to show history: ");
                    String historyType = scanner.nextLine();
                    wallet.showHistory(historyType);
                    menu[0].run();
                    break;
                case "8":
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
                    menu[0].run();
            }
        };
        menu[0].run();
    }
}
