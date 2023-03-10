package com.example.apparty;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentRegisterTypeTicketBinding;


public class RegisterTypeTicketFragment extends Fragment {

    private FragmentRegisterTypeTicketBinding binding;

    public RegisterTypeTicketFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterTypeTicketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}