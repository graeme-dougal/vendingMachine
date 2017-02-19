package com.tiderian.vending.machine;

import com.tiderian.vending.machine.exception.*;
import com.tiderian.vending.machine.model.Coin;
import com.tiderian.vending.machine.model.Item;
import com.tiderian.vending.machine.util.ChangeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by graeme.dougal on 19/02/2017.
 *
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {

    // Initial number of each item inside the vending machine
    protected static final int INITIAL_STOCK = 10;

    // state ON / OFF (true/false)
    private boolean on;

    // Manage currently inserted coins
    private List<Coin> insertedCoins;

    // Manage coins available in the machine (pre-initialised)
    private Map<Coin, Integer> availableCoins = new HashMap<>();

    // Manage the available items inside the vending machine
    private Map<Item, Integer> availableItems;

    private ChangeHandler changeHandler;

    /**
     * Default Constructor
     */
    public VendingMachine() {
        super();
        insertedCoins = new ArrayList<>();
        initialiseAvailableItems();
        changeHandler = new ChangeHandler();
    }

    /**
     * isOn
     *
     * @return boolean
     */
    public boolean isOn() {
        return on;
    }

    /**
     * setOn
     */
    public void setOn() {
        on = true;
    }

    /**
     * setOff
     */
    public void setOff() {
        on = false;
    }

    /**
     * getBalance
     *
     * @return int
     */
    public int getBalance() {
        return insertedCoins.stream().mapToInt(Coin::getValue).sum();
    }

    /**
     * returnCoins
     *
     * @return List<Coin>
     * @throws VendingMachineException
     */
    public List<Coin> returnCoins() throws VendingMachineException {

        checkMachineStatus();

        if (getBalance() == 0) {
            throw new NoCoinsInsertedException("No Coins Inserted");
        }

        List<Coin> returnCoins = new ArrayList<>();
        returnCoins.addAll(insertedCoins);

        // re-initialise the inserted coins
        insertedCoins.clear();

        return returnCoins;
    }

    /**
     * insertCoin
     *
     * @param coin
     * @throws  VendingMachineException - if an invalid coin is inserted
     */
    public void insertCoin(Coin coin) throws VendingMachineException {

        checkMachineStatus();
        validateCoin(coin);

        insertedCoins.add(coin);
    }

    /**
     * getNumberAvailable - get the number available for a specific item
     *
     * @param item
     * @return int - number available of supplied item
     */
    public int getNumberAvailable(Item item) throws VendingMachineException {

        checkMachineStatus();

        return availableItems.get(item);
    }

    /**
     * vendItem
     *
     * (1) Check That Machine is switched on
     * (2) Check that requested item is available
     * (3) Check that there is enough inserted coins
     * (4) Add inserted coins to machine store
     * (5) Check for available change / balance
     * (6) Decrement Item availability
     * (7) Return item
     *
     * @param item
     * @return Item
     * @throws VendingMachineException if unable to dispense item
     */
    public Item vendItem(Item item) throws VendingMachineException {

        checkMachineStatus();
        checkItemAvailable(item);
        checkSufficientFundsForItem(item);
        addInsertedCoins();

        // make change available for further vend or coin return if possible
        checkForChange(item);

        decrementStockItem(item);

        return item;
    }

    /**
     * checkMachineStatus
     *
     * @throws VendingMachineException
     */
    private void checkMachineStatus() throws VendingMachineException {

        if (!isOn()) {
            throw new VendingMachineOffException("Vending Machine is turned off");
        }
    }

    /**
     * validateCoin
     *
     * @param coin
     */
    private void validateCoin(Coin coin) throws UnknownCoinException {

        if (!coin.isValid()) {
            throw new UnknownCoinException("Unknown Coin detected");
        }
    }

    /**
     * initialiseAvailableItems - initialise the list of available items inside the vending machine
     */
    private void initialiseAvailableItems() {

        availableItems = new HashMap<>();

        for (Item item : Item.values()) {
            availableItems.put(item, INITIAL_STOCK);
        }
    }

    /**
     * checkSufficientFundsForItem
     *
     * @param item
     */
    private void checkSufficientFundsForItem(Item item) throws VendingMachineException {

        if (getBalance() < item.getCost()) {
            throw new InsertMoreCoinsException("Insert More Coins");
        }
    }

    /**
     * checkItemAvailable
     *
     * @param item
     * @throws VendingMachineException
     */
    private void checkItemAvailable(Item item) throws VendingMachineException {

        if (availableItems.get(item) == 0) {
            throw new ItemUnavailableException("Item Unavailable");
        }
    }

    /**
     * addInsertedCoins - add inserted coins to the total coins contained within the machine
     */
    private void addInsertedCoins() {

        for (Coin coin : insertedCoins) {
            availableCoins.put(coin, availableCoins.containsKey(coin) ? availableCoins.get(coin) +1 : 1);
        }
    }

    /**
     * checkForChange
     *
     * Check whether change is applicable and if so delegate to ChangeHandler to calculate change to be given based
     * on what change is available in the machine.
     *
     * @param item
     * @throws VendingMachineException - if unable to provide change
     */
    private void checkForChange(Item item) throws VendingMachineException {

        List<Coin> change = new ArrayList<>();

        if (getBalance() - item.getCost() > 0) {
            change = changeHandler.getChange(availableCoins, getBalance() - item.getCost());
        }

        insertedCoins = change;
    }

    /**
     * decrementStockItem
     *
     * @param item
     */
    private void decrementStockItem(Item item) {
        availableItems.put(item, availableItems.get(item) -1);
    }
}
