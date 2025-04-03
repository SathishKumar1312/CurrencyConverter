package currencyconverter;

import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Converter converter = new Converter();

        while (true) {
            try {
                // Display main menu
                System.out.println("\nCurrency Converter");
                System.out.println("1. Show Available Currencies");
                System.out.println("2. Convert Currency");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        converter.showAvailableCurrencies();
                        break;
                    case 2:
                        converter.performConversion(scanner);
                        break;
                    case 3:
                        System.out.println("Thank you for using Currency Converter!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }
}

