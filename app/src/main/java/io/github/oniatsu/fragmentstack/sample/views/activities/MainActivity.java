package io.github.oniatsu.fragmentstack.sample.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.github.fragmentstack.FragmentStack;
import io.github.fragmentstack.PageManager;
import io.github.oniatsu.fragmentstack.sample.R;
import io.github.oniatsu.fragmentstack.sample.databinding.MainActivityBinding;
import io.github.oniatsu.fragmentstack.sample.views.adapters.TabFragmentPagerAdapter;
import io.github.oniatsu.fragmentstack.sample.views.fragments.BaseDefaultFragment;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set global config (Option)
        FragmentStack.globalConfig()
                .transitionInterceptor((fragmentPagerManager, fragmentTransaction) -> {
                    Fragment lastFragment = fragmentPagerManager.getLastFragment();
                    if (lastFragment != null) {
                        fragmentTransaction.hide(lastFragment);
                    }
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                })
                .setup();

        // Register
        FragmentStack.register(this, R.id.main_fragment_container, new BaseDefaultFragment())
                // Set local config (Option)
                .localConfig()
                .transitionInterceptor((fragmentPagerManager, fragmentTransaction) -> {
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                })
                .setup();

        binding.tabViewPager.setAdapter(new TabFragmentPagerAdapter(this, getSupportFragmentManager()));
        binding.tabViewPager.setOffscreenPageLimit(3);
        binding.tabLayout.setupWithViewPager(binding.tabViewPager);
    }

    @Override
    public void onBackPressed() {
        TabFragmentPagerAdapter tabFragmentPagerAdapter = (TabFragmentPagerAdapter) binding.tabViewPager.getAdapter();
        Fragment currentFragment = tabFragmentPagerAdapter.getItem(binding.tabViewPager.getCurrentItem());

        PageManager currentPageManager = FragmentStack.of(currentFragment);
        if (currentPageManager != null && currentPageManager.hasPage()) {
            currentPageManager.back();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister
        FragmentStack.unregister(this);
        super.onDestroy();
    }
}
