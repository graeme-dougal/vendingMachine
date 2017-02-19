package com.tiderian.machine;

/**
 * Created by graeme.dougal on 19/02/2017.
 */
public enum Item {

    A(60),
    B(100),
    C(170);

    private final int cost;

    /**
     * Constructor
     *
     * @param cost
     */
    Item(final int cost) {
        this.cost = cost;
    }

    /**
     * getCost
     *
     * @return int
     */
    public int getCost() {
        return cost;
    }
}
