package com.example.apparty.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentDetailEventItemBinding;

public class DetailEventItemFragment extends Fragment {

    private FragmentDetailEventItemBinding binding;

    public DetailEventItemFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailEventItemBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}