package com.talor.fields;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_Chat extends BaseActivity {

    public static final String FIELD_PID = "FIELD_PID";
    private String field_pid = "";

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private MassageAdapter massageAdapter;
    private User user;
    private Field currentField;
    private List<Message> messageList;

    private RecyclerView chat_RCV_recyclerview;
    private TextView chat_EDT_massage;
    private TextView chat_LBL_field_name;
    private ImageButton chat_BTN_send;
    private ImageButton chat_BTN_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();

        field_pid = (String) getIntent().getStringExtra(FIELD_PID);
        findViews();
        getCurrentUser();
        getParkByPid(field_pid);

    }

    private void findViews() {
        chat_RCV_recyclerview = findViewById(R.id.chat_RCV_recyclerview);
        chat_EDT_massage = findViewById(R.id.chat_EDT_massage);
        chat_BTN_send = findViewById(R.id.chat_BTN_send);
        chat_BTN_back = findViewById(R.id.chat_BTN_back);
        chat_LBL_field_name = findViewById(R.id.chat_LBL_field_name);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();
        messageList = new ArrayList<>();

        //back button
        chat_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void getCurrentUser() {

        //get current user from firebase
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        user.setUid(currentUser.getUid());

        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                baseUser = user;
                readMessageFromInput();
                manageMessage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessageFromInput() {
        chat_BTN_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(chat_EDT_massage.getText().toString())) {
                    Message massage = new Message(chat_EDT_massage.getText().toString(), user);
                    chat_EDT_massage.setText("");
                    myRef.push().setValue(massage);

                } else {
                    Toast.makeText(Activity_Chat.this, "you cannot send blank massage", Toast.LENGTH_SHORT).show();

                }
            }


        });
    }

    private void manageMessage() {

        myRef = database.getReference("Massages").child(field_pid);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message massage = snapshot.getValue(Message.class);
                massage.setKey(snapshot.getKey());

                messageList.add(massage);
                displayMassages(messageList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message massage = snapshot.getValue(Message.class);
                massage.setKey(snapshot.getKey());

                List<Message> newMessages = new ArrayList<>();

                for (Message m : messageList) {
                    if (m.getKey().equals(massage.getKey())) {
                        newMessages.add(massage);
                    } else {
                        newMessages.add(m);
                    }
                }
                messageList = newMessages;
                displayMassages(messageList);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Message massage = snapshot.getValue(Message.class);
                massage.setKey(snapshot.getKey());

                List<Message> newMessages = new ArrayList<>();

                for (Message m : messageList) {
                    if (!m.getKey().equals(massage.getKey())) {
                        newMessages.add(m);
                    }

                }

                messageList = newMessages;
                displayMassages(messageList);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayMassages(List<Message> massageList) {
        chat_RCV_recyclerview.setLayoutManager(new LinearLayoutManager(Activity_Chat.this));
        massageAdapter = new MassageAdapter(Activity_Chat.this, massageList, myRef, user.getUid());
        chat_RCV_recyclerview.setAdapter(massageAdapter);
        //Recyclerview start from Bottom
        chat_RCV_recyclerview.scrollToPosition(massageAdapter.getItemCount() - 1);

    }

    private void getParkByPid(String pid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Fields");
        myRef.child(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentField = dataSnapshot.getValue(Field.class);
                TextView chat_LBL_park_name = findViewById(R.id.chat_LBL_field_name);
                chat_LBL_park_name.setText(currentField.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}