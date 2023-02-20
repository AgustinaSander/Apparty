package com.example.apparty;

import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.FragmentEventResultsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Utils;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class EventResultsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener {

    private GestorEvent gestorEvent = GestorEvent.getInstance();
    private Filter filters;
    private String wordsFilter;
    private List<Event> filteredEvents = new ArrayList<>();

    private FragmentEventResultsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public EventResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventResultsBinding.inflate(inflater, container, false);

        String filterString = getArguments().getString("filters");
        wordsFilter = getArguments().getString("wordsFilter");
        filters = Utils.getGsonParser().fromJson(filterString, Filter.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerResult;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        setEventList();

        if(filteredEvents.size()==0){
            binding.noResults.setVisibility(View.VISIBLE);
            binding.textResume.setVisibility(View.GONE);
        } else {
            binding.noResults.setVisibility(View.GONE);
            binding.textResume.setVisibility(View.VISIBLE);
        }


        adapter = new ResultsRecyclerAdapter(filteredEvents, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setClickable(true);
    }

    private void setEventList() {
        filteredEvents = new ArrayList<>();
        filteredEvents = gestorEvent.getFilteredEvents(wordsFilter, filters);
    }

    @Override
    public void onNoteClick(int idEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        NavHostFragment.findNavController(EventResultsFragment.this).navigate(R.id.action_eventResultsFragment_to_eventDetailFragment, bundle);
    }

}
