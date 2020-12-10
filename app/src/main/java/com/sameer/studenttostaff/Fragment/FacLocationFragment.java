package com.sameer.studenttostaff.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sameer.studenttostaff.FacultyObject.Faculty;
import com.sameer.studenttostaff.R;


public class FacLocationFragment extends Fragment {


    private DatabaseReference mFacultyDatabase;
    //private DatabaseReference CurrentUser;
    private FirebaseAuth mAuth;
    private String mCurrent_user_Id;

    RadioGroup radioGroup;
    RadioButton radioButton;






    public FacLocationFragment() {
        // Required empty public constructor
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_fac_location, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_Id = mAuth.getCurrentUser().getUid();


        mFacultyDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Faculty").child(mCurrent_user_Id);

        radioGroup = (RadioGroup)v.findViewById(R.id.radiogroup);
        //checkButton(v);



        //int radioID = radioGroup.getCheckedRadioButtonId();
        //radioButton = getActivity().findViewById(radioID);


        //Log.d("Sameer",""+ radioID);



        //HashMap<String,String> userMap = new HashMap<>();
        //userMap.replace("Location",radioButton.getText().toString());
        //mFacultyDatabase.setValue(userMap);
        // Log.d("Output",radioButton.getText().toString());
        //mFacultyDatabase.child("Location").setValue(radioButton.getText().toString());


        Button button_new = v.findViewById(R.id.apply_btn);
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);

            }
        });














        //selected = (TextView)v.findViewById(R.id.selected);





        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkButton(View view){

        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = getActivity().findViewById(radioID);


        //Log.d("Sameer",""+ radioID);



        //HashMap<String,String> userMap = new HashMap<>();
        //userMap.replace("Location",radioButton.getText().toString());
        //mFacultyDatabase.setValue(userMap);
       // Log.d("Output",radioButton.getText().toString());
        mFacultyDatabase.child("Location").setValue(radioButton.getText().toString());
        Faculty faculty = new Faculty();
        faculty.setLocation(radioButton.getText().toString());
        Toast.makeText(getActivity(), "Selected Location: "+radioButton.getText().toString(), Toast.LENGTH_SHORT).show();




    }
}