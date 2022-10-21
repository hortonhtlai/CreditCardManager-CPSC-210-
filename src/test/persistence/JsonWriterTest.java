package persistence;

import model.CreditCard;
import model.Wallet;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonWriter class
public class JsonWriterTest extends JsonTest {

    @Test
    public void testJsonWriterIllegalFile() {
        try {
            Wallet testWallet1 = new Wallet();
            JsonWriter testJsonWriter1 = new JsonWriter("./data/test\0IllegalWallet.json");
            testJsonWriter1.open();
            fail("Failed to throw IOException from writing to illegal wallet file");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testJsonWriterEmptyWallet() {
        try {
            Wallet testWallet1 = new Wallet();
            JsonWriter testJsonWriter1 = new JsonWriter("./data/testWriterEmptyWallet.json");
            testJsonWriter1.open();
            testJsonWriter1.write(testWallet1);
            testJsonWriter1.close();

            JsonReader testJsonReader1 = new JsonReader("./data/testWriterEmptyWallet.json");
            Wallet returnedWallet1 = testJsonReader1.read();
            assertEquals(0, returnedWallet1.getCreditCardList().size());
        } catch (IOException e) {
            fail("Failed to write to empty wallet file");
        }
    }

    @Test
    public void testJsonWriterGeneralWallet() {
        try {
            Wallet testWallet1 = new Wallet();
            CreditCard testCreditCard1 = new CreditCard("Bank A Gourmet Rewards Extra",
                    4030,
                    2023,
                    10,
                    20,
                    "1 reward points per $1 spent on groceries or restaurants",
                    true);
            CreditCard testCreditCard2 = new CreditCard("Bank B Business Travel",
                    3967,
                    2024,
                    8,
                    29,
                    "0.1 miles per $10 spent on travel expenses",
                    false);
            testWallet1.addCreditCard(testCreditCard1);
            testWallet1.addCreditCard(testCreditCard2);

            JsonWriter testJsonWriter1 = new JsonWriter("./data/testWriterGeneralWallet.json");
            testJsonWriter1.open();
            testJsonWriter1.write(testWallet1);
            testJsonWriter1.close();

            JsonReader testJsonReader1 = new JsonReader("./data/testWriterGeneralWallet.json");
            Wallet returnedWallet1 = testJsonReader1.read();
            assertEquals(2, returnedWallet1.getCreditCardList().size());
            checkCreditCard("Bank A Gourmet Rewards Extra",
                    4030,
                    2023,
                    10,
                    20,
                    "1 reward points per $1 spent on groceries or restaurants",
                    true,
                    returnedWallet1.getCreditCardList().get(0));
            checkCreditCard("Bank B Business Travel",
                    3967,
                    2024,
                    8,
                    29,
                    "0.1 miles per $10 spent on travel expenses",
                    false,
                    returnedWallet1.getCreditCardList().get(1));
        } catch (IOException e) {
            fail("Failed to write to general wallet file");
        }
    }
}
