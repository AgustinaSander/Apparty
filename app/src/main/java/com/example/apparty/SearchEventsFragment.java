package com.example.apparty;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.example.apparty.databinding.FragmentSearchEventsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchEventsFragment extends Fragment implements CarouselRecyclerAdapter.OnNoteListener{
    private Filter filters;
    private FragmentSearchEventsBinding binding;
    private GestorEvent gestorEvent;

    private RecyclerView recyclerViewRecent;
    private RecyclerView.Adapter adapterRecent;
    private RecyclerView.LayoutManager layoutManagerRecent;

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
        setMostRecentEventsCarousel();
    }


    private void setClickEvents() {
        binding.filterBtn.setOnClickListener(e -> {
            showFilterDialog();
        });

        binding.searchEventsBtn.setOnClickListener(e -> {
            String wordsFilter = binding.searchInputEditText.getText().toString();
            showEvents(wordsFilter);
        });
    }

    private void showEvents(String wordsFilter) {
        Bundle bundle = new Bundle();
        bundle.putString("filters", Utils.getGsonParser().toJson(filters));
        bundle.putString("wordsFilter", wordsFilter);

        NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventResultsFragment, bundle);
    }

    private void setMostRecentEventsCarousel() {
        recyclerViewRecent = binding.mostRecentCarousel;
        recyclerViewRecent.setHasFixedSize(true);
        layoutManagerRecent = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecent.setLayoutManager(layoutManagerRecent);

        List<Event> mostRecentEvents = gestorEvent.getMostRecentEvents();

        if(mostRecentEvents.size() > 0) {
            adapterRecent = new CarouselRecyclerAdapter(mostRecentEvents, this);
            recyclerViewRecent.setAdapter(adapterRecent);
            recyclerViewRecent.setClickable(true);
        } else {
            Snackbar.make(getView(), "No hay eventos proximos", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showFilterDialog() {
        new FilterDialogFragment().show(getChildFragmentManager(), null);

    }

    @Override
    public void onNoteClick(int idEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventDetailFragment, bundle);
    }
}