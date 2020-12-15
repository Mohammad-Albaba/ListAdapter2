package com.example.ListAdapterNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        ArrayList<Integer> photos = new ArrayList<>();
        photos.add(R.drawable.a1);
        photos.add(R.drawable.a2);
        photos.add(R.drawable.a3);
        photos.add(R.drawable.a4);
        photos.add(R.drawable.a5);
        photos.add(R.drawable.a6);
        photos.add(R.drawable.a7);
        photos.add(R.drawable.a8);
        photos.add(R.drawable.a9);
        photos.add(R.drawable.a10);
        photos.add(R.drawable.a11);
        photos.add(R.drawable.a12);
        photos.add(R.drawable.a13);
        photos.add(R.drawable.a14);
        photos.add(R.drawable.a15);

        GridView listView = findViewById(R.id.adapterView);
        GridAdapter gridAdapter = new GridAdapter(photos);
        listView.setAdapter(gridAdapter);
    }
}