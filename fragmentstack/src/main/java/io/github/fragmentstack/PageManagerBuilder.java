package io.github.fragmentstack;

import android.support.v4.app.Fragment;

import io.github.fragmentstack.listeners.FragmentPagerTransitionInterceptor;

public class PageManagerBuilder {

    private final PageManager pageManager;

    FragmentPagerTransitionInterceptor localFragmentPagerTransitionInterceptor;
    Fragment defaultFragment;

    PageManagerBuilder(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    /**
     * Build and setup.
     */
    public void setup() {
        pageManager.localConfig(this);
    }

    /**
     * Set a PageManager local transition interceptor.
     * The interceptor used on connected the PagerManger's fragment page transitions.
     *
     * @param localFragmentPagerTransitionInterceptor Local transition interceptor.
     * @return PageManagerBuilder.
     */
    public PageManagerBuilder transitionInterceptor(FragmentPagerTransitionInterceptor localFragmentPagerTransitionInterceptor) {
        this.localFragmentPagerTransitionInterceptor = localFragmentPagerTransitionInterceptor;
        return this;
    }
}
