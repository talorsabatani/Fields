package com.talor.fields;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Field_Activity extends BaseActivity {

    public final int FAVORITE = 1;
    public final int UNFAVORITE = 0;
    private int fieldStatus;
    public final String JOINED = "Undo";
    public final String NOT_JOINED = "Join";
    private User currentUser;
    private RecyclerView list_RV_users;
    private UserAdapter userAdapter;
    public ArrayList<User> users;
    private Field field;
    public static final String FIELD = "FIELD";
    private Button join_BTN;
    private Rating rating;
    private RatingBar field_RB_rate;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        Objects.requireNonNull(getSupportActionBar()).hide();

        field = (Field) getIntent().getSerializableExtra(FIELD);
        getCurrentUserFromDatabase();
        findView();
        users = new ArrayList<>();
        rating = new Rating();
        setAdapter();
    }

    private void findView() {
        list_RV_users = findViewById(R.id.list_RV_users);
        TextView field_LBL_status_address = findViewById(R.id.field_LBL_status_address);
        TextView field_LBL_status_benches = findViewById(R.id.field_LBL_status_benches);
        TextView field_LBL_status_bags = findViewById(R.id.field_LBL_status_bags);
        TextView field_LBL_status_water = findViewById(R.id.field_LBL_status_water);
        TextView field_name = findViewById(R.id.field_name);

        field_name.setText(field.getName());
        field_LBL_status_address.setText(field.getAddress());
        field_LBL_status_benches.setText(field.getBenches());
        field_LBL_status_bags.setText(field.getBags());
        field_LBL_status_water.setText(field.getWater());

        ImageButton go_to_map = findViewById(R.id.go_to_map);
        go_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MaterialButton field_MBTN_rate = findViewById(R.id.field_MBTN_rate);
        field_MBTN_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rating.checkIfUserExist(currentUser.getUid())) {
                    getUserRating();
                } else {
                    Toast.makeText(Field_Activity.this, "You have already rated the field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton field_IBTN_chat = findViewById(R.id.field_chat);
        field_IBTN_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(Field_Activity.this, Activity_Chat.class);
                myIntent2.putExtra("FIELD_PID", field.getPid());
                startActivity(myIntent2);
            }
        });

        field_RB_rate = findViewById(R.id.field_RB_rate);

        join_BTN = findViewById(R.id.join_BTN);
        join_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteField();
            }
        });
    }

    private void getUserRating() {
        float rate = field_RB_rate.getRating();
        rating.setRating(rate);
        rating.calcRating();
        field.setRating(rating.getTotRating());
        rating.addUserToRatingList(currentUser.getUid());
        field_RB_rate.setRating(rating.getTotRating());
        field_RB_rate.setIsIndicator(true);

        Toast.makeText(Field_Activity.this, "Thank`s for rating !", Toast.LENGTH_SHORT).show();
        updateRateDatabase();
        updateFieldDatabase();
    }

    private void updateRateDatabase() {
        myRef = database.getReference("Rating").child(field.getPid());
        myRef.setValue(rating);
    }

    private void updateFieldDatabase() {
        myRef = database.getReference("Fields").child(field.getPid());
        myRef.setValue(field);
    }


    private void getUsersInFieldList(FirebaseDatabase database) {
        database.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    try {
                        User user = key.getValue(User.class);
                        //if current field is user`s favorite field - add user to list
                        assert user != null;
                        if (user.getFavoriteField().equals(field.getPid()) && checkIfUserInField(user) == -1) {
                            users.add(user);
                            userAdapter.notifyItemChanged(users.size());
                        }
                        //if the user removed a "favorite " from the field
                        else if (!user.getFavoriteField().equals(field.getPid()) && checkIfUserInField(user) != -1) {
                            int id = checkIfUserInField(user);
                            users.remove(id);
                            userAdapter.notifyItemRemoved(id);
                            userAdapter.notifyItemRangeChanged(id, users.size());
                        }
                    } catch (Exception ex) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private int checkIfUserInField(User checkUser) {
        for (User user : users) {
            if (!user.getUid().equals(checkUser.getUid())) {
                continue;
            }
            return users.indexOf(user);
        }
        return -1;
    }

    private void updateUserDatabase(User user) {
        myRef = database.getReference("Users").child(user.getUid());
        myRef.setValue(user);
    }

    private void getCurrentUserFromDatabase() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                baseUser = currentUser;
                checkIfFieldIsFavorite();
                getUsersInFieldList(database);
                getRatesFromDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkIfFieldIsFavorite() {
        if (currentUser.getFavoriteField().equals(field.getPid())) {
            join_BTN.setText(JOINED);
            fieldStatus = FAVORITE;
        } else {
            fieldStatus = UNFAVORITE;
        }
    }

    private void setAdapter() {
        list_RV_users.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this, users);
        list_RV_users.setAdapter(userAdapter);

        userAdapter.setClickListener(new UserAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openProfileActivity(users.get(position));
            }
        });
    }


    private void getRatesFromDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Rating").child(field.getPid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    updateRateDatabase();
                } else {
                    rating = snapshot.getValue(Rating.class);
                    String uid = currentUser.getUid();
                    if (rating.checkIfUserExist(uid)) {
                        field_RB_rate.setIsIndicator(true);
                    }
                    field_RB_rate.setRating((float) rating.getTotRating());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openProfileActivity(User user) {
        Intent myIntent = new Intent(Field_Activity.this, Activity_Profile.class);
        myIntent.putExtra("USER_PROFILE", user);
        startActivity(myIntent);
    }

    private void favoriteField() {
        //if user click to FAVORITE field
        if (fieldStatus == UNFAVORITE) {
            // if user already has a favorite field
            if (!currentUser.getFavoriteField().equals("")) {
                Toast.makeText(this, "You already have a favorite field", Toast.LENGTH_SHORT).show();
            } else {
                //update favorite field
                userPreference(field.getPid(), JOINED, FAVORITE);
                field.addUserToField(currentUser.getUid());
                Toast.makeText(this, "You have successfully added the field to favorites", Toast.LENGTH_SHORT).show();
            }
        }
        //if user click to UNFAVORITE field
        else {
            userPreference("", NOT_JOINED, UNFAVORITE);
            field.removeUserFromField(currentUser.getUid());
        }
        //update field and user in FirebaseDatabase
        updateUserDatabase(currentUser);
        updateFieldDatabase();
    }

    private void userPreference(String pid, String join, int status) {
        currentUser.setFavoriteField(pid);
        join_BTN.setText(join);
        fieldStatus = status;
    }


}