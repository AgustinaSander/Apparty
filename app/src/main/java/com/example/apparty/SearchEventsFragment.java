package com.example.apparty;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.example.apparty.databinding.FragmentSearchEventsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Utils;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class SearchEventsFragment extends Fragment {
    private Filter filters;
    private FragmentSearchEventsBinding binding;
    private List<CarouselItem> carouselItemList;
    private GestorEvent gestorEvent;

    public SearchEventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getChildFragmentManager().setFragmentResultListener("requestFilter", this, (requestKey, bundle) -> {
            String result = bundle.getString("filters");
            filters = Utils.getGsonParser().fromJson(result, Filter.class);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        gestorEvent = GestorEvent.getInstance(getContext());

        filters = null;
        setClickEvents();
        setCarousels();
    }


    private void setClickEvents() {
        binding.filterBtn.setOnClickListener(e -> {
            showFilterDialog();
        });

        binding.searchEventsBtn.setOnClickListener(e -> {
            String wordsFilter = binding.searchInputEditText.getText().toString();
            showEvents(wordsFilter);
        });

        binding.scanQRTest.setOnClickListener(e -> {
            NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_scanQRFragment);
        });
    }

    private void showEvents(String wordsFilter) {
        Bundle bundle = new Bundle();
        bundle.putString("filters", Utils.getGsonParser().toJson(filters));
        bundle.putString("wordsFilter", wordsFilter);

        NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventResultsFragment, bundle);
    }

    private void setCarousels() {
        carouselItemList = new ArrayList<>();
        ImageCarousel carousel1 = binding.carousel;
        ImageCarousel carousel2 = binding.carousel2;
        carousel1.registerLifecycle(getLifecycle());
        carousel2.registerLifecycle(getLifecycle());

        List<Pair<Integer, Pair<Integer, String>>> carouselInfo = new ArrayList<>();
        //INICIALIZAR CAROUSEL
        List<Event> events = gestorEvent.getEventList();

        events.stream().forEach(e -> {
            carouselInfo.add(Pair.create(e.getId(), Pair.create(R.drawable.party1, e.getName())));
        });
        for (Pair<Integer, Pair<Integer, String>> info : carouselInfo) {
            carouselItemList.add(new CarouselItem(info.second.first, info.second.second));
        }
        carousel1.setData(carouselItemList);
        carousel2.setData(carouselItemList);

        carousel1.setCarouselListener(new CarouselListener() {
            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                int idEvent = carouselInfo.get(i).first;
                Bundle bundle = new Bundle();
                bundle.putInt("idEvent", idEvent);
                NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventDetailFragment, bundle);
            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {
            }

            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
            }
        });

        carousel2.setCarouselListener(new CarouselListener() {
            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                int idEvent = carouselInfo.get(i).first;
                Bundle bundle = new Bundle();
                bundle.putInt("idEvent", idEvent);
                NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventDetailFragment, bundle);
            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {
            }

            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
            }
        });
    }

    private void showFilterDialog() {
        new FilterDialogFragment().show(getChildFragmentManager(), null);

    }
}