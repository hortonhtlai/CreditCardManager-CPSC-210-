package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {
    private CreditCard testCreditCard1;
    private CreditCard testCreditCard2;

    @BeforeEach
    public void runBefore() {
        testCreditCard1 = new CreditCard("Bank A Cash Back",
                0000,
                2022,
                12,
                31,
                "2% cash back on all purchases");
        testCreditCard2 = new CreditCard("Bank B Travel Rewards",
                4917,
                2053,
                03,
                02,
                "3X reward points per $1 spent on flights");
    }

    @Test
    public void testConstructor() {
        assertEquals("Bank A Cash Back", testCreditCard1.getName());
        assertEquals(0, testCreditCard1.getLast4Digits());
        assertEquals(2022, testCreditCard1.getPromotionEndYear());
        assertEquals(12, testCreditCard1.getPromotionEndMonth());
        assertEquals(31, testCreditCard1.getPromotionEndDate());
        assertEquals("2% cash back on all purchases", testCreditCard1.getPromotionDetails());
        assertTrue(testCreditCard1.getActiveStatus());

        assertEquals("Bank B Travel Rewards", testCreditCard2.getName());
        assertEquals(4917, testCreditCard2.getLast4Digits());
        assertEquals(2053, testCreditCard2.getPromotionEndYear());
        assertEquals(3, testCreditCard2.getPromotionEndMonth());
        assertEquals(2, testCreditCard2.getPromotionEndDate());
        assertEquals("3X reward points per $1 spent on flights",
                testCreditCard2.getPromotionDetails());
        assertTrue(testCreditCard2.getActiveStatus());
    }

    @Test
    public void testCheckCreditCardDetails() {
        assertEquals("name is Bank A Cash Back, last4Digits are 0, promotionEndYear is 2022, " +
                "promotionEndMonth is 12, promotionEndDate is 31, promotionDetails are 2% cash back on all " +
                "purchases, status is active", testCreditCard1.checkCreditCardDetails());

        testCreditCard2.inactivate();
        assertEquals("name is Bank B Travel Rewards, last4Digits are 4917, promotionEndYear is 2053, " +
                "promotionEndMonth is 3, promotionEndDate is 2, promotionDetails are 3X reward points per $1 " +
                "spent on flights, status is inactive", testCreditCard2.checkCreditCardDetails());
    }

    @Test
    public void testInactivate() {
        testCreditCard1.inactivate();
        assertFalse(testCreditCard1.getActiveStatus());
    }

    @Test
    public void testReactivate() {
        testCreditCard2.inactivate();
        assertFalse(testCreditCard2.getActiveStatus());
        testCreditCard2.reactivate();
        assertTrue(testCreditCard2.getActiveStatus());
    }
}