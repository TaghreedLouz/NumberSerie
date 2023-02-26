package com.devtaghreed.numberseries;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.devtaghreed.numberseries.databinding.ActivityRegisterBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Register_Activity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    public String Email, Password, Re_password, Full_name, Username, Birthdate, SpCountry;
    public int Age;
    public final String Age_key = "Age";
    public final String Email_key = "Email";
    public final String Password_key = "Password";
    public final String Re_password_key = "Re_password";
    public final String Full_name_key = "Full_name";
    public final String Username_key = "Username";
    public final String Birthdate_key = "Birthdate";
    public final String SpCountry_key = "SpCountry";
    public final String Gender_key = "Gender";
    public final String Photo_key = "Photo";
    public Uri uri;
    public Bitmap uri1;

    ActivityResultLauncher<String> permission;

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        edit = sp.edit();


        //Image profile Gallery
        ActivityResultLauncher<String> Gallery =
                registerForActivityResult(new ActivityResultContracts.GetContent(),
                        new ActivityResultCallback<Uri>() {
                            @Override
                            public void onActivityResult(Uri result) {
                                binding.registarImgProfile.setImageURI(result);
                                uri = result;
                            }
                        });

        //Image profile Camera
        ActivityResultLauncher<Void> Camera =
                registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                        new ActivityResultCallback<Bitmap>() {
                            @Override
                            public void onActivityResult(Bitmap result) {
                                binding.registarImgProfile.setImageBitmap(result);
                                uri1 = result;
                            }
                        });

        binding.registarImgProfile.setImageURI(uri);


        //permission
        permission = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            Toast.makeText(Register_Activity.this, "permission obtained", Toast.LENGTH_SHORT).show();
                            binding.registarImgProfile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register_Activity.this);
                                    builder.setTitle("Get A Picture")
                                            .setMessage("Where do you want to put your profile picture?")
                                            .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Gallery.launch("image/*");
                                                }
                                            }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Camera.launch(null);
                                        }
                                    }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(Register_Activity.this, "Canceled", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }
                            });
                        } else {
                            Toast.makeText(Register_Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        permission.launch("android.permission.CAMERA");
        permission.launch("android.permission.READ_EXTERNAL_STORAGE");

        //date picker
        binding.registarEtBdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        binding.registarEtBdate.setText(String.valueOf(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                        Age = now.get(Calendar.YEAR) - year;
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getSupportFragmentManager(), " null ");
            }
        });

        //Register info
        binding.registarBtnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                Email = binding.registarEtEmail.getText().toString().trim();
                Password = binding.registarEtPassword.getText().toString().trim();
                Re_password = binding.registarEtRepassword.getText().toString().trim();
                Full_name = binding.registarEtFullname.getText().toString().trim();
                Username = binding.registarEtUsrname.getText().toString().trim();
                Birthdate = binding.registarEtBdate.getText().toString().trim();
                SpCountry = binding.registarSpCountry.getSelectedItem().toString();

                //Gender
                int Gender = sp.getInt(Gender_key, 3);
                if (Gender == 1) {
                    binding.registarRbFemale.setChecked(true);

                } else if (Gender == 0) {
                    binding.registarRbMale.setChecked(true);
                }

                binding.registarRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i == R.id.registar_rb_Female) {
                            edit.putInt(Gender_key, 1);

                        } else if (i == R.id.registar_rb_male) {
                            edit.putInt(Gender_key, 0);
                        }
                        edit.apply();
                    }
                });


                //EditText Validation
                if (Full_name.isEmpty()) {
                    binding.registarEtFullname.setError("Please Enter your Name");
                    binding.registarEtFullname.requestFocus();
                } else if (Email.isEmpty()) {
                    binding.registarEtEmail.setError("Please Enter your Email Address");
                    binding.registarEtEmail.requestFocus();
                    return;
                } else if (!(Email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    binding.registarEtEmail.setError("Invalid Email Address");
                    binding.registarEtEmail.requestFocus();
                    return;
                } else if (Username.isEmpty()) {
                    binding.registarEtUsrname.setError("Please Enter your UserName");
                    binding.registarEtUsrname.requestFocus();
                    return;
                } else if (Password.isEmpty()) {
                    binding.registarEtPassword.setError("Please Enter Password");
                    binding.registarEtPassword.requestFocus();
                    return;
                } else if (Password.length() <= 6) {
                    binding.registarEtPassword.setError("Please Enter Password Minimum 6 Char");
                    binding.registarEtPassword.requestFocus();
                    return;
                } else if (!Re_password.equals(Password)) {
                    binding.registarEtRepassword.setError("Make sure the password matches");
                    binding.registarEtRepassword.requestFocus();
                } else if (Birthdate.isEmpty()) {
                    binding.registarEtBdate.setError("Select your Birthdate");
                    binding.registarEtBdate.requestFocus();
                    return;
                } else {
                    edit.putString(Full_name_key, Full_name);
                    edit.putString(Username_key, Username);
                    edit.putString(Email_key, Email);
                    edit.putString(Password_key, Password);
                    edit.putString(Re_password_key, Re_password);
                    edit.putString(Birthdate_key, Birthdate);
                    edit.putString(SpCountry_key, SpCountry);
                    edit.putInt(Age_key, Age);
                    edit.putString(Photo_key, String.valueOf(uri));
                    edit.putString(Photo_key, String.valueOf(uri1));
                    edit.apply();
                    Intent intent = new Intent();
                    intent.putExtra("Username", Username);
                    intent.putExtra("Password", Password);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });

    }
}
