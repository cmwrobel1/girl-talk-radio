package com.girltalkradio;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This Activity will serve as the calling instance for the MediaPlayerService
 * This will be connected to the RSS Activity so that when the specific url is clicked
 * it will be passed to this class.
 *
 * Then the service will run this url.
 *
 * Might need to change this later to implement a Recycler View
 */



public class ListeningScreenActivity extends AppCompatActivity {

    public MediaPlayerService player;      //instance of the service
    boolean serviceBound = false;

    int currentPosition = 0;
    boolean isRunning = false;

    SeekBar seekBar;
    TextView currTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // player.seeker();

//        String currentTime = b.getString("CurrentTime");
//        currTextView.setText(currentTime);

        //int time = player.showTime();

        Intent in = getIntent();
        Bundle b = in.getExtras();
        String s = b.getString("url");
        String pic = b.getString("pic");


        //seekBar.setMax(player.getTotalDuration());

        //int durationTime = player.getTotalDuration();

        ImageButton seekBackButton = (ImageButton) findViewById(R.id.seekBack);
        ImageButton seekForwardButton = (ImageButton) findViewById(R.id.seekForward);


        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        ImageButton pauseButton = (ImageButton) findViewById(R.id.pauseButton);

        seekBar = (SeekBar) findViewById(R.id.seekBar);


        currTextView = (TextView) findViewById(R.id.TVcurr);
        TextView durrTextView = (TextView) findViewById(R.id.TVmax);

        //Displaying the max duration of the audio file
        Uri uriTest = Uri.parse(s);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(s);
        String dStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int mili = Integer.parseInt(dStr);
        dStr = DateUtils.formatElapsedTime(mili / 1000);
        durrTextView.setText(dStr);
        seekBar.setMax(mili / 1000);


        //Display the picture for the audio file
        ImageView i = (ImageView)findViewById(R.id.imageViewListening);
        Picasso.get().load(pic).into(i);

        //Start play button hidden
        playButton.setVisibility(View.INVISIBLE);





        playAudio(s);



        seekBar.setProgress(getPos());
        String stringTest = DateUtils.formatElapsedTime(getPos() / 1000);
        currTextView.setText(stringTest);




        seekBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekBack15Seconds();
            }
        });

        seekForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekForward15Seconds();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ListeningScreenActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                player.pauseMedia();
                pauseButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);

            }
        });

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ListeningScreenActivity.this, "Play", Toast.LENGTH_SHORT).show();
                player.resumeMedia();
                pauseButton.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.INVISIBLE);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean user) {
                //player.seekTo(i);
                if(user) {
                    player.seekTo(i * 1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }




    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        // Seek bar
        Handler podcastMethodsHandler = new Handler();
        Runnable musicRun = new Runnable() {

            @Override
            public void run() {
                if (serviceBound == true) { // Check if service bounded

                    int musicCurTime = player.getPosition();

                    seekBar.setProgress(musicCurTime / 1000);
                    String stringTest = DateUtils.formatElapsedTime(musicCurTime / 1000);
                    currTextView.setText(stringTest);

                    podcastMethodsHandler.postDelayed(this, 1000);
                }
            }
        };
        podcastMethodsHandler.postDelayed(musicRun,1000);


//        //Volume
//        Handler podcastVolumeHandler = new Handler();
//        Runnable volumeRun = new Runnable() {
//
//            @Override
//            public void run() {
//                if (serviceBound == true) { // Check if service bounded
//
//                    AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//                    // For example to set the volume of played media to maximum.
//                    audioManager.setStreamVolume (AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
//
//                    podcastVolumeHandler.postDelayed(this, 1000);
//                }
//            }
//        };
//        podcastVolumeHandler.postDelayed(musicRun,1000);
    }
    @Override
    protected void onStop() {
        super.onStop();
        player.stopMedia();
        unbindService(serviceConnection);
        serviceBound = false;
    }






    public int getPos(){
        Intent in = getIntent();
        Bundle b = in.getExtras();
        int p = b.getInt("pos");
        return p;

    }


    private void pauseAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            playerIntent.putExtra("media", media);
            player.pauseMedia();
            stopService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver

        }
    }


    private void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver

        }
    }

    //testing binding stuff





    //Binding this Client to the AudioPlayer Service
    //Services need to be "bound" to something so that they will begin execution
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(ListeningScreenActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

}