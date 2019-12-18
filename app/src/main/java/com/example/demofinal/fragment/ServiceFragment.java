package com.example.demofinal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofinal.MainActivity;
import com.example.demofinal.R;
import com.example.demofinal.ServiceActivity;
import com.example.demofinal.utility.MyAlert;
import com.example.demofinal.utility.ServiceAdapter;
import com.example.demofinal.utility.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceFragment extends Fragment {
    private String nameString, currentPostString, uidString;
    private String tag = "27MarV1";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createToolbar();

        FindMyMe(); // get data element from firebase
        //   Post Controller
        postController();
        //Create RecycleView
        createRecycleView();
        //Create Toolbar
        createToolbar();
    }// Main method

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarService);
        ((ServiceActivity) getActivity()).setSupportActionBar(toolbar);
        ((ServiceActivity) getActivity()).getSupportActionBar().setTitle("Service");
        ((ServiceActivity) getActivity()).getSupportActionBar().setSubtitle("Please Choose Photo and Fill All Blank");
        ((ServiceActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((ServiceActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_service, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSignOut) {
            Log.d(tag, "Sign out leow!");
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        moveToMainFragment();
    }

    private void moveToMainFragment() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }


    private void createRecycleView() {
        final RecyclerView recyclerView = getView().findViewById(R.id.recycleViewUser);
        final int[] countInts = new int[]{0};
        final ArrayList<String> photoStringArrayList = new ArrayList<>();
        final ArrayList<String> nameStringArrayList = new ArrayList<>();
        final ArrayList<String> postStringArrayList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = (int) dataSnapshot.getChildrenCount(); // Get numbers of child
                ArrayList<UserModel> modelArrayList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UserModel userModel = dataSnapshot1.getValue(UserModel.class);
                    modelArrayList.add(userModel);

                } // for loop

                ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(), modelArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void FindMyMe() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uidString = firebaseAuth.getCurrentUser().getUid();
        Log.d(tag, "uid => " + uidString);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(uidString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                nameString = String.valueOf(map.get("nameString"));
                currentPostString = String.valueOf(map.get("myPostString"));
                Log.d(tag, "Name ==> " + nameString);
                Log.d(tag, "Post ==> " + currentPostString);
                //                Toast.makeText(getActivity(), "Name => " + nameString, Toast.LENGTH_SHORT).show();
                               }
                @Override public void onCancelled (@NonNull DatabaseError databaseError){
                }
            });
        }

        private void postController () {
            Button button = getView().findViewById(R.id.btnPost);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = getView().findViewById(R.id.editPost);
                    String postString = editText.getText().toString().trim();
                    if (postString.isEmpty()) {
                        MyAlert myAlert = new MyAlert(getActivity());
                        myAlert.normalDialog("Post False", "Please type on Post");
                    } else {
                        editCurrentPost(postString.trim());
                        editText.setText("");
                    }
                }
            });
        }

        private void editCurrentPost (String postString){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(uidString);

            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("myPostString", changeMyData(postString.trim()));
            databaseReference.updateChildren(stringObjectMap);
        }

        private String changeMyData (String postString){
            String resultString = null;
            resultString = currentPostString.substring(1, currentPostString.length() - 1);
            String[] strings = resultString.split(",");
            ArrayList<String> stringArrayList = new ArrayList<>();

            for (int i = 0; i < strings.length; i += 1) {
                stringArrayList.add(strings[i]);
            }
            stringArrayList.add(postString);
            return stringArrayList.toString();
        }


        @Nullable
        @Override public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_service, container, false);
            return view;
        } // onCreate view method
         }











