package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentDetailEventItemBinding;
import com.example.apparty.databinding.FragmentEventDetailBinding;

public class DetailEventItem extends Fragment {

    private FragmentDetailEventItemBinding binding;

    public DetailEventItem() {}


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