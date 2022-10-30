package ui;

import model.CreditCard;
import model.exception.DuplicateNameException;
import model.exception.EmptyNameException;
import model.exception.OutOfRangeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class CardAdder extends JPanel {
    public static final java.util.List<Integer> monthsWith31Days = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    public static final List<Integer> monthsWith30Days = Arrays.asList(4, 6, 9, 11);

    private CreditCardManagerAppGUI managerPanel;
    private JTextField nameField;
    private JTextField last4DigitsField;
    private JTextField promotionEndYearField;
    private JTextField promotionEndMonthField;
    private JTextField promotionEndDateField;
    private JTextArea promotionDetailsArea;

    public CardAdder(CreditCardManagerAppGUI managerPanel) {
        this.managerPanel = managerPanel;
        JPanel trivialInfoPanel = trivialInfoPanel();
        setLayout(new BorderLayout());
        add(trivialInfoPanel, BorderLayout.NORTH);
        add(promotionDetailsArea, BorderLayout.CENTER);
        add(new JButton(new SubmitNewCardInfoAction()), BorderLayout.SOUTH);
    }

    private class SubmitNewCardInfoAction extends AbstractAction {
        SubmitNewCardInfoAction() {
            super("Submit New Card Info");
        }

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

    private JPanel trivialInfoPanel() {
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
        return trivialInfoPanel;
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

        managerPanel.getWallet().addCreditCard(new CreditCard(newCardName,
                newCardLast4Digits,
                newCardPromotionEndYear,
                newCardPromotionEndMonth,
                newCardPromotionEndDate,
                newCardPromotionDetails,
                true));

        managerPanel.creditCardListToListModel();
    }

    // EFFECTS: prompts user for and returns name of new credit card if it is non-empty and unique, or null if it
    //          is empty or another card with user-entered name already exists in wallet
    private String validateName(String newCardName) throws EmptyNameException, DuplicateNameException {
        if (newCardName.equals("")) {
            JOptionPane.showInternalMessageDialog(this,
                    "Your new credit card cannot have an empty name.");
            throw new EmptyNameException();
        } else if (managerPanel.getWallet().selectCreditCard(newCardName) == null) {
            return newCardName;
        } else {
            JOptionPane.showInternalMessageDialog(this,
                    "A credit card with the name " + newCardName + " already exists.");
            throw new DuplicateNameException();
        }
    }

    // EFFECTS: prompts user for and returns last 4 digits of new credit card if it is between [0, 9999], or
    //          -1 otherwise
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

    // EFFECTS: prompts user for and returns promotion end year of new credit card if it is between [2022, 2122], or
    //          -1 otherwise
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

    // EFFECTS: prompts user for and returns promotion end month of new credit card if it is between [1, 12], or
    //          -1 otherwise
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

    // EFFECTS: prompts user for and returns promotion end date of new credit card if it forms a valid date with
    //          newCardPromotionEndYear and newCardPromotionEndMonth, or -1 otherwise
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

    public void clearFields() {
        nameField.setText("");
        last4DigitsField.setText("");
        promotionEndYearField.setText("");
        promotionEndMonthField.setText("");
        promotionEndDateField.setText("");
        promotionDetailsArea.setText("");
    }
}
