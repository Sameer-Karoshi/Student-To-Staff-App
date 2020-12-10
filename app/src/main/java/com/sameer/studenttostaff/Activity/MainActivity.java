package com.sameer.studenttostaff.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sameer.studenttostaff.Fragment.ChatFragment;
import com.sameer.studenttostaff.Fragment.ContactFragment;
import com.sameer.studenttostaff.Fragment.FacLocationFragment;
import com.sameer.studenttostaff.Fragment.StuLocViewFragment;
import com.sameer.studenttostaff.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String mCurrent_user_Id;
    private DatabaseReference mReference_fac;
    private DatabaseReference mReference_Stu;


    public Boolean Faculty = false;
    public Boolean Student = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            mAuth = FirebaseAuth.getInstance();
            mCurrent_user_Id = mAuth.getCurrentUser().getUid();


            mReference_fac = FirebaseDatabase.getInstance().getReference().child("Users").child("Faculty");




            mReference_fac.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot mySnapshot: dataSnapshot.getChildren()){


                        String fac_UserId = String.valueOf(mySnapshot.getKey());
                        //Log.d("Sameer", "onDataChange: " + fac_UserId);
                        if(fac_UserId.equals(mCurrent_user_Id)){
                            Faculty = true;
                            break;

                        }

                    }

                    //Log.d("Sameer", "onDataChange: "+Faculty);





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("XYZ","Cancel");

                }
            });

            mReference_Stu = FirebaseDatabase.getInstance().getReference().child("Users").child("Student");

            mReference_Stu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot mySnapshot: dataSnapshot.getChildren()){


                        String Stu_UserId = String.valueOf(mySnapshot.getKey());
                        //Log.d("Sameer", "onDataChange: " + Stu_UserId);
                        if(Stu_UserId.equals(mCurrent_user_Id)){
                            Student = true;
                            break;
                        }

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("XYZ","Cancel");

                }
            });










            if(mAuth.getCurrentUser() != null && item.getItemId() == R.id.contact_to_faculty){
                loadFragment(new ContactFragment());
                return true;
            }

            if (mAuth.getCurrentUser() != null && item.getItemId() == R.id.location_of_faculty && Faculty){
                loadFragment(new FacLocationFragment());
                return true;
            }



            if(mAuth.getCurrentUser() != null && item.getItemId() == R.id.location_of_faculty && Student){
                loadFragment(new StuLocViewFragment());
                return true;

            }

            if(mAuth.getCurrentUser() != null && item.getItemId() == R.id.chat_with_faculty){
                loadFragment(new ChatFragment());
                return true;
            }




           return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new ContactFragment());
        BottomNavigationView navView = findViewById(R.id.am_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        mAuth = FirebaseAuth.getInstance();




    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        sendToStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            sendToStart();

        }
    }



    private void sendToStart() {
        Intent StartIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(StartIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Logout();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.am_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }








}