package io.github.oniatsu.fragmentstack.sample.views.fragments;

import io.github.oniatsu.fragmentstack.sample.beans.PageBean;
import io.github.oniatsu.fragmentstack.sample.utils.Logger;

public class BaseDefaultFragment extends BaseFragment {

    public enum ExtraKey {
        pageBean,
    }

    PageBean pageBean;

    @Override
    public void onForeground() {
        super.onForeground();
        Logger.i("Default onForeground");
    }

    @Override
    public void onBackground() {
        super.onBackground();
        Logger.i("Default onBackground");
    }
}
