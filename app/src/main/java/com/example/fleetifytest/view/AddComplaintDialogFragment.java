package com.example.fleetifytest.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fleetifytest.R;
import com.example.fleetifytest.core.source.response.ComplaintResponse;
import com.example.fleetifytest.core.source.response.ListVehicleResponse;
import com.example.fleetifytest.databinding.DiaologAddComplaintBinding;
import com.example.fleetifytest.view.viewmodel.MainViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddComplaintDialogFragment extends DialogFragment {

    private DiaologAddComplaintBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private String userId = "P2Ye3WDnbNfpX8y";

    private MainViewModel mainViewModel;

    private List<String> vehicleList = new ArrayList<>();

    private Uri photoUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DiaologAddComplaintBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        setFormattedDateTime();
        getListVehicle();

        binding.btnPhoto.setOnClickListener(v -> showPhotoSelectionDialog());
    }

    private void setFormattedDateTime() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String dayOfWeek = dayFormat.format(calendar.getTime());
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());

        String formattedDateTime = dayOfWeek + ", " + date + " - " + time;

        binding.etDate.setText(formattedDateTime);
    }

    private void showPhotoSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Pilih Sumber Foto").setItems(new CharSequence[]{"Ambil Foto dengan Kamera", "Pilih Foto dari Gallery"}, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else {
                openGallery();
            }
        }).show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(getContext(), "com.example.fleetifytest.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else {
            requestCameraPermission();
        }
    }

    private File createImageFile() {
        try {
            String imageFileName = "JPEG_" + System.currentTimeMillis();
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                        binding.ivPhoto.setImageBitmap(selectedImageBitmap);
                        Log.d("TAG", "VALUE IMAGE : " + selectedImageBitmap + "  =  " + selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == TAKE_PHOTO_REQUEST) {
                if (photoUri != null) {
                    try {
                        Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);
                        binding.ivPhoto.setImageBitmap(selectedImageBitmap);
                        Log.d("TAG", "VALUE IMAGE : " + selectedImageBitmap + "  =  " + photoUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void getListVehicle() {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.getListVehicle().observe(getViewLifecycleOwner(), new Observer<List<ListVehicleResponse.Vehicle>>() {
            @Override
            public void onChanged(List<ListVehicleResponse.Vehicle> vehicles) {
                if (vehicles != null) {
                    vehicleList.clear();
                    for (ListVehicleResponse.Vehicle vehicle : vehicles) {
                        vehicleList.add(vehicle.getType());
                    }
                    setVehicleDropdown();
                }
            }
        });
    }

    private void setVehicleDropdown() {
        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(getContext(), R.layout.item_list_text_dropdown, vehicleList);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.tilDropdown.getEditText();
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapterVehicle);
        }
    }

    private void createComplaint(String vehicleId, String note, String userId, File photo) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.createComplaint(vehicleId, note, userId, photo).observe(getViewLifecycleOwner(), new
                Observer<ComplaintResponse>() {
                    @Override
                    public void onChanged(ComplaintResponse complaintResponse) {
                        if (complaintResponse.isStatus() == true) {
                            Toast.makeText(getContext(), "Laporan berhasil dibuat", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(getContext()).setMessage("Izin kamera diperlukan untuk mengambil foto.").setPositiveButton("OK", (dialog, which) -> {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }).setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Izin kamera diperlukan untuk menggunakan fitur ini.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
