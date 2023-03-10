package com.example.apparty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.apparty.databinding.FragmentFilterDialogBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.RangeSlider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilterDialogFragment extends DialogFragment {

    private FragmentFilterDialogBinding binding;
    private TextView fromDateText;
    private TextView toDateText;
    private Date fromDate;
    private Date toDate;

    private double minPrice;
    private double maxPrice;
    private RangeSlider rangePrice;

    private GestorEvent gestorEvent;

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
        gestorEvent = GestorEvent.getInstance(this.getContext());
        setRangeSlider();
        setDresscodeOptions();
        setClickEvents();

    }

    private void setDresscodeOptions() {
        ChipGroup chipGroup = binding.chipGroup;
        List<DressCode> dressCodes = gestorEvent.getDressCodeList();
        for(DressCode d : dressCodes){
            Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(),
                    null,
                    0,
                    R.style.FilterChip);
            chip.setChipDrawable(chipDrawable);
            chip.setText(d.getDressCode());
            chip.setId(d.getId());
            chip.setCheckedIconEnabled(true);
            chipGroup.addView(chip);
        }
    }

    private List<Integer> getCheckedChips(){
        ChipGroup chipGroup = binding.chipGroup;
        return chipGroup.getCheckedChipIds();
    }

    private void setRangeSlider() {
        minPrice = gestorEvent.getMinPrice();
        maxPrice = gestorEvent.getMaxPrice();
        rangePrice = binding.rangeSlider;
        rangePrice.setValues((float) minPrice, (float)maxPrice);
        rangePrice.setValueFrom((float) minPrice);
        rangePrice.setValueTo((float) maxPrice);
        binding.maxPrice.setText("$"+Double.toString(maxPrice));
        binding.minPrice.setText("$"+Double.toString(minPrice));
    }

    private void setClickEvents() {
        MaterialButton fromDateBtn = binding.fromDateBtn;
        fromDateText = binding.fromDate;
        fromDateBtn.setOnClickListener(e -> showFromDatePickerDialog());

        MaterialButton toDateBtn = binding.toDateBtn;
        toDateText = binding.toDate;
        toDateBtn.setOnClickListener(e -> showToDatePickerDialog());

        binding.applyBtn.setOnClickListener(e -> applyFilters());
        binding.deleteBtn.setOnClickListener(e -> resetFilters());
    }

    private void resetFilters() {
        rangePrice = binding.rangeSlider;
        rangePrice.setValues((float) minPrice, (float)maxPrice);
        binding.chipGroup.clearCheck();
        binding.fromDate.setText("");
        binding.toDate.setText("");
        fromDate = null;
        toDate = null;
        binding.toDateBtn.setEnabled(fromDate != null);
    }

    private void applyFilters() {
        double minPriceSelected = binding.rangeSlider.getValues().get(0);
        double maxPriceSelected = binding.rangeSlider.getValues().get(1);

        List<Integer> dresscodeIds = getCheckedChips();

        String fromDateString = "";
        String toDateString = "";
        if(fromDate != null){
            fromDateString = new SimpleDateFormat("MM/dd/yyyy").format(fromDate);
        }
        if(toDate != null){
            toDateString = new SimpleDateFormat("MM/dd/yyyy").format(toDate);
        }

        Filter filters = new Filter(minPriceSelected, maxPriceSelected, fromDateString, toDateString, dresscodeIds);
        sendFilters(filters);
        getDialog().dismiss();
    }

    private void sendFilters(Filter filters){
        Bundle bundle = new Bundle();
        String filterJsonString = Utils.getGsonParser().toJson(filters);

        bundle.putString("filters", filterJsonString);
        getParentFragmentManager().setFragmentResult("requestFilter", bundle);
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