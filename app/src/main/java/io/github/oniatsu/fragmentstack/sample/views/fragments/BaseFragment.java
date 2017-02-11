package io.github.oniatsu.fragmentstack.sample.views.fragments;

import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import io.github.fragmentstack.views.FragmentPagerLifeCycleFragment;
import io.github.oniatsu.fragmentstack.sample.R;

public abstract class BaseFragment extends FragmentPagerLifeCycleFragment {

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            if (enter) {
                return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom);
            } else {
                return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
            }
        }

        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
            if (enter) {
                return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom);
            } else {
                return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
}
