package com.example.demofinal.utility;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.demofinal.R;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private Context context;
    private ArrayList<String> photoStringArrayList, nameStringArrayList, postStringArrayList;
    private LayoutInflater layoutInflater;
    ArrayList<UserModel> userList;

    public ServiceAdapter(Context context, ArrayList<String> photoStringArrayList, ArrayList<String> nameStringArrayList, ArrayList<String> postStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.photoStringArrayList = photoStringArrayList;
        this.nameStringArrayList = nameStringArrayList;
        this.postStringArrayList = postStringArrayList;
    }

    public ServiceAdapter(FragmentActivity activity, ArrayList<UserModel> modelArrayList) {
        userList = modelArrayList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_view_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        UserModel userModel = userList.get(position);
        String uriPathString = userModel.getPathUrlString();
        String nameString = userModel.getNameString();
        String postString = userModel.getMyPostString();
        holder.nameTextView.setText(nameString);
        holder.postTextView.setText(postString);
        Picasso.get().load(uriPathString).resize(150, 150).into(holder.circleImageView);
    }


    @Override
    public int getItemCount() {
        //  return nameStringArrayList.size();
        return userList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView nameTextView, postTextView;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circlePhoto);
            nameTextView = itemView.findViewById(R.id.txtName);
            postTextView = itemView.findViewById(R.id.txtPost);
        }
    }
    // Service class
}