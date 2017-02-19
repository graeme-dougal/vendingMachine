package com.tiderian.machine;

/**
 * Created by graeme.dougal on 19/02/2017.
 *
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {

    // state ON / OFF (true/false)
    private boolean on;

    /**
     * Default Constructor
     */
    public VendingMachine() {
        super();
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

    public void setOff() {
        on = false;
    }
}
