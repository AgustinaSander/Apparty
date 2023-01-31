package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentPaymentPurchaseBinding;
import com.example.apparty.databinding.FragmentSearchEventsBinding;

public class PaymentPurchaseFragment extends Fragment {

    private FragmentPaymentPurchaseBinding binding;
    private Bundle bundle = new Bundle();

    public PaymentPurchaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentPurchaseBinding.inflate(inflater, container, false);
        String ticketJson = getArguments().getString("ticket");
        bundle.putString("ticket", ticketJson);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setClickEvents();
    }

    private void setClickEvents() {

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.detailBtn:
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.goBackToDetailPurchase, bundle);
                    return true;

                case R.id.paymentBtn:
                    return true;
            }
            return false;
        });

        binding.payBtn.setOnClickListener(e -> {doPayment();});
    }

    private void doPayment() {
        new CompletePurchaseFragment().show(getChildFragmentManager(), null);
    }

}