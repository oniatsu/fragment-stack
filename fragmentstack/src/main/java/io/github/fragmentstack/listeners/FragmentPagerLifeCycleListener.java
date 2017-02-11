package io.github.fragmentstack.listeners;

/**
 * Fragment page life cycle listener.
 */
public interface FragmentPagerLifeCycleListener {

    /**
     * Called when the fragment page become foreground to the user.
     * This is called after Fragment#onResume().
     */
    void onForeground();

    /**
     * Called when the Fragment page become background to the user.
     * This is called before Fragment#onPouse().
     */
    void onBackground();
}
