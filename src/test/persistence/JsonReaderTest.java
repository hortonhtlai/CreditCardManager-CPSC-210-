package persistence;

import model.CreditCard;
import model.Wallet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonReader class
public class JsonReaderTest extends JsonTest {

    @Test
    public void testJsonReaderNonExistentWallet() {
        JsonReader testJsonReader1 = new JsonReader("./data/testNonExistentWallet.json");
        try {
            Wallet returnedWallet1 = testJsonReader1.read();
            fail("Failed to throw IOException from reading from non-existent wallet file.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testJsonReaderEmptyWallet() {
        JsonReader testJsonReader1 = new JsonReader("./data/testReaderEmptyWallet.json");
        try {
            Wallet returnedWallet1 = testJsonReader1.read();
            assertEquals(0, returnedWallet1.getCreditCardList().size());
        } catch (IOException e) {
            fail("Failed to read from empty wallet file.");
        }
    }

    @Test
    public void testJsonReaderGeneralWallet() {
        JsonReader testJsonReader1 = new JsonReader("./data/testReaderGeneralWallet.json");
        try {
            Wallet returnedWallet1 = testJsonReader1.read();
            List<CreditCard> returnedCreditCardList1 = returnedWallet1.getCreditCardList();
            assertEquals(2, returnedCreditCardList1.size());
            checkCreditCard("Bank A Gourmet Rewards Extra",
                    4030,
                    2023,
                    10,
                    20,
                    "1 reward points per $1 spent on groceries or restaurants",
                    true,
                    returnedCreditCardList1.get(0));
            checkCreditCard("Bank B Business Travel",
                    3967,
                    2024,
                    8,
                    29,
                    "0.1 miles per $10 spent on travel expenses",
                    false,
                    returnedCreditCardList1.get(1));
        } catch (IOException e) {
            fail("Failed to read from general wallet file.");
        }
    }
}
