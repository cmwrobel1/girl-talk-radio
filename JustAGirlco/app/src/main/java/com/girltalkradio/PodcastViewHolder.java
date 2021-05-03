package com.girltalkradio;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class PodcastViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    ImageView image;


    public PodcastViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textView2);
        image = itemView.findViewById(R.id.imageView2);
    }

    public void setItem(Podcast pod){
        title.setText(pod.getTitle());
        Uri authorImage = Uri.parse(pod.getImage());
        Picasso.get().load(authorImage).into(image);
    }

}