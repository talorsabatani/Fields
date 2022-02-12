package com.talor.fields;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Login_SignUp_Activity extends AppCompatActivity {

    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, Profile_Edit_Activity.class));
            this.finish();
        }


        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // sign in the user or create a new user
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            login();
                        } else {
                            // signing in failed
                            IdpResponse response = IdpResponse.fromResultIntent(data);
                        }
                    }
                });
        findView();
    }

    private void findView() {
        ImageView start_background = findViewById(R.id.start_background);
        Glide.with(this)
                .load("https://wallpaperaccess.com/full/2447465.jpg")
                .into(start_background);
    }

    private void login() {
        Intent intent = new Intent(this, Profile_Edit_Activity.class);
        startActivity(intent);
        this.finish();
    }

    public void handleLoginRegister(View view) {

        List<AuthUI.IdpConfig> provider = Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setLogo(R.drawable.ic_soccer_light)
                .setAlwaysShowSignInMethodScreen(true)
                .build();
        someActivityResultLauncher.launch(intent);

    }

}