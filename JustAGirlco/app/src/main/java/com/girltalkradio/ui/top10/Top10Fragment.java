package com.girltalkradio.ui.top10;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.girltalkradio.Podcast;
import com.girltalkradio.R;
import com.girltalkradio.RssActivity;
import com.girltalkradio.Top10ViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Top10Fragment extends Fragment {

    private Top10ViewModel top10ViewModel;
    RecyclerView recview;
    FirebaseRecyclerOptions<Podcast> options;
    FirebaseRecyclerAdapter<Podcast , Top10ViewHolder> theAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        top10ViewModel =
                new ViewModelProvider(this).get(Top10ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top_10, container, false);

        recview = (RecyclerView)root.findViewById(R.id.recview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://justagirlco-b4de1-default-rtdb.firebaseio.com/1StAH6C83cDbx5l8WidtkteVUpwXHi2DBcpJtd4WGpCY/top10");

       // podcastListener = this;
        options = new FirebaseRecyclerOptions.Builder<Podcast>().setQuery(myRef, Podcast.class).build();

       // adapter = new Top10Adapter(options,podcastListener,getActivity());
        theAdapter = new FirebaseRecyclerAdapter<Podcast, Top10ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Top10ViewHolder holder, int position, @NonNull Podcast model) {
                holder.setItem(model);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle b1 = new Bundle();
                        b1.putString("url",model.getRss());
                        Intent in = new Intent(getActivity(), RssActivity.class);
                        in.putExtras(b1);
                        getActivity().startActivity(in);
                    }
                });
            }

            @NonNull
            @Override
            public Top10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_10_grid,parent,false);
                return new Top10ViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recview.setLayoutManager(gridLayoutManager);
        recview.setAdapter(theAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        theAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        theAdapter.stopListening();
    }

//    @Override
//    public void onPodcastListner(int position) {
//        //add click listener code here to flip to the Rss Activity to list the podcasts
//       // System.out.println("clicked position: " + position);
//        System.out.println(adapter.getRef(position).get());
////        Bundle b1 = new Bundle();
////        b1.putString("url",podUrl);
////        Intent in = new Intent(getActivity(), RssActivity.class);
////        in.putExtras(b1);
////        getActivity().startActivity(in);
//    }

}
