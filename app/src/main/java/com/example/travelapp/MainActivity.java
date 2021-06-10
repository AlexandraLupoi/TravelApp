package com.example.travelapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button camera_open_id;
    ImageView click_image_id;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    RecyclerView locationRV;
    LocationAdapter adapter;
    ArrayList<LocationModal> locationModalArrayList;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap photo = (Bitmap)data.getExtras().get("data");
                        click_image_id.setImageBitmap(photo);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera_open_id = (Button)findViewById(R.id.camera_button);
        click_image_id = (ImageView)findViewById(R.id.click_image);

        camera_open_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(camera_intent);
            }
        });

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        locationRV = findViewById(R.id.idRVLocations);
        buildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {
        ArrayList<LocationModal> filteredlist = new ArrayList<>();

        for(LocationModal item : locationModalArrayList) {
            if(item.getLocationName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if(filteredlist.isEmpty()) {
            Toast.makeText(this, "No location with this name...", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {
        locationModalArrayList = new ArrayList<>();

        locationModalArrayList.add(new LocationModal("Turkey", "Come to visit Turkey."));
        locationModalArrayList.add(new LocationModal("France", "Come to visit France."));
        locationModalArrayList.add(new LocationModal("Spain", "Come to visit Spain."));
        locationModalArrayList.add(new LocationModal("Greece", "Come to visit Greece."));
        locationModalArrayList.add(new LocationModal("Japan", "Come to visit Japan."));

        adapter = new LocationAdapter(locationModalArrayList, MainActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        locationRV.setHasFixedSize(true);
        locationRV.setLayoutManager(manager);
        locationRV.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.share_button:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Turkey, France, Spain, Greece, Japan";
                String shareSubject = "Check out this awesome list with locations to visit!";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
