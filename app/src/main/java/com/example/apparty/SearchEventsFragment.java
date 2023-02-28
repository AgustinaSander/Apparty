package com.example.apparty;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.example.apparty.databinding.FragmentSearchEventsBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchEventsFragment extends Fragment {
    private Filter filters;
    private FragmentSearchEventsBinding binding;
    private List<CarouselItem> carouselMostRecentItemList;
    private List<CarouselItem> carouselCloseItemList;
    private GestorEvent gestorEvent;

    public SearchEventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getChildFragmentManager().setFragmentResultListener("requestFilter", this, (requestKey, bundle) -> {
            String result = bundle.getString("filters");
            filters = Utils.getGsonParser().fromJson(result, Filter.class);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        gestorEvent = GestorEvent.getInstance(getContext());

        filters = null;
        setClickEvents();
        setCarousels();
    }


    private void setClickEvents() {
        binding.filterBtn.setOnClickListener(e -> {
            showFilterDialog();
        });

        binding.searchEventsBtn.setOnClickListener(e -> {
            String wordsFilter = binding.searchInputEditText.getText().toString();
            showEvents(wordsFilter);
        });

        //SUBIR FOTO
        binding.addImage.setOnClickListener(e -> {
            uploadImage();
        });
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        uploadPictureLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> uploadPictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    binding.uploadImage.setImageURI(selectedImageUri);
                }
            });

    private void showEvents(String wordsFilter) {
        Bundle bundle = new Bundle();
        bundle.putString("filters", Utils.getGsonParser().toJson(filters));
        bundle.putString("wordsFilter", wordsFilter);

        NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventResultsFragment, bundle);
    }

    private void setCarousels() {
        setMostRecentEventsCarousel();
        setCloseEventsCarousel();
    }

    private void setCloseEventsCarousel() {
        carouselCloseItemList = new ArrayList<>();
        ImageCarousel carousel2 = binding.carousel2;
        carousel2.registerLifecycle(getLifecycle());

        //ACA SE AGREGARIAN LOS EVENTOS MAS CERCANOS SEGUN UBICACION!!!!!!
        List<Event> events = gestorEvent.getEventList(); //ESTO SE TIENE QUE CAMBIAR
        List<Pair<Integer, Pair<Integer, String>>> carouselInfo = new ArrayList<>();

        events.stream().limit(5).forEach(e -> {
            carouselInfo.add(Pair.create(e.getId(), Pair.create(R.drawable.party1, e.getName())));
        });

        for (Pair<Integer, Pair<Integer, String>> info : carouselInfo) {
            carouselCloseItemList.add(new CarouselItem(info.second.first, info.second.second));
        }

        carousel2.setData(carouselCloseItemList);

        carousel2.setCarouselListener(new CarouselListener() {
            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                int idEvent = carouselInfo.get(i).first;
                Bundle bundle = new Bundle();
                bundle.putInt("idEvent", idEvent);
                NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventDetailFragment, bundle);
            }
            @Override public void onLongClick(int i, @NonNull CarouselItem carouselItem) {}
            @Nullable @Override public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {return null;}
            @Override public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
            }
        });
    }

    private void setMostRecentEventsCarousel() {
        carouselMostRecentItemList = new ArrayList<>();
        ImageCarousel carousel1 = binding.carousel;
        carousel1.registerLifecycle(getLifecycle());

        List<Pair<Integer, Pair<Integer, String>>> carouselInfo = new ArrayList<>();
        List<Event> mostRecentEvents = gestorEvent.getMostRecentEvents();

        if(mostRecentEvents.size() > 0) {
            mostRecentEvents.stream().forEach(e -> {
                carouselInfo.add(Pair.create(e.getId(), Pair.create(R.drawable.party1, e.getName())));
            });

            for (Pair<Integer, Pair<Integer, String>> info : carouselInfo) {
                carouselMostRecentItemList.add(new CarouselItem(info.second.first, info.second.second));
            }

            carousel1.setData(carouselMostRecentItemList);

            carousel1.setCarouselListener(new CarouselListener() {
                @Override
                public void onClick(int i, @NonNull CarouselItem carouselItem) {
                    int idEvent = carouselInfo.get(i).first;
                    Bundle bundle = new Bundle();
                    bundle.putInt("idEvent", idEvent);
                    NavHostFragment.findNavController(SearchEventsFragment.this).navigate(R.id.action_searchEvents_to_eventDetailFragment, bundle);
                }
                @Override public void onLongClick(int i, @NonNull CarouselItem carouselItem) {}
                @Nullable @Override public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {return null;}
                @Override public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {}
            });

        } else {
            Snackbar.make(getView(), "No hay eventos proximos", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showFilterDialog() {
        new FilterDialogFragment().show(getChildFragmentManager(), null);

    }
}