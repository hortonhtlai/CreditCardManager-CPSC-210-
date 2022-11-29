package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
                "2% cash back on all purchases",
                true);
        EventLog.getInstance().clear();
    }

    @Test
    public void testConstructor() {
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
        Iterator<Event> testLogIterator1 = EventLog.getInstance().iterator();
        testLogIterator1.next();
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card inactivated."));
    }

    @Test
    public void testReactivateOnce() {
        testCreditCard1.inactivate();
        assertFalse(testCreditCard1.getActiveStatus());
        testCreditCard1.reactivate();
        assertTrue(testCreditCard1.getActiveStatus());
        Iterator<Event> testLogIterator1 = EventLog.getInstance().iterator();
        testLogIterator1.next();
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card inactivated."));
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card reactivated."));
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
        Iterator<Event> testLogIterator1 = EventLog.getInstance().iterator();
        testLogIterator1.next();
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card inactivated."));
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card reactivated."));
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card inactivated."));
        assertTrue(testLogIterator1.next().getDescription().equals("Bank A Cash Back credit card reactivated."));
    }
}