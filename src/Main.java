import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        userInterface();

        // Currency Codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "GBP");
        currencyCodes.put(3, "JPY");
        currencyCodes.put(4, "EUR");
        currencyCodes.put(5, "CAD");
        currencyCodes.put(6, "AUD");
        currencyCodes.put(7, "NZD");
        currencyCodes.put(8, "CHF");

        String code1, code2;
        double amount;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Currency Converter!");
        System.out.println("What is your base currency?");
        System.out.println("1: USD\t\t 2: GBP \t 3: JPY \t 4: EUR \t 5: CAD \t 6: AUD \t 7: NZD \t 8: CHF");
        code1 = currencyCodes.get(scanner.nextInt());
        System.out.println("What is your quote currency?");
        code2 = currencyCodes.get(scanner.nextInt());
        System.out.println("Please enter the amount to be converted.");
        amount = scanner.nextFloat();

        sendHttpsGETRequest(code1, code2, amount);
    }

    public static void sendHttpsGETRequest(String code1, String code2, double amount) throws IOException {
        String api_key = "fca_live_jmMg2e2vOHTZk1h0jIkgSoDOfPoCQzTl3ECFFprO";
        String get_url = "https://api.freecurrencyapi.com/v1/latest?apikey=" + api_key + "&currencies=" + code2 + "&base_currency=" + code1;
        URL url = new URL(get_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        if  (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null)  {
                response.append(inputLine);
            } in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("data").getDouble(code2);
            double convertedAmount = Math.round((amount * exchangeRate) * 100.0) / 100.0;
            System.out.println(amount + " "+ code1 + " = " + convertedAmount + " " + code2);
        }
        else {
            System.out.println("GET request failed");
        }
    }

    public static void userInterface()  {
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 500);
        frame.setVisible(true);

    }
}
