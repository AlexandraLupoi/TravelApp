package com.example.travelapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started");

        TextView titleTextView = (TextView) findViewById(R.id.textView);
        titleTextView.setText("HOLIDAY");

        TextView comment1 = (TextView) findViewById(R.id.textView2);
        comment1.setText("Ana: a fost o excursie exceptionala! Ne vom intoarce si la anul!");
        TextView comment2 = (TextView) findViewById(R.id.textView3);
        comment2.setText("Loredana: un loc de neuitat!");

    }

}
