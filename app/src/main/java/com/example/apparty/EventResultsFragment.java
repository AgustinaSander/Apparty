package com.example.apparty;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.FragmentEventResultsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;


public class EventResultsFragment extends Fragment implements ResultsRecyclerAdapter.OnNoteListener, OnMapReadyCallback {

    private GestorEvent gestorEvent;
    private Filter filters;
    private String wordsFilter;
    private List<Event> filteredEvents = new ArrayList<>();

    private FragmentEventResultsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FusedLocationProviderClient fusedLocationProviderClient;


    public EventResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventResultsBinding.inflate(inflater, container, false);

        //Initialize map
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        //Async map
        supportMapFragment.getMapAsync(this::onMapReady);

        String filterString = getArguments().getString("filters");
        wordsFilter = getArguments().getString("wordsFilter");
        filters = Utils.getGsonParser().fromJson(filterString, Filter.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        gestorEvent = GestorEvent.getInstance(this.getContext());
        recyclerView = binding.recyclerResult;
        //mapView = binding.mapView;

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


    private void updateMap(GoogleMap googleMap) {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 9999);
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //When map is loaded
        updateMap(googleMap);
        LatLng coordInicial = new LatLng(-31.611106676143716, -60.7028264088395);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordInicial, 12));
        for (Event e: filteredEvents){
            String address = e.getAddress().getAddress() + ", " + e.getAddress().getLocalty() + ", " + e.getAddress().getProvince();
            LatLng location = getLocationFromAddress(this.getContext(), address);
            ///Initialize marker options
            MarkerOptions markerOptions = new MarkerOptions();
            //Set position of marker
            markerOptions.position(location);
            //Color
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)); 
            //Set title of marker
            markerOptions.title(e.getName());
            //Add marer on map
            googleMap.addMarker(markerOptions);
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
