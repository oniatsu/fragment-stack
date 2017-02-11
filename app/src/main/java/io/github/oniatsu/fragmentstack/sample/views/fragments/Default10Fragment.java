package io.github.oniatsu.fragmentstack.sample.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.fragmentstack.FragmentStack;
import io.github.oniatsu.fragmentstack.sample.R;
import io.github.oniatsu.fragmentstack.sample.beans.PageBean;
import io.github.oniatsu.fragmentstack.sample.databinding.Default10FragmentBinding;

public class Default10Fragment extends BaseDefaultFragment {

    private Default10FragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Default10FragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentStack.register(this, R.id.default_fragment1_container, new Sub1xFragment());

        pageBean = (PageBean) getArguments().getSerializable(ExtraKey.pageBean.toString());
        binding.setPageBean(pageBean);

        binding.componentPage.button1.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Sub1xFragment sub1xFragment = new Sub1xFragment();
            bundle.putSerializable(ExtraKey.pageBean.toString(), new PageBean(pageBean.getName(), pageBean.getNumber() + 1));
            sub1xFragment.setArguments(bundle);

            FragmentStack.of(this).add(sub1xFragment);
        });
    }

    @Override
    public void onDestroy() {
        FragmentStack.unregister(this);
        super.onDestroy();
    }
}
