package com.girltalkradio;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class Top10ViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView num;
    ImageView image;

    public Top10ViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textView2);
        image = itemView.findViewById(R.id.imageView);
        num = itemView.findViewById(R.id.num);

    }
    public void setItem(Podcast pod) {
        title.setText(pod.getTitle());
        num.setText(String.valueOf(pod.getOrder()));
        Uri authorImage = Uri.parse(pod.getImage());
        Picasso.get().load(authorImage).into(image);
    }

}