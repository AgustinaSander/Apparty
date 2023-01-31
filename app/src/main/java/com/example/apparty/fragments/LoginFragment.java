package com.example.apparty.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.R;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        binding.registryBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registryFragment);
        });
    }

    //private void setClickEvents() {
    //    binding.registryBtn.setOnClickListener(e -> {
      //      NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_registryFragment);
        //});
    //}

}