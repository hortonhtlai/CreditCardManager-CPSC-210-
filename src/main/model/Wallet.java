package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a wallet for storing credit cards
// Code partially based on Workroom application presented in CPSC 210 as indicated for specific methods
public class Wallet {
    private List<CreditCard> creditCardList;

    // EFFECTS: creates a new wallet object with no credit cards
    public Wallet() {
        creditCardList = new ArrayList<>();
    }

    // REQUIRES: newCard identified by unique name is not already in this wallet
    // MODIFIES: this
    // EFFECTS: adds newCard to this wallet
    public void addCreditCard(CreditCard newCard) {
        creditCardList.add(newCard);
    }

    // EFFECTS: returns credit card with case-insensitive queryName in this wallet, or null if no such card is found
    public CreditCard selectCreditCard(String queryName) {
        queryName = queryName.toLowerCase();
        String cardName;
        for (CreditCard c : creditCardList) {
            cardName = c.getName();
            cardName = cardName.toLowerCase();
            if (cardName.equals(queryName)) {
                return c;
            }
        }
        return null;
    }

    public List<CreditCard> getCreditCardList() {
        return creditCardList;
    }

    // EFFECTS: returns this wallet as JSON object
    // Code based on Workroom application
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("credit card list", creditCardListToJson());
        return jsonObject;
    }

    // EFFECTS: returns list of credit cards in this wallet as JSON array
    // Code based on Workroom application
    public JSONArray creditCardListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (CreditCard c : creditCardList) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}