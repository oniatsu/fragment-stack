package io.github.oniatsu.fragmentstack.sample.views.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import io.github.oniatsu.fragmentstack.sample.beans.PageBean;
import io.github.oniatsu.fragmentstack.sample.views.fragments.BaseDefaultFragment;
import io.github.oniatsu.fragmentstack.sample.views.fragments.Default00Fragment;
import io.github.oniatsu.fragmentstack.sample.views.fragments.Default10Fragment;
import io.github.oniatsu.fragmentstack.sample.views.fragments.Default20Fragment;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    public TabFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        PageBean pageBean = new PageBean("Tab " + position, 0);
        switch (position) {
            case 0:
                Default00Fragment default00Fragment = new Default00Fragment();
                bundle.putSerializable(BaseDefaultFragment.ExtraKey.pageBean.toString(), pageBean);
                default00Fragment.setArguments(bundle);
                return default00Fragment;
            case 1:
                Default10Fragment default10Fragment = new Default10Fragment();
                bundle.putSerializable(BaseDefaultFragment.ExtraKey.pageBean.toString(), pageBean);
                default10Fragment.setArguments(bundle);
                return default10Fragment;
            case 2:
                Default20Fragment default20Fragment = new Default20Fragment();
                bundle.putSerializable(BaseDefaultFragment.ExtraKey.pageBean.toString(), pageBean);
                default20Fragment.setArguments(bundle);
                return default20Fragment;
            default:
                throw new IllegalStateException("the position is not defined.");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }
}
