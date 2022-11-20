package ui;

import model.CreditCard;
import model.Wallet;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static javax.swing.SwingConstants.*;

// Represents a credit card manager application with graphical user interface
// Code partially based on Teller application, Workroom application, and Alarm System application presented in
// CPSC 210 as indicated for specific methods
// Teller: https://github.students.cs.ubc.ca/CPSC210/TellerApp
// Workroom: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Alarm System: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class CreditCardManagerAppGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/wallet.json";
    private static final String INACTIVATE_IMAGE = "./data/inactivateCreditCard.jpg";

    private Wallet wallet;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JInternalFrame managerPanel;
    private DefaultListModel<String> creditCardListModel;
    private JList<String> creditCardJList;
    private JToggleButton filterForInactiveCards;
    private CardAdder creditCardAdderPanel;

    // EFFECTS: runs the credit card manager application and initializes the graphical user interface
    // Code based on Teller application and Alarm System application
    public CreditCardManagerAppGUI() {
        init();

        JDesktopPane desktop = new JDesktopPane();
        setContentPane(desktop);
        setTitle("Credit Card Manager Application");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new ConsolePrinter());
        setVisible(true);

        managerPanel = new JInternalFrame("Manager",
                true,
                false,
                true,
                false);
        managerPanel.setLayout(new BorderLayout());
        managerPanel.pack();
        managerPanel.setVisible(true);
        managerPanel.reshape(0, 0, 700, 500);

        addCreditCardListPanel();
        addButtonPanel();
        addCreditCardAdderPanel();

        desktop.add(managerPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds credit card list panel with switch active status button to manager panel of this
    private void addCreditCardListPanel() {
        creditCardListModel = new DefaultListModel<>();
        creditCardListToListModel();
        creditCardJList = new JList<>(creditCardListModel);
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

    // MODIFIES: this
    // EFFECTS: updates creditCardListModel of this to include active credit cards if filterForInactiveCards is not
    //          selected or exclude active credit cards if it is selected, and display filtered credit cards in a
    //          numbered list
    void creditCardListToListModel() {
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

    // Represents the action event when the user clicks on the button labelled "Switch Active Status"
    // Code based on Alarm System application
    private class SwitchActiveStatusAction extends AbstractAction {
        // EFFECTS: creates a new SwitchActiveStatusAction object
        SwitchActiveStatusAction() {
            super("Switch Active Status");
        }

        // MODIFIES: this
        // EFFECTS: switches active status of selected credit card in wallet of this, and displays inactivation image
        //          if the switch represents card inactivation
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedCardIndex = creditCardJList.getSelectedIndex();
            if (selectedCardIndex != -1) {
                CreditCard selectedCard = wallet.getCreditCardList().get(selectedCardIndex);
                switchCardActiveStatus(selectedCard);
                creditCardListToListModel();
                if (!(selectedCard.getActiveStatus())) {
                    JOptionPane.showInternalMessageDialog(managerPanel, null, "Inactivating Credit Card",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(INACTIVATE_IMAGE, "image"));
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds button panel to manager panel of this
    // Code based on Alarm System application
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

    // Represents the action event when the user clicks on the button labelled "Add Credit Card"
    // Code based on Alarm System application
    private class AddCreditCardAction extends AbstractAction {
        // EFFECTS: creates a new AddCreditCardAction object
        AddCreditCardAction() {
            super("Add Credit Card");
        }

        // MODIFIES: this
        // EFFECTS: expands creditCardAdderPanel of this if collapsed, or collapses and clears it if expanded
        @Override
        public void actionPerformed(ActionEvent e) {
            if (creditCardAdderPanel.isVisible()) {
                creditCardAdderPanel.setVisible(false);
                creditCardAdderPanel.clearFields();
            } else {
                creditCardAdderPanel.setVisible(true);
            }
        }
    }

    // Represents the action event when the user clicks on the button labelled "Filter for Inactive Credit Cards"
    // Code based on Alarm System application
    private class FilterInactiveCardsAction extends AbstractAction {
        // EFFECTS: creates a new FilterInactiveCardsAction object
        FilterInactiveCardsAction() {
            super("Filter for Inactive Credit Cards");
        }

        // MODIFIES: this
        // EFFECTS: updates creditCardListModel of this and credit card list display to include active credit cards
        //          if filterForInactiveCards is not selected or exclude active credit cards if it is selected
        @Override
        public void actionPerformed(ActionEvent e) {
            creditCardListToListModel();
        }
    }

    // Represents the action event when the user clicks on the button labelled "Save Wallet"
    // Code based on Alarm System application
    private class SaveWalletAction extends AbstractAction {
        // EFFECTS: creates a new SaveWalletAction object
        SaveWalletAction() {
            super("Save Wallet");
        }

        // EFFECTS: saves wallet from this to file at JSON_STORE
        @Override
        public void actionPerformed(ActionEvent e) {
            saveWallet();
        }
    }

    // Represents the action event when the user clicks on the button labelled "Load Wallet"
    // Code based on Alarm System application
    private class LoadWalletAction extends AbstractAction {
        // EFFECTS: creates a new LoadWalletAction object
        LoadWalletAction() {
            super("Load Wallet");
        }

        // MODIFIES: this
        // EFFECTS: loads wallet from file at JSON_STORE to wallet of this and updates credit card list display
        @Override
        public void actionPerformed(ActionEvent e) {
            loadWallet();
            creditCardListToListModel();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds credit card adder panel to manager panel of this
    private void addCreditCardAdderPanel() {
        creditCardAdderPanel = new CardAdder(this);
        creditCardAdderPanel.setVisible(false);
        managerPanel.add(creditCardAdderPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: initializes wallet
    // Code based on Workroom application
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        wallet = new Wallet();
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
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "Your wallet has been saved.");
        } catch (FileNotFoundException e) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "Failed to save wallet. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads wallet from file at JSON_STORE
    // Code based on Workroom application
    private void loadWallet() {
        try {
            wallet = jsonReader.read();
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "Your wallet has been loaded.");
        } catch (IOException e) {
            JOptionPane.showInternalMessageDialog(managerPanel,
                    "Failed to load wallet. Please try again.");
        }
    }

    // EFFECTS: returns wallet in this credit card manager app
    Wallet getWallet() {
        return wallet;
    }
}
