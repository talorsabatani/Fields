package com.talor.fields;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private Fragment_Map fragment_map;
//    private FieldsData fieldsData;
    private User currentUser;

    private Field currentField;
    private Field field;
    private TextView map_LBL_park_name;
    private TextView popup_LBL_online;
    private TextView popup_LBL_rating;
    private TextView popup_LBL_rate_users;
    private MaterialButton popup_BTN_goto;
    private FloatingActionButton fab;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_map = new Fragment_Map();
//        fieldsData = new FieldsData(this);
        currentUser = new User();
        getCurrentUserFromDatabase();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentUser.getFavoriteField().equals("")) {
                    updateParkByPid(currentUser.getFavoriteField(), true);
                } else {
                    Toast.makeText(MainActivity.this, "You are not registered for field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getCurrentUserFromDatabase() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                assert currentUser != null;
//                fragment_map = new Fragment_Map(currentUser.getLastLat(), currentUser.getLastLng());
                fragment_map.setUserLat(currentUser.getLastLat());
                fragment_map.setUserLng(currentUser.getLastLng());
                getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment_map).commit();
                fragment_map.setCallBack_showPopUp(callBack_showPopUp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, Login_SignUp_Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                startLoginActivity();
                        }
                    });
            return true;
        } else if (id == R.id.edit_profile) {
            startProfileEdit();
        } else if (id == R.id.my_profile) {
            Intent myIntent = new Intent(MainActivity.this, Activity_Profile.class);
            myIntent.putExtra("USER_PROFILE", currentUser);
            startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startProfileEdit() {
        Intent myIntent = new Intent(this, Profile_Edit_Activity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        fieldsData.setCallBack_UploadFields(callBack_uploadFields);
    }

    private void updatePopUpView(String pid, View popUpView) {
        map_LBL_park_name = popUpView.findViewById(R.id.map_LBL_park_name);
        popup_LBL_online = popUpView.findViewById(R.id.popup_LBL_online);
        popup_LBL_rating = popUpView.findViewById(R.id.popup_LBL_rating);
        popup_LBL_rate_users = popUpView.findViewById(R.id.popup_LBL_rate_users);
        popup_BTN_goto = popUpView.findViewById(R.id.popup_BTN_goto);


        for (Field field : fragment_map.fieldsData.getFieldsList()) {
            if (field.getPid() == pid) {
                currentField = field;
//                setDistanceFromCurrLocation();
                updateParkByPid(currentField.getPid(), false);
                map_LBL_park_name.setText(currentField.getName());
                popup_LBL_online.setText("" + currentField.count_user);
            }
        }

        //go to button on the popup
        popup_BTN_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get park by pid and open park activity
                updateParkByPid(currentField.getPid(), true);
            }
        });


    }


    private void updateParkByPid(String pid, boolean openParkActivity) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Fields");

        myRef.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                field = dataSnapshot.getValue(Field.class);

                if (openParkActivity) {
                    currentField = field;
                    openParkActivity();
                } else {
                    getRatingByPid(field.getPid());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void getRatingByPid(String pid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Rating");

        myRef.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Rating rating = dataSnapshot.getValue(Rating.class);
                    popup_LBL_rate_users.setText("" + rating.getTotNumOfRates());
                    popup_LBL_rating.setText("" + rating.getTotRating());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void openParkActivity() {
        Intent myIntent = new Intent(this, Field_Activity.class);
        myIntent.putExtra("FIELD", currentField);
        startActivity(myIntent);
    }

    public void showPopUpWindowOnMap(String pid) {
        PopUPWindow mPopupWindow = new PopUPWindow(this);
        updatePopUpView(pid, mPopupWindow.PopUpWindowOnMap());
    }

    private CallBack_showPopUp callBack_showPopUp = new CallBack_showPopUp() {
        @Override
        public void PopUpWindowOnMap(String pid) {
            showPopUpWindowOnMap(pid);
        }
    };


}