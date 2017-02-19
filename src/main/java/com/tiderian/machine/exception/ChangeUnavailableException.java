package com.tiderian.machine.exception;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public class ChangeUnavailableException extends VendingMachineException {

    /**
     * Constructor
     *
     * @param message
     */
    public ChangeUnavailableException(String message) {
        super(message);
    }
}
