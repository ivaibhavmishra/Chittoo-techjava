package com.example.chitto_techjava;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstPage extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private Button logoutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();


        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));


        constraintLayout.setBackgroundColor(Color.parseColor("#87CEEB"));


        emailEditText = new EditText(this);
        emailEditText.setHint("Enter Email");
        emailEditText.setId(View.generateViewId());
        emailEditText.setHintTextColor(Color.BLACK);
        emailEditText.setTextColor(Color.BLACK);
        emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEditText.setPadding(40, 30, 40, 30);
        emailEditText.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, 200));
        emailEditText.setBackground(createRoundedBackground(Color.WHITE, Color.BLACK));


        passwordEditText = new EditText(this);
        passwordEditText.setHint("Enter Password");
        passwordEditText.setId(View.generateViewId());
        passwordEditText.setHintTextColor(Color.BLACK);
        passwordEditText.setTextColor(Color.BLACK);
        passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, 200));
        passwordEditText.setBackground(createRoundedBackground(Color.WHITE, Color.BLACK));

        loginButton = new Button(this);
        loginButton.setText("Login");
        loginButton.setId(View.generateViewId());
        loginButton.setBackgroundColor(Color.parseColor("#007BFF"));
        loginButton.setTextColor(Color.WHITE);


        registerButton = new Button(this);
        registerButton.setText("Don't have an account? Register here");
        registerButton.setId(View.generateViewId());
        registerButton.setBackgroundColor(Color.RED);
        registerButton.setTextColor(Color.WHITE);


        logoutButton = new Button(this);
        logoutButton.setText("Logout");
        logoutButton.setId(View.generateViewId());
        logoutButton.setBackgroundColor(Color.parseColor("#007BFF"));
        logoutButton.setTextColor(Color.WHITE);


        constraintLayout.addView(emailEditText);
        constraintLayout.addView(passwordEditText);
        constraintLayout.addView(loginButton);
        constraintLayout.addView(registerButton);
        constraintLayout.addView(logoutButton);


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);


        constraintSet.connect(emailEditText.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 150);
        constraintSet.connect(emailEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(emailEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);


        constraintSet.connect(passwordEditText.getId(), ConstraintSet.TOP, emailEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(passwordEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(passwordEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);


        constraintSet.connect(loginButton.getId(), ConstraintSet.TOP, passwordEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(loginButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(loginButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);


        constraintSet.connect(registerButton.getId(), ConstraintSet.TOP, loginButton.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(registerButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(registerButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);


        constraintSet.connect(logoutButton.getId(), ConstraintSet.TOP, registerButton.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(logoutButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(logoutButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.applyTo(constraintLayout);


        setContentView(constraintLayout);


        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(FirstPage.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });


        registerButton.setOnClickListener(v -> {

            Intent intent = new Intent(FirstPage.this, SignupPage.class);
            startActivity(intent);
        });


        logoutButton.setOnClickListener(v -> {

            mAuth.signOut();
            Toast.makeText(FirstPage.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        });
    }

    // Login user with email and password
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Intent intent = new Intent(FirstPage.this, ProfilePage.class);
                            startActivity(intent);
                            finish(); // Close login page

                        }
                    } else {
                        // If sign-in fails, display a message to the user
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Authentication failed.";
                        Toast.makeText(FirstPage.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private GradientDrawable createRoundedBackground(int fillColor, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setCornerRadius(20f);
        drawable.setStroke(2, strokeColor);
        return drawable;
    }
}
