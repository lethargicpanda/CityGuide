package com.thomasezan.lyft.providers;

import com.squareup.otto.Bus;

/**
 * Created by thomas on 2/14/15.
 */

public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
            return BUS;
    }
}