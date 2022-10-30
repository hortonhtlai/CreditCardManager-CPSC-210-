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

// Represents a credit card manager application
// Code partially based on Teller application and Workroom application presented in CPSC 210 as indicated for
// specific methods
public class CreditCardManagerAppGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/wallet.json";
    private static final String INACTIVATE_IMAGE = "./data/inactivateCreditCard.jpg";

    private Wallet wallet;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JInternalFrame managerPanel;
    private DefaultListModel creditCardListModel;
    private JList creditCardJList;
    private JToggleButton filterForInactiveCards;

    private CardAdder creditCardAdderPanel;

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

    private class SwitchActiveStatusAction extends AbstractAction {
        SwitchActiveStatusAction() {
            super("Switch Active Status");
        }

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
                creditCardAdderPanel.clearFields();
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
        creditCardAdderPanel = new CardAdder(this);
        creditCardAdderPanel.setVisible(false);
        managerPanel.add(creditCardAdderPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: initializes wallet
    // Code based on Teller application and Workroom application
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

    Wallet getWallet() {
        return wallet;
    }
}
