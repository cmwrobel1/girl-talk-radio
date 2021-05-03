package com.girltalkradio.ui.podcasts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.database.paging.LoadingState;
//import com.girltalkradio.AuthorAdapter;
import com.girltalkradio.Podcast;
import com.girltalkradio.PodcastViewHolder;
import com.girltalkradio.R;
import com.girltalkradio.RssActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PodcastsFragment extends Fragment{

    private PodcastsViewModel podcastsViewModel;
    RecyclerView podcastList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    FirebaseRecyclerPagingAdapter<Podcast, PodcastViewHolder> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        podcastsViewModel =
                new ViewModelProvider(this).get(PodcastsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        mSwipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);

        podcastList = root.findViewById(R.id.podcast_list);      //recycler view
        podcastList.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        podcastList.setLayoutManager(gridLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://justagirlco-b4de1-default-rtdb.firebaseio.com/1StAH6C83cDbx5l8WidtkteVUpwXHi2DBcpJtd4WGpCY/podcasts");

        // This configuration comes from the Paging Support Library
        // https://developer.android.com/reference/androidx/paging/PagedList.Config
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setPageSize(5)
                .build();

        // The options for the adapter combine the paging configuration with query information
        // and application-specific options for lifecycle, etc.
        DatabasePagingOptions<Podcast> options = new DatabasePagingOptions.Builder<Podcast>()
                .setLifecycleOwner(this)
                .setQuery(myRef, config, Podcast.class)
                .build();

        adapter = new FirebaseRecyclerPagingAdapter<Podcast, PodcastViewHolder>(options) {

            @NonNull
            @Override
            public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid_layout,parent,false);
                return new PodcastViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PodcastViewHolder holder, int position, @NonNull Podcast model) {
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

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                switch(state){
                    case LOADING_INITIAL:
                        //the initial load has begun

                    case LOADING_MORE:
                        //the adapter has started to load an additional page
                        mSwipeRefreshLayout.setRefreshing(true);
                        break;

                    case LOADED:
                        //the previous load (either initial or additional) completed
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;

                    case FINISHED:
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;

                    case ERROR:
                        //the previous load (either initial or additional) failed. call the
                        //retry() method in order to retry the load operation
                        retry();
                        break;

                }
            }

            @Override
            protected void onError(@NonNull DatabaseError databaseError) {
                super.onError(databaseError);
                mSwipeRefreshLayout.setRefreshing(false);
                databaseError.toException().printStackTrace();
            }

        };

        //set Adapter to the recyclerview
        podcastList.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });

        return root;
    }

    //Start Listening Adapter
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //Stop Listening Adapter
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}