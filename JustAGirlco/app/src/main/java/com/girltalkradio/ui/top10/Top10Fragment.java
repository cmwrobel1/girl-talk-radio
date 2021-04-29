package com.girltalkradio.ui.top10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.girltalkradio.PodcastTop10;
import com.girltalkradio.R;
import com.girltalkradio.Top10Adapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Top10Fragment extends Fragment implements Top10Adapter.onPodcastListener {

    private Top10ViewModel top10ViewModel;
    Top10Adapter adapter;
    RecyclerView recview;
    FirebaseRecyclerOptions<PodcastTop10> options;
    private Top10Adapter.onPodcastListener podcastListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        top10ViewModel =
                new ViewModelProvider(this).get(Top10ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top_10, container, false);

        recview = (RecyclerView)root.findViewById(R.id.recview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://justagirlco-b4de1-default-rtdb.firebaseio.com/1StAH6C83cDbx5l8WidtkteVUpwXHi2DBcpJtd4WGpCY/top10");

        podcastListener = this;
        options = new FirebaseRecyclerOptions.Builder<PodcastTop10>().setQuery(myRef, PodcastTop10.class).build();

        adapter = new Top10Adapter(options,podcastListener,getActivity());

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
        //add click listener code here to flip to the Rss Activity to list the podcasts
    }

}
