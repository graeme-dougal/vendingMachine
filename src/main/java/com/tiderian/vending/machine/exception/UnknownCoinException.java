package com.tiderian.vending.machine.exception;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public class UnknownCoinException extends VendingMachineException {

    /**
     * Constructor
     *
     * @param message
     */
    public UnknownCoinException(String message) {
        super(message);
    }
}
