package io.github.fragmentstack.listeners;

import android.support.v4.app.FragmentTransaction;

import io.github.fragmentstack.PageManager;

/**
 * Transition interceptor interface.
 */
public interface FragmentPagerTransitionInterceptor {

    /**
     * Called when fragment transaction is setting up.
     *
     * @param pageManager Active PageManager
     * @param fragmentTransaction Active fragmentTransaction
     */
    void onTransaction(PageManager pageManager, FragmentTransaction fragmentTransaction);
}
