package com.tiderian.machine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public class VendingMachineTest {

    private VendingMachine vendingMachine;

    /**
     * Initialise the vending machine before each test.
     */
    @Before
    public void before() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void defaultStateIsOff() {
        assertFalse(vendingMachine.isOn());
    }

    @Test
    public void turnsOn() {
        vendingMachine.setOn();
        assertTrue(vendingMachine.isOn());
    }
}
