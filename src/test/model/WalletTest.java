package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testSelectCreditCard() {
        testWallet1.addCreditCard(testCreditCard1);
        testWallet1.addCreditCard(testCreditCard2);
        assertEquals(testCreditCard1, testWallet1.selectCreditCard("Bank A Cash Back"));
        assertEquals(testCreditCard2, testWallet1.selectCreditCard("Bank B Travel Rewards"));
    }
}
