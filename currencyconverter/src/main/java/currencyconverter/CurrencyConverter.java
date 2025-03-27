package currencyconverter;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_KEY = "API_KEY";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Map to store common currencies with their descriptions
    private static final Map<String, String> COMMON_CURRENCIES = new HashMap<>();

    static {
        COMMON_CURRENCIES.put("USD", "United States Dollar");
        COMMON_CURRENCIES.put("EUR", "Euro");
        COMMON_CURRENCIES.put("GBP", "British Pound Sterling");
        COMMON_CURRENCIES.put("JPY", "Japanese Yen");
        COMMON_CURRENCIES.put("AUD", "Australian Dollar");
        COMMON_CURRENCIES.put("CAD", "Canadian Dollar");
        COMMON_CURRENCIES.put("CHF", "Swiss Franc");
        COMMON_CURRENCIES.put("CNY", "Chinese Yuan");
        COMMON_CURRENCIES.put("INR", "Indian Rupee");
        COMMON_CURRENCIES.put("NZD", "New Zealand Dollar");
        COMMON_CURRENCIES.put("SGD", "Singapore Dollar");
        COMMON_CURRENCIES.put("HKD", "Hong Kong Dollar");
        COMMON_CURRENCIES.put("MXN", "Mexican Peso");
        COMMON_CURRENCIES.put("BRL", "Brazilian Real");
        COMMON_CURRENCIES.put("KRW", "South Korean Won");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                        showAvailableCurrencies();
                        break;
                    case 2:
                        performConversion(scanner);
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

    private static void showAvailableCurrencies() {
        System.out.println("\nAvailable Currencies:");
        System.out.println("--------------------");
        for (Map.Entry<String, String> entry : COMMON_CURRENCIES.entrySet()) {
            System.out.printf("%-5s : %s%n", entry.getKey(), entry.getValue());
        }
    }

    private static void performConversion(Scanner scanner) {
        try {
            showAvailableCurrencies();

            System.out.print("\nEnter base currency (e.g., USD): ");
            String baseCurrency = scanner.next().toUpperCase();

            if (!COMMON_CURRENCIES.containsKey(baseCurrency)) {
                System.out.println("Warning: Currency code '" + baseCurrency + "' is not in the common list.");
            }

            System.out.print("Enter target currency (e.g., EUR): ");
            String targetCurrency = scanner.next().toUpperCase();

            if (!COMMON_CURRENCIES.containsKey(targetCurrency)) {
                System.out.println("Warning: Currency code '" + targetCurrency + "' is not in the common list.");
            }

            System.out.print("Enter amount to convert: ");
            double amount = scanner.nextDouble();

            // Perform conversion
            double result = convertCurrency(baseCurrency, targetCurrency, amount);

            // Display result
            System.out.printf("%n%.2f %s = %.2f %s%n",
                    amount, baseCurrency, result, targetCurrency);

        } catch (Exception e) {
            System.out.println("Error during conversion: " + e.getMessage());
        }
    }

    private static double convertCurrency(String from, String to, double amount) throws Exception {
        // Build API URL
        String urlString = BASE_URL + API_KEY + "/pair/" + from + "/" + to;

        // Create URL object
        URL url = new URL(urlString);

        // Create HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check response code
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Failed to get exchange rate. HTTP error code: " + responseCode);
        }

        // Read response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());
        double conversionRate = jsonResponse.getDouble("conversion_rate");

        // Calculate converted amount
        return amount * conversionRate;
    }
}