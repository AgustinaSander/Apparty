package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentSearchEventsBinding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class SearchEvents extends Fragment {

    private FragmentSearchEventsBinding binding;
    private List<CarouselItem> carouselItemList = new ArrayList<>();

    public SearchEvents() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        ImageCarousel carousel = binding.carousel;
        carousel.registerLifecycle(getLifecycle());

        carouselItemList.add(new CarouselItem(R.drawable.party1));
        carouselItemList.add(new CarouselItem(R.drawable.party2));
        carousel.setData(carouselItemList);
    }
}