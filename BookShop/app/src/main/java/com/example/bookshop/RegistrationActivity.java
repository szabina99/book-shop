package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.acl.Group;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = RegistrationActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 123;

    EditText ETUserName;
    EditText ETEmail;
    EditText ETPassword;
    EditText ETPasswordAgain;
    EditText ETAdress;
    RadioGroup roleGroup;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secret_key = getIntent().getIntExtra("SECRET_KEY",0);

        if(secret_key != 123){
            finish();
        }

        ETUserName = findViewById(R.id.ETUserName);
        ETEmail = findViewById(R.id.ETEmail);
        ETPassword = findViewById(R.id.ETPassword);
        ETPasswordAgain = findViewById(R.id.ETPasswordAgain);
        ETAdress = findViewById(R.id.ETAdress);
        roleGroup = findViewById(R.id.role);
        roleGroup.check(R.id.customer);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG,"onCreate");

    }

    public void register(View view) {
        String userName = ETUserName.getText().toString();
        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();
        String passwordAgain = ETPasswordAgain.getText().toString();


        if(!password.equals(passwordAgain)){
            Log.e(LOG_TAG, "A jelszó nem egyezik!");
            return;
        }

        String adress = ETAdress.getText().toString();
        int checkRadioId = roleGroup.getCheckedRadioButtonId();
        RadioButton radioButton = roleGroup.findViewById(checkRadioId);
        String roleType = radioButton.getText().toString();

        Log.i(LOG_TAG, "Regisztrált: " + userName + " Email: " + email + " Cim: "+ adress);


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User created succesfully!");
                    startShopping();
                }else{
                    Toast.makeText(RegistrationActivity.this, "User wasn't created succesfully: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    public void startShopping(){
        Intent intent = new Intent(this, ShopListActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",ETEmail.getText().toString());
        editor.apply();

        Log.i(LOG_TAG,"onPause");
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}