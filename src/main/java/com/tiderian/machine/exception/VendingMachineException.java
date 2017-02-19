package com.tiderian.machine.exception;

/**
 * Created by graeme.dougal on 19/02/2017.
 *
 * Base Class for Vending Machine Exceptions
 */
public abstract class VendingMachineException extends Exception {

    public VendingMachineException(String message) {
        super(message);
    }
}
