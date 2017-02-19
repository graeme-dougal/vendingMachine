package com.tiderian.vending.machine.exception;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public class NoCoinsInsertedException extends VendingMachineException {

    /**
     * Constructor
     * @param message
     */
    public NoCoinsInsertedException(String message) {
        super(message);
    }
}
