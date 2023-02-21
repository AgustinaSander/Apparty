package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentPurchasedEventsBinding;
import com.example.apparty.model.Event;

import java.util.ArrayList;
import java.util.List;

public class PurchasedEventsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener{

    private FragmentPurchasedEventsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<Event> myPurchases = new ArrayList<>();

    public PurchasedEventsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPurchasedEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        setPurchasesList();

        recyclerView = binding.recyclerPurchasedEvents;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ResultsRecyclerAdapter(myPurchases, this);
        recyclerView.setAdapter(adapter);

        //DESHABILITAR CLICK DE LOS ITEMS FUTUROS
        recyclerView.setClickable(true);
    }

    private void setPurchasesList() {
        //BUSCAR TODAS LAS COMPRAS QUE HAYA HECHO LA PERSONA LOGUEADA!!!!!!
    }

    @Override
    public void onNoteClick(int idEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        //NavHostFragment.findNavController(PurchasedEventsFragment.this).navigate(R.id, bundle);
    }
}