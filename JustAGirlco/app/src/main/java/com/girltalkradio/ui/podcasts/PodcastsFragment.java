package com.girltalkradio.ui.podcasts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.girltalkradio.Adapter;
import com.girltalkradio.R;
import com.girltalkradio.RssReader;

import java.util.ArrayList;
import java.util.List;

public class PodcastsFragment extends Fragment {

    private onFragmentBtnSelected listener;

    private PodcastsViewModel podcastsViewModel;

    RecyclerView podcastList;

    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RssReader reader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        podcastsViewModel =
                new ViewModelProvider(this).get(PodcastsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        podcastList = root.findViewById(R.id.podcast_list);      //recycler view
        titles = new ArrayList<>();
        images = new ArrayList<>();

//        String rss = getString(R.string.CompilherPodcast);
//        reader = new RssReader(rss);
//        String img = reader.getRssImage();



        titles.add("First Item");
        titles.add("Second Item");
        titles.add("Third Item");
        titles.add("Fourth Item");

        images.add(R.drawable.rp2);
        images.add(R.drawable.rp2);
        images.add(R.drawable.rp3);
        images.add(R.drawable.rp3);

        adapter = new Adapter(getActivity(), titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        podcastList.setLayoutManager(gridLayoutManager);
        podcastList.setAdapter(adapter);

        //button create
        Button clickme = root.findViewById(R.id.button2);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                listener.onButtonSelected();
            }
        });

       // final TextView textView = root.findViewById(R.id.text_podcasts);
//        podcastsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;

    }
    //attach button to the fragment
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        }else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }
    //this is the function that will pass the button click listener between the activity and the fragment
    public interface onFragmentBtnSelected{
        boolean onNavigationItemSelected(@NonNull MenuItem item);

        public void onButtonSelected();
    }
}