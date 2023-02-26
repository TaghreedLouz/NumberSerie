package com.devtaghreed.numberseries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.devtaghreed.numberseries.databinding.ActivityAllGameBinding;

import java.util.ArrayList;

public class AllGame_Activity extends AppCompatActivity {
    ActivityAllGameBinding binding;
    App_DataBase DB;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar3);
        setTitle(" ");

        recyclerView = findViewById(R.id.rv);
        DB = new App_DataBase(this);
        ArrayList<Game> games = DB.select_all_game();
        App_Recycler_Adapter adapter = new App_Recycler_Adapter(games, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(adapter);

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "logout checked", Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();
        Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(intent2);
        finish();
        return true;
    }
}