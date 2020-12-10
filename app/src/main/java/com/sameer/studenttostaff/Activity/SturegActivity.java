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


public class SturegActivity extends AppCompatActivity {

    private TextView mStuName;
    private TextView mStuEmail;
    private TextView mStuPass;
    private TextView mStuConfirmPass;
    private TextView mStuClg;
    private TextView mStuDept;
    private TextView mStuDiv;
    private TextView mStuRoll;
    private TextView mStuGr;
    private Button mRegisterStuBtn;

    //Creating firebase instance variable

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mUSERDATABASE;
    private ProgressDialog mProgressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stureg);



        mStuName = (TextView)findViewById(R.id.stu_name);
        mStuEmail = (TextView)findViewById(R.id.stu_email);
        mStuPass = (TextView)findViewById(R.id.stu_pass);
        mStuConfirmPass = (TextView)findViewById(R.id.stu_confirm_pass);
        mStuClg = (TextView)findViewById(R.id.stu_clg);
        mStuDept = (TextView)findViewById(R.id.stu_dept);
        mStuDiv = (TextView)findViewById(R.id.stu_div);
        mStuRoll = (TextView)findViewById(R.id.stu_roll);
        mStuGr = (TextView)findViewById(R.id.stu_gr);


        mUSERDATABASE = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();

        mRegisterStuBtn = (Button)findViewById(R.id.stu_register_btn);

        mRegisterStuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getStuName = mStuName.getText().toString();
                String getStuEmail = mStuEmail.getText().toString();
                String getStuPass = mStuPass.getText().toString();
                String getStuConfirmPass = mStuConfirmPass.getText().toString();
                String getStuClg = mStuClg.getText().toString();
                String getStuDept = mStuDept.getText().toString();
                String getStuDiv = mStuDiv.getText().toString();
                String getStuRoll = mStuRoll.getText().toString();
                String getStuGr = mStuGr.getText().toString();

                if (!TextUtils.isEmpty(getStuName)&&!TextUtils.isEmpty(getStuEmail)&&!TextUtils.isEmpty(getStuPass)&&!TextUtils.isEmpty(getStuConfirmPass)&&!TextUtils.isEmpty(getStuClg)&&!TextUtils.isEmpty(getStuDept)&&!TextUtils.isEmpty(getStuDiv)&&!TextUtils.isEmpty(getStuRoll)&&!TextUtils.isEmpty(getStuGr)){
                    if(getStuPass.equals(getStuConfirmPass)){

                        getStuClg = getStuClg.toLowerCase();
                        if (getStuClg.equals("vit")){
                            if(getStuEmail.endsWith("vit.edu")){

                                register_student(getStuName,getStuEmail,getStuPass,getStuClg,getStuDept,getStuDiv,getStuRoll,getStuGr);

                            }
                            else{
                                Toast.makeText(SturegActivity.this, "Please Register with vit.edu Email Address", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            register_student(getStuName,getStuEmail,getStuPass,getStuClg,getStuDept,getStuDiv,getStuRoll,getStuGr);

                        }

                    }
                    else{
                        Toast.makeText(SturegActivity.this, "Please Enter Both Passwords Same...", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(SturegActivity.this, "Enter All Fields...", Toast.LENGTH_SHORT).show();
                }

            }
        });








    }

    private void register_student(final String getStuName, String getStuEmail, String getStuPass, final String getStuClg, final String getStuDept, final String getStuDiv, final String getStuRoll, final String getStuGr) {
        mAuth.createUserWithEmailAndPassword(getStuEmail,getStuPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String UID = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Student").child(UID);
                    HashMap<String,String> UserMap = new HashMap<>();
                    UserMap.put("Name",getStuName);
                    UserMap.put("College",getStuClg);
                    UserMap.put("Department",getStuDept);
                    UserMap.put("Division",getStuDiv);
                    UserMap.put("Roll Number",getStuRoll);
                    UserMap.put("Gr Number",getStuGr);


                    mDatabase.setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){



                                String current_user_id = mAuth.getCurrentUser().getUid();

                                String deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

                                mDatabase.child("device_token").setValue(deviceId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mProgressDialog = new ProgressDialog(SturegActivity.this);
                                        mProgressDialog.setTitle("Sign in......");
                                        mProgressDialog.setMessage("Please wait,Sign in is in progress");
                                        //mProgressDialog.setCanceledOnTouchOutside(false);
                                        mProgressDialog.show();

                                        Intent mainIntent = new Intent(SturegActivity.this,MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        //finish();





                                    }
                                });




                            }

                        }
                    });




                }

                else {
                    mProgressDialog.dismiss();
                    Toast.makeText(SturegActivity.this,"Cannot Sign in.Please check the form and try again",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}