package com.devtaghreed.numberseries;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.devtaghreed.numberseries.databinding.ActivityLoginBinding;


public class Login_Activity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String username_login, password_login;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    public final String Password_key = "Password";
    public final String Username_key = "Username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Username & Password return
        ActivityResultLauncher<Intent> arl =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                binding.loginEtUsername.setText(result.getData().getStringExtra("Username"));
                                binding.loginEtPassword.setText(result.getData().getStringExtra("Password"));
                            }
                        });

        //Register
        binding.loginBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Register_Activity.class);
                arl.launch(intent);
            }
        });
        //Checkbox Checked
        binding.loginCbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                username_login = binding.loginEtUsername.getText().toString().trim();
                password_login = binding.loginEtPassword.getText().toString().trim();
                if ((password_login.isEmpty() || username_login.isEmpty())) {
                    Toast.makeText(getBaseContext(), "Please sign in", Toast.LENGTH_SHORT).show();
                } else {
                    if (compoundButton.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "true");
                        editor.apply();
                        Toast.makeText(Login_Activity.this, "Checked", Toast.LENGTH_LONG).show();
                    } else if (!compoundButton.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember", "false");
                        editor.apply();
                        Toast.makeText(Login_Activity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //checkbox valid
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("false")) {
            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        } else if (checkbox.equals("true")) {
            Intent intent = new Intent(getBaseContext(), Game_Activity.class);
            startActivity(intent);
            finish();
        }

        //Login
        binding.loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                edit = sp.edit();
                String username_sp = sp.getString(Username_key, "null");
                String password_sp = sp.getString(Password_key, "null");
                username_login = binding.loginEtUsername.getText().toString().trim();
                password_login = binding.loginEtPassword.getText().toString().trim();

                if (password_login.equals(password_sp) && username_login.equals(username_sp)
                        && !(password_login.isEmpty() || username_login.isEmpty())) {
                    Intent intent1 = new Intent(getBaseContext(), Game_Activity.class);
                    startActivity(intent1);
                    finish();
                } else if (username_login.isEmpty()) {
                    binding.loginEtUsername.setError("Please Enter your Username");
                    if (password_login.isEmpty()) {
                        binding.loginEtPassword.setError("Please Enter your Password");
                        Toast.makeText(getBaseContext(), "Please sign in", Toast.LENGTH_SHORT).show();
                    }
                } else if (!(username_login.equals(username_sp))) {
                    binding.loginEtUsername.setError("Username you entered  incorrect");
                } else if (!(password_login.equals(username_sp))) {
                    binding.loginEtPassword.setError("Password you entered  incorrect");
                }

            }
        });
    }

}