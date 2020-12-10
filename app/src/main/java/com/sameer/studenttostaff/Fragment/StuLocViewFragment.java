package com.sameer.studenttostaff.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sameer.studenttostaff.Adapter.LocationAdapter;
import com.sameer.studenttostaff.FacultyObject.Faculty;
import com.sameer.studenttostaff.R;

import java.util.ArrayList;
import java.util.List;

public class StuLocViewFragment extends Fragment {

    private static final String TAG = "StuLocViewFragment";
    private ProgressBar mProgress;
    private List<Faculty> mFacultyList;

    private RecyclerView mRecycler;
    private DatabaseReference mFacultyDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_Id;



    private DatabaseReference mAllUsersDatabase;


    public StuLocViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView =  inflater.inflate(R.layout.fragment_stu_loc_view, container, false);

        mRecycler = mMainView.findViewById(R.id.loc_recyler_view);
        mProgress = mMainView.findViewById(R.id.fc_progress);


        mFacultyList = new ArrayList<>();


        try {
            mAuth = FirebaseAuth.getInstance();
            mCurrent_user_Id = mAuth.getCurrentUser().getUid();
        }
        catch (NullPointerException n){
            Toast.makeText(mMainView.getContext(), n.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mAllUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAllUsersDatabase.keepSynced(true);

        if(mAuth.getCurrentUser() != null){
            mFacultyDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Faculty").child(mCurrent_user_Id);
            mFacultyDatabase.keepSynced(true);

        }


        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        //mProgress.setVisibility(View.VISIBLE);


        mAllUsersDatabase.child("Faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Log.d("XYZ","Ondatachange");




                for (DataSnapshot mySnapshot: dataSnapshot.getChildren()){



                    String contact = String.valueOf(mySnapshot.child("Contact Number").getValue());
                    String name = String.valueOf(mySnapshot.child("Name").getValue());
                    String Department = String.valueOf(mySnapshot.child("Department").getValue());
                    String Post = String.valueOf(mySnapshot.child("Post").getValue());
                    String CabinNumber = String.valueOf(mySnapshot.child("Cabin Number").getValue());
                    String college = String.valueOf(mySnapshot.child("College").getValue());
                    String location = String.valueOf(mySnapshot.child("Location").getValue());

                    //Log.d("Names",contact);



                    Faculty faculty = new Faculty(name,contact,Department,CabinNumber,Post,college,location);

                    mFacultyList.add(faculty);

                    loadFaculty();




                }


                if(mAuth.getCurrentUser() != null){

                }
               // mProgress.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("XYZ","Cancel");

            }
        });
        return mMainView;
    }

    public void loadFaculty() {
        //mProgress.setVisibility(View.VISIBLE);
        LocationAdapter adapter = new LocationAdapter(getActivity(), mFacultyList);
        if(mAuth.getCurrentUser() != null){
            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

        }
        mRecycler.setAdapter(adapter);
    }
}