package com.devtaghreed.numberseries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.devtaghreed.numberseries.databinding.ActivitySettingsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

public class Settings_Activity extends AppCompatActivity {
    ActivitySettingsBinding binding;

    String currant_password, new_password, confirm_password;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    public final String Password_key = "Password";

    App_DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar2);
        setTitle("Number Series");

        //Show All
        binding.settingsBtnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AllGame_Activity.class);
                startActivity(intent);
            }
        });
        //Change Password
        binding.settingsBtnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cancel
                binding.settingsBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.settingsEtNewpass.setText("");
                        binding.settingsEtCurrentpass.setText("");
                        binding.settingsEtConfirmpass.setText("");
                        binding.layChangePassword.setVisibility(View.GONE);
                        binding.settingsBtnChangePassword.setVisibility(View.VISIBLE);
                    }
                });

                binding.layChangePassword.setVisibility(View.VISIBLE);
                binding.settingsBtnChangePassword.setVisibility(View.GONE);

                //Change
                binding.settingsBtnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sp = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                        edit = sp.edit();
                        String password_sp = sp.getString(Password_key, "null");
                        new_password = binding.settingsEtNewpass.getText().toString().trim();
                        currant_password = binding.settingsEtCurrentpass.getText().toString().trim();
                        confirm_password = binding.settingsEtConfirmpass.getText().toString().trim();

                        if (currant_password.isEmpty()) {
                            binding.settingsEtCurrentpass.setError("Please Enter Password");

                        } else if (!(currant_password.equals(password_sp))) {
                            binding.settingsEtCurrentpass.setError("its not the currant password");

                        } else if (new_password.isEmpty()) {
                            binding.settingsEtNewpass.setError("Please Enter Password");

                        } else if (new_password.length() <= 6) {
                            binding.settingsEtNewpass.setError("Enter password > 6 char, please");
                            binding.settingsEtNewpass.requestFocus();

                        } else if (currant_password.equals(new_password)) {
                            binding.settingsEtNewpass.setError("please Enter Different password");
                            binding.settingsEtNewpass.requestFocus();

                        } else if (confirm_password.isEmpty()) {
                            binding.settingsEtConfirmpass.setError("please Enter Confirm password");
                            binding.settingsEtConfirmpass.requestFocus();
                        } else if (!(confirm_password.equals(new_password))) {
                            binding.settingsEtConfirmpass.setError("Does’t match the new password");

                        } else if (currant_password.equals(password_sp)) {
                            if (confirm_password.equals(new_password)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Settings_Activity.this);
                                builder.setTitle("Confirm Change Password?")
                                        .setMessage("Are you sure you want to change the password?")
                                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                edit.putString(Password_key, new_password);
                                                edit.apply();
                                                Snackbar.make(binding.getRoot(), "the password has been changed", Snackbar.LENGTH_LONG).show();
                                                binding.layChangePassword.setVisibility(View.GONE);
                                                binding.settingsEtNewpass.setText("");
                                                binding.settingsEtCurrentpass.setText("");
                                                binding.settingsEtConfirmpass.setText("");
                                                binding.settingsBtnChangePassword.setVisibility(View.VISIBLE);
                                            }
                                        }).setNegativeButton("Un Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        binding.settingsEtNewpass.setText("");
                                        binding.settingsEtCurrentpass.setText("");
                                        binding.settingsEtConfirmpass.setText("");
                                        binding.layChangePassword.setVisibility(View.GONE);
                                        binding.settingsBtnChangePassword.setVisibility(View.VISIBLE);
                                        Toast.makeText(Settings_Activity.this, "The Password is’t Changed", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Settings_Activity.this, "Canceled", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } else if (!(confirm_password.equals(new_password))) {
                            binding.settingsEtConfirmpass.setError("Does’t match the new password");

                        }
                    }
                });
            }
        });

        //Show Last
        binding.settingsBtnShowLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB = new App_DataBase(getBaseContext());
                Toast.makeText(Settings_Activity.this, DB.select_date(), Toast.LENGTH_SHORT).show();
            }
        });
        //Clear Game Destroy
        binding.settingsBtnCleargame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings_Activity.this);
                builder.setTitle("Confirm Delete?")
                        .setMessage("Are you sure you want to delete the data?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DB = new App_DataBase(getBaseContext());
                                DB.destroy_game();
                                Snackbar.make(binding.getRoot(), "All data has been cleared", Snackbar.LENGTH_LONG).show();

                            }
                        }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Settings_Activity.this, "Canceled , The Data is’t Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout_back, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DB = new App_DataBase(Settings_Activity.this);
        Toast.makeText(this, "logout checked", Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.clear();
        editor.apply();
        Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(intent2);
        finish();
        return true;
    }

}