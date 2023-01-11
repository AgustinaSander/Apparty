package com.example.apparty;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apparty.databinding.FragmentFilterDialogBinding;
import com.example.apparty.databinding.FragmentSearchEventsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.Date;

public class FilterDialogFragment extends DialogFragment {

    private FragmentFilterDialogBinding binding;
    private TextView fromDateText;
    private TextView toDateText;
    private Date fromDate;
    private Date toDate;

    public FilterDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setClickEvents();
    }

    private void setClickEvents() {
        MaterialButton fromDateBtn = binding.fromDateBtn;
        fromDateText = binding.fromDate;
        fromDateBtn.setOnClickListener(e -> showFromDatePickerDialog());

        MaterialButton toDateBtn = binding.toDateBtn;
        toDateText = binding.toDate;
        toDateBtn.setOnClickListener(e -> showToDatePickerDialog());
    }

    private void showFromDatePickerDialog() {
        CalendarConstraints.Builder constraintBuilder = setDatePickerConstraints();
        constraintBuilder.setValidator(DateValidatorPointForward.now());
        MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha inicial")
                .setCalendarConstraints(constraintBuilder.build());
        if(fromDate != null) datePickerBuilder.setSelection(fromDate.getTime());

        MaterialDatePicker materialDatePicker = datePickerBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                fromDateText.setText(""+materialDatePicker.getHeaderText());
                Date dateSelected = new Date((Long) materialDatePicker.getSelection());
                fromDate = new Date(dateSelected.getTime() + (1000 * 60 * 60 * 24));

                binding.toDateBtn.setEnabled(fromDate != null);
                toDate = null;
                binding.toDate.setText("Fecha de inicio");
            }
        });
        materialDatePicker.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    private void showToDatePickerDialog() {
        CalendarConstraints.Builder constraintBuilder = setDatePickerConstraints();
        constraintBuilder.setValidator(DateValidatorPointForward.from(fromDate.getTime()));
        MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha final")
                .setCalendarConstraints(constraintBuilder.build());
        if(toDate != null) datePickerBuilder.setSelection(toDate.getTime());

        MaterialDatePicker materialDatePicker = datePickerBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                toDateText.setText(""+materialDatePicker.getHeaderText());
                Date dateSelected = new Date((Long) materialDatePicker.getSelection());
                toDate = new Date(dateSelected.getTime() + (1000 * 60 * 60 * 24));
            }
        });
        materialDatePicker.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    private CalendarConstraints.Builder setDatePickerConstraints() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        long month = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setStart(month);
        return constraintBuilder;
    }
}