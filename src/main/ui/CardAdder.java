package ui;

import model.CreditCard;
import ui.exception.DuplicateNameException;
import ui.exception.EmptyNameException;
import ui.exception.OutOfRangeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

// Represents the expandable credit card adder panel of the manager panel in a credit card manager application
// Code partially based on Alarm System application presented in CPSC 210 as indicated for specific methods
// Alarm System: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class CardAdder extends JPanel {
    public static final java.util.List<Integer> monthsWith31Days = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    public static final List<Integer> monthsWith30Days = Arrays.asList(4, 6, 9, 11);

    private CreditCardManagerAppGUI managerApp;
    private JTextField nameField;
    private JTextField last4DigitsField;
    private JTextField promotionEndYearField;
    private JTextField promotionEndMonthField;
    private JTextField promotionEndDateField;
    private JTextArea promotionDetailsArea;

    // EFFECTS: creates a new CardAdder panel object
    public CardAdder(CreditCardManagerAppGUI managerApp) {
        this.managerApp = managerApp;
        JPanel trivialInfoPanel = trivialInfoPanel();
        setLayout(new BorderLayout());
        add(trivialInfoPanel, BorderLayout.NORTH);
        promotionDetailsArea = new JTextArea();
        promotionDetailsArea.setColumns(20);
        promotionDetailsArea.setRows(3);
        promotionDetailsArea.setLineWrap(true);
        add(promotionDetailsArea, BorderLayout.CENTER);
        add(new JButton(new SubmitNewCardInfoAction()), BorderLayout.SOUTH);
    }

    // Represents the action event when the user clicks on the button labelled "Submit New Card Info"
    // Code based on Alarm System application
    private class SubmitNewCardInfoAction extends AbstractAction {
        // EFFECTS: creates a new SubmitNewCardInfoAction object
        SubmitNewCardInfoAction() {
            super("Submit New Card Info");
        }

        // MODIFIES: this
        // EFFECTS: adds new credit card with given info to wallet in manager app of this and closes and clears
        //          this, or terminates addition if exception is caught
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                addNewCardWithSubmittedInfo();
                setVisible(false);
                clearFields();
            } catch (Exception exception) {
                // terminate
            }
        }
    }

    // EFFECTS: returns a panel for entry of trivial info of new credit card except for promotion details
    private JPanel trivialInfoPanel() {
        JPanel trivialInfoPanel = new JPanel(new GridLayout(0, 1));

        trivialInfoPanel.add(new JLabel("Name of new credit card:"));
        nameField = new JTextField();
        trivialInfoPanel.add(nameField);

        trivialInfoPanel.add(new JLabel("Last 4 digits:"));
        last4DigitsField = new JTextField();
        trivialInfoPanel.add(last4DigitsField);

        trivialInfoPanel.add(new JLabel("Promotion end year:"));
        promotionEndYearField = new JTextField();
        trivialInfoPanel.add(promotionEndYearField);

        trivialInfoPanel.add(new JLabel("Promotion end month:"));
        promotionEndMonthField = new JTextField();
        trivialInfoPanel.add(promotionEndMonthField);

        trivialInfoPanel.add(new JLabel("Promotion end date:"));
        promotionEndDateField = new JTextField();
        trivialInfoPanel.add(promotionEndDateField);

        trivialInfoPanel.add(new JLabel("Promotion details:"));
        return trivialInfoPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds new credit card with given info to wallet in manager app of this and update credit card list
    //          display, or throws specific exceptions if new card info is invalid
    private void addNewCardWithSubmittedInfo() throws DuplicateNameException, EmptyNameException, OutOfRangeException {
        String newCardName = validateName(nameField.getText());
        int newCardLast4Digits = validateLast4Digits(last4DigitsField.getText());
        int newCardPromotionEndYear = validatePromotionEndYear(promotionEndYearField.getText());
        int newCardPromotionEndMonth = validatePromotionEndMonth(promotionEndMonthField.getText());
        int newCardPromotionEndDate = validatePromotionEndDate(newCardPromotionEndYear,
                newCardPromotionEndMonth,
                promotionEndDateField.getText());
        String newCardPromotionDetails = promotionDetailsArea.getText();

        managerApp.getWallet().addCreditCard(new CreditCard(newCardName,
                newCardLast4Digits,
                newCardPromotionEndYear,
                newCardPromotionEndMonth,
                newCardPromotionEndDate,
                newCardPromotionDetails,
                true));

        managerApp.creditCardListToListModel();
    }

    // EFFECTS: returns name of new credit card if it is non-empty and unique or throws EmptyNameException if it
    //          is empty or throws DuplicateNameException if another card with newCardName already exists in wallet
    private String validateName(String newCardName) throws EmptyNameException, DuplicateNameException {
        if (newCardName.equals("")) {
            JOptionPane.showInternalMessageDialog(this,
                    "Your new credit card cannot have an empty name.");
            throw new EmptyNameException();
        } else if (managerApp.getWallet().selectCreditCard(newCardName) == null) {
            return newCardName;
        } else {
            JOptionPane.showInternalMessageDialog(this,
                    "A credit card with the name " + newCardName + " already exists.");
            throw new DuplicateNameException();
        }
    }

    // EFFECTS: returns last 4 digits of new credit card if it is between [0, 9999] or throws OutOfRangeException if
    //          it is out of range or throws NumberFormatException if newCardLast4DigitsString does not represent
    //          an integer
    private int validateLast4Digits(String newCardLast4DigitsString) throws OutOfRangeException {
        try {
            int newCardLast4Digits = Integer.parseInt(newCardLast4DigitsString);
            if (0 <= newCardLast4Digits && newCardLast4Digits <= 9999) {
                return newCardLast4Digits;
            } else {
                JOptionPane.showInternalMessageDialog(this,
                        "The last 4 digits you entered are out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(this,
                    "The last 4 digits you entered are not an integer.");
            throw new NumberFormatException();
        }
    }

    // EFFECTS: returns promotion end year of new credit card if it is between [2022, 2122] or throws
    //          OutOfRangeException if it is out of range or throws NumberFormatException if
    //          newCardPromotionEndYearString does not represent an integer
    private int validatePromotionEndYear(String newCardPromotionEndYearString) throws OutOfRangeException {
        try {
            int newCardPromotionEndYear = Integer.parseInt(newCardPromotionEndYearString);
            if (2022 <= newCardPromotionEndYear && newCardPromotionEndYear <= 2122) {
                return newCardPromotionEndYear;
            } else {
                JOptionPane.showInternalMessageDialog(this,
                        "The promotion end year you entered is out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(this,
                    "The promotion end year you entered is not an integer.");
            throw new NumberFormatException();
        }
    }

    // EFFECTS: returns promotion end month of new credit card if it is between [1, 12] or throws OutOfRangeException
    //          if it is out of range or throws NumberFormatException if newCardPromotionEndMonthString does not
    //          represent an integer
    private int validatePromotionEndMonth(String newCardPromotionEndMonthString) throws OutOfRangeException {
        try {
            int newCardPromotionEndMonth = Integer.parseInt(newCardPromotionEndMonthString);
            if (1 <= newCardPromotionEndMonth && newCardPromotionEndMonth <= 12) {
                return newCardPromotionEndMonth;
            } else {
                JOptionPane.showInternalMessageDialog(this,
                        "The promotion end month you entered is out of range.");
                throw new OutOfRangeException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(this,
                    "The promotion end month you entered is not an integer.");
            throw new NumberFormatException();
        }
    }

    // REQUIRES: newCardPromotionEndYear is between [2022, 2012] AND newCardPromotionEndMonth is between [1, 12]
    // EFFECTS: returns promotion end date of new credit card if it forms a valid date with newCardPromotionEndYear
    //          and newCardPromotionEndMonth, or throws OutOfRangeException if it is out of range, or throws
    //          NumberFormatException if newCardPromotionEndDateString does not represent an integer
    private int validatePromotionEndDate(int newCardPromotionEndYear,
                                         int newCardPromotionEndMonth,
                                         String newCardPromotionEndDateString) throws OutOfRangeException {
        try {
            int newCardPromotionEndDate = Integer.parseInt(newCardPromotionEndDateString);
            if (monthsWith31Days.contains(newCardPromotionEndMonth) && 1 <= newCardPromotionEndDate
                    && newCardPromotionEndDate <= 31) {
                return newCardPromotionEndDate;
            } else if (monthsWith30Days.contains(newCardPromotionEndMonth) && 1 <= newCardPromotionEndDate
                    && newCardPromotionEndDate <= 30) {
                return newCardPromotionEndDate;
            } else if (isValidFebruaryDate(newCardPromotionEndYear, newCardPromotionEndDate)) {
                return newCardPromotionEndDate;
            }
            JOptionPane.showInternalMessageDialog(this,
                    "The promotion end date you entered is out of range.");
            throw new OutOfRangeException();
        } catch (NumberFormatException e) {
            JOptionPane.showInternalMessageDialog(this,
                    "The promotion end date you entered is not an integer.");
            throw new NumberFormatException();
        }
    }

    // EFFECTS: returns true if newCardPromotionEndDate is a valid date in February of newCardPromotionEndYear, or
    //          false otherwise
    private boolean isValidFebruaryDate(int newCardPromotionEndYear, int newCardPromotionEndDate) {
        if (newCardPromotionEndYear % 4 != 0
                || (newCardPromotionEndYear % 100 == 0 && newCardPromotionEndYear % 400 != 0)) {
            return 1 <= newCardPromotionEndDate && newCardPromotionEndDate <= 28;
        } else {
            return 1 <= newCardPromotionEndDate && newCardPromotionEndDate <= 29;
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the text in JTextFields and JTextAreas of this
    public void clearFields() {
        nameField.setText("");
        last4DigitsField.setText("");
        promotionEndYearField.setText("");
        promotionEndMonthField.setText("");
        promotionEndDateField.setText("");
        promotionDetailsArea.setText("");
    }
}
