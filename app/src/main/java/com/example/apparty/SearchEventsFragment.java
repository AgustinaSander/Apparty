package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.apparty.databinding.FragmentSearchEventsBinding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class SearchEventsFragment extends Fragment {

    private FragmentSearchEventsBinding binding;
    private List<CarouselItem> carouselItemList = new ArrayList<>();

    public SearchEventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ImageCarousel carousel1 = binding.carousel;
        ImageCarousel carousel2 = binding.carousel2;
        carousel1.registerLifecycle(getLifecycle());
        carousel2.registerLifecycle(getLifecycle());

        List<Pair<Integer, String>> carouselInfo = new ArrayList<>();
        //INICIALIZAR CAROUSEL
        carouselInfo.add(Pair.create(R.drawable.party1, "Fiesta en Salones del puerto"));
        carouselInfo.add(Pair.create(R.drawable.party2, "Poolparty en ATE"));

        for (Pair<Integer, String> info : carouselInfo) {
            carouselItemList.add(new CarouselItem(info.first, info.second));
        }
        carousel1.setData(carouselItemList);
        carousel2.setData(carouselItemList);

        carousel1.setCarouselListener(new CarouselListener() {
            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                //CON ESTO VAMOS A REDIRECCIONAR AL DETALLE DEL EVENTO SELECCIONADO
                Log.i("PRESIONADO", "onClick: " + carouselItem.getCaption());
            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {
            }

            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
            }
        });

        carousel2.setCarouselListener(new CarouselListener() {
            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                //CON ESTO VAMOS A REDIRECCIONAR AL DETALLE DEL EVENTO SELECCIONADO
                Log.i("PRESIONADO", "onClick: " + carouselItem.getCaption());
            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {
            }

            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i) {
            }
        });

        binding.filterBtn.setOnClickListener( e -> {
            showFilterDialog();
        });
    }

    private void showFilterDialog() {
        DialogFragment newFragment = new FilterDialogFragment();
        newFragment.show(getFragmentManager(), "dialog");
    }
}