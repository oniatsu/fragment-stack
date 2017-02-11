package io.github.fragmentstack.views;

import android.support.v4.app.Fragment;

import io.github.fragmentstack.listeners.FragmentPagerLifeCycleListener;

/**
 * Base life cycle fragment.
 * You should inherit this fragment on every page fragments to call onForeground / onBackground appropriately.
 */
public abstract class FragmentPagerLifeCycleFragment extends Fragment implements FragmentPagerLifeCycleListener {

    @Override
    public void onForeground() {
    }

    @Override
    public void onBackground() {
    }

    @Override
    public void onResume() {
        super.onResume();
        onForeground();
    }

    @Override
    public void onPause() {
        onBackground();
        super.onPause();
    }
}
