package com.example.apparty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.FragmentEventResultsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;

import java.util.List;

import lombok.NonNull;

public class EventResults extends Fragment {

    private GestorEvent gestorEvent;

    private FragmentEventResultsBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public EventResults() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        gestorEvent = GestorEvent.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        adapter = new ResultsRecyclerAdapter(gestorEvent.getEventList(), (ResultsRecyclerAdapter.OnNoteListener)this);
        recyclerView.setAdapter(adapter);

        recyclerView.setClickable(true);
    }

}
