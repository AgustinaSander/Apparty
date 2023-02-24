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
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchasedEventsFragment extends Fragment implements PurchasesRecyclerAdapter.OnNoteListener{

    private FragmentPurchasedEventsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewPast;
    private RecyclerView.LayoutManager layoutManagerPast;
    private RecyclerView.Adapter adapterPast;
    private TabLayout tabLayout;

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
        tabLayout = binding.tabLayout;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        gestorPurchase = GestorPurchase.getInstance(this.getContext());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tabLayout.getSelectedTabPosition();
                if(position==0){
                    binding.purchasesLayout.setVisibility(View.VISIBLE);
                    binding.pastPurchasesLayout.setVisibility(View.GONE);
                } else {
                    binding.purchasesLayout.setVisibility(View.GONE);
                    binding.pastPurchasesLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        setPurchasesList();

        recyclerView = binding.recyclerPurchases;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewPast = binding.recyclerPastPurchases;
        recyclerViewPast.setHasFixedSize(true);
        layoutManagerPast = new LinearLayoutManager(view.getContext());
        recyclerViewPast.setLayoutManager(layoutManagerPast);

        if(myPurchases.size()==0){
            binding.noResultsPurchases.setVisibility(View.VISIBLE);
        } else {
            binding.noResultsPurchases.setVisibility(View.GONE);
        }

        if(myPastPurchases.size()==0){
            binding.noResultsPastPurchases.setVisibility(View.VISIBLE);
        } else {
            binding.noResultsPastPurchases.setVisibility(View.GONE);
        }

        adapter = new PurchasesRecyclerAdapter(myPurchases, this);
        recyclerView.setAdapter(adapter);
        adapterPast = new PurchasesRecyclerAdapter(myPastPurchases, this);
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