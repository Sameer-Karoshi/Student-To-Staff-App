package com.sameer.studenttostaff.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sameer.studenttostaff.FacultyObject.Faculty;
import com.sameer.studenttostaff.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {


    private static final String TAG = "ContactAdapter";
    private Activity mContext;
    private List<Faculty> mList;


    public ContactAdapter(Activity mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {


       /* boolean Flag = false;


        //--------------------------------------------------------------

        try {

            String StartTime = "10:00:00" ;
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(StartTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String EndTime = "20:00:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(EndTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar obj = Calendar.getInstance();
            String CurrentTime = obj.getTime().toString();
            Date d = new SimpleDateFormat("HH:mm:ss").parse(CurrentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                Flag = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------------
        Log.d("Sameer", "onBindViewHolder: "+Flag);

        */





        final Faculty faculty = mList.get(position);
        holder.mName.setText("  Prof." + faculty.getName());
        holder.mNumber.setText("  +91" + faculty.getContact_Number());
        //final boolean finalFlag = Flag;
        holder.mCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {






                //String CurrentTime = CalObj.getTime().toString();

               // String StartTime = "10:00:00";
                //String EndTime = "17:00:00"



                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", faculty.getContact_Number(), null));

                        mContext.startActivity(intent);





            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mNumber;
        private ImageView mCall;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mNumber = itemView.findViewById(R.id.department);
            mCall = itemView.findViewById(R.id.cl_call);
        }
    }


}
