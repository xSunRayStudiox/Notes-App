package com.sun_ray.srnotes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sun_ray.srnotes.Activity_Write_Notes;
import com.sun_ray.srnotes.R;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.fragment.Notes_List;
import com.sun_ray.srnotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Notes_List notes_list;
    Context context;
    List<Note> list = new ArrayList<>();
    NoteDatabase database;

    public NoteAdapter(Notes_List notes_list, Context context, List<Note> list, NoteDatabase database) {
        this.notes_list = notes_list;
        this.context = context;
        this.list = list;
        this.database = database;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note i = list.get(position);
        holder.title.setText(i.getTitle());
        holder.date.setText(i.getDate());
        holder.des.setText(i.getContent());

        // Retrieve Item Border Color Array
        TypedArray border = context.getResources().obtainTypedArray(R.array.dark_color);
        int[] borderArray = new int[border.length()];

        for (int ij = 0; ij < border.length(); ij++){
            borderArray[ij] = border.getColor(ij,0);
        }
        border.recycle();
        holder.borderColor.setBackgroundColor(borderArray[i.getColorIndex()]);

        // Retrieve Item Page Color Array
        TypedArray page = context.getResources().obtainTypedArray(R.array.color_picker);
        int[] pageArray = new int[page.length()];

        for (int j = 0; j < page.length(); j++){
            pageArray[j] = page.getColor(j,0);
        }
        page.recycle();
        holder.pageColor.setBackgroundColor(pageArray[i.getColorIndex()]);

        holder.btn.setOnClickListener(v -> {
            OpenNotes(v, i);
        });

        holder.btn.setOnLongClickListener(v -> {
            DeleteItem(i);
            return true;
        });
    }

    private void DeleteItem(Note i) {
        boolean a = true;
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are You Sure Want To Delete ?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Yes", (dialog1, which) -> {
                    database.noteDao().deleteNote(new Note(
                            i.getId(),
                            i.getTitle(),
                            i.getContent(),
                            i.getDate(),
                            i.getColorIndex(),
                            i.getImagePath()));
                    notes_list.ShowNotes();
                }).setNegativeButton("No", (dialog12, which) -> {}).show();
    }

    private void OpenNotes(View v, Note i) {
        try {
            Intent intent = new Intent(context, Activity_Write_Notes.class);
            intent.putExtra("Title",i.getTitle());
            intent.putExtra("Description",i.getContent());
            intent.putExtra("Index",i.getColorIndex());
            intent.putExtra("ID",i.getId());
            intent.putExtra("IMG",i.getImagePath());
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("Error" , e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Note> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView btn;
        View borderColor;
        MaterialTextView title, date;
        TextView des;
        LinearLayout pageColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.noteItem);
            borderColor = itemView.findViewById(R.id.BorderColor);
            title = itemView.findViewById(R.id.Title);
            date = itemView.findViewById(R.id.noteDate);
            des = itemView.findViewById(R.id.noteContent);
            pageColor = itemView.findViewById(R.id.PageColor);
        }
    }
}
