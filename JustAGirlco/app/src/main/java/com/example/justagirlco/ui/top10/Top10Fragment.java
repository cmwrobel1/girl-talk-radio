package com.example.justagirlco.ui.top10;

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

public class Top10Fragment extends Fragment {

    private Top10ViewModel top10ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        top10ViewModel =
                new ViewModelProvider(this).get(Top10ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top_10, container, false);
        final TextView textView = root.findViewById(R.id.text_top_10);
        top10ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}