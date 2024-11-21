package com.example.chitto_techjava;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupPage extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    private Button submitButton, googleSignInButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> signInActivityResultLauncher;

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("YOUR_WEB_CLIENT_ID")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            try {
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                firebaseAuthWithGoogle(account);
                            } catch (ApiException e) {
                                Toast.makeText(this, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
                    }
                });

        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        constraintLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        nameEditText = createEditText("Enter Name");
        nameEditText.setId(EditText.generateViewId());

        emailEditText = createEditText("Enter Email");
        emailEditText.setId(EditText.generateViewId());

        phoneEditText = createEditText("Enter Phone Number");
        phoneEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneEditText.setId(EditText.generateViewId());

        passwordEditText = createEditText("Enter Password");
        passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setId(EditText.generateViewId());

        confirmPasswordEditText = createEditText("Confirm Password");
        confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmPasswordEditText.setId(EditText.generateViewId());

        submitButton = new Button(this);
        submitButton.setText("Submit");
        submitButton.setId(Button.generateViewId());
        submitButton.setBackgroundColor(Color.BLUE);
        submitButton.setTextColor(Color.WHITE);
        setRoundedBackground(submitButton);

        googleSignInButton = new Button(this);
        googleSignInButton.setText("Sign in with Google");
        googleSignInButton.setId(Button.generateViewId());
        googleSignInButton.setBackgroundColor(Color.RED);
        googleSignInButton.setTextColor(Color.WHITE);
        setRoundedBackground(googleSignInButton);

        constraintLayout.addView(nameEditText);
        constraintLayout.addView(emailEditText);
        constraintLayout.addView(phoneEditText);
        constraintLayout.addView(passwordEditText);
        constraintLayout.addView(confirmPasswordEditText);
        constraintLayout.addView(submitButton);
        constraintLayout.addView(googleSignInButton);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(nameEditText.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 100);
        constraintSet.connect(nameEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(nameEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.connect(emailEditText.getId(), ConstraintSet.TOP, nameEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(emailEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(emailEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.connect(phoneEditText.getId(), ConstraintSet.TOP, emailEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(phoneEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(phoneEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.connect(passwordEditText.getId(), ConstraintSet.TOP, phoneEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(passwordEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(passwordEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.connect(confirmPasswordEditText.getId(), ConstraintSet.TOP, passwordEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(confirmPasswordEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(confirmPasswordEditText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.connect(submitButton.getId(), ConstraintSet.TOP, confirmPasswordEditText.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(submitButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(submitButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.connect(googleSignInButton.getId(), ConstraintSet.TOP, submitButton.getId(), ConstraintSet.BOTTOM, 50);
        constraintSet.connect(googleSignInButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 50);
        constraintSet.connect(googleSignInButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 50);

        constraintSet.applyTo(constraintLayout);

        setContentView(constraintLayout);

        submitButton.setOnClickListener(v -> registerUser());

        googleSignInButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            signInActivityResultLauncher.launch(signInIntent);
        });
    }

    private void registerUser() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("phone", phone);
                            databaseReference.child(user.getUid()).setValue(userMap);
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupPage.this, ProfilePage.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(SignupPage.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setRoundedBackground(Button button) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(30);
        drawable.setColor(Color.parseColor("#6200EE"));
        button.setBackground(drawable);
    }

    private GradientDrawable createRoundedBackground(int backgroundColor, int borderColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(30);
        drawable.setColor(backgroundColor);
        drawable.setStroke(3, borderColor);
        return drawable;
    }

    private EditText createEditText(String hint) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setTextColor(Color.BLACK);
        editText.setHintTextColor(Color.BLACK);
        editText.setPadding(40, 30, 40, 30);
        editText.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, 200));
        editText.setBackground(createRoundedBackground(Color.WHITE, Color.BLACK));
        return editText;
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(SignupPage.this, MainActivity.class));
                    } else {
                        Toast.makeText(SignupPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
