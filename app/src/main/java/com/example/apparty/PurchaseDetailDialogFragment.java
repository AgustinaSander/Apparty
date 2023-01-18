package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentPurchaseDetailDialogBinding;

public class PurchaseDetailDialogFragment extends DialogFragment {

    private FragmentPurchaseDetailDialogBinding binding;

    public PurchaseDetailDialogFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPurchaseDetailDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}