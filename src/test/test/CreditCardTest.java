package test;

import model.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for CreditCard class
class CreditCardTest {
    private CreditCard testCreditCard1;

    @BeforeEach
    public void runBefore() {
        testCreditCard1 = new CreditCard("Bank A Cash Back",
                0000,
                2022,
                9,
                28,
                "2% cash back on all purchases");
    }

    @Test
    public void testConstructorWithActiveStatus() {
        CreditCard testCreditCard2 = new CreditCard("Bank B Travel Rewards",
                4917,
                2053,
                12,
                31,
                "3X reward points per $1 spent on flights",
                false);

        assertEquals("Bank B Travel Rewards", testCreditCard2.getName());
        assertEquals(4917, testCreditCard2.getLast4Digits());
        assertEquals(2053, testCreditCard2.getPromotionEndYear());
        assertEquals(12, testCreditCard2.getPromotionEndMonth());
        assertEquals(31, testCreditCard2.getPromotionEndDate());
        assertEquals("3X reward points per $1 spent on flights", testCreditCard2.getPromotionDetails());
        assertFalse(testCreditCard2.getActiveStatus());
    }

    @Test
    public void testConstructorNoActiveStatus() {
        assertEquals("Bank A Cash Back", testCreditCard1.getName());
        assertEquals(0, testCreditCard1.getLast4Digits());
        assertEquals(2022, testCreditCard1.getPromotionEndYear());
        assertEquals(9, testCreditCard1.getPromotionEndMonth());
        assertEquals(28, testCreditCard1.getPromotionEndDate());
        assertEquals("2% cash back on all purchases", testCreditCard1.getPromotionDetails());
        assertTrue(testCreditCard1.getActiveStatus());
    }

    @Test
    public void testInactivateOnce() {
        testCreditCard1.inactivate();
        assertFalse(testCreditCard1.getActiveStatus());
    }

    @Test
    public void testReactivateOnce() {
        testCreditCard1.inactivate();
        assertFalse(testCreditCard1.getActiveStatus());
        testCreditCard1.reactivate();
        assertTrue(testCreditCard1.getActiveStatus());
    }

    @Test
    public void testInactivateReactivateMultipleTimes() {
        testCreditCard1.inactivate();
        assertFalse(testCreditCard1.getActiveStatus());
        testCreditCard1.reactivate();
        assertTrue(testCreditCard1.getActiveStatus());
        testCreditCard1.inactivate();
        assertFalse(testCreditCard1.getActiveStatus());
        testCreditCard1.reactivate();
        assertTrue(testCreditCard1.getActiveStatus());
    }
}