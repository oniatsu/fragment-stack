package io.github.fragmentstack;

import io.github.fragmentstack.listeners.FragmentPagerTransitionInterceptor;

public class FragmentStackBuilder {

    FragmentPagerTransitionInterceptor globalFragmentPagerTransitionInterceptor;

    FragmentStackBuilder() {
    }

    /**
     * Build and setup.
     */
    public void setup() {
        FragmentStack.build(this);
    }

    /**
     * Set a application global transition interceptor.
     * The interceptor used on every fragment page transitions.
     *
     * @param globalFragmentPagerTransitionInterceptor Global transition interceptor.
     * @return FragmentStackBuilder.
     */
    public FragmentStackBuilder transitionInterceptor(FragmentPagerTransitionInterceptor globalFragmentPagerTransitionInterceptor) {
        this.globalFragmentPagerTransitionInterceptor = globalFragmentPagerTransitionInterceptor;
        return this;
    }
}
