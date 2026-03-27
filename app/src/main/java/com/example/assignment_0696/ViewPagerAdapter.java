package com.example.assignment_0696;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new NowShowingFragment();
        else
            return new ComingSoonFragment();
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
