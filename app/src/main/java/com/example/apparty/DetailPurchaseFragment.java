package com.example.apparty;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.apparty.databinding.FragmentDetailPurchaseBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Stock;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DetailPurchaseFragment extends Fragment {

    private FragmentDetailPurchaseBinding binding;
    private GestorEvent gestorEvent = GestorEvent.getInstance();
    private Ticket ticket;

    public DetailPurchaseFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailPurchaseBinding.inflate(inflater, container, false);

        String ticketJson = getArguments().getString("ticket");
        ticket = Utils.getGsonParser().fromJson(ticketJson, Ticket.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        setValues();
        setClickEvents();
    }

    private void setClickEvents() {

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.detailBtn:
                    binding.paymentLayout.setVisibility(View.GONE);
                    binding.detailLayout.setVisibility(View.VISIBLE);
                    return true;

                case R.id.paymentBtn:
                    binding.detailLayout.setVisibility(View.GONE);
                    binding.paymentLayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        });
    }

    private void setValues() {
        List<Pair<Integer, Integer>> ticketsList = ticket.getTickets();
        ticketsList.stream().forEach(t -> {
            Stock stock = gestorEvent.getStockByIdByEvent(t.first, ticket.getEvent().getId());
            //Create row
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setPadding(20, 0, 20, 0);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);

            TextView title = new TextView(getActivity());
            title.setTextSize(16);
            title.setWidth(100);
            title.setText(stock.getType());

            TextView quantity = new TextView(getActivity());
            quantity.setText(String.valueOf(t.second));
            quantity.setTextSize(16);
            quantity.setGravity(Gravity.CENTER);

            TextView price = new TextView(getActivity());
            price.setText("$ "+stock.getPrice());
            price.setTextSize(16);
            price.setGravity(Gravity.CENTER);

            TextView total = new TextView(getActivity());
            double totalPrice = t.second * stock.getPrice();
            total.setText("$ "+totalPrice);
            total.setTextSize(16);
            total.setGravity(Gravity.CENTER);

            tableRow.addView(title);
            tableRow.addView(quantity);
            tableRow.addView(price);
            tableRow.addView(total);
            binding.tableTickets.addView(tableRow);
        });


    }
}