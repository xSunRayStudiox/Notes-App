package com.sun_ray.srnotes.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.card.MaterialCardView;
import com.sun_ray.srnotes.Activity_Write_Notes;
import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.adapter.NoteAdapter;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.model.Note;

public class Notes_List extends Fragment {

    MaterialCardView create;
    ConstraintLayout NoData;
    RecyclerView recyclerView;
    NoteDatabase database;
    NoteAdapter adapter;
    List<Note> list;
    EditText searchView;

    private ActivityResultLauncher<String[]> permissionLauncher;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        Initialization_Class(view);

        // setupPermissionLauncher();

        // checkAndRequestPermissions();

        ShowNotes();

        SearchNotesClass();

        create.setOnClickListener(v -> {
            Intent i = new Intent(requireActivity(), Activity_Write_Notes.class);
            startActivity(i);
        });

        return view;
    }

    private void Initialization_Class(View view) {
        create = view.findViewById(R.id.add_not_fab);
        searchView = view.findViewById(R.id.search_filter);
        NoData = view.findViewById(R.id.noData);
        recyclerView = view.findViewById(R.id.rvNote);

        database = NoteDatabase.getDB(getContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
    }

    private void SearchNotesClass() {
        try {
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    // This is where you handle text change.
                    filterSearch(newText.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

        } catch (Exception error) {
            Log.d("Error", error.toString());
        }
    }

    private void filterSearch(String newText) {
        try {
            List<Note> filterList = new ArrayList<>();
            for (Note singleNote : list) {
                if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                        || singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                    filterList.add(singleNote);
                }
            }
            adapter.filterList(filterList);
        } catch (Exception e) {
            Log.d("Empty", e.toString());
        }
    }

    public void ShowNotes() {
        list = database.noteDao().getNote();
        if (!list.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            NoData.setVisibility(View.GONE);
            adapter = new NoteAdapter(this, getContext(), list, database);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        } else {
            NoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void setupPermissionLauncher() {
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    for (String permission : result.keySet()) {
                        if (Boolean.TRUE.equals(result.get(permission))) {
                            Log.d("Permission", permission + " granted.");
                        } else {
                            Log.d("Permission", permission + " denied.");
                            showSettingsDialog();
                        }
                    }
                }
        );
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toArray(new String[0]));
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    // Show a dialog to explain the need for permissions and redirect to settings
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app requires permissions to use certain features. You can grant them in app settings.");
        builder.setPositiveButton("Go to Settings", (dialog, which) -> {
            dialog.cancel();
            openAppSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}