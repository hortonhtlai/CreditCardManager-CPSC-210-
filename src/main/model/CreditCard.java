package model;

// Represents a credit card
public class CreditCard {

    // REQUIRES: name is unique AND last4Digits is between [0, 9999] AND promotionEndYear is between [2022, 2122] AND
    //           promotionEndMonth is between [1, 12] AND promotionEndDate is between [1, 31] AND promotionEndYear,
    //           promotionEndMonth, and promotionEndDate form a valid date in the future
    // EFFECTS: creates a new credit card object
    public CreditCard(String name,
                      int last4Digits,
                      int promotionEndYear,
                      int promotionEndMonth,
                      int promotionEndDate,
                      String promotionDetails) {
        // stub
    }

    // EFFECTS: returns name, last4Digits, promotionEndYear, promotionEndMonth, promotionEndDate, and promotionDetails
    //          of credit card in sentences
    public String checkCreditCardDetails() {
        return null; // stub
    }

    // REQUIRES: credit card is active
    // MODIFIES: this
    // EFFECTS: switches credit card to inactive
    public void inactivate() {
        // stub
    }

    // REQUIRES: credit card is inactive
    // MODIFIES: this
    // EFFECTS: switches credit card to active
    public void reactivate() {
        // stub
    }

    // EFFECTS: returns name of credit card
    public String getName() {
        return ""; // stub
    }

    // EFFECTS: returns last4Digits of credit card
    public int getLast4Digits() {
        return 0; // stub
    }

    // EFFECTS: returns promotionEndYear of credit card
    public int getPromotionEndYear() {
        return 0; // stub
    }

    // EFFECTS: returns promotionEndMonth of credit card
    public int getPromotionEndMonth() {
        return 0; // stub
    }

    // EFFECTS: returns promotionEndDate of credit card
    public int getPromotionEndDate() {
        return 0; // stub
    }

    // EFFECTS: returns promotionDetails of credit card
    public String getPromotionDetails() {
        return ""; //stub
    }

    // EFFECTS: returns true if credit card is active or false otherwise
    public boolean getActiveStatus() {
        return true; // stub
    }
}
