package com.example.apparty.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.apparty.fragments.CompletePurchaseFragment;
import com.example.apparty.fragments.DetailPurchaseFragment;
import com.example.apparty.fragments.PaymentPurchaseFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 1:
                return new PaymentPurchaseFragment();
            case 2:
                return new CompletePurchaseFragment();
            case 0:
            default:
                return new DetailPurchaseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
