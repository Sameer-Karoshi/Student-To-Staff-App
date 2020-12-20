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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {


    private static final String TAG = "ContactAdapter";
    private Activity mContext;
    private List<Faculty> mList;


    public ChatAdapter(Activity mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_layout, parent, false);
        return new ChatViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {






        final Faculty faculty = mList.get(position);
        holder.mName.setText("  Prof." + faculty.getName());
        holder.mNumber.setText("  +91" + faculty.getContact_Number());
        //final boolean finalFlag = Flag;
        holder.mCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {





                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.setData(Uri.parse("sms:" + faculty.getContact_Number()));
                //intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                //startActivity(intent);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.setData(Uri.parse("sms:" + faculty.getContact_Number()));

                mContext.startActivity(sendIntent);





            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mNumber;
        private ImageView mCall;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mNumber = itemView.findViewById(R.id.department);
            mCall = itemView.findViewById(R.id.cl_call);
        }
    }


}
