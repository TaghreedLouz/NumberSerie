package com.devtaghreed.numberseries;

import static java.util.Calendar.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devtaghreed.numberseries.databinding.ActivityGameBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Game_Activity extends AppCompatActivity {
    ActivityGameBinding binding;

    public SharedPreferences sp;
    SharedPreferences.Editor edit;
    public final String Full_name_key = "Full_name";
    public final String Age_key = "Age";

    App_DataBase DB;
    Game game;
    public static int hid_num, old_score, new_score;
    String date, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Toolbar
        setSupportActionBar(binding.toolbar);
        setTitle("Number Series");

        FillData();

        //name & age
        sp = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        edit = sp.edit();
        String playername_sp = sp.getString(Full_name_key, "null");
        int playerage_sp = sp.getInt(Age_key, 0);
        binding.gameTvPlayername.setText(playername_sp);
        binding.gameTvPlayerage.setText(String.valueOf("[" + playerage_sp + "]"));


        //sound effect
        final MediaPlayer mediaPlayer_right = MediaPlayer.create(Game_Activity.this, R.raw.right_answer);
        final MediaPlayer mediaPlayer_false = MediaPlayer.create(Game_Activity.this, R.raw.false_answer);

        //Check
        binding.gameBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.gameEtAnswer.getText().toString().isEmpty()) {
                    binding.gameEtAnswer.setError("Enter The Number");
                    binding.gameEtAnswer.requestFocus();
                } else {if (hid_num == Integer.parseInt(binding.gameEtAnswer.getText().toString())) {
                    //custom_toast_true
                    View view1 = LayoutInflater.from(Game_Activity.this).inflate(R.layout.custom_toast_true, null, false);
                    TextView tv_true = view1.findViewById(R.id.tv_ctoast_true);
                    ImageView img = view1.findViewById(R.id.img_ctoast_true);
                    tv_true.setText(R.string.coast_true);
                    Toast toast_true = new Toast(Game_Activity.this);
                    toast_true.setView(view1);
                    toast_true.setDuration(Toast.LENGTH_LONG);
                    toast_true.setGravity(Gravity.CENTER | Gravity.BOTTOM, 10, 0);
                    toast_true.show();
                    //score
                    old_score = Integer.parseInt(binding.gameTvScoreNum.getText().toString());
                    new_score = old_score + 10;
                    binding.gameTvScoreNum.setText(String.valueOf(new_score));
                    //clear EditText
                    binding.gameEtAnswer.setText("");
                    FillData();
                    //start sound_effect
                    mediaPlayer_right.start();
                } else {
                    //custom_toast_false
                    View view1 = LayoutInflater.from(Game_Activity.this).inflate(R.layout.custom_toast_false, null, false);
                    TextView tv_toast = view1.findViewById(R.id.tv_ctoast_false);
                    ImageView img = view1.findViewById(R.id.img_ctoast_true);
                    tv_toast.setText(R.string.coast_false);
                    Toast toast_false = new Toast(Game_Activity.this);
                    toast_false.setView(view1);
                    toast_false.setDuration(Toast.LENGTH_LONG);
                    toast_false.setGravity(Gravity.CENTER | Gravity.BOTTOM, 10, 0);
                    toast_false.show();
                    //start sound_effect
                    mediaPlayer_false.start();
                    //clear EditText
                    binding.gameEtAnswer.setText("");
                }
                Database();
            }}
        });

        DB = new App_DataBase(Game_Activity.this);
        new_score = DB.select_score();
        binding.gameTvScoreNum.setText(String.valueOf(new_score));

        binding.gameBtnNewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillData();
            }
        });

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sittings:
                Intent intent = new Intent(getApplicationContext(), Settings_Activity.class);
                startActivity(intent);
                return true;
            case R.id.log_out:
                DB = new App_DataBase(Game_Activity.this);
                Toast.makeText(this, "logout checked", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.clear();
                editor.apply();
                edit.clear();
                edit.apply();
                DB.destroy_game();
                Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent2);
                finish();
                return true;
        }
        return false;
    }

    public void FillData() {
        Question question = num_processing.generateQuestion();
        String[][] q = question.getData().clone();
        hid_num = question.getHiddenNumber();
        binding.gameTvNum1.setText(q[0][0]);
        binding.gameTvNum2.setText(q[0][1]);
        binding.gameTvNum3.setText(q[0][2]);

        binding.gameTvNum4.setText(q[1][0]);
        binding.gameTvNum5.setText(q[1][1]);
        binding.gameTvNum6.setText(q[1][2]);

        binding.gameTvNum7.setText(q[2][0]);
        binding.gameTvNum8.setText(q[2][1]);
        binding.gameTvNum9.setText(q[2][2]);
    }

    public void Database() {
        DB = new App_DataBase(Game_Activity.this);
        date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        name = binding.gameTvPlayername.getText().toString();
        new_score = Integer.parseInt(binding.gameTvScoreNum.getText().toString());
        game = new Game(name, date, new_score);
        DB.insert_game(game);
    }
}
