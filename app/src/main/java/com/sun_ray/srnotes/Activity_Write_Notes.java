package com.sun_ray.srnotes;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.sun_ray.srnotes.db.NoteDatabase;
import com.sun_ray.srnotes.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_Write_Notes extends AppCompatActivity {

    ImageView back_Button, save_Note, fabColor_Pick, noteImage, addImageButton;

    static final int REQUEST_IMAGE_PICK = 1;
    Uri imageUri;

    LinearLayout Screen_change;
    int[] colorArray;
    int ArgID, ArgIndex, colorIndex = 0;
    TypedArray typedArrayColor;

    float current_Rotation = 0f;
    RotateAnimation rotateAnimation;

    EditText et_Title, et_Content;
    TextView set_date;
    String date, Title, Des, ArgTitle, ArgDes, img;

    private NoteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);

        // initialize
        InitializationClass();

        back_Button.setOnClickListener(v -> Back());

        addImageButton.setOnClickListener(v -> OpenImagePicker());

        // Retrieve Colour Array
        RetrieveColourArray();

        // Set Date
        set_date.setText(date);

        // Get Argument
        GetArgument();

        // color set
        Screen_change.setBackgroundColor(colorArray[colorIndex]);

        fabColor_Pick.setOnClickListener(v -> {
            Rotate_Animation();
        });

        // Save Data
        save_Note.setOnClickListener(v -> {
            SaveNotes();
        });
    }

    private void GetArgument() {
        ArgTitle = getIntent().getStringExtra("Title");
        ArgDes = getIntent().getStringExtra("Description");
        ArgID = getIntent().getIntExtra("ID", 0);
        ArgIndex = getIntent().getIntExtra("Index", 1);
        img = getIntent().getStringExtra("IMG");

        // Set Data
        if (ArgTitle != null) {
            et_Title.setText(ArgTitle);
            et_Content.setText(ArgDes);
            colorIndex = ArgIndex;

            try {
                if (img != null && !img.isEmpty()) {
                    noteImage.setVisibility(View.VISIBLE);

                    // Load image using Glide
                    Glide.with(this)
                            .load(img)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(noteImage);
                }
            } catch (Exception e) {
                Log.d("Error", "Error loading image: " + e.toString());
            }
        }
    }

    private void RetrieveColourArray() {

        typedArrayColor = getResources().obtainTypedArray(R.array.color_picker);
        colorArray = new int[typedArrayColor.length()];

        for (int i = 0; i < typedArrayColor.length(); i++) {
            colorArray[i] = typedArrayColor.getColor(i, 0);
        }
        typedArrayColor.recycle();
    }

    private void InitializationClass() {
        back_Button = findViewById(R.id.backButton);
        addImageButton = findViewById(R.id.addImageButton);

        fabColor_Pick = findViewById(R.id.fabColorPick);
        Screen_change = findViewById(R.id.noteContentFragmentParent);

        save_Note = findViewById(R.id.saveNote);
        database = NoteDatabase.getDB(this);

        noteImage = findViewById(R.id.noteImage);
        et_Title = findViewById(R.id.etTitle);
        et_Content = findViewById(R.id.etContent);

        // get Date
        date = new SimpleDateFormat("dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
        set_date = findViewById(R.id.get_date);
    }

    private void Get_Data() {
        Title = et_Title.getText().toString();
        Des = et_Content.getText().toString();
    }

    private void SaveNotes() {
        Get_Data();
        if (!Des.isEmpty()) {
            String imagePath = (imageUri != null) ? imageUri.toString() : null;

            if (ArgTitle != null) {
                // Update
                database.noteDao().updateNote(new Note(ArgID, Title, Des, date, colorIndex, imagePath));
                Back();
            } else {
                // Add
                database.noteDao().addNote(new Note(Title, Des, date, colorIndex, imagePath));
                Back();
            }
        }
    }

    private void OpenImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageUri = data.getData();
                noteImage.setVisibility(View.VISIBLE);
                noteImage.setImageURI(imageUri);
            }
        }
    }

    private void Back() {
        Intent i = new Intent(getApplicationContext(), Activity_Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void Change_Color() {
        colorIndex++;
        if (colorIndex >= colorArray.length) {
            colorIndex = 0;
        }
        Screen_change.setBackgroundColor(colorArray[colorIndex]);
    }

    private void Rotate_Animation() {
        current_Rotation += 120f;
        rotateAnimation = new RotateAnimation(
                current_Rotation - 120f,
                current_Rotation,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        fabColor_Pick.startAnimation(rotateAnimation);
        Change_Color();
    }
}