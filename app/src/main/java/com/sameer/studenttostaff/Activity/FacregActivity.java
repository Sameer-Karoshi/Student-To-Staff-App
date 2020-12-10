package com.sameer.studenttostaff.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sameer.studenttostaff.R;

import java.util.HashMap;

public class FacregActivity extends AppCompatActivity {

    private TextView mFacName;
    private TextView mFacEmail;
    private TextView mFacPass;
    private TextView mFacConfirmPass;
    private TextView mFacClg;
    private TextView mFacDept;
    private TextView mFacPost;
    private TextView mFacCabinNumber;
    private TextView mFacContactNumber;
    private Button mRegisterFacBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mUSERDATABASE;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facreg);

       mFacName = findViewById(R.id.fac_name);
       mFacEmail = findViewById(R.id.fac_email);
       mFacPass = findViewById(R.id.fac_pass);
       mFacConfirmPass = findViewById(R.id.fac_confirm_pass);
       mFacClg = findViewById(R.id.fac_clg);
       mFacDept = findViewById(R.id.fac_dept);
       mFacPost = findViewById(R.id.fac_post);
       mFacCabinNumber = findViewById(R.id.fac_cabin_number);
       mFacContactNumber = findViewById(R.id.fac_contact_number);

        mUSERDATABASE = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();

        mRegisterFacBtn = (Button)findViewById(R.id.fac_register_btn);
        mRegisterFacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FacName = mFacName.getText().toString();
                String FacEmail = mFacEmail.getText().toString();
                String FacPass = mFacPass.getText().toString();
                String FacConfirmPass = mFacConfirmPass.getText().toString();
                String FacClg = mFacClg.getText().toString();
                String FacDept = mFacDept.getText().toString();
                String FacPost = mFacPost.getText().toString();
                String FacCabinNumber = mFacCabinNumber.getText().toString();
                String FacContactNumber = mFacContactNumber.getText().toString();

                if (!TextUtils.isEmpty(FacName)||!TextUtils.isEmpty(FacEmail)||!TextUtils.isEmpty(FacPass)||!TextUtils.isEmpty(FacConfirmPass)||!TextUtils.isEmpty(FacClg)||!TextUtils.isEmpty(FacDept)||!TextUtils.isEmpty(FacPost)||!TextUtils.isEmpty(FacCabinNumber)||!TextUtils.isEmpty(FacContactNumber)){
                    if(FacPass.equals(FacConfirmPass)){
                        FacClg = FacClg.toLowerCase();
                        if(FacClg.equals("vit")){
                            if (FacEmail.endsWith("vit.edu")){

                                register_faculty(FacName,FacEmail,FacPass,FacClg,FacDept,FacPost,FacCabinNumber,FacContactNumber);
                            }
                            else{
                                Toast.makeText(FacregActivity.this, "Please Register with vit.edu Email Address ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{

                            register_faculty(FacName,FacEmail,FacPass,FacClg,FacDept,FacPost,FacCabinNumber,FacContactNumber);

                        }

                    }
                    else{
                        Toast.makeText(FacregActivity.this, "Please Enter Both Passwords Same...", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(FacregActivity.this, "Enter All Fields...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void register_faculty(final String facName, String facEmail, String facPass, final String facClg, final String facDept, final String facPost, final String facCabinNumber, final String facContactNumber) {

        mAuth.createUserWithEmailAndPassword(facEmail,facPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String UID = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Faculty").child(UID);
                    HashMap<String,String> userMap = new HashMap<>();

                    userMap.put("Name",facName);
                    userMap.put("College",facClg);
                    userMap.put("Department",facDept);
                    userMap.put("Post",facPost);
                    userMap.put("Cabin Number",facCabinNumber);
                    userMap.put("Contact Number",facContactNumber);
                    userMap.put("Location","Other");



                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){



                                String current_user_id = mAuth.getCurrentUser().getUid();

                                String deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

                                mDatabase.child("device_token").setValue(deviceId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mProgressDialog = new ProgressDialog(FacregActivity.this);
                                        mProgressDialog.setTitle("Sign in......");
                                        mProgressDialog.setMessage("Please wait,Sign in is in progress");
                                        //mProgressDialog.setCanceledOnTouchOutside(false);
                                        mProgressDialog.show();

                                        Intent mainIntent = new Intent(FacregActivity.this,MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        //finish();





                                    }
                                });




                            }

                        }
                    });

                }

            }
        });

    }
}