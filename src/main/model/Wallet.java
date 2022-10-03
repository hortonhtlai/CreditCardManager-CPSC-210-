package model;

import java.util.List;

// Represents a wallet
public class Wallet {

    // EFFECTS: creates a new wallet object
    public Wallet() {
        // stub
    }

    // REQUIRES: newCard is not already in wallet
    // MODIFIES: this
    // EFFECTS: adds newCard to wallet
    public void addCreditCard(CreditCard newCard) {
        // stub
    }

    // EFFECTS: returns list of names of credit cards in wallet
    public List<String> viewCreditCardList() {
        return null; // stub
    }

    // EFFECTS: returns credit card with given name
    public CreditCard selectCreditCard(String name) {
        return null; // stub
    }

    // EFFECTS: returns list of credit cards in wallet
    public List<CreditCard> getCreditCardList() {
        return null; // stub
    }
}
