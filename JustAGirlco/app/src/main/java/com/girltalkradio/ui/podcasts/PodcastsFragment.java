package com.girltalkradio.ui.podcasts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

public class PodcastsFragment extends Fragment {

    private onFragmentBtnSelected listener;

    private PodcastsViewModel podcastsViewModel;

    RecyclerView podcastList;

    List<String> titles;
    List<String> imageStrings;
    Adapter adapter;
    RssReader reader;

    String[] hardcodedScumPodcasts = {"https://anchor.fm/s/a3e10d4/podcast/rss","https://anchor.fm/s/7f66de4/podcast/rss","https://feed.podbean.com/hotflashescooltopics/feed.xml","http://www.omnycontent.com/d/playlist/f4077a89-6f34-4e4a-88a6-a89e006843e6/117a7e3a-81a5-44f1-9a86-a8a4018720a0/e2ba843f-68dd-4fce-becd-a8a401872217/podcast.rss","https://anchor.fm/s/3b2d3670/podcast/rss"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        podcastsViewModel =
                new ViewModelProvider(this).get(PodcastsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        podcastList = root.findViewById(R.id.podcast_list);      //recycler view
        titles = new ArrayList<>();
        imageStrings = new ArrayList<>();



//        for(int i = 0; i < hardcodedScumPodcasts.length ; i++){
//            reader = new RssReader(hardcodedScumPodcasts[i]);
//            String img = reader.getRssImage();
//            imageStrings.add(img);
//            titles.add("First Item");
//        }


        reader = new RssReader("https://anchor.fm/s/a3e10d4/podcast/rss");
        RssReader.ProcessInBackground process= reader.new ProcessInBackground();
        process.execute();

        //FIGURE OUT HOW TO ADD SOME SORT OF WAIT UNTIL THE ASYNC PROCESS IS DONE TO THEN POPULATE THE UI
        String img = reader.getRssImage();
        imageStrings.add(img);

        //Log.d("IMAGE",imageStrings.get(0));


         titles.add("First Item");
//        titles.add("Second Item");
//        titles.add("Third Item");
//        titles.add("Fourth Item");

        adapter = new Adapter(getActivity(), titles, imageStrings);
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