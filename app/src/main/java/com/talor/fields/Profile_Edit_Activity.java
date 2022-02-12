package com.talor.fields;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.InputStream;
import java.util.Objects;

public class Profile_Edit_Activity extends BaseActivity {
    private MaterialButton login;
    public User currentUser;
    private MyLocation myLocation;
    private TextInputLayout name;
    private TextInputLayout nickName;
    private TextInputLayout age;
    private TextInputLayout position;
    private MaterialButtonToggleGroup gender;
    private TextInputLayout about;
    private ImageView pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        } else {
            currentUser = new User();
            locationService();
            addAndUpdateUser();
            findView();
            getText();
            importPhoto();
        }
    }

    private void importPhoto() {
        String[] storagePermission;
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!checkStoragePermission()) {
                    requestPermissions(storagePermission, 101);
                } else {
                    pickFromGallery();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    InputStream stream = getContentResolver().openInputStream(resultUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    pick.setImageBitmap(bitmap);
//                    currentUser.setImageUrl(result.getUri().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pickFromGallery() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, Login_SignUp_Activity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void setUserText(User currentUser) {
        if (currentUser.getName() == null) {
            name.getEditText().setText("New Member");
        } else {
            name.getEditText().setText(currentUser.getName());
        }
        position.getEditText().setText(currentUser.getPosition());
        about.getEditText().setText(currentUser.getAbout());
        age.getEditText().setText(currentUser.getAge());
        nickName.getEditText().setText(currentUser.getNickName());

        if (currentUser.getGender().equals("MALE")) {
            gender.check(R.id.btn_male);
        } else if (currentUser.getGender().equals("FEMALE")) {
            gender.check(R.id.edit_female);
        }
        ImageView pic = findViewById(R.id.pick_img);
        if (currentUser.getImageUrl() != null) {
            Glide.with(this)
                    .load(currentUser.getImageUrl())
                    .into(pic);
        }
    }

    private void findView() {
        pick = findViewById(R.id.pick_img);
        database = FirebaseDatabase.getInstance();
        name = findViewById(R.id.edit_name);
        nickName = findViewById(R.id.edit_nick_name);
        age = findViewById(R.id.edit_age);
        position = findViewById(R.id.edit_position);
        gender = findViewById(R.id.pick_gender);
        about = findViewById(R.id.edit_about);



        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
                startMainActivity();
                finish();
            }
        });
    }

    private void getText() {
        name.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus(name.getEditText());
                }
                return false;
            }
        });
        nickName.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus(nickName.getEditText());
                }
                return false;
            }
        });
        age.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus(age.getEditText());
                }
                return false;
            }
        });
        position.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus(position.getEditText());
                }
                return false;
            }
        });
        if (currentUser.getGender().equals("MALE")) {
            gender.check(R.id.btn_male);
        } else if (currentUser.getGender().equals("FEMALE")) {
            gender.check(R.id.edit_female);
        }
        about.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocus(about.getEditText());
                }
                return false;
            }
        });
    }

    public void clearFocus(EditText EditText) {
        EditText.clearFocus();
    }

    private void startMainActivity() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    private void saveDetails() {
        currentUser.setAbout(about.getEditText().getText().toString());
        currentUser.setName(name.getEditText().getText().toString());
        currentUser.setPosition(position.getEditText().getText().toString());
        currentUser.setNickName(nickName.getEditText().getText().toString());
        currentUser.setAge(age.getEditText().getText().toString());
        currentUser.setAbout(about.getEditText().getText().toString());

        if (gender.getCheckedButtonId() == R.id.btn_male) {
            currentUser.setGender("MALE");
        } else if (gender.getCheckedButtonId() == R.id.edit_female) {
            currentUser.setGender("FEMALE");
        }
        updateUserDatabase();

    }


    private void addNewUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        User user = new User();
        user.setUid(firebaseAuth.getUid())
                .setName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                .setStatus("offline");
        baseUser = user;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(user.getUid()).setValue(user);
    }

    private void addAndUpdateUser() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    addNewUser();
//                    return;
                } else {
                    currentUser = dataSnapshot.getValue(User.class);
                    baseUser = currentUser;
                    setUserText(baseUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    //get current location callback
    private CallBack_Location callBack_location = new CallBack_Location() {
        @Override
        public void startLocation(double lat, double lng) {
            // check if currentUser is not null and the distance is more than 20 meters from the previous location to update lan ,lng
//            if (currentUser.getUid() != null && distance.checkIfDistanceChanged(lat, lng)) {
            if (currentUser.getUid() != null) {
                currentUser.setLastLat(lat);
                currentUser.setLastLng(lng);
            }
        }
    };


    private void updateUserDatabase() {
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(currentUser.getUid());
//        myRef.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.exists()) {
//                    addNewUser();
//                    return;
//                }
//                currentUser = dataSnapshot.getValue(User.class);
//                baseUser = currentUser;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
        myRef.setValue(currentUser);

    }

    private void locationService() {
        myLocation = new MyLocation(this);
        myLocation.setCallBack_location(callBack_location);
    }

    //Request for location Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == myLocation.PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myLocation.getLocation();
            }
        } else {
            myLocation.setLocationAccepted(false);
        }
    }
}