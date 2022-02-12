package com.talor.fields;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FieldsData {
    private List<Field> fieldsList = new ArrayList<>();
    private Context context;
    public CallBack_UploadFields callBack_uploadFields;

    public void setCallBack_UploadFields(CallBack_UploadFields callBack_uploadParks) {
        this.callBack_uploadFields = callBack_uploadParks;
    }


    public FieldsData() {
//        createParks();
    }

    public FieldsData(Context context) {
        this.context = context;
        createParks();
    }

    public List<Field> getFieldsList() {
        return fieldsList;
    }

    public FieldsData setFieldsList(List<Field> fieldsList) {
        this.fieldsList = fieldsList;
        return this;
    }

    public void getFields() {
        DatabaseReference mDatabas = FirebaseDatabase.getInstance().getReference().child("Fields");
        mDatabas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    Field field = key.getValue(Field.class);
                    fieldsList.add(field);
                }
                addFieldsToMap();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addFieldsToMap() {
        if (callBack_uploadFields != null)
            callBack_uploadFields.UploadFields(fieldsList);
    }


    private void createParks() {
        DatabaseReference mDatabas = FirebaseDatabase.getInstance().getReference().child("Fields");
        Field field_1 = new Field().setAddress("Beni").setLat(32.105332).setLng(34.816797).setPid("field_1")
                .setWater("yes").setBenches("1").setBags("Yes")
                .setName("Field 1");

        Field field_2 = new Field().setAddress("Avraham").setLat(32.114151).setLng(34.817486).setPid("field_2")
                .setWater("yes").setBenches("1").setBags("Yes")
                .setName("Field 2").setParkImage1("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg");


        mDatabas.child(field_1.getPid()).setValue(field_1);
        mDatabas.child(field_2.getPid()).setValue(field_2);

    }


}
