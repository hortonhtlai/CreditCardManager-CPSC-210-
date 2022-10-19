package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Wallet class
public class WalletTest {
    private Wallet testWallet1;
    private CreditCard testCreditCard1;
    private CreditCard testCreditCard2;

    @BeforeEach
    public void runBefore() {
        testWallet1 = new Wallet();

        testCreditCard1 = new CreditCard("Bank A Cash Back",
                0000,
                2022,
                9,
                28,
                "2% cash back on all purchases");

        testCreditCard2 = new CreditCard("Bank B Travel Rewards",
                4917,
                2053,
                12,
                31,
                "3X reward points per $1 spent on flights");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testWallet1.getCreditCardList().size());
    }

    @Test
    public void testAddCreditCardOnce() {
        testWallet1.addCreditCard(testCreditCard2);
        assertEquals(1, testWallet1.getCreditCardList().size());
        assertEquals(testCreditCard2, testWallet1.getCreditCardList().get(0));
    }

    @Test
    public void testAddCreditCardMultipleTimes() {
        testWallet1.addCreditCard(testCreditCard1);
        testWallet1.addCreditCard(testCreditCard2);
        assertEquals(2, testWallet1.getCreditCardList().size());
        assertEquals(testCreditCard1, testWallet1.getCreditCardList().get(0));
        assertEquals(testCreditCard2, testWallet1.getCreditCardList().get(1));
    }

    @Test
    public void testSelectCreditCardFoundCaseMatch() {
        testWallet1.addCreditCard(testCreditCard1);
        testWallet1.addCreditCard(testCreditCard2);
        assertEquals(testCreditCard1, testWallet1.selectCreditCard("Bank A Cash Back"));
    }

    @Test
    public void testSelectCreditCardFoundCaseInsensitive() {
        testWallet1.addCreditCard(testCreditCard1);
        testWallet1.addCreditCard(testCreditCard2);
        assertEquals(testCreditCard2, testWallet1.selectCreditCard("bAnK b tRaVeL rEwArDs"));
    }

    @Test
    public void testSelectCreditCardNotFound() {
        testWallet1.addCreditCard(testCreditCard1);
        testWallet1.addCreditCard(testCreditCard2);
        assertNull(testWallet1.selectCreditCard("bank c air miles"));
    }
}
