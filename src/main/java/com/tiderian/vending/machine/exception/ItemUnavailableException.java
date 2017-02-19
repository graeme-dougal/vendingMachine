package com.tiderian.vending.machine.exception;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public class ItemUnavailableException extends VendingMachineException {

    /**
     * Constructor
     * @param message
     */
    public ItemUnavailableException(String message) {
        super(message);
    }
}
