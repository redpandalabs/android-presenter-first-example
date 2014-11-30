package com.latebound.pf;

import java.util.Collection;

/**
* Created by latebound on 11/30/14.
*/
public class Events {
    public static void fire(Collection<Runnable> listeners) {
        for (Runnable l: listeners) {
            l.run();
        }
    }
}
