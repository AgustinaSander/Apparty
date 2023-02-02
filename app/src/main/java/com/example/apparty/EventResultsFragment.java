package com.example.apparty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.FragmentEventResultsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import lombok.NonNull;

public class EventResultsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener {

    private GestorEvent gestorEvent;

    private FragmentEventResultsBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public EventResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        gestorEvent = GestorEvent.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerResult;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //dsp agregar la lista
        adapter = new ResultsRecyclerAdapter(gestorEvent.getEventList(), this);
        recyclerView.setAdapter(adapter);

        recyclerView.setClickable(true);
    }

    @Override
    public void onNoteClick(int idEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        NavHostFragment.findNavController(EventResultsFragment.this).navigate(R.id.action_eventResultsFragment_to_eventDetailFragment, bundle);
    }

}
