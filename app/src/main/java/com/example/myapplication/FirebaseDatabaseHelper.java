package com.example.myapplication;

import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private User user = new User();

    public FirebaseDatabaseHelper()
    {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Users");
    }

    public interface DataStatus{
        void DataIsLoaded(User user);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public void extract(final DataStatus dataStatus)
    {
        mReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.e("yo",user.contact1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Log.e("yo",user.contact1);
        dataStatus.DataIsLoaded(user);
    }
}
