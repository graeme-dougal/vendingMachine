package com.tiderian.machine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public enum Coin {

    TEN_PENCE(10),
    TWENTY_PENCE(20),
    FIFTY_PENCE(50),
    ONE_POUND(100),
    UNKNOWN(0);

    private final int value;

    private static List<Coin> validCoins = new ArrayList<>();

    static {
        validCoins.add(TEN_PENCE);
        validCoins.add(TWENTY_PENCE);
        validCoins.add(FIFTY_PENCE);
        validCoins.add(ONE_POUND);
    }

    /**
     * Constructor
     *
     * @param value
     */
    Coin(final int value) {
        this.value = value;
    }

    /**
     * getValue
     *
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     * validCoin - checks whether the coin is a valid known coin
     *
     * @param coin
     * @return boolean
     */
    public boolean isValidCoin(Coin coin) {
        return validCoins.contains(coin);
    }
}
