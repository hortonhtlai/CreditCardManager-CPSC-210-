package ui;

import model.CreditCard;
import model.Wallet;

import java.util.Scanner;

// Represents a credit card manager application
// Code partially based on Teller application presented in CPSC 210 as indicated in each method
public class CreditCardManagerApp {
    private Wallet wallet;
    private Scanner input;

    public CreditCardManagerApp() {
        runManager();
    }

    public void runManager() {
        boolean usingManager = true;
        String command;

        init();

        while (usingManager) {
            mainMenuGreeting();
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

    public void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        wallet = new Wallet();
        CreditCard creditCard1 = new CreditCard("Bank A Rewards",
                4030, 2023, 10, 20,
                "1 reward points per $1 spent on groceries or restaurants");
        CreditCard creditCard2 = new CreditCard("Bank B Cash Back",
                9217, 2025, 5, 16,
                "2% cash back on all purchases");
        CreditCard creditCard3 = new CreditCard("Bank C Business Travel",
                3967, 2024, 8, 29,
                "0.1 miles per $10 spent on travel expenses");
        wallet.addCreditCard(creditCard1);
        wallet.addCreditCard(creditCard2);
        wallet.addCreditCard(creditCard3);
    }

    public void mainMenuGreeting() {
        System.out.println("\nWelcome to your Personal Credit Card Manager. How may I help you today?");
        System.out.println("\tTo add a new credit card, enter 'add'.");
        System.out.println("\tTo view your list of credit cards, enter 'list'.");
        System.out.println("\tTo check the details of a credit card, enter 'details'.");
        System.out.println("\tTo update the active status of a credit card, enter 'update'.");
        System.out.println("\tTo close the application, enter 'close'.");
    }

    public void processMainMenuCommand(String command) {
        if (command.equals("add")) {
            collectNewCardInfo();
        } else if (command.equals("list")) {
            displayCardList();
        } else if ((command.equals("details")) || (command.equals("update"))) {
            selectCard(command);
        } else {
            System.out.println("\nThe command you entered is invalid. Please try again.");
        }
    }

    public void collectNewCardInfo() {
        System.out.println("\nPlease enter the name of your new credit card:");
        String newCardName = input.next();
        System.out.println("Please enter the last 4 digits of " + newCardName + ":");
        int newCardLast4Digits = input.nextInt();
        System.out.println("Please enter the promotion end year of " + newCardName + ":");
        int newCardPromotionEndYear = input.nextInt();
        System.out.println("Please enter the promotion end month of " + newCardName + ":");
        int newCardPromotionEndMonth = input.nextInt();
        System.out.println("Please enter the promotion end date of " + newCardName + ":");
        int newCardPromotionEndDate = input.nextInt();
        System.out.println("Please enter the promotion details of " + newCardName + ":");
        String newCardPromotionDetails = input.next();

        wallet.addCreditCard(new CreditCard(newCardName,
                newCardLast4Digits,
                newCardPromotionEndYear,
                newCardPromotionEndMonth,
                newCardPromotionEndDate,
                newCardPromotionDetails));
        System.out.println(newCardName + " has been added.");
    }

    public void displayCardList() {
        System.out.println("\nHere is a list of your credit cards:");
        int i = 1;
        for (CreditCard c : wallet.getCreditCardList()) {
            System.out.println("\t" + Integer.toString(i) + ". " + c.getName());
            i = i + 1;
        }
    }

    public void selectCard(String command) {
        System.out.println("\nPlease select a credit card by entering the name:");
        String selectedCardName = input.next();
        CreditCard selectedCard = wallet.selectCreditCard(selectedCardName);
        if (selectedCard == null) {
            System.out.println("The credit card you selected does not exist. Please try again.");
        } else {
            processSelectCardCommand(command, selectedCard);
        }
    }

    public void processSelectCardCommand(String command, CreditCard selectedCard) {
        if (command.equals("details")) {
            displayCardDetails(selectedCard);
        } else if (command.equals("update")) {
            updateCardActiveStatus(selectedCard);
        }
    }

    public void displayCardDetails(CreditCard selectedCard) {
        System.out.println("\nHere are the details of " + selectedCard.getName() + ":");
        displayActiveStatus(selectedCard);
        System.out.println("\tLast 4 digits: " + selectedCard.getLast4Digits());
        System.out.println("\tPromotion end date (DD-MM-YYYY): " + selectedCard.getPromotionEndDate() + "-"
                + selectedCard.getPromotionEndMonth() + "-" + selectedCard.getPromotionEndYear());
        System.out.println("\tPromotion details: " + selectedCard.getPromotionDetails());
    }

    public void displayActiveStatus(CreditCard selectedCard) {
        if (selectedCard.getActiveStatus()) {
            System.out.println("\t<< ACTIVE >>");
        } else {
            System.out.println("\t<< INACTIVE >>");
        }
    }

    public void updateCardActiveStatus(CreditCard selectedCard) {
        if (selectedCard.getActiveStatus()) {
            selectedCard.inactivate();
            System.out.println(selectedCard.getName() + " has been inactivated.");
        } else {
            selectedCard.reactivate();
            System.out.println(selectedCard.getName() + " has been reactivated.");
        }
    }
}
