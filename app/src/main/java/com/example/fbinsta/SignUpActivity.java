package com.example.fbinsta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {

        private EditText userNameInput;
        private EditText passwordInput;
        private Button loginBtn;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            userNameInput = findViewById(R.id.userName_et);
            passwordInput = findViewById(R.id.password_et);
            loginBtn = findViewById(R.id.login_btn);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = userNameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    login(username,password);
                }

            });

        }

        private void login(String username, String password) {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.d("LoginActivity", "Login successful");
                        final Intent intent = new Intent (com.example.fbinsta.SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("LoginActivity", "Login Faliure");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

