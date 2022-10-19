package persistence;

import model.CreditCard;
import model.Wallet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads wallet from JSON data stored in file
// Code based on Workroom application presented in CPSC 210
public class JsonReader {
    private String source;

    // EFFECTS: creates a new JsonReader object which reads from given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads and returns wallet from source file or throws IOException if error occurs when reading data
    //          from source file
    public Wallet read(String source) throws IOException {
        String walletJsonText = readFile(source);
        JSONObject walletJsonObject = new JSONObject(walletJsonText);
        return parseWallet(walletJsonObject);
    }

    // EFFECTS: reads source file as string and returns it, or throws IOException if error occurs when reading data
    //          from source file
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses wallet from walletJsonObject and returns it
    public Wallet parseWallet(JSONObject walletJsonObject) {
        Wallet wallet = new Wallet();
        addCreditCardList(wallet, walletJsonObject);
        return wallet;
    }

    // MODIFIES: wallet
    // EFFECTS: parses credit cards from walletJsonObject and adds them to wallet
    private void addCreditCardList(Wallet wallet, JSONObject walletJsonObject) {
        JSONArray creditCardListJsonArray = walletJsonObject.getJSONArray("credit card list");
        for (Object json : creditCardListJsonArray) {
            JSONObject creditCardJsonObject = (JSONObject) json;
            addCreditCard(wallet, creditCardJsonObject);
        }
    }

    // MODIFIES: wallet
    // EFFECTS: parses single credit card from creditCardJsonObject and adds it to wallet
    private void addCreditCard(Wallet wallet, JSONObject creditCardJsonObject) {
        String name = creditCardJsonObject.getString("name");
        int last4Digits = creditCardJsonObject.getInt("last 4 digits");
        int promotionEndYear = creditCardJsonObject.getInt("promotion end year");
        int promotionEndMonth = creditCardJsonObject.getInt("promotion end month");
        int promotionEndDate = creditCardJsonObject.getInt("promotion end date");
        String promotionDetails = creditCardJsonObject.getString("promotion details");
        boolean isActive = creditCardJsonObject.getBoolean("is active");
        CreditCard creditCard = new CreditCard(name,
                last4Digits,
                promotionEndYear,
                promotionEndMonth,
                promotionEndDate,
                promotionDetails,
                isActive);
        wallet.addCreditCard(creditCard);
    }
}
