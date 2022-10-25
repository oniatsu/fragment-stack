package io.github.fragmentstack;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.github.fragmentstack.listeners.FragmentPagerTransitionInterceptor;
import io.github.fragmentstack.views.FragmentPagerLifeCycleFragment;

/**
 * Fragment page manager to add and back pages.
 */
public class PageManager {

    private final FragmentManager fragmentManager;
    private final int containerViewId;
    private Fragment defaultFragment;
    private FragmentPagerTransitionInterceptor localFragmentPagerTransitionInterceptor;

    PageManager(FragmentManager fragmentManager, @IdRes int containerViewId, Fragment defaultFragment) {
        this.fragmentManager = fragmentManager;
        this.containerViewId = containerViewId;
        this.defaultFragment = defaultFragment;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    /**
     * Call and start PageManager local config setup.
     *
     * @return Local config setup builder.
     */
    public PageManagerBuilder localConfig() {
        return new PageManagerBuilder(this);
    }

    public PageManager localConfig(PageManagerBuilder builder) {
        localFragmentPagerTransitionInterceptor = builder.localFragmentPagerTransitionInterceptor;
        defaultFragment = builder.defaultFragment;

        if (defaultFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .add(containerViewId, defaultFragment, defaultFragment.getClass().getName())
                    .commit();
        }
        return this;
    }

    /**
     * Check the PageManager has any pages.
     *
     * @return If true, there are some added pages.
     */
    public boolean hasPage() {
        return (fragmentManager.getBackStackEntryCount() != 0);
    }

    /**
     * Back the last page.
     *
     * @return If true, removed a page is successful.
     */
    public boolean back() {
        if (!hasPage()) {
            return false;
        }

        fragmentManager.popBackStackImmediate();

        FragmentPagerLifeCycleFragment fragmentPagerListener = getLastFragmentPagerListener();
        if (fragmentPagerListener != null) {
            fragmentPagerListener.onForeground();
        }
        return true;
    }

    /**
     * Back the some count pages.
     *
     * @param count Backed count.
     * @return If true, removed a page is successful.
     */
    public boolean back(int count) {
        if (!hasPage()) {
            return false;
        }

        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        int entryAt = backStackEntryCount - 1 - count;
        if (entryAt < 0) {
            entryAt = 0;
        }
        int id = fragmentManager.getBackStackEntryAt(entryAt).getId();
        fragmentManager.popBackStackImmediate(id, 0);

        FragmentPagerLifeCycleFragment fragmentPagerListener = getLastFragmentPagerListener();
        if (fragmentPagerListener != null) {
            fragmentPagerListener.onForeground();
        }
        return true;
    }

    /**
     * Back to the fragment page.
     *
     * @param fragment Backed page.
     * @param flags    Either 0 or FragmentManager#POP_BACK_STACK_INCLUSIVE.
     * @return If true, removed a page is successful.
     */
    public boolean back(Fragment fragment, int flags) {
        return back(fragment.getClass().getName(), flags);
    }

    /**
     * Back to the class page.
     *
     * @param cls   Backed class.
     * @param flags Either 0 or FragmentManager#POP_BACK_STACK_INCLUSIVE.
     * @return If true, removed a page is successful.
     */
    public boolean back(Class cls, int flags) {
        return back(cls.getName(), flags);
    }

    /**
     * Back to the tag name page.
     *
     * @param name  Backed tag name.
     * @param flags Either 0 or FragmentManager#POP_BACK_STACK_INCLUSIVE.
     * @return If true, removed a page is successful.
     */
    public boolean back(String name, int flags) {
        if (!hasPage()) {
            return false;
        }

        fragmentManager.popBackStackImmediate(name, flags);

        FragmentPagerLifeCycleFragment fragmentPagerListener = getLastFragmentPagerListener();
        if (fragmentPagerListener != null) {
            fragmentPagerListener.onForeground();
        }
        return true;
    }

    /**
     * Clear all back stacked pages.
     *
     * @return If true, removed a page is successful.
     */
    public boolean clear() {
        if (!hasPage()) {
            return false;
        }

        int id = fragmentManager.getBackStackEntryAt(0).getId();
        fragmentManager.popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentPagerLifeCycleFragment fragmentPagerListener = getLastFragmentPagerListener();
        if (fragmentPagerListener != null) {
            fragmentPagerListener.onForeground();
        }
        return true;
    }

    /**
     * Add a new fragment page.
     *
     * @param newFragment New fragment to be added.
     */
    public void add(Fragment newFragment) {
        add(newFragment, null);
    }

    /**
     * Add a new fragment page.
     *
     * @param newFragment                            New fragment to be added.
     * @param eachFragmentPagerTransitionInterceptor Each transition interceptor.
     */
    public void add(Fragment newFragment, FragmentPagerTransitionInterceptor eachFragmentPagerTransitionInterceptor) {
        FragmentPagerLifeCycleFragment lastFragmentPagerListener = getLastFragmentPagerListener();
        if (lastFragmentPagerListener != null) {
            lastFragmentPagerListener.onBackground();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .add(containerViewId, newFragment, newFragment.getClass().getName())
                .addToBackStack(newFragment.getClass().getName());

        FragmentPagerTransitionInterceptor globalFragmentPagerInterceptor = FragmentStack.getGlobalFragmentPagerTransitionInterceptor();
        if (globalFragmentPagerInterceptor != null) {
            globalFragmentPagerInterceptor.onTransaction(this, fragmentTransaction);
        }
        if (localFragmentPagerTransitionInterceptor != null) {
            localFragmentPagerTransitionInterceptor.onTransaction(this, fragmentTransaction);
        }
        if (eachFragmentPagerTransitionInterceptor != null) {
            eachFragmentPagerTransitionInterceptor.onTransaction(this, fragmentTransaction);
        }

        fragmentTransaction.commit();
    }

    /**
     * Get a last added fragment pager's life cycle listener.
     *
     * @return Life cycle listener.
     */
    public FragmentPagerLifeCycleFragment getLastFragmentPagerListener() {
        Fragment lastFragment = getLastFragment();
        if (lastFragment instanceof FragmentPagerLifeCycleFragment) {
            return (FragmentPagerLifeCycleFragment) lastFragment;
        }
        return null;
    }

    /**
     * Get a last added fragment.
     *
     * @return Last fragment.
     */
    public Fragment getLastFragment() {
        int index = fragmentManager.getBackStackEntryCount() - 1;
        if (index == -1) {
            return defaultFragment;
        }
        FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
        String tag = backEntry.getName();
        return fragmentManager.findFragmentByTag(tag);
    }
}
