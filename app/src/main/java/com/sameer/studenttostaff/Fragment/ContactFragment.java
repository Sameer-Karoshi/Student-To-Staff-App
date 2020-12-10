package com.sameer.studenttostaff.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.sameer.studenttostaff.Adapter.ContactAdapter;
import com.sameer.studenttostaff.FacultyObject.Faculty;
import com.sameer.studenttostaff.R;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";
    private TextView mNumber, mEmail, mTwitter, mLinkedIn;
    private ProgressBar mProgress;
    private List<Faculty> mContactList;
    private String num, eml, twt, in;

    private RecyclerView mRecycler;
    private DatabaseReference mFacultyDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_Id;



    private DatabaseReference mAllUsersDatabase;


    public ContactFragment(){

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View mMainView = inflater.inflate(R.layout.fragment_contact, container, false);



         mNumber = mMainView.findViewById(R.id.fc_number);
         mEmail = mMainView.findViewById(R.id.fc_email);
         mLinkedIn = mMainView.findViewById(R.id.in_linkedin);
         mTwitter = mMainView.findViewById(R.id.fc_twitter);
         mProgress = mMainView.findViewById(R.id.fc_progress);
         mRecycler = mMainView.findViewById(R.id.fc_recycler);


        mContactList = new ArrayList<>();



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


        mProgress.setVisibility(View.VISIBLE);


        mAllUsersDatabase.child("Faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("XYZ","Ondatachange");




                for (DataSnapshot mySnapshot: dataSnapshot.getChildren()){



                    String contact = String.valueOf(mySnapshot.child("Contact Number").getValue());
                    String name = String.valueOf(mySnapshot.child("Name").getValue());
                    String Department = String.valueOf(mySnapshot.child("Department").getValue());
                    String Post = String.valueOf(mySnapshot.child("Post").getValue());
                    String CabinNumber = String.valueOf(mySnapshot.child("Cabin Number").getValue());
                    String college = String.valueOf(mySnapshot.child("College").getValue());
                    String location = String.valueOf(mySnapshot.child("Location").getValue());
                    Log.d("Names",contact);



                    Faculty faculty = new Faculty(name,contact,Department,CabinNumber,Post,college,location);

                    mContactList.add(faculty);

                    //loadContacts();




                }


               if(mAuth.getCurrentUser() != null){
                   loadContacts();
               }
                mProgress.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("XYZ","Cancel");

            }
        });




        num = "+912024283001";
        if (num != null){
            mNumber.setText(num);
        }
        twt = "https://twitter.com/vitpune?lang=en";
        if(twt != null){
            mTwitter.setText(twt);
        }
        in = "https://www.linkedin.com/school/vishwakarma-institute-of-technology-pune/?originalSubdomain=in";
        if(in != null){
            mLinkedIn.setText(in);
        }

        eml = "accounts@vit.edu";
        if(eml != null){
            mEmail.setText(eml);
        }
        mNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num!=null){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                    startActivity(intent);
                }
            }
        });
        mLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (in != null){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(in)));
                }
            }
        });
        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twt != null){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twt)));
                }
            }
        });


        //AllFacultyContacts();






        return  mMainView;
    }



   /* private void AllFacultyContacts(){

        //mProgress.setVisibility(View.VISIBLE);
       // mContactList.clear();

        Log.d("XYZ","OnStart");




        mAllUsersDatabase.child("Faculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("XYZ","Ondatachange");




                for (DataSnapshot mySnapshot: dataSnapshot.getChildren()){



                    String contact = String.valueOf(mySnapshot.child("Contact Number").getValue());
                    String name = String.valueOf(mySnapshot.child("Name").getValue());
                    String Department = String.valueOf(mySnapshot.child("Department").getValue());;
                    String Post = String.valueOf(mySnapshot.child("Post").getValue());;
                    String CabinNumber = String.valueOf(mySnapshot.child("Cabin Number").getValue());
                    String college = String.valueOf(mySnapshot.child("College").getValue());
                    Log.d("Names",contact);



                    Faculty faculty = new Faculty(name,contact,Department,CabinNumber,Post,college);

                    mContactList.add(faculty);




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("XYZ","Cancelllll");

            }
        });


        Log.d("XYZ","OnStartEnd");


        loadContacts();
        //mProgress.setVisibility(View.INVISIBLE);


    }

    */


    public void loadContacts() {
        //mProgress.setVisibility(View.VISIBLE);
        ContactAdapter adapter = new ContactAdapter(getActivity(), mContactList);
        if(mAuth.getCurrentUser() != null){
          //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

        }
        mRecycler.setAdapter(adapter);
    }


}