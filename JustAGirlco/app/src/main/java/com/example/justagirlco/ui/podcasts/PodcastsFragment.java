package com.example.justagirlco.ui.podcasts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.justagirlco.R;

public class PodcastsFragment extends Fragment {

    private onFragmentBtnSelected listener;

    private PodcastsViewModel podcastsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        podcastsViewModel =
                new ViewModelProvider(this).get(PodcastsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        //button create
        Button clickme = root.findViewById(R.id.button2);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                listener.onButtonSelected();
            }
        });

        final TextView textView = root.findViewById(R.id.text_podcasts);
        podcastsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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
        public void onButtonSelected();
    }
}