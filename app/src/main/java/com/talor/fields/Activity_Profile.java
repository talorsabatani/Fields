package com.talor.fields;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Objects;

public class Activity_Profile extends BaseActivity {
    private static String USER_PROFILE = "USER_PROFILE";
    private User user;
    private TextView profile_LBL_name;
    private TextView profile_nickName;
    private TextView profile_LBL_age;
    private TextView profile_LBL_position;
    private TextView profile_LBL_gender;
    private TextView profile_LBL_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //get user
        user = (User) getIntent().getSerializableExtra(USER_PROFILE);
        baseUser = user;
        findView();
        setText();
    }

    private void setText() {
        profile_LBL_name.setText(user.getName());
        profile_nickName.setText(user.getNickName());
        profile_LBL_age.setText(user.getAge());
        profile_LBL_position.setText(user.getAge());
        profile_LBL_gender.setText(user.getGender());
        profile_LBL_about.setText(user.getAbout());
    }

    private void findView() {
        profile_LBL_name = findViewById(R.id.profile_LBL_name);
        profile_nickName = findViewById(R.id.profile_nickName);
        profile_LBL_age = findViewById(R.id.profile_LBL_age);
        profile_LBL_position = findViewById(R.id.profile_LBL_position);
        profile_LBL_gender = findViewById(R.id.profile_LBL_gender);
        profile_LBL_about = findViewById(R.id.profile_LBL_about);
        ImageButton profile_BTN_back = findViewById(R.id.profile_BTN_back);
        profile_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ShapeableImageView user_photo = findViewById(R.id.user_photo);
        if (user.getImageUrl() != null) {
            Glide.with(this)
                    .load(user.getImageUrl())
                    .into(user_photo);
        }
    }
}