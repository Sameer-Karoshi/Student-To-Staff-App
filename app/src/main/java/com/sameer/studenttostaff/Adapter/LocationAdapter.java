package com.sameer.studenttostaff.Adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sameer.studenttostaff.FacultyObject.Faculty;
import com.sameer.studenttostaff.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {


    private static final String TAG = "ContactAdapter";
    private Activity mContext;
    private List<Faculty> mList;


    public LocationAdapter(Activity mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.location_layout, parent, false);
        return new LocationViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {






        final Faculty faculty = mList.get(position);
        holder.mName.setText("Name: " + "  Prof." + faculty.getName());
        holder.mDepartment.setText("Department: "+faculty.getDepartment());
        holder.mPost.setText("Post: "+faculty.getPost());
        holder.mCabinNumber.setText("Cabin Number: " + faculty.getCabinNumber());
        holder.mLocation.setText("Location: "+faculty.getLocation());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mDepartment;
        private TextView mPost;
        private  TextView mCabinNumber;
        private TextView mLocation;


        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mDepartment = itemView.findViewById(R.id.department);
            mPost = itemView.findViewById(R.id.post);
            mCabinNumber = itemView.findViewById(R.id.cabin_number);
            mLocation = itemView.findViewById(R.id.location);
        }
    }


}
