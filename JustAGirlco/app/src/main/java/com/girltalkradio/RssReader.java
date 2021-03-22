package com.girltalkradio;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RssReader {
    ArrayList<String> titles;
    ArrayList<String> links;

    private String rssAuthorImage;
    private String rssAuthorTitle;
    private String rss;


    public RssReader(String rss){
        this.rss = rss;

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

    //getters
    public String getRssImage() {
//        Log.d("getRss",titles.get(0));
        return rssAuthorImage;
    }
    public String getRssTitle(){ return rssAuthorTitle;}

    //go into a new thread to read the document
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        // ProgressDialog progressDialog = new ProgressDialog(RssActivity.class);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  progressDialog.setMessage("Rss feed is loading.. please wait.. :)");        //message for long loading screen
            // progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try {
                URL url = new URL(rss);       //girl talk radio RSS feed
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();          //xml pull parser gets data from rss xml doc

                factory.setNamespaceAware(false); //no support for xml namespaces?

                XmlPullParser xpp = factory.newPullParser();        //xml parser
                xpp.setInput(getInputStream(url), "UTF_8");     //set input to the rss url and note that encoding is utf-8

                boolean insideItem = false;     //data to extract is inside the item tags only -- dont want tags ex:<title>
                boolean insideImageTag = false;
                int eventType = xpp.getEventType();     //this tells you what thing ur on in the while loop below

                Log.d("CHECK", "can i log in here?");
                while (eventType != XmlPullParser.END_DOCUMENT) {     //keep executing until you reach end of document
                    if (eventType == XmlPullParser.START_TAG) {
                        //specific podcast info
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        }else if (xpp.getName().equalsIgnoreCase("title")) {
                            //first make sure you're inside an item tag
                            if (insideItem) {
                                //save the title into the titles array list
                                titles.add(xpp.nextText());         //nextText returns the data inside that tag
                            }
                        } else if (xpp.getName().equalsIgnoreCase("enclosure")) {
                            if (insideItem) {
                                links.add(xpp.getAttributeValue(2));            //this gets the mp3 url
                            }
                        }

                        //Author Image and Author Title urls
                        if (eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("image") ){
                            insideImageTag = true;
                        }else if (xpp.getName().equalsIgnoreCase("url")){
                            if(insideImageTag){
                                rssAuthorImage = xpp.nextText();
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("title")){
                            if(insideImageTag){
                                rssAuthorTitle = xpp.nextText();
                            }
                        }

                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("image")) {
                        insideImageTag = false;
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
    }




}
