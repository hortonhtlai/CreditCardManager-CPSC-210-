package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a wallet for storing credit cards
// Code partially based on Workroom application presented in CPSC 210 as indicated for specific methods
// Workroom: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Wallet implements Writable {
    private List<CreditCard> creditCardList;

    // EFFECTS: creates a new wallet object with no credit cards
    public Wallet() {
        creditCardList = new ArrayList<>();
    }

    // REQUIRES: newCard identified by unique name is not already in this wallet
    // MODIFIES: this
    // EFFECTS: adds newCard to this wallet and logs card addition
    public void addCreditCard(CreditCard newCard) {
        creditCardList.add(newCard);
        EventLog.getInstance().logEvent(new Event(newCard.getName() + " credit card added to wallet."));
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

    // REQUIRES: statusType is one of false or null
    // EFFECTS: returns list of inactive credit cards in this wallet and logs filter imposition if statusType is
    //          false, or returns complete list of credit cards in this wallet and logs filter removal if
    //          statusType is null
    public List<CreditCard> getCreditCardListByStatus(Boolean statusType) {
        if (statusType != null) {
            List<CreditCard> creditCardListByStatus = new ArrayList<>();
            for (CreditCard c : creditCardList) {
                if (c.getActiveStatus() == statusType) {
                    creditCardListByStatus.add(c);
                }
            }
            EventLog.getInstance().logEvent(new Event("Filter for inactive credit cards imposed."));
            return creditCardListByStatus;
        }
        EventLog.getInstance().logEvent(new Event("Filter for inactive credit cards removed."));
        return getCreditCardList();
    }

    // EFFECTS: returns this wallet as JSON object
    // Code based on Workroom application
    @Override
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