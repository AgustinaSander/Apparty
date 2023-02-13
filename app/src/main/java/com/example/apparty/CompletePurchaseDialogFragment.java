package com.example.apparty;

import static android.content.Context.WINDOW_SERVICE;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.apparty.databinding.FragmentCompletePurchaseDialogBinding;
import com.google.android.material.snackbar.Snackbar;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class CompletePurchaseDialogFragment extends DialogFragment {

    private FragmentCompletePurchaseDialogBinding binding;

    public CompletePurchaseDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletePurchaseDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        generateQR();
        setClickEvents();
    }

    private void generateQR() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        QRGEncoder qrgEncoder = new QRGEncoder("hola", null, QRGContents.Type.TEXT, 2000);
        Bitmap myBitmap = qrgEncoder.getBitmap();
        ImageView qrImage = binding.qrImage;
        qrImage.setImageBitmap(myBitmap);
    }

    private void setClickEvents() {
        binding.downloadBtn.setOnClickListener(e -> downloadQR());
    }

    private void downloadQR() {
        Snackbar.make(getView(), "Funcionalidad de Descargar no implementada", Snackbar.LENGTH_SHORT).show();
    }
}