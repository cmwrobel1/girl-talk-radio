package com.girltalkradio.ui.podcasts;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.girltalkradio.RssActivity;
import com.girltalkradio.RssReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PodcastsFragment extends Fragment {

    private onFragmentBtnSelected listener;

    private PodcastsViewModel podcastsViewModel;

    RecyclerView podcastList;

    List<String> titles;
    List<String> imageStrings;
    Adapter adapter;
    RssReader reader;
    String rss;

    String[] hardcodedScumPodcasts = {"https://anchor.fm/s/a3e10d4/podcast/rss","https://anchor.fm/s/7f66de4/podcast/rss","https://feed.podbean.com/hotflashescooltopics/feed.xml","http://www.omnycontent.com/d/playlist/f4077a89-6f34-4e4a-88a6-a89e006843e6/117a7e3a-81a5-44f1-9a86-a8a4018720a0/e2ba843f-68dd-4fce-becd-a8a401872217/podcast.rss","https://anchor.fm/s/3b2d3670/podcast/rss"};
    private String[] rssAuthorImage = new String[hardcodedScumPodcasts.length];
    private String[] rssAuthorTitle = new String[hardcodedScumPodcasts.length];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        podcastsViewModel =
                new ViewModelProvider(this).get(PodcastsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        podcastList = root.findViewById(R.id.podcast_list);      //recycler view
        titles = new ArrayList<>();
        imageStrings = new ArrayList<>();


        //executing the AsyncThread to get the images and titles
            try {
                new ProcessInBackground().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

    public InputStream getInputStream(URL url) {
        try {
            //if no error, then try to open the connection to the url
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            //if error return null
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading Podcasts");        //message for long loading screen
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {
            //this is only coded to get the images and titles necessary for this fragment
            for (int i = 0; i < hardcodedScumPodcasts.length; i++) {
                try {

                    URL url = new URL(hardcodedScumPodcasts[i]);       //girl talk radio RSS feed
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();          //xml pull parser gets data from rss xml doc

                    factory.setNamespaceAware(false); //no support for xml namespaces?

                    XmlPullParser xpp = factory.newPullParser();        //xml parser
                    xpp.setInput(getInputStream(url), "UTF_8");     //set input to the rss url and note that encoding is utf-8

                    boolean insideItem = false;     //data to extract is inside the item tags only -- dont want tags ex:<title>
                    boolean insideImageTag = false;
                    int eventType = xpp.getEventType();     //this tells you what thing ur on in the while loop below

                    while (eventType != XmlPullParser.END_DOCUMENT) {     //keep executing until you reach end of document
                        if (eventType == XmlPullParser.START_TAG) {
                            //specific podcast info
                            //Author Image and Author Title urls
                            if (eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("image")) {
                                insideImageTag = true;
                            } else if (xpp.getName().equalsIgnoreCase("url")) {
                                if (insideImageTag) {
                                    imageStrings.add(xpp.nextText());
                                }
                            } else if (xpp.getName().equalsIgnoreCase("title")) {
                                if (insideImageTag) {
                                    titles.add(xpp.nextText());
                                }
                            }
                        } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("image")) {
                            insideImageTag = false;
                        }
                        eventType = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return exception;
        }
    }
}