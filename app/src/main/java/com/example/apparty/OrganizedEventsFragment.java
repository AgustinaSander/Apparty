package com.example.apparty;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentOrganizedEventsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;

public class OrganizedEventsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener{

    private FragmentOrganizedEventsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewToday;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapterToday;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerToday;
    private List<Event> myEvents = new ArrayList<>();
    private List<Event> todayEvents = new ArrayList<>();
    private GestorEvent gestorEvent = GestorEvent.getInstance();

    public OrganizedEventsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrganizedEventsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerOrganizedEvents;
        recyclerViewToday = binding.recyclerTodayOrganizedEvents;
        recyclerView.setHasFixedSize(true);
        recyclerViewToday.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManagerToday = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewToday.setLayoutManager(layoutManagerToday);

        setEventList();

        if(myEvents.size()==0){
            binding.noResults.setVisibility(View.VISIBLE);
            binding.textResume.setVisibility(View.GONE);
            binding.noResultsToday.setVisibility(View.GONE);
        } else {
            binding.noResults.setVisibility(View.GONE);
            binding.textResume.setVisibility(View.VISIBLE);
        }

        if(todayEvents.size()==0){
            binding.noResultsToday.setVisibility(View.VISIBLE);
        } else {
            binding.noResultsToday.setVisibility(View.GONE);
        }

        adapter = new ResultsRecyclerAdapter(myEvents, this);
        adapterToday = new ResultsRecyclerAdapter(todayEvents, this);
        recyclerView.setAdapter(adapter);
        recyclerViewToday.setAdapter(adapterToday);

        //DESHABILITAR CLICK DE LOS ITEMS FUTUROS
        recyclerView.setClickable(true);
        recyclerViewToday.setClickable(true);

        binding.createEventBtn.setOnClickListener(e -> {
            NavHostFragment.findNavController(OrganizedEventsFragment.this).navigate(R.id.action_organizedEventsFragment_to_fragmentRegisterEvent);
        });
    }

    private void setEventList() {
        // ACA DEBERIA OBTENER EL USUARIO QUE YA SE ENCUENTRA LOGUEADO (VER COMO HACER) !!!
        int idUser = 1;
        myEvents = gestorEvent.getEventsOrganizedByUser(idUser);
        Log.i("Eventos del organizador", myEvents.toString());
        todayEvents = myEvents.stream().filter(e -> e.getDate().isEqual(LocalDate.now())).collect(Collectors.toList());
        Log.i("Eventos del dia", todayEvents.toString());
    }

    @Override
    public void onNoteClick(int idEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        NavHostFragment.findNavController(OrganizedEventsFragment.this).navigate(R.id.action_organizedEventsFragment_to_scanQRFragment, bundle);
    }
}