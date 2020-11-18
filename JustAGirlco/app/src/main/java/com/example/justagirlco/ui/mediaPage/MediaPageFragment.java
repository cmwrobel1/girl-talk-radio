package com.example.justagirlco.ui.mediaPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.justagirlco.R;

public class MediaPageFragment extends Fragment {

    private MediaPageViewModel mediaPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mediaPageViewModel =
                new ViewModelProvider(this).get(MediaPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_media_page, container, false);
        final TextView textView = root.findViewById(R.id.text_mediaPage);
        mediaPageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}