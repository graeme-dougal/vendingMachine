package com.tiderian.machine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void turnsOff() {
        assertFalse(vendingMachine.isOn());
        vendingMachine.setOn();
        assertTrue(vendingMachine.isOn());
        vendingMachine.setOff();
        assertFalse(vendingMachine.isOn());
    }

    @Test
    public void initialBalanceIsZero() {
        assertEquals("Initial Balance should be zero", 0, vendingMachine.getBalance());
    }

    @Test
    public void balanceIncreasesWhenCoinsInserted() throws Exception {

        vendingMachine.insertCoin(Coin.TEN_PENCE);
        assertEquals("Balance should be 10", 10, vendingMachine.getBalance());

        vendingMachine.insertCoin(Coin.TWENTY_PENCE);
        assertEquals("Balance should be 30", 30, vendingMachine.getBalance());

        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        assertEquals("Balance should be 80", 80, vendingMachine.getBalance());

        vendingMachine.insertCoin(Coin.ONE_POUND);
        assertEquals("Balance should be 180", 180, vendingMachine.getBalance());
    }
}
