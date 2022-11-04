# Credit Card Manager

#### by Horton Lai

### What will the application do?

My Credit Card Manager streamlines the management of multiple credit cards. Whenever users sign up for a new credit 
card to exploit promotion benefits, the credit card may be added to my manager. The application facilitates checking
of details and end dates to assist users in:
- Evaluating *which credit cards maximize their financial gains* for a specific purchase, and
- Determining *when to cancel credit cards* when the benefits are no longer in effect.

### Who will use it?

Anyone who uses credit cards regularly will benefit from my elegant solution.

### Why is this project of interest to you?

Personal finance management is crucial in life. While users are enticed with numerous credit card sign-up bonuses and 
special offers, comparing promotion details and monitoring end dates are often difficult. With my credit card manager,
users will be reaping these financial benefits without a hitch.

### User Stories

Phase 0
- As a user, I want to be able to add a credit card to my wallet
- As a user, I want to be able to view the list of credit cards in my wallet
- As a user, I want to be able to select a credit card and check its promotion details and end date
- As a user, I want to be able to mark a credit card in my wallet as inactive

Phase 2
- As a user, I want to be able to save my list of credit cards to file
- As a user, I want to be able to load my list of credit cards from file

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by...
  - Click the button labelled "Add Credit Card"
  - Enter the requested info in the adder panel (display error messages if invalid)
    - A unique non-empty name in the field under "Name of new credit card:"
    - 4 digits in the field under "Last 4 digits:"
    - A year between 2022 and 2012 in the field under "Promotion End Year:"
    - A month between 1 and 12 in the field under "Promotion End Month:"
    - A valid date for the given year and month in the field under "Promotion End Date:"
    - Any string in the field under "Promotion details:"
  - Click the button labelled "Submit New Card Info"
- You can generate the second required event related to adding Xs to a Y by...
  - (Need some active and some inactive credit cards to see effect, add some by following 1st required event section,
    inactivate some by following visual component section)
  - Click on the button labelled "Filter For Inactive Credit Cards"
  - Click on the button labelled "Filter For Inactive Credit Cards" again
- You can locate my visual component by...
  - Click on an active credit card in the scroll pane under "Credit Card List"
  - Click on the button labelled "Switch Active Status"
- You can save the state of my application by...
  - Click on the button labelled "Save Wallet"
  - Click on the button labelled "OK" in the dialog
- You can reload the state of my application by...
  - Click on the button labelled "Load Wallet"
  - Click on the button labelled "OK" in the dialog