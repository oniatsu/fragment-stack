package io.github.oniatsu.fragmentstack.sample.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.fragmentstack.FragmentStack;
import io.github.oniatsu.fragmentstack.sample.beans.PageBean;
import io.github.oniatsu.fragmentstack.sample.databinding.SubFragmentBinding;

public class Sub1xFragment extends BaseDefaultFragment {

    private SubFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SubFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pageBean = (PageBean) getArguments().getSerializable(ExtraKey.pageBean.toString());
        binding.setPageBean(pageBean);

        binding.componentPage.button1.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Sub1xFragment sub0xFragment = new Sub1xFragment();
            bundle.putSerializable(ExtraKey.pageBean.toString(), new PageBean(pageBean.getName(), pageBean.getNumber() + 1));
            sub0xFragment.setArguments(bundle);

            FragmentStack.of(Default10Fragment.class).add(sub0xFragment);
        });
    }
}
