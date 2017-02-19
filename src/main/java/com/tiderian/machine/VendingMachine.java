package com.tiderian.machine;

import com.tiderian.machine.exception.VendingMachineException;
import com.tiderian.machine.exception.VendingMachineOffExceptoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by graeme.dougal on 19/02/2017.
 *
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {

    // state ON / OFF (true/false)
    private boolean on;

    // Manage inserted coins
    private List<Coin> insertedCoins;

    /**
     * Default Constructor
     */
    public VendingMachine() {
        super();
        insertedCoins = new ArrayList<Coin>();
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
     * insertCoin
     *
     * @param coin
     */
    public void insertCoin(Coin coin) throws VendingMachineException {

        if (isOn()) {
            insertedCoins.add(coin);
        }

        throw new VendingMachineOffExceptoin("Vending Machine not switched on");
    }
}
