package model;

import java.util.List;

// Represents a wallet
public class Wallet {

    // EFFECTS: creates a new wallet object
    public Wallet() {
        // stub
    }

    // REQUIRES: name is unique AND last4Digits is between [0, 9999] AND promotionEndYear is between [2022, 2122] AND
    //           promotionEndMonth is between [1, 12] AND promotionEndDate is between [1, 31] AND promotionEndYear,
    //           promotionEndMonth, and promotionEndDate form a valid date in the future
    // MODIFIES: this
    // EFFECTS: adds a new credit card with given information to wallet
    public void addCreditCard(String name,
                              int last4Digits,
                              int promotionEndYear,
                              int promotionEndMonth,
                              int promotionEndDate,
                              String promotionDetails) {
        // stub
    }

    // EFFECTS: returns list of names of credit cards in wallet
    public List<String> viewCreditCardList() {
        return null; // stub
    }

    // EFFECTS: selects credit card with given name
    public CreditCard selectCreditCard(String name) {
        return null; // stub
    }
}
