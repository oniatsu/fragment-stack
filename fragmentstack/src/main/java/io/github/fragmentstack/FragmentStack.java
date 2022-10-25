package io.github.fragmentstack;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import io.github.fragmentstack.listeners.FragmentPagerTransitionInterceptor;

/**
 * Fragment utility used for fragment pages.
 */
public final class FragmentStack {

    private static Map<String, PageManager> fragmentPagerManagers = new HashMap<>();
    private static FragmentPagerTransitionInterceptor globalFragmentPagerTransitionInterceptor;

    public static FragmentPagerTransitionInterceptor getGlobalFragmentPagerTransitionInterceptor() {
        return globalFragmentPagerTransitionInterceptor;
    }

    private FragmentStack() {
    }

    /**
     * Call and start application global config setup.
     *
     * @return Global config setup builder.
     */
    public static FragmentStackBuilder globalConfig() {
        return new FragmentStackBuilder();
    }

    /**
     * Build setting up.
     *
     * @param builder Setup builder.
     */
    public static void build(FragmentStackBuilder builder) {
        FragmentStack.globalFragmentPagerTransitionInterceptor = builder.globalFragmentPagerTransitionInterceptor;
    }

    /**
     * Register the PageManager to use child fragment pages.
     *
     * @param activity        Registered activity.
     * @param containerViewId Container view ID to place new fragment.
     * @param defaultFragment A default fragment to show first. If null, the initialized container will be empty.
     * @return Registered PageManager.
     */
    public static PageManager register(AppCompatActivity activity, @IdRes int containerViewId, Fragment defaultFragment) {
        if (activity == null) {
            throw new NullPointerException("A activity is not set");
        }
        return register(activity.getClass().getName(), activity.getSupportFragmentManager(), containerViewId, defaultFragment);
    }

    /**
     * Register the PageManager to use child fragment pages.
     *
     * @param fragment        Registered fragment.
     * @param containerViewId Container view ID to place new fragment.
     * @param defaultFragment A default fragment to show first. If null, the initialized container will be empty.
     * @return Registered PageManager.
     */
    public static PageManager register(Fragment fragment, @IdRes int containerViewId, Fragment defaultFragment) {
        if (fragment == null) {
            throw new NullPointerException("A fragment is not set");
        }
        return register(fragment.getClass().getName(), fragment.getChildFragmentManager(), containerViewId, defaultFragment);
    }

    /**
     * Register the PageManager to use child fragment pages.
     *
     * @param managerTag      Registered manager tag name
     * @param fragmentManager Fragment manager to use pages
     * @param containerViewId Container view ID to place new fragment
     * @param defaultFragment A default fragment to show first. If null, the initialized container will be empty.
     * @return Registered PageManager.
     */
    public static PageManager register(String managerTag, FragmentManager fragmentManager, @IdRes int containerViewId, Fragment defaultFragment) {
        if (managerTag == null) {
            throw new NullPointerException("A manager tag is not set");
        }

        PageManager pageManager = new PageManager(fragmentManager, containerViewId, defaultFragment);
        fragmentPagerManagers.put(managerTag, pageManager);
        return pageManager;
    }

    /**
     * Get the PagerManger connected with the activity.
     *
     * @param activity Registered activity.
     * @return Connected PageManager.
     */
    public static PageManager of(AppCompatActivity activity) {
        if (activity == null) {
            throw new NullPointerException("A activity is not set");
        }
        return of(activity.getClass().getName());
    }

    /**
     * Get the PagerManger connected with the activity.
     *
     * @param fragment Registered fragment.
     * @return Connected PageManager.
     */
    public static PageManager of(Fragment fragment) {
        if (fragment == null) {
            throw new NullPointerException("A fragment is not set");
        }
        return of(fragment.getClass().getName());
    }

    /**
     * Get the PagerManger connected with the class.
     *
     * @param cls Registered class.
     * @return Connected PageManager.
     */
    public static PageManager of(Class cls) {
        return of(cls.getName());
    }

    /**
     * Get the PagerManger connected with the manager tag name.
     *
     * @param managerTag Registered manager tag name.
     * @return Connected PageManager.
     */
    public static PageManager of(String managerTag) {
        return fragmentPagerManagers.get(managerTag);
    }

    /**
     * Unregister the PageManager to use child fragment pages.
     *
     * @param activity Unregistered activity.
     */
    public static void unregister(AppCompatActivity activity) {
        if (activity == null) {
            throw new NullPointerException("A activity is not set");
        }
        unregister(activity.getClass().getName());
    }

    /**
     * Unregister the PageManager to use child fragment pages.
     *
     * @param fragment Unregistered fragment.
     */
    public static void unregister(Fragment fragment) {
        if (fragment == null) {
            throw new NullPointerException("A fragment is not set");
        }
        unregister(fragment.getClass().getName());
    }

    /**
     * Unregister the PageManager to use child fragment pages.
     *
     * @param managerTag Unregistered manager tag name.
     */
    public static void unregister(String managerTag) {
        if (managerTag == null) {
            throw new NullPointerException("A manager tag is not set");
        }
        PageManager removedManager = fragmentPagerManagers.remove(managerTag);
        if (removedManager == null) {
            throw new IllegalStateException("The manager tag is not used");
        }
    }
}
