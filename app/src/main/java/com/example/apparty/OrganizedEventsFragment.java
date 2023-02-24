package com.example.apparty;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.FragmentOrganizedEventsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.User;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;

public class OrganizedEventsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener{

    private FragmentOrganizedEventsBinding binding;
    private RecyclerView recyclerViewNext;
    private RecyclerView recyclerViewPast;
    private RecyclerView.Adapter adapterNext;
    private RecyclerView.Adapter adapterPast;
    private RecyclerView.LayoutManager layoutManagerNext;
    private RecyclerView.LayoutManager layoutManagerPast;
    private TabLayout tabLayout;
    private List<Event> nextEvents = new ArrayList<>();
    private List<Event> pastEvents = new ArrayList<>();
    private GestorEvent gestorEvent;

    public OrganizedEventsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrganizedEventsBinding.inflate(inflater,container,false);
        tabLayout = binding.tabLayout;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gestorEvent = GestorEvent.getInstance(getContext());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tabLayout.getSelectedTabPosition();
                if(position==0){
                    binding.nextEventsLayout.setVisibility(View.VISIBLE);
                    binding.pastEventsLayout.setVisibility(View.GONE);
                } else {
                    binding.nextEventsLayout.setVisibility(View.GONE);
                    binding.pastEventsLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        recyclerViewNext = binding.recyclerNextEvents;
        recyclerViewPast = binding.recyclerPastEvents;
        recyclerViewNext.setHasFixedSize(true);
        recyclerViewPast.setHasFixedSize(true);
        layoutManagerNext = new LinearLayoutManager(view.getContext());
        layoutManagerPast = new LinearLayoutManager(view.getContext());
        recyclerViewNext.setLayoutManager(layoutManagerNext);
        recyclerViewPast.setLayoutManager(layoutManagerPast);

        setEventList();

        if(nextEvents.size()==0){
            binding.noResultsNextEvents.setVisibility(View.VISIBLE);
            binding.textResume.setVisibility(View.GONE);
        } else {
            binding.noResultsNextEvents.setVisibility(View.GONE);
            binding.textResume.setVisibility(View.VISIBLE);
        }

        if(pastEvents.size()==0){
            binding.noResultsPastEvents.setVisibility(View.VISIBLE);
            binding.textResume.setVisibility(View.GONE);
        } else {
            binding.noResultsPastEvents.setVisibility(View.GONE);
            binding.textResume.setVisibility(View.VISIBLE);
        }

        adapterNext = new ResultsRecyclerAdapter(nextEvents, this);
        adapterPast = new ResultsRecyclerAdapter(pastEvents, this);

        recyclerViewNext.setAdapter(adapterNext);
        recyclerViewPast.setAdapter(adapterPast);

        recyclerViewNext.setClickable(true);
        recyclerViewPast.setClickable(false);

        binding.createEventBtn.setOnClickListener(e -> {
            NavHostFragment.findNavController(OrganizedEventsFragment.this).navigate(R.id.action_organizedEventsFragment_to_fragmentRegisterEvent);
        });
    }

    private void setEventList() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("loginInfo",MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", 0);

        List<Event> myEvents = gestorEvent.getEventsOrganizedByUser(idUser);
        nextEvents = myEvents.stream()
                .filter(e -> e.getDate().isEqual(LocalDate.now()) || e.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        pastEvents = myEvents.stream()
                .filter(e -> e.getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public void onNoteClick(int idEvent) {
        if(binding.pastEventsLayout.getVisibility() == View.GONE){
            Bundle bundle = new Bundle();
            bundle.putInt("idEvent", idEvent);
            NavHostFragment.findNavController(OrganizedEventsFragment.this).navigate(R.id.action_organizedEventsFragment_to_scanQRFragment, bundle);
        }
    }
}