package com.girltalkradio.ui.top10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.girltalkradio.Podcast;
import com.girltalkradio.R;
import com.girltalkradio.Top10Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Top10Fragment extends Fragment implements Top10Adapter.onPodcastListener {

    private Top10ViewModel top10ViewModel;
    Top10Adapter adapter;
    RecyclerView recview;
    FirebaseRecyclerOptions<Podcast> options;
    private Top10Adapter.onPodcastListener podcastListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        top10ViewModel =
                new ViewModelProvider(this).get(Top10ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top_10, container, false);

        recview = (RecyclerView)root.findViewById(R.id.recview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://justagirlco-b4de1-default-rtdb.firebaseio.com/1StAH6C83cDbx5l8WidtkteVUpwXHi2DBcpJtd4WGpCY/top10");

                myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Podcast> pods = (ArrayList<Podcast>) snapshot.getValue();

                System.out.println(pods);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        podcastListener = this;
        options = new FirebaseRecyclerOptions.Builder<Podcast>().setQuery(myRef, Podcast.class).build();

        adapter = new Top10Adapter(options,podcastListener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recview.setLayoutManager(gridLayoutManager);
        recview.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onPodcastListner(int position) {

    }

}
