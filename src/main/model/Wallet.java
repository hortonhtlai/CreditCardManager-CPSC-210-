package model;

import java.util.ArrayList;
import java.util.List;

// Represents a wallet
public class Wallet {
    private List<CreditCard> creditCardList;

    // EFFECTS: creates a new wallet object
    public Wallet() {
        creditCardList = new ArrayList<>();
    }

    // REQUIRES: newCard is not already in wallet
    // MODIFIES: this
    // EFFECTS: adds newCard to wallet
    public void addCreditCard(CreditCard newCard) {
        creditCardList.add(newCard);
    }

    // EFFECTS: returns credit card with given name
    public CreditCard selectCreditCard(String queryName) {
        queryName = queryName.toLowerCase();
        String cardName;
        for (CreditCard c: creditCardList) {
            cardName = c.getName();
            cardName = cardName.toLowerCase();
            if (cardName.equals(queryName)) {
                return c;
            }
        }
        return null;
    }

    // EFFECTS: returns list of credit cards in wallet
    public List<CreditCard> getCreditCardList() {
        return creditCardList;
    }
}