package persistence;

import model.CreditCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit test methods for Json objects
public class JsonTest {
    protected void checkCreditCard(String name,
                                   int last4Digits,
                                   int promotionEndYear,
                                   int promotionEndMonth,
                                   int promotionEndDate,
                                   String promotionDetails,
                                   boolean isActive,
                                   CreditCard creditCard) {
        assertEquals(name, creditCard.getName());
        assertEquals(last4Digits, creditCard.getLast4Digits());
        assertEquals(promotionEndYear, creditCard.getPromotionEndYear());
        assertEquals(promotionEndMonth, creditCard.getPromotionEndMonth());
        assertEquals(promotionEndDate, creditCard.getPromotionEndDate());
        assertEquals(promotionDetails, creditCard.getPromotionDetails());
        assertEquals(isActive, creditCard.getActiveStatus());
    }
}
