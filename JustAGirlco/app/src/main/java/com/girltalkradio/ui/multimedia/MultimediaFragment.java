package com.girltalkradio.ui.multimedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.girltalkradio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class MultimediaFragment extends Fragment {

    private MultimediaViewModel multimediaViewModel;
    ImageView multImage;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        multimediaViewModel = new ViewModelProvider(this).get(MultimediaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_multimedia, container, false);

        multImage = root.findViewById(R.id.multimedia_image);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("multimedia");
        DatabaseReference getImage = databaseReference.child("profilePic").child("imageUrl");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> temp = collectImages((Map<String, Object>) snapshot.getValue());
                String link = temp.get(1);//snapshot.getValue(String.class);
                Picasso.get().load(link).into(multImage);
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