package com.example.carbonmongol;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

@SuppressWarnings("rawtypes")
public class Login extends AppCompatActivity {

    TextInputEditText editEmail, editPwd;
    Button btn;
    TextView clickToRegister;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.email);
        editPwd = findViewById(R.id.pwd);
        btn = findViewById(R.id.login_btn);
        clickToRegister = findViewById(R.id.click_to_register);
        progressBar = findViewById(R.id.progress_bar);

        clickToRegister.setOnClickListener(v -> Login.this.goToPage(Register.class));

        btn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.ashGray));

        btn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, pwd;
            email = String.valueOf(editEmail.getText());
            pwd = String.valueOf(editPwd.getText());

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)){
                Toast.makeText(Login.this, "Бүх хэсгийг бөглөнө үү", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                btn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.teaBrown));
            }

            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Login.this.goToPage(MainActivity.class);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            btn.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.ashGray));
                        }
                    });
        });
    }

    public void goToPage(Class sal){
        Intent intent = new Intent(getApplicationContext(), sal);
        startActivity(intent);
        finish();
    }
}