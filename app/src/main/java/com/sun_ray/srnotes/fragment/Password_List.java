package com.sun_ray.srnotes.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.adapter.PassAdapter;
import com.sun_ray.srnotes.db.PassDatabase;
import com.sun_ray.srnotes.model.Password;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Password_List extends Fragment {

    AppBarLayout appBarLayout;
    RecyclerView recyclerView;
    CardView Add_Pass;
    MaterialCardView create;
    ConstraintLayout NoData;

    TextView setDate;
    EditText pass_Title, pass_id, pass_pass, searchView;
    Button Save, Cancel;

    String get_Title, get_id, get_pass, date;
    PassDatabase database;
    PassAdapter adapter;
    List<Password> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        appBarLayout = view.findViewById(R.id.app_bar_layout);
        create = view.findViewById(R.id.add_not_fab);
        NoData = view.findViewById(R.id.noData);
        recyclerView = view.findViewById(R.id.rvPassword);
        Add_Pass = view.findViewById(R.id.add_password);
        searchView = view.findViewById(R.id.search_filter);

        setDate = view.findViewById(R.id.pass_date);
        pass_Title = view.findViewById(R.id.password_title);
        pass_id = view.findViewById(R.id.password_id);
        pass_pass = view.findViewById(R.id.password_password);

        Save = view.findViewById(R.id.save);
        Cancel = view.findViewById(R.id.cancel);

        // get Date
        date = new SimpleDateFormat("dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
        setDate.setText(date);

        database = PassDatabase.getDB(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        create.setOnClickListener(v -> {
            Add_Pass.setVisibility(View.VISIBLE);
            appBarLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            create.setVisibility(View.GONE);
            NoData.setVisibility(View.GONE);
        });

        Save.setOnClickListener(v -> {
            SavePass();
        });

        Cancel.setOnClickListener(v -> {
            Add_Pass.setVisibility(View.GONE);
            appBarLayout.setVisibility(View.VISIBLE);
            create.setVisibility(View.VISIBLE);
            ShowPass();
        });

        ShowPass();

        // Searching
        try {
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    filterSearch(newText.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });

        } catch (Exception error) {
            Log.d("Error", error.toString());}

        return view;
    }

    private void SavePass() {
        get_Title = pass_Title.getText().toString();
        get_id = pass_id.getText().toString();
        get_pass = pass_pass.getText().toString();

        if (get_Title.isEmpty() ){
            Toast.makeText(getContext(), "Empty Heading", Toast.LENGTH_SHORT).show();
        }
        else if (get_id.isEmpty()) {
            Toast.makeText(getContext(), "Empty ID", Toast.LENGTH_SHORT).show();
        }
        else if (get_pass.isEmpty()) {
            Toast.makeText(getContext(), "Empty Password", Toast.LENGTH_SHORT).show();
        }
        else {
            // Save
            database.passDao().addPass(new Password(get_Title,date, get_id, get_pass));
            pass_Title.getText().clear();
            pass_id.getText().clear();
            pass_pass.getText().clear();

            Add_Pass.setVisibility(View.GONE);
            create.setVisibility(View.VISIBLE);
            appBarLayout.setVisibility(View.VISIBLE);
            ShowPass();
        }
    }

    public void ShowPass() {
        list = database.passDao().getPass();
        if (!list.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            NoData.setVisibility(View.GONE);
            adapter = new PassAdapter(this, getContext(), list, database);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        } else {
            NoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void filterSearch(String newText) {
        try {
            List<Password> filterList = new ArrayList<>();
            for (Password singleNote : list) {
                if (singleNote.getHeading().toLowerCase().contains(newText.toLowerCase())) {
                    filterList.add(singleNote);
                }
            }
            adapter.filterList(filterList);
        } catch (Exception e) {
            Log.d("Empty", e.toString());
        }
    }
}