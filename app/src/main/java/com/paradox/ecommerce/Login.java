package com.paradox.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {
    private EditText user_name, pass_word;
    FirebaseAuth mAuth;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        user_name=findViewById(R.id.email);
        pass_word=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        logo=findViewById(R.id.logo);
        YoYo.with(Techniques.RubberBand).duration(1500).repeat(100).playOn(logo);
    }

    public void onClick(View v){
    if (v.getId()==R.id.btn_login){
        String email= user_name.getText().toString().trim();
        String password=pass_word.getText().toString().trim();
        if(email.isEmpty())
        {
            user_name.setError("Email is empty");
            user_name.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            user_name.setError("Enter the valid email");
            user_name.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            pass_word.setError("Password is empty");
            pass_word.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            pass_word.setError("Length of password is more than 6");
            pass_word.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
            else
            {
                Toast.makeText(Login.this,
                        "Please Check Your login Credentials",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
        else if(v.getId()==R.id.btn_signup)
        startActivity(new Intent(Login.this,Registration.class ));

    }
}