package ui;

import model.CreditCard;
import model.Wallet;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Represents a credit card manager application
// Code partially based on Teller application and Workroom application presented in CPSC 210 as indicated for
// specific methods
public class CreditCardManagerApp {
    private static final String JSON_STORE = "./data/wallet.json";
    public static final List<Integer> monthsWith31Days = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    public static final List<Integer> monthsWith30Days = Arrays.asList(4, 6, 9, 11);

    private Wallet wallet;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the credit card manager application
    // Code based on Teller application
    public CreditCardManagerApp() {
        runManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input into the credit card manager application
    // Code based on Teller application
    private void runManager() {
        boolean usingManager = true;
        String command;

        init();

        while (usingManager) {
            displayMainMenuGreeting();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("close")) {
                usingManager = false;
            } else {
                processMainMenuCommand(command);
            }
        }
        System.out.println("\nThank you for stopping by. See you soon.");
    }

    // MODIFIES: this
    // EFFECTS: initializes wallet
    // Code based on Teller application and Workroom application
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        wallet = new Wallet();
    }

    // EFFECTS: displays main menu of options with greetings to user
    private void displayMainMenuGreeting() {
        System.out.println("\nWelcome to your Personal Credit Card Manager. How may I help you today?");
        System.out.println("\tTo add a new credit card, enter 'add'.");
        System.out.println("\tTo view your list of credit cards, enter 'list'.");
        System.out.println("\tTo check the details of a credit card, enter 'details'.");
        System.out.println("\tTo update the active status of a credit card, enter 'update'.");
        System.out.println("\tTo save the current state of your wallet, enter 'save'.");
        System.out.println("\tTo load the last saved state of your wallet, enter 'load'.");
        System.out.println("\tTo close the application, enter 'close'.");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from main menu
    // Code based on Teller application
    private void processMainMenuCommand(String command) {
        if (command.equals("add")) {
            collectNewCardInfo();
        } else if (command.equals("list")) {
            displayCardList();
        } else if ((command.equals("details")) || (command.equals("update"))) {
            selectCard(command);
        } else if (command.equals("save")) {
            saveWallet();
        } else if (command.equals("load")) {
            loadWallet();
        } else {
            System.out.println("\nThe command you entered is invalid. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds active credit card with user-entered info to wallet or displays error message if info is invalid
    private void collectNewCardInfo() {
        String newCardName = collectUniqueName();
        if (newCardName != null) {
            int newCardLast4Digits = collectValidLast4Digits();
            if (newCardLast4Digits != -1) {
                int newCardPromotionEndYear = collectValidPromotionEndYear();
                if (newCardPromotionEndYear != -1) {
                    int newCardPromotionEndMonth = collectValidPromotionEndMonth();
                    if (newCardPromotionEndMonth != -1) {
                        int newCardPromotionEndDate = collectValidPromotionEndDate(newCardPromotionEndYear,
                                newCardPromotionEndMonth);
                        if (newCardPromotionEndDate != -1) {
                            String newCardPromotionDetails = collectPromotionDetails();
                            wallet.addCreditCard(new CreditCard(newCardName, newCardLast4Digits,
                                    newCardPromotionEndYear, newCardPromotionEndMonth, newCardPromotionEndDate,
                                    newCardPromotionDetails, true));
                            System.out.println(newCardName + " has been added.");
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Your new credit card has not been added. Please try again.");
    }

    // EFFECTS: prompts user for and returns name of new credit card if it is non-empty and unique, or null if it
    //          is empty or another card with user-entered name already exists in wallet
    private String collectUniqueName() {
        System.out.println("\nPlease enter the name of your new credit card:");
        String newCardName = input.next();
        if (newCardName.equals("")) {
            System.out.println("Your new credit card cannot have an empty name.");
            return null;
        } else if (wallet.selectCreditCard(newCardName) == null) {
            return newCardName;
        } else {
            System.out.println("A credit card with the name " + newCardName + " already exists.");
            return null;
        }
    }

    // EFFECTS: prompts user for and returns last 4 digits of new credit card if it is between [0, 9999], or
    //          -1 otherwise
    private int collectValidLast4Digits() {
        System.out.println("Please enter the last 4 digits of your new credit card:");
        if (input.hasNextInt()) {
            int newCardLast4Digits = input.nextInt();
            if ((0 <= newCardLast4Digits) && (newCardLast4Digits <= 9999)) {
                return newCardLast4Digits;
            }
        } else {
            input.next();
        }
        System.out.println("The last 4 digits you entered are invalid.");
        return -1;
    }

    // EFFECTS: prompts user for and returns promotion end year of new credit card if it is between [2022, 2122], or
    //          -1 otherwise
    private int collectValidPromotionEndYear() {
        System.out.println("Please enter the promotion end year of your new credit card:");
        if (input.hasNextInt()) {
            int newCardPromotionEndYear = input.nextInt();
            if ((2022 <= newCardPromotionEndYear) && (newCardPromotionEndYear <= 2122)) {
                return newCardPromotionEndYear;
            }
        } else {
            input.next();
        }
        System.out.println("The promotion end year you entered is invalid.");
        return -1;
    }

    // EFFECTS: prompts user for and returns promotion end month of new credit card if it is between [1, 12], or
    //          -1 otherwise
    private int collectValidPromotionEndMonth() {
        System.out.println("Please enter the promotion end month of your new credit card:");
        if (input.hasNextInt()) {
            int newCardPromotionEndMonth = input.nextInt();
            if ((1 <= newCardPromotionEndMonth) && (newCardPromotionEndMonth <= 12)) {
                return newCardPromotionEndMonth;
            }
        } else {
            input.next();
        }
        System.out.println("The promotion end month you entered is invalid.");
        return -1;
    }

    // EFFECTS: prompts user for and returns promotion end date of new credit card if it forms a valid date with
    //          newCardPromotionEndYear and newCardPromotionEndMonth, or -1 otherwise
    private int collectValidPromotionEndDate(int newCardPromotionEndYear, int newCardPromotionEndMonth) {
        System.out.println("Please enter the promotion end date of your new credit card:");
        if (input.hasNextInt()) {
            int newCardPromotionEndDate = input.nextInt();
            if (monthsWith31Days.contains(newCardPromotionEndMonth)) {
                if ((1 <= newCardPromotionEndDate) && (newCardPromotionEndDate <= 31)) {
                    return newCardPromotionEndDate;
                }
            } else if (monthsWith30Days.contains(newCardPromotionEndMonth)) {
                if ((1 <= newCardPromotionEndDate) && (newCardPromotionEndDate <= 30)) {
                    return newCardPromotionEndDate;
                }
            } else if (isValidFebruaryDate(newCardPromotionEndYear, newCardPromotionEndDate)) {
                return newCardPromotionEndDate;
            }
        } else {
            input.next();
        }
        System.out.println("The promotion end date you entered is invalid.");
        return -1;
    }

    // EFFECTS: returns true if newCardPromotionEndDate is a valid date in February of newCardPromotionEndYear, or
    //          false otherwise
    private boolean isValidFebruaryDate(int newCardPromotionEndYear, int newCardPromotionEndDate) {
        if ((newCardPromotionEndYear % 4 != 0)
                || ((newCardPromotionEndYear % 100 == 0) && (newCardPromotionEndYear % 400 != 0))) {
            return ((1 <= newCardPromotionEndDate) && (newCardPromotionEndDate <= 28));
        } else {
            return ((1 <= newCardPromotionEndDate) && (newCardPromotionEndDate <= 29));
        }
    }

    // EFFECTS: prompts user for and returns promotion details of new credit card
    private String collectPromotionDetails() {
        System.out.println("Please enter the promotion details of your new credit card:");
        return input.next();
    }

    // EFFECTS: displays a numbered list of names of credit cards in wallet
    private void displayCardList() {
        System.out.println("\nHere is a list of your credit cards:");
        int i = 1;
        for (CreditCard c : wallet.getCreditCardList()) {
            System.out.println("\t" + i + ". " + c.getName());
            i = i + 1;
        }
    }

    // REQUIRES: command is one of "details" or "update"
    // MODIFIES: this
    // EFFECTS: prompts user to select a credit card in wallet and applies command to selected credit card, or
    //          displays error message if selected credit card does not exist
    private void selectCard(String command) {
        System.out.println("\nPlease select a credit card by entering the name:");
        String selectedCardName = input.next();
        CreditCard selectedCard = wallet.selectCreditCard(selectedCardName);
        if (selectedCard == null) {
            System.out.println("The credit card you selected does not exist. Please try again.");
        } else {
            processSelectCardCommand(command, selectedCard);
        }
    }

    // REQUIRES: command is one of "details" or "update"
    // MODIFIES: this
    // EFFECTS: processes user command and applies to selectedCard
    // Code based on Teller application
    private void processSelectCardCommand(String command, CreditCard selectedCard) {
        if (command.equals("details")) {
            displayCardDetails(selectedCard);
        } else if (command.equals("update")) {
            switchCardActiveStatus(selectedCard);
        }
    }

    // EFFECTS: displays details of selectedCard
    private void displayCardDetails(CreditCard selectedCard) {
        System.out.println("\nHere are the details of " + selectedCard.getName() + ":");
        displayActiveStatus(selectedCard);
        System.out.println("\tLast 4 digits: " + selectedCard.getLast4Digits());
        System.out.println("\tPromotion end date (D-M-Y): " + selectedCard.getPromotionEndDate() + "-"
                + selectedCard.getPromotionEndMonth() + "-" + selectedCard.getPromotionEndYear());
        System.out.println("\tPromotion details: " + selectedCard.getPromotionDetails());
    }

    // EFFECTS: displays active status of selectedCard in all caps
    private void displayActiveStatus(CreditCard selectedCard) {
        if (selectedCard.getActiveStatus()) {
            System.out.println("\t<< ACTIVE >>");
        } else {
            System.out.println("\t<< INACTIVE >>");
        }
    }

    // MODIFIES: this
    // EFFECTS: switches selectedCard to opposite active status
    private void switchCardActiveStatus(CreditCard selectedCard) {
        if (selectedCard.getActiveStatus()) {
            selectedCard.inactivate();
            System.out.println(selectedCard.getName() + " has been inactivated.");
        } else {
            selectedCard.reactivate();
            System.out.println(selectedCard.getName() + " has been reactivated.");
        }
    }

    // EFFECTS: saves wallet to file at JSON_STORE
    // Code based on Workroom application
    private void saveWallet() {
        try {
            jsonWriter.open();
            jsonWriter.write(wallet);
            jsonWriter.close();
            System.out.println("Your wallet has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to write to file at " + JSON_STORE + ". Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads wallet from file at JSON_STORE
    // Code based on Workroom application
    private void loadWallet() {
        try {
            wallet = jsonReader.read();
            System.out.println("Your wallet has been loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Failed to read from file at " + JSON_STORE + ". Please try again.");
        }
    }
}
