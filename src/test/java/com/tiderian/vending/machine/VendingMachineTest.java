package com.tiderian.vending.machine;

import com.tiderian.vending.machine.exception.*;
import com.tiderian.vending.machine.model.Coin;
import com.tiderian.vending.machine.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.tiderian.vending.machine.VendingMachine.INITIAL_STOCK;
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

    @Test(expected = NoCoinsInsertedException.class)
    public void returnInsertedCoinsWhenNoCoindsInsertedThrowsException() throws Exception {

        vendingMachine.setOn();

        vendingMachine.returnCoins();
        fail("Expected NoCoinsInsertedException");
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

    @Test
    public void defaultNumberOfItemsAvailable() throws Exception {

        vendingMachine.setOn();

        assertEquals("Expected " + INITIAL_STOCK, INITIAL_STOCK, vendingMachine.getNumberAvailable(Item.A));
        assertEquals("Expected " + INITIAL_STOCK, INITIAL_STOCK, vendingMachine.getNumberAvailable(Item.B));
        assertEquals("Expected " + INITIAL_STOCK, INITIAL_STOCK, vendingMachine.getNumberAvailable(Item.C));
    }

    @Test
    public void vendAvailableItemAWithExactMoney() throws Exception {

        vendingMachine.setOn();
        vendingMachine.insertCoin(Coin.TEN_PENCE);
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);

        // inital item available = 10
        int numberAvailable = vendingMachine.getNumberAvailable(Item.A);

        Item item = vendingMachine.vendItem(Item.A);

        assertNotNull(item);
        assertEquals("Expected Item A ", Item.A, item);
        assertEquals("Expected one less item", numberAvailable-1, vendingMachine.getNumberAvailable(Item.A));

        // balance should now be zero
        assertEquals("Expected Zero Balance", 0, vendingMachine.getBalance());
    }

    @Test(expected = InsertMoreCoinsException.class)
    public void vendAvailableItemWithInsufficientFunds() throws Exception {

        vendingMachine.setOn();
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);

        vendingMachine.vendItem(Item.A);
        fail("Expected InsertMoreCoinsException");
    }

    @Test(expected = ItemUnavailableException.class)
    public void vendUnavailableItemThrowsItemUnavailableException() throws Exception {

        vendingMachine.setOn();

        /**
         * Default number of initial items is 10
         *
         * Vend all 10 so there are no more available and then attempt to vend another one
         */
        for (int i=0; i<10; i++) {
            vendingMachine.insertCoin(Coin.TEN_PENCE);
            vendingMachine.insertCoin(Coin.FIFTY_PENCE);
            vendingMachine.vendItem(Item.A);
        }

        vendingMachine.insertCoin(Coin.TEN_PENCE);
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        vendingMachine.vendItem(Item.A);

        fail("Expected ItemUnavailableException");
    }

    @Test(expected = ChangeUnavailableException.class)
    public void vendAvailableItemWhenNoChangeAvailableThrowsException() throws Exception {

        vendingMachine.setOn();
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);

        vendingMachine.vendItem(Item.A);
        fail("Expected ChangeUnavailableException");

    }

    @Test
    public void vendAvailableItemWithCorrectChangeAvailable() throws Exception {

        vendingMachine.setOn();

        vendingMachine.insertCoin(Coin.TWENTY_PENCE);
        vendingMachine.insertCoin(Coin.TWENTY_PENCE);
        vendingMachine.insertCoin(Coin.TWENTY_PENCE);

        // vend item A = 60, should now be 3 x 20p available in machine
        vendingMachine.vendItem(Item.A);

        // insert 2 x 50p (£1)
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);

        // Vend Item A again, there is already 3 x 20p available inside machine so there should be 40p change available
        Item item = vendingMachine.vendItem(Item.A);
        assertNotNull("Expected item", item);

        // Vending machine should have balance of 40 available
        assertEquals("Expected remaining balance of 40", 40, vendingMachine.getBalance());

        // return coins - should return available change (40)
        List<Coin> coins = vendingMachine.returnCoins();
        assertNotNull("Expected Coins to be returned", coins);
        assertEquals("Expected 2 coins",2, coins.size());
        assertEquals("Expected 20p,", Coin.TWENTY_PENCE, coins.get(0));
        assertEquals("Expected 20p,", Coin.TWENTY_PENCE, coins.get(1));

        // Machine balance should now be ero
        assertEquals("Balance shoulc be zero", 0, vendingMachine.getBalance());
    }
}
