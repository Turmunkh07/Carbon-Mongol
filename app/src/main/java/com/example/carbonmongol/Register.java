package com.example.carbonmongol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

@SuppressWarnings("rawtypes")
public class Register extends AppCompatActivity {

    TextView editEmail, editPwd, editPwd2;
    Button btn;
    ProgressBar progressBar;
    TextView clickToLogin;
    FirebaseAuth mAuth;
    public String email, pwd, pwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmail = findViewById(R.id.email);
        editPwd = findViewById(R.id.pwd);
        editPwd2 = findViewById(R.id.pwd2);
        btn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progress_bar);
        clickToLogin = findViewById(R.id.click_to_login);
        mAuth = FirebaseAuth.getInstance();

        btn.setBackgroundColor(ContextCompat.getColor(Register.this, R.color.ashGray));

        clickToLogin.setOnClickListener(v -> Register.this.goToPage(Login.class));

        btn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            email = String.valueOf(editEmail.getText());
            pwd = String.valueOf(editPwd.getText());
            pwd2 = String.valueOf(editPwd2.getText());

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd) && TextUtils.equals(pwd, pwd2)){
                Toast.makeText(Register.this, "Бүх хэсгийг бөглөнө үү", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            } else {
                btn.setBackgroundColor(ContextCompat.getColor(Register.this, R.color.teaBrown));
            }


            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(task -> {
                        if (pwd.equals(pwd2)) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registered successfully",
                                        Toast.LENGTH_SHORT).show();
                                Register.this.goToPage(MainActivity.class);
                            } else {
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                btn.setBackgroundColor(ContextCompat.getColor(Register.this, R.color.ashGray));
                            }
                        }
                    });
        });
    }

    public void goToPage(Class cal){
        Intent intent = new Intent(getApplicationContext(), cal);
        startActivity(intent);
        finish();
    }
}