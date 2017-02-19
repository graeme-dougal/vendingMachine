package com.tiderian.machine;

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
}
