package com.example.apparty;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentPurchaseBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PurchaseFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;

    private FragmentPurchaseBinding binding;

    public PurchaseFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false);

        viewPager = binding.pager;
        tabLayout = binding.tablayout;
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        //viewPager.setUserInputEnabled(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


        int idEvent = getArguments().getInt("idEvent");
        ArrayList<Pair<Integer, Integer>> tickets = (ArrayList<Pair<Integer, Integer>>) getArguments().getSerializable("tickets");
        Log.i("EVENTO", String.valueOf(idEvent));
        Log.i("TICKETS", String.valueOf(tickets));

        return binding.getRoot();
    }
}