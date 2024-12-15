package com.example.fleetifytest.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;
import com.example.fleetifytest.databinding.ActivityMainBinding;
import com.example.fleetifytest.view.adapter.ComplaintAdapter;
import com.example.fleetifytest.view.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private List<String> typeNameList = new ArrayList<>();

    private String userId = "P2Ye3WDnbNfpX8y";

    private ComplaintAdapter complaintAdapter = new ComplaintAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.rvMain;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintAdapter);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        mainViewModel.getAllComplaint(userId).observe(this, new Observer<List<ListAllComplaintResponse.Complaint>>() {
            @Override
            public void onChanged(List<ListAllComplaintResponse.Complaint> complaintList) {
                if (complaintList != null) {
                    complaintAdapter.setComplaintList(complaintList);
                }
            }
        });
//

//
//
//        ArrayAdapter<String> adapterType = new ArrayAdapter<>(
//                this, R.layout.item_list_text_dropdown, typeNameList
//        );
//
//        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.til2.getEditText();
//        if (autoCompleteTextView != null) {
//            autoCompleteTextView.setAdapter(adapterType);
//        }
//
//    }

    }
}