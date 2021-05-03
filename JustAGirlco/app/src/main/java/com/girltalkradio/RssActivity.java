package com.girltalkradio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssActivity extends AppCompatActivity {

    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> pictures;
    String podcastFromRecycler;
    RecyclerView podcastList;
    RecyclerView.Adapter<PodsListViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        Intent intent = getIntent();

        //lvRss = (ListView) findViewById(R.id.LvRss);
        podcastList = findViewById(R.id.recview);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        pictures = new ArrayList<String>();



        Bundle b = intent.getExtras();
        podcastFromRecycler = b.getString("url");


        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position));       //this will open an http link to an mp3 file lol
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri); //change this so that it will move to the PodcastListen Activity or Fragment?
                Intent in = new Intent(RssActivity.this, ListeningScreenActivity.class);
                Bundle b1 = new Bundle();
                Bundle b2 = new Bundle();
                b1.putString("pic",pictures.get(position));
                b2.putString("url",links.get(position));
                in.putExtras(b1);
                in.putExtras(b2);
                RssActivity.this.startActivity(in);
            }
        });
        new ProcessInBackground().execute();

        adapter = new RecyclerView.Adapter<PodsListViewHolder>(){
        List<String> titles;
        List<String> imageStrings;
        List<String>mp3;
        LayoutInflater inflater;
        Context context;

            @NonNull
            @Override
            public PodsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
                return new PodsListViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull PodsListViewHolder holder, int position) {
                holder.title.setText(titles.get(position));
                //convert the images to urls and use picasso?
                Uri authorImage = Uri.parse(imageStrings.get(position));       //convert the string to uri
                Picasso.get().load(authorImage).into(holder.image);
                //  holder.gridIcon.setImageResource(images.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle b1 = new Bundle();
                        b1.putString("url",mp3.get(position));
                        Intent in = new Intent(RssActivity.this, ListeningScreenActivity.class);
                        in.putExtras(b1);
                        RssActivity.this.startActivity(in);
                    }
                });

            }
            @Override
            public int getItemCount() {
                return titles.size();
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        podcastList.setLayoutManager(gridLayoutManager);
        podcastList.setAdapter(adapter);

    }


    //make a connection with the podcast URL
    public InputStream getInputStream(URL url) {
        try {
            //if no error, then try to open the connection to the url
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            //if error return null
            return null;
        }
    }

    //go into a new thread to read the document
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(RssActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Rss feed is loading.. please wait.. :)");        //message for long loading screen
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try {
                URL url = new URL(podcastFromRecycler);       //girl talk radio RSS feed
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();          //xml pull parser gets data from rss xml doc

                factory.setNamespaceAware(false); //no support for xml namespaces?

                XmlPullParser xpp = factory.newPullParser();        //xml parser
                xpp.setInput(getInputStream(url), "UTF_8");     //set input to the rss url and note that encoding is utf-8

                boolean insideItem = false;     //data to extract is inside the item tags only -- dont want tags ex:<title>
                int eventType = xpp.getEventType();     //this tells you what thing ur on in the while loop below

                while (eventType != XmlPullParser.END_DOCUMENT) {     //keep executing until you reach end of document
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            //first make sure you're inside an item tag
                            if (insideItem) {
                                //save the title into the titles array list
                                titles.add(xpp.nextText());         //nextText returns the data inside that tag
                            }
                        } else if (xpp.getName().equalsIgnoreCase("enclosure")) {
                            if (insideItem) {
                                links.add(xpp.getAttributeValue(0));            //this gets the mp3 url
                            }
                        }
                          else if (xpp.getName().equalsIgnoreCase("itunes:image")) { //Gets picture for podcast
                              if (insideItem) {
                                  pictures.add(xpp.getAttributeValue(0));
                              }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (MalformedURLException e) {    //if url is not correct
                exception = e;
            } catch (XmlPullParserException e) {   //if u can't get data from rss
                exception = e;
            } catch (IOException e) {      //input and output - timeout errors
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RssActivity.this, android.R.layout.simple_list_item_1, titles);
            lvRss.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}