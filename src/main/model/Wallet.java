package model;

import java.util.ArrayList;
import java.util.List;

// Represents a wallet for storing credit cards
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
}