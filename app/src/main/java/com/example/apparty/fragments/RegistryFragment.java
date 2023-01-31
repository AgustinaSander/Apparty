package com.example.apparty.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.R;


public class RegistryFragment extends Fragment {

    private FragmentRegistryBinding binding;

    public RegistryFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        binding.loginBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(RegistryFragment.this).navigate(R.id.loginFragment);
        });
    }
}