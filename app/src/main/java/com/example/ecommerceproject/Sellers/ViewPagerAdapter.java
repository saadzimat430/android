package com.example.ecommerceproject.Sellers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitles = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(Objects.requireNonNull(fm));
    }

    @Override
    public Fragment getItem(int position) {

        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitles.size();
    }

    public CharSequence getPageTitle(int position)
    {
        return listTitles.get(position);
    }


    public void AddFragment(Fragment fragment, String title)
    {
        listFragment.add(fragment);
        listTitles.add(title);
    }

}
