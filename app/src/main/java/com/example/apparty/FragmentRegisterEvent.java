package com.example.apparty;

import static android.text.format.DateFormat.is24HourFormat;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.apparty.databinding.FragmentFilterDialogBinding;
import com.example.apparty.databinding.FragmentRegisterEventBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class FragmentRegisterEvent extends Fragment {
    private FragmentRegisterEventBinding binding;
    private TextView DateText;
    private TextView TimeText;
    private Date Date;

    public FragmentRegisterEvent() {
    }

    public static FragmentRegisterEvent newInstance(String param1, String param2) {
        FragmentRegisterEvent fragment = new FragmentRegisterEvent();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setClickEvents();

    }

    private void setClickEvents() {
        MaterialButton DateBtn = binding.DateBtn;
        DateText = binding.textViewDate;
        DateBtn.setOnClickListener(e -> showDatePickerDialog());

        MaterialButton TimeBtn = binding.TimeBtn;
        TimeText = binding.textViewTime;
        TimeBtn.setOnClickListener(e -> showTimePickerDialog());
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c= Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                String time = format.format(c.getTime());
                TimeText.setText(time);
            }
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        CalendarConstraints.Builder constraintBuilder = setDatePickerConstraints();
        constraintBuilder.setValidator(DateValidatorPointForward.now());
        MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha del evento")
                .setCalendarConstraints(constraintBuilder.build());
        if(Date != null) datePickerBuilder.setSelection(Date.getTime());

        MaterialDatePicker materialDatePicker = datePickerBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                DateText.setText(""+materialDatePicker.getHeaderText());
                Date dateSelected = new Date((Long) materialDatePicker.getSelection());
                Date = new Date(dateSelected.getTime() + (1000 * 60 * 60 * 24));

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