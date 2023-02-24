package com.example.apparty;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import com.example.apparty.gestores.GestorPurchase;
import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchasedEventsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener{

    private FragmentPurchasedEventsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewPast;
    private RecyclerView.LayoutManager layoutManagerPast;
    private RecyclerView.Adapter adapterPast;

    private GestorPurchase gestorPurchase;

    private List<Purchase> myPurchases = new ArrayList<>();
    private List<Purchase> myPastPurchases = new ArrayList<>();

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

        gestorPurchase = GestorPurchase.getInstance(this.getContext());

        setPurchasesList();

        recyclerView = binding.recyclerPurchases;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewPast = binding.recyclerPastPurchases;
        recyclerViewPast.setHasFixedSize(true);
        layoutManagerPast = new LinearLayoutManager(view.getContext());
        recyclerViewPast.setLayoutManager(layoutManagerPast);

        adapter = new ResultsRecyclerAdapter(myPurchases, this);
        recyclerView.setAdapter(adapter);
        adapterPast = new ResultsRecyclerAdapter(myPastPurchases, this);
        recyclerViewPast.setAdapter(adapterPast);

        recyclerView.setClickable(true);
        recyclerViewPast.setClickable(true);
    }

    private void setPurchasesList() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("loginInfo",MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", 0);

        List<Purchase> purchases = gestorPurchase.getPurchasesByIdUser(idUser);
        myPurchases = purchases.stream()
                .filter(p -> p.getEvent().getDate().isEqual(LocalDate.now()) || p.getEvent().getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        myPastPurchases = purchases.stream()
                .filter(p -> p.getEvent().getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public void onNoteClick(int idEvent) {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        //NavHostFragment.findNavController(PurchasedEventsFragment.this).navigate(R.id, bundle);
    }
}