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
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

        private EditText userNameInput;
        private EditText passwordInput;
        private EditText emailInput;
        private Button signupBtn;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            userNameInput = findViewById(R.id.userName_et);
            passwordInput = findViewById(R.id.password_et);
            signupBtn = findViewById(R.id.signup_btn);
            emailInput = findViewById(R.id.email_et);

            signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = userNameInput.getText().toString();
                    final String password = passwordInput.getText().toString();
                    final String email = emailInput.getText().toString();

                    signUp(username,password, email);
                }

            });

        }


        private void signUp(final String username, final String password, final String email){

            // Create the ParseUser
            ParseUser user = new ParseUser();
            // Set core properties
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            //TODO add handle and password confirmation
            // Set custom properties
            //user.put("phone", "650-253-0000");
            // Invoke signUpInBackground
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        login(username, password, email);
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Log.e("SignUpActivity", "SignUp Faliure");
                        e.printStackTrace();
                    }
                }
            });

        }

        private void login(String username, String password, String email) {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.d("LoginActivity", "Login successful");
                        final Intent intent = new Intent (com.example.fbinsta.SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("SignUpActivity", "Login Faliure");
                        e.printStackTrace();
                    }
                }
            });

        }
    }

