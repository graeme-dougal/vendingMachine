package com.tiderian.machine;

import com.tiderian.machine.exception.*;

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

    /**
     * Default Constructor
     */
    public VendingMachine() {
        super();
        insertedCoins = new ArrayList<Coin>();
        initialiseAvailableItems();
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
     * @return
     */
    public int getNumberAvailable(Item item) throws VendingMachineException {

        checkMachineStatus();

        return availableItems.get(item);
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
     * vendItem
     *
     * @param item
     * @return
     */
    public Item vendItem(Item item) throws VendingMachineException {

        if (getBalance() >= item.getCost()) {

            if (availableItems.get(item) > 0 ) {
                // decrement available stock
                availableItems.put(item, availableItems.get(item) -1);

                // store inserted coins
                for (Coin coin : insertedCoins) {
                    availableCoins.put(coin, availableCoins.containsKey(coin) ? availableCoins.get(coin) +1 : 1);
                }

                // TODO - Check for change

                // clear inserted coins
                insertedCoins.clear();
            } else {

                throw new ItemUnavailableException("Item Unavailable");
            }
        } else {

            throw new InsertMoreCoinsException("Insert More Coins");
        }

        return item;
    }
}
