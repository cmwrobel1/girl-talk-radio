package com.girltalkradio.ui.multimedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.girltalkradio.R;
import com.girltalkradio.MultimediaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultimediaFragment extends Fragment {

    private MultimediaViewModel multimediaViewModel;
    RecyclerView multList;

    List<MultimediaPost> multimediaPostList = new ArrayList<>();
    List<String> texts = new ArrayList<>();
    List<String> imageStrings = new ArrayList<>();

    MultimediaAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        multimediaViewModel = new ViewModelProvider(this).get(MultimediaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_multimedia, container, false);

        multList = root.findViewById(R.id.multimedia_list);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("multimedia");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                texts.clear();
                imageStrings.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    MultimediaPost multimediaPost = postSnapshot.getValue(MultimediaPost.class);
                    texts.add(multimediaPost.getText());
                    imageStrings.add(multimediaPost.getImageUrl());
                }
                adapter = new MultimediaAdapter(getActivity(), texts, imageStrings);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
                multList.setLayoutManager(gridLayoutManager);
                multList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error Loading Image", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private ArrayList<String> collectImages(Map<String, Object> images) {
        ArrayList<String> imageUrls = new ArrayList<>();
        for (Map.Entry<String, Object> entry : images.entrySet()) {
            Map singleImage = (Map) entry.getValue();
            imageUrls.add((String) singleImage.get("imageUrl"));
        }
        return imageUrls;
    }
}