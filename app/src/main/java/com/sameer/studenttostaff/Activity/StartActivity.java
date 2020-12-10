package com.sameer.studenttostaff.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sameer.studenttostaff.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button need_a_new_account = (Button)findViewById(R.id.need_a_new_account);
        need_a_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRegisterDialog();

            }
        });
        Button already_have_a_account = (Button)findViewById(R.id.already_have_account);
        already_have_a_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(logIntent);
            }
        });
    }

    private void ShowRegisterDialog(){
        final String[] listItems = {"Register as a Student","Register as a Faculty"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(StartActivity.this);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0){
                    Intent stuIntent = new Intent(StartActivity.this,SturegActivity.class);
                    startActivity(stuIntent);
                }
                if (i == 1){
                    Intent facIntent = new Intent(StartActivity.this,FacregActivity.class);
                    startActivity(facIntent);
                }

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }
}