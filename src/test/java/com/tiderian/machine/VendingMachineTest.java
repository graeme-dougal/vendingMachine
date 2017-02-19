package com.tiderian.machine;

import com.tiderian.machine.exception.UnknownCoinException;
import com.tiderian.machine.exception.VendingMachineOffException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

        vendingMachine.setOn();

        vendingMachine.insertCoin(Coin.TEN_PENCE);
        assertEquals("Balance should be 10", 10, vendingMachine.getBalance());

        vendingMachine.insertCoin(Coin.TWENTY_PENCE);
        assertEquals("Balance should be 30", 30, vendingMachine.getBalance());

        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        assertEquals("Balance should be 80", 80, vendingMachine.getBalance());

        vendingMachine.insertCoin(Coin.ONE_POUND);
        assertEquals("Balance should be 180", 180, vendingMachine.getBalance());
    }

    @Test(expected = VendingMachineOffException.class)
    public void insertCoinsWhenMachineIsOffThrowsException() throws Exception {

        vendingMachine.insertCoin(Coin.TEN_PENCE);
        fail("Expected VendingMachineOffException");
    }

    @Test(expected = UnknownCoinException.class)
    public void insertUnknownCoinThrowsException() throws Exception {

        vendingMachine.setOn();
        vendingMachine.insertCoin(Coin.UNKNOWN);
        fail("Expected UnknownCoinException");
    }

    @Test(expected = VendingMachineOffException.class)
    public void returnCoinsWHenMachineOffThrowsExcption() throws Exception {

        vendingMachine.insertCoin(Coin.TEN_PENCE);
        fail("Expected VendingMachineOffException");
    }

    @Test
    public void returnInsertedCoins() throws Exception {

        vendingMachine.setOn();

        vendingMachine.insertCoin(Coin.TEN_PENCE);
        vendingMachine.insertCoin(Coin.TEN_PENCE);
        vendingMachine.insertCoin(Coin.TEN_PENCE);

        List<Coin> returnedCoins = vendingMachine.returnCoins();
        assertNotNull(returnedCoins);
        assertEquals("Expected 3 coins", 3, returnedCoins.size());

        int totalReturnedCoins = returnedCoins.stream().mapToInt(Coin::getValue).sum();
        assertEquals("Returned coins should sum 30", 30, totalReturnedCoins);

        // current machine balance should now be zero
        assertEquals("Balance should be zero", 0, vendingMachine.getBalance());
    }
}
