package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a credit card with unique name as identifier, active status, and info regarding promotions
public class CreditCard implements Writable {
    private String name;
    private int last4Digits;
    private int promotionEndYear;
    private int promotionEndMonth;
    private int promotionEndDate;
    private String promotionDetails;
    private boolean isActive;

    // REQUIRES: name is unique AND last4Digits is between [0, 9999] AND promotionEndYear is between [2022, 2122]
    //           AND promotionEndYear, promotionEndMonth, and promotionEndDate form a valid date
    // EFFECTS: creates a new credit card object with given name, last4Digits, promotionEndYear, promotionEndMonth,
    //          promotionEndDate, promotionDetails, and active status set to isActive
    public CreditCard(String name,
                      int last4Digits,
                      int promotionEndYear,
                      int promotionEndMonth,
                      int promotionEndDate,
                      String promotionDetails,
                      boolean isActive) {
        this.name = name;
        this.last4Digits = last4Digits;
        this.promotionEndYear = promotionEndYear;
        this.promotionEndMonth = promotionEndMonth;
        this.promotionEndDate = promotionEndDate;
        this.promotionDetails = promotionDetails;
        this.isActive = isActive;
    }

    // REQUIRES: this credit card is active
    // MODIFIES: this
    // EFFECTS: switches this credit card to inactive and logs status change
    public void inactivate() {
        isActive = false;
        EventLog.getInstance().logEvent(new Event(name + " credit card inactivated."));
    }

    // REQUIRES: this credit card is inactive
    // MODIFIES: this
    // EFFECTS: switches this credit card to active and logs status change
    public void reactivate() {
        isActive = true;
        EventLog.getInstance().logEvent(new Event(name + " credit card reactivated."));
    }

    public String getName() {
        return name;
    }

    public int getLast4Digits() {
        return last4Digits;
    }

    public int getPromotionEndYear() {
        return promotionEndYear;
    }

    public int getPromotionEndMonth() {
        return promotionEndMonth;
    }

    public int getPromotionEndDate() {
        return promotionEndDate;
    }

    public String getPromotionDetails() {
        return promotionDetails;
    }

    public boolean getActiveStatus() {
        return isActive;
    }

    // EFFECTS: returns this credit card as JSON object
    // Code based on Workroom application
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("last 4 digits", last4Digits);
        jsonObject.put("promotion end year", promotionEndYear);
        jsonObject.put("promotion end month", promotionEndMonth);
        jsonObject.put("promotion end date", promotionEndDate);
        jsonObject.put("promotion details", promotionDetails);
        jsonObject.put("is active", isActive);
        return jsonObject;
    }
}
