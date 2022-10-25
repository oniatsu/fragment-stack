package io.github.oniatsu.fragmentstack.sample.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.github.fragmentstack.FragmentStack;
import io.github.oniatsu.fragmentstack.sample.R;
import io.github.oniatsu.fragmentstack.sample.beans.PageBean;
import io.github.oniatsu.fragmentstack.sample.databinding.Default00FragmentBinding;

public class Default00Fragment extends BaseDefaultFragment {

    private Default00FragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Default00FragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentStack.register(this, R.id.default_fragment0_container, new Sub0xFragment());
        pageBean = (PageBean) getArguments().getSerializable(ExtraKey.pageBean.toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setPageBean(pageBean);
        binding.componentPage.button1.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Sub0xFragment sub0xFragment = new Sub0xFragment();
            bundle.putSerializable(ExtraKey.pageBean.toString(), new PageBean(pageBean.getName(), pageBean.getNumber() + 1));
            sub0xFragment.setArguments(bundle);

            FragmentStack.of(this).add(sub0xFragment, (pageManager, fragmentTransaction) -> {

            });
        });
    }

    @Override
    public void onDestroy() {
        FragmentStack.unregister(this);
        super.onDestroy();
    }
}
