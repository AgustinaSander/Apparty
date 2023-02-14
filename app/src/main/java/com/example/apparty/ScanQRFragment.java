package com.example.apparty;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apparty.databinding.FragmentScanQRBinding;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanQRFragment extends Fragment {

    private FragmentScanQRBinding binding;

    public ScanQRFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScanQRBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //escanear QR
        binding.scanQR.setOnClickListener(e -> {
            barcodeLauncher.launch(new ScanOptions());
        });

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    // Launch
    public void onButtonClick(View view) {
        barcodeLauncher.launch(new ScanOptions());
    }
}