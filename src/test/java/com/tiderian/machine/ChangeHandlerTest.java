package com.tiderian.machine;

import com.tiderian.machine.exception.ChangeUnavailableException;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.opto.MachCallNode;

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

    @Test(expected = ChangeUnavailableException.class)
    public void changeIsUnavialableWhenChangeRequiredIsFourtyAndThereIsOneFiftyAvailable() throws Exception{

        Map<Coin, Integer> availableCoins = new HashMap<>();
        availableCoins.put(Coin.FIFTY_PENCE, 1);

        List<Coin> coins = changeHandler.getChange(availableCoins, 40);
        fail("Expected ChangeUnavailableException");
    }
}