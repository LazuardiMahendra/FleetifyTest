package com.example.fleetifytest.view.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fleetifytest.R;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;
import com.example.fleetifytest.databinding.ActivityMainBinding;
import com.example.fleetifytest.view.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private List<String> typeNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getListVehicle().observe(this, new Observer<List<ListVehicleResponse.Vehicle>>() {
            @Override
            public void onChanged(List<ListVehicleResponse.Vehicle> vehicles) {
                if (vehicles != null) {
                    typeNameList.clear();
                    for (ListVehicleResponse.Vehicle vehicle : vehicles) {
                        typeNameList.add(vehicle.getType());
                    }
                }
            }
        });


        ArrayAdapter<String> adapterType = new ArrayAdapter<>(
                this, R.layout.item_list_text_dropdown, typeNameList
        );

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.til2.getEditText();
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapterType);
        }

    }


}