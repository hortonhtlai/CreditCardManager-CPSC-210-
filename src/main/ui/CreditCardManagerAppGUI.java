package ui;

import model.CreditCard;
import model.Wallet;
import model.exception.DuplicateNameException;
import model.exception.EmptyNameException;
import model.exception.OutOfRangeException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static javax.swing.SwingConstants.*;

// Represents a credit card manager application
// Code partially based on Teller application and Workroom application presented in CPSC 210 as indicated for
// specific methods
public class CreditCardManagerAppGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/wallet.json";
    public static final List<Integer> monthsWith31Days = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    public static final List<Integer> monthsWith30Days = Arrays.asList(4, 6, 9, 11);

    private Wallet wallet;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JInternalFrame managerPanel;
    private DefaultListModel creditCardListModel;
    private JList creditCardJList;
    private JToggleButton filterForInactiveCards;

    private JPanel creditCardAdderPanel;
    private JTextField nameField;
    private JTextField last4DigitsField;
    private JTextField promotionEndYearField;
    private JTextField promotionEndMonthField;
    private JTextField promotionEndDateField;
    private JTextArea promotionDetailsArea;

    // EFFECTS: runs the credit card manager application
    // Code based on Teller application and Alarm System application
    public CreditCardManagerAppGUI() {
        init();

        JDesktopPane desktop = new JDesktopPane();
        managerPanel = new JInternalFrame("Manager",
                true,
                false,
                true,
                false);
        managerPanel.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("Credit Card Manager Application");
        setSize(WIDTH, HEIGHT);

        addCreditCardListPanel();
        addButtonPanel();
        addCreditCardAdderPanel();

        managerPanel.pack();
        managerPanel.setVisible(true);
        managerPanel.reshape(0, 0, 700, 500);
        desktop.add(managerPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addCreditCardListPanel() {
        creditCardListModel = new DefaultListModel<>();
        creditCardListToListModel();
        creditCardJList = new JList(creditCardListModel);
        creditCardJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        creditCardJList.setSelectedIndex(0);
        creditCardJList.setVisibleRowCount(20);
        JScrollPane creditCardListScrollPane = new JScrollPane(creditCardJList);
        JLabel creditCardListLabel = new JLabel("Credit Card List", CENTER);
        creditCardListScrollPane.setColumnHeaderView(creditCardListLabel);

        JPanel creditCardListPanel = new JPanel(new BorderLayout());
        creditCardListPanel.add(creditCardListScrollPane, BorderLayout.CENTER);
        creditCardListPanel.add(new JButton(new SwitchActiveStatusAction()), BorderLayout.SOUTH);

        managerPanel.add(creditCardListPanel, BorderLayout.CENTER);
    }

    private void creditCardListToListModel() {
        creditCardListModel.removeAllElements();
        List<CreditCard> creditCardList = wallet.getCreditCardList();
        int i = 1;
        for (CreditCard c : creditCardList) {
            if (!(filterForInactiveCards.isSelected()) || !(c.getActiveStatus())) {
                creditCardListModel.addElement(i + ". " + displayActiveStatus(c) + " " + c.getName());
                i = i + 1;
            }
        }
    }

    private class SwitchActiveStatusAction extends AbstractAction {
        SwitchActiveStatusAction() {
            super("Switch Active Status");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedCardIndex = creditCardJList.getSelectedIndex();
            if (selectedCardIndex != -1) {
                switchCardActiveStatus(wallet.getCreditCardList().get(selectedCardIndex));
                creditCardListToListModel();
            }
        }
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        buttonPanel.add(new JButton(new AddCreditCardAction()));
        filterForInactiveCards = new JToggleButton(new FilterInactiveCardsAction());
        filterForInactiveCards.setSelected(false);
        buttonPanel.add(filterForInactiveCards);
        buttonPanel.add(new JButton(new SaveWalletAction()));
        buttonPanel.add(new JButton(new LoadWalletAction()));

        managerPanel.add(buttonPanel, BorderLayout.WEST);
    }

    private class AddCreditCardAction extends AbstractAction {
        AddCreditCardAction() {
            super("Add Credit Card");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (creditCardAdderPanel.isVisible()) {
                creditCardAdderPanel.setVisible(false);
                nameField.setText("");
                last4DigitsField.setText("");
                promotionEndYearField.setText("");
                promotionEndMonthField.setText("");
                promotionEndDateField.setText("");
                promotionDetailsArea.setText("");
            } else {
                creditCardAdderPanel.setVisible(true);
            }
        }
    }

    private class FilterInactiveCardsAction extends AbstractAction {
        FilterInactiveCardsAction() {
            super("Filter for Inactive Credit Cards");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            creditCardListToListModel();
        }
    }

    private class SaveWalletAction extends AbstractAction {
        SaveWalletAction() {
            super("Save Wallet");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveWallet();
        }
    }

    private class LoadWalletAction extends AbstractAction {
        LoadWalletAction() {
            super("Load Wallet");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadWallet();
            creditCardListToListModel();
        }
    }

    private void addCreditCardAdderPanel() {
        nameField = new JTextField();
        last4DigitsField = new JTextField();
        promotionEndYearField = new JTextField();
        promotionEndMonthField = new JTextField();
        promotionEndDateField = new JTextField();
        promotionDetailsArea = new JTextArea();
        promotionDetailsArea.setColumns(20);
        promotionDetailsArea.setRows(3);
        promotionDetailsArea.setLineWrap(true);

        JPanel trivialInfoPanel = new JPanel(new GridLayout(0, 1));
        trivialInfoPanel.add(new JLabel("Name of new credit card:"));
        trivialInfoPanel.add(nameField);
        trivialInfoPanel.add(new JLabel("Last 4 digits:"));
        trivialInfoPanel.add(last4DigitsField);
        trivialInfoPanel.add(new JLabel("Promotion end year:"));
        trivialInfoPanel.add(promotionEndYearField);
        trivialInfoPanel.add(new JLabel("Promotion end month:"));
        trivialInfoPanel.add(promotionEndMonthField);
        trivialInfoPanel.add(new JLabel("Promotion end date:"));
        trivialInfoPanel.add(promotionEndDateField);
        trivialInfoPanel.add(new JLabel("Promotion details:"));

        creditCardAdderPanel = new JPanel(new BorderLayout());
        creditCardAdderPanel.add(trivialInfoPanel, BorderLayout.NORTH);
        creditCardAdderPanel.add(promotionDetailsArea, BorderLayout.CENTER);
        creditCardAdderPanel.add(new JButton(new SubmitNewCardInfoAction()), BorderLayout.SOUTH);
        creditCardAdderPanel.setVisible(false);

        managerPanel.add(creditCardAdderPanel, BorderLayout.EAST);
    }

    private class SubmitNewCardInfoAction extends AbstractAction {
        SubmitNewCardInfoAction() {
            super("Submit New Card Info");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                addNewCardWithSubmittedInfo();
            } catch (Exception exception) {
                // terminate
            }
        }
    }

    private void addNewCardWithSubmittedInfo() throws DuplicateNameException, EmptyNameException, OutOfRangeException {
        String newCardName = validateName(nameField.getText());
        int newCardLast4Digits = validateLast4Digits(last4DigitsField.getText());
        int newCardPromotionEndYear = validatePromotionEndYear(promotionEndYearField.getText());
        int newCardPromotionEndMonth = validatePromotionEndMonth(promotionEndMonthField.getText());
        int newCardPromotionEndDate = validatePromotionEndDate(newCardPromotionEndYear,
                newCardPromotionEndMonth,
                promotionEndDateField.getText());
        String newCardPromotionDetails = promotionDetailsArea.getText();

        wallet.addCreditCard(new CreditCard(newCardName,
                newCardLast4Digits,
                newCardPromotionEndYear,
                newCardPromotionEndMonth,
                newCardPromotionEndDate,
                newCardPromotionDetails,
                true));

        creditCardListToListModel();

        creditCardAdderPanel.setVisible(false);

        nameField.setText("");
        last4DigitsField.setText("");
        promotionEndYearField.setText("");
        promotionEndMonthField.setText("");
        promotionEndDateField.setText("");
        promotionDetailsArea.setText("");
    }

    // MODIFIES: this
    // EFFECTS: initializes wallet
    // Code based on Teller application and Workroom application
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        wallet = new Wallet();
    }

    // EFFECTS: prompts user for and returns name of new credit card if it is non-empty and unique, or null if it
    //          is empty or another card with user-entered name already exists in wallet
    private String validateName(String newCardName) throws EmptyNameException, DuplicateNameException {
        if (newCardName.equals("")) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "Your new credit card cannot have an empty name.");
            throw new EmptyNameException();
        } else if (wallet.selectCreditCard(newCardName) == null) {
            return newCardName;
        } else {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "A credit card with the name " + newCardName + " already exists.");
            throw new DuplicateNameException();
        }
    }

    // EFFECTS: prompts user for and returns last 4 digits of new credit card if it is between [0, 9999], or
    //          -1 otherwise
    private int validateLast4Digits(String newCardLast4DigitsString) throws OutOfRangeException {
        try {
            int newCardLast4Digits = Integer.parseInt(newCardLast4DigitsString);
            if ((0 <= newCardLast4Digits) && (newCardLast4Digits <= 9999)) {
                return newCardLast4Digits;
            } else {
                JOptionPane.showInternalMessageDialog(managerPanel,
                        "The last 4 digits you entered are out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "The last 4 digits you entered are invalid.");
            throw new NumberFormatException();
        }
    }

    // EFFECTS: prompts user for and returns promotion end year of new credit card if it is between [2022, 2122], or
    //          -1 otherwise
    private int validatePromotionEndYear(String newCardPromotionEndYearString) throws OutOfRangeException {
        try {
            int newCardPromotionEndYear = Integer.parseInt(newCardPromotionEndYearString);
            if ((2022 <= newCardPromotionEndYear) && (newCardPromotionEndYear <= 2122)) {
                return newCardPromotionEndYear;
            } else {
                JOptionPane.showInternalMessageDialog(managerPanel,
                        "The promotion end year you entered is out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "The promotion end year you entered is invalid.");
            throw new NumberFormatException();
        }
    }

    // EFFECTS: prompts user for and returns promotion end month of new credit card if it is between [1, 12], or
    //          -1 otherwise
    private int validatePromotionEndMonth(String newCardPromotionEndMonthString) throws OutOfRangeException {
        try {
            int newCardPromotionEndMonth = Integer.parseInt(newCardPromotionEndMonthString);
            if ((1 <= newCardPromotionEndMonth) && (newCardPromotionEndMonth <= 12)) {
                return newCardPromotionEndMonth;
            } else {
                JOptionPane.showInternalMessageDialog(managerPanel,
                        "The promotion end month you entered is out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "The promotion end month you entered is invalid.");
            throw new NumberFormatException();
        }
    }

    // EFFECTS: prompts user for and returns promotion end date of new credit card if it forms a valid date with
    //          newCardPromotionEndYear and newCardPromotionEndMonth, or -1 otherwise
    private int validatePromotionEndDate(int newCardPromotionEndYear,
                                         int newCardPromotionEndMonth,
                                         String newCardPromotionEndDateString) throws OutOfRangeException {
        try {
            int newCardPromotionEndDate = Integer.parseInt(newCardPromotionEndDateString);
            if (monthsWith31Days.contains(newCardPromotionEndMonth)) {
                if ((1 <= newCardPromotionEndDate) && (newCardPromotionEndDate <= 31)) {
                    return newCardPromotionEndDate;
                } else {
                    JOptionPane.showInternalMessageDialog(managerPanel,
                            "The promotion end date you entered is out of range.");
                    throw new OutOfRangeException();
                }
            } else if (monthsWith30Days.contains(newCardPromotionEndMonth)) {
                if ((1 <= newCardPromotionEndDate) && (newCardPromotionEndDate <= 30)) {
                    return newCardPromotionEndDate;
                } else {
                    JOptionPane.showInternalMessageDialog(managerPanel,
                            "The promotion end date you entered is out of range.");
                    throw new OutOfRangeException();
                }
            } else if (isValidFebruaryDate(newCardPromotionEndYear, newCardPromotionEndDate)) {
                return newCardPromotionEndDate;
            } else {
                JOptionPane.showInternalMessageDialog(managerPanel,
                        "The promotion end date you entered is out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "The promotion end date you entered is invalid.");
            throw new NumberFormatException();
        }
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

    // EFFECTS: displays active status of selectedCard in all caps
    private String displayActiveStatus(CreditCard selectedCard) {
        if (selectedCard.getActiveStatus()) {
            return "<< ACTIVE >>";
        } else {
            return "<< INACTIVE >>";
        }
    }

    // MODIFIES: this
    // EFFECTS: switches selectedCard to opposite active status
    private void switchCardActiveStatus(CreditCard selectedCard) {
        if (selectedCard.getActiveStatus()) {
            selectedCard.inactivate();
        } else {
            selectedCard.reactivate();
        }
    }

    // EFFECTS: saves wallet to file at JSON_STORE
    // Code based on Workroom application
    private void saveWallet() {
        try {
            jsonWriter.open();
            jsonWriter.write(wallet);
            jsonWriter.close();
//            System.out.println("Your wallet has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
//            System.out.println("Failed to write to file at " + JSON_STORE + ". Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads wallet from file at JSON_STORE
    // Code based on Workroom application
    private void loadWallet() {
        try {
            wallet = jsonReader.read();
//            System.out.println("Your wallet has been loaded from " + JSON_STORE);
        } catch (IOException e) {
//            System.out.println("Failed to read from file at " + JSON_STORE + ". Please try again.");
        }
    }
}
