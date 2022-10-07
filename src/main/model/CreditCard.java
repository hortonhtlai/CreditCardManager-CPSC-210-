package model;

// Represents a credit card
public class CreditCard {
    private String name;
    private int last4Digits;
    private int promotionEndYear;
    private int promotionEndMonth;
    private int promotionEndDate;
    private String promotionDetails;
    private boolean isActive;

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
        this.name = name;
        this.last4Digits = last4Digits;
        this.promotionEndYear = promotionEndYear;
        this.promotionEndMonth = promotionEndMonth;
        this.promotionEndDate = promotionEndDate;
        this.promotionDetails = promotionDetails;
        this.isActive = true;
    }

    // REQUIRES: credit card is active
    // MODIFIES: this
    // EFFECTS: switches credit card to inactive
    public void inactivate() {
        isActive = false;
    }

    // REQUIRES: credit card is inactive
    // MODIFIES: this
    // EFFECTS: switches credit card to active
    public void reactivate() {
        isActive = true;
    }

    // EFFECTS: returns name of credit card
    public String getName() {
        return name;
    }

    // EFFECTS: returns last4Digits of credit card
    public int getLast4Digits() {
        return last4Digits;
    }

    // EFFECTS: returns promotionEndYear of credit card
    public int getPromotionEndYear() {
        return promotionEndYear;
    }

    // EFFECTS: returns promotionEndMonth of credit card
    public int getPromotionEndMonth() {
        return promotionEndMonth;
    }

    // EFFECTS: returns promotionEndDate of credit card
    public int getPromotionEndDate() {
        return promotionEndDate;
    }

    // EFFECTS: returns promotionDetails of credit card
    public String getPromotionDetails() {
        return promotionDetails;
    }

    // EFFECTS: returns true if credit card is active or false otherwise
    public boolean getActiveStatus() {
        return isActive;
    }
}
