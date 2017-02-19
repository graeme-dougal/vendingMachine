package com.tiderian.vending.machine.util;

import com.tiderian.vending.machine.exception.ChangeUnavailableException;
import com.tiderian.vending.machine.model.Coin;
import com.tiderian.vending.machine.util.ChangeHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public class ChangeHandlerTest {

    private ChangeHandler changeHandler;

    @Before
    public void before() {
        changeHandler = new ChangeHandler();
    }

    /**
     *
     * changeIsUnavailableWhenChangeRequiredIsFortyAndThereIsOneFiftyAvailable
     *
     * Insert           : FIFTY
     * Change Required  : 40
     * Change Available : NO
     *
     * @throws Exception
     */
    @Test(expected = ChangeUnavailableException.class)
    public void changeIsUnavailableWhenChangeRequiredIsFortyAndThereIsOneFiftyAvailable() throws Exception{

        Map<Coin, Integer> availableCoins = new HashMap<>();
        availableCoins.put(Coin.FIFTY_PENCE, 1);

        changeHandler.getChange(availableCoins, 40);
        fail("Expected ChangeUnavailableException");
    }

    /**
     * changeIsFourtyWhenThereAreTwoTwentyCoinsAvailable
     *
     * Insert           : TWENTY + TWENTY
     * Change Required  : 40
     * Change Available : YES
     *
     * @throws Exception
     */
    @Test
    public void changeIsFourtyWhenThereAreTwoTwentyCoinsAvailable() throws Exception {

        Map<Coin, Integer> availableCoins = new HashMap<>();
        availableCoins.put(Coin.TWENTY_PENCE, 2);

        List<Coin> coins = changeHandler.getChange(availableCoins, 40);
        assertNotNull("Expected Coins to be returned", coins);
        assertEquals("Expected 2 coins", 2, coins.size());

        assertEquals("Expected Twenty Pence", Coin.TWENTY_PENCE, coins.get(0));
        assertEquals("Expected Twenty Pence", Coin.TWENTY_PENCE, coins.get(1));
    }


    /**
     * changeIsSeventyWhen
     *
     *
     * Insert           : TEN + TEN + TWENTY + FIFTY + FIFTY + ONE_HUNDRED (240)
     * Change Required  : 70 (FIFTY + TWENTY)
     * Change Available : YES
     */
    @Test
    public void changeIsSeventyWhenThereIsOneFiftyAndOneTwentyAvailable() throws Exception {

        Map<Coin, Integer> availableCoins = new HashMap<>();
        availableCoins.put(Coin.TEN_PENCE, 2);
        availableCoins.put(Coin.TWENTY_PENCE, 1);
        availableCoins.put(Coin.FIFTY_PENCE, 2);
        availableCoins.put(Coin.ONE_POUND, 1);

        List<Coin> coins = changeHandler.getChange(availableCoins, 70);
        assertNotNull("Expected Coins to be returned", coins);
        assertEquals("Expected 3 coins", 2, coins.size());

        assertEquals("Expected Twenty Pence", Coin.FIFTY_PENCE, coins.get(0));
        assertEquals("Expected Twenty Pence", Coin.TWENTY_PENCE, coins.get(1));
    }
}