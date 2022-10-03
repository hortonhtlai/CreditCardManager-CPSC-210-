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

    // EFFECTS: returns list of names of credit cards in wallet
    public List<String> viewCreditCardList() {
        List<String> cardNameList = new ArrayList<>();
        for (CreditCard c: creditCardList) {
            cardNameList.add(c.getName());
        }
        return cardNameList;
    }

    // EFFECTS: returns credit card with given name
    public CreditCard selectCreditCard(String name) {
        for (CreditCard c: creditCardList) {
            if (c.getName().equals(name)) {
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
