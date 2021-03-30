package com.girltalkradio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class RssActivity extends AppCompatActivity {

    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> pictures;
    String podcastFromRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        Intent intent = getIntent();

        lvRss = (ListView) findViewById(R.id.LvRss);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        pictures = new ArrayList<String>();

        titles.add("Jack's MP3 Test");
        links.add("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3");
        pictures.add("https://i1.sndcdn.com/artworks-zjw8CwzCVpT3nLGO-tEVPtQ-t500x500.jpg");

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
                Log.d("LISTEN URL",links.get(position));
                in.putExtras(b1);
                in.putExtras(b2);
                RssActivity.this.startActivity(in);
            }
        });
        new ProcessInBackground().execute();
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