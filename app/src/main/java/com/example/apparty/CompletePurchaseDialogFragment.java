package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentCompletePurchaseDialogBinding;
import com.google.android.material.snackbar.Snackbar;

public class CompletePurchaseDialogFragment extends DialogFragment {

    private FragmentCompletePurchaseDialogBinding binding;

    public CompletePurchaseDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletePurchaseDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setClickEvents();
    }

    private void setClickEvents() {
        binding.downloadBtn.setOnClickListener(e -> downloadQR());
    }

    private void downloadQR() {
        Snackbar.make(getView(), "Funcionalidad de Descargar no implementada", Snackbar.LENGTH_SHORT).show();
    }
}