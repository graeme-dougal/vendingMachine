package com.tiderian.vending.machine.util;

import com.tiderian.vending.machine.exception.ChangeUnavailableException;
import com.tiderian.vending.machine.exception.VendingMachineException;
import com.tiderian.vending.machine.model.Coin;

import java.util.*;

/**
 * Created by graeme.dougal on 19/02/2017.
 *
 * Calculate if change can be give from the available coins and the change required.  Placed in separate class as
 * testable unit in its own right and has sufficient amount of code to be abstracted.
 */
public class ChangeHandler {

    /**
     * getChange
     *
     * Based on the change required and the number of coins available - determine if change can be given and return it.
     *
     * @param availableCoins - the coins available
     * @param changeRequired - the amount of change required
     * @return List<Coin> - List of coins in change
     * @throws VendingMachineException - if it nos possible to return changed based on coins available or amount required
     */
    public List<Coin> getChange(Map<Coin, Integer> availableCoins, int changeRequired) throws VendingMachineException {

        // Create a copy of the available coins in the machine
        Map<Coin, Integer> changePot = new HashMap<>();
        changePot.putAll(availableCoins);

        // initialise change to be returned
        List<Coin> changeCoins = new ArrayList<>();

        // Create array of all available coins (largest first as most efficient way to calculate change)
        Coin[] allCoins = Coin.values();
        Arrays.sort(allCoins, Collections.reverseOrder());

        while (changeRequired > 0) {
            for (Coin coin : allCoins) {

                // check if coin less than or equal to change needed and that one of these coins is available
                if (coin.getValue() <= changeRequired && changePot.containsKey(coin) && changePot.get(coin) > 0) {

                    /**
                     * Coin can be used in change
                     * (1) Decrement coin from available change pot
                     * (2) Add coin to change
                     * (3) Adjust amount of change now required
                     * (4) Check again
                     */
                    changePot.put(coin, changePot.get(coin) - 1);
                    changeCoins.add(coin);
                    changeRequired -= coin.getValue();
                    break;

                } else if (coin.equals(Coin.TEN_PENCE)) {
                    /**
                     * Down to last permutation of coin, either..
                     * (1) Coin is larger than change needed
                     * (2) Do not have any of these coins left
                     *
                     * Therefore - change cannot be given!!
                     */
                    throw new ChangeUnavailableException("Unable to dispense change");
                }
            }
        }

        // refresh available coins in the machine to reflect the change given
        availableCoins.clear();
        availableCoins.putAll(changePot);

        return changeCoins;
    }
}
