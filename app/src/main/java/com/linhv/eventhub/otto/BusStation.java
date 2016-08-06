package com.linhv.eventhub.otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by ManhNV on 8/4/16.
 */
public class BusStation  {
    private static Bus bus = new Bus(ThreadEnforcer.ANY);

    public static Bus getBus() {
        return bus;
    }

}
