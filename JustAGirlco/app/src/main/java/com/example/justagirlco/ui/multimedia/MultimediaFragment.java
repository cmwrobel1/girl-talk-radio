package com.example.justagirlco.ui.multimedia;

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

public class MultimediaFragment extends Fragment {

    private MultimediaViewModel multimediaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        multimediaViewModel =
                new ViewModelProvider(this).get(MultimediaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_multimedia, container, false);
        final TextView textView = root.findViewById(R.id.text_multimedia);
        multimediaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}