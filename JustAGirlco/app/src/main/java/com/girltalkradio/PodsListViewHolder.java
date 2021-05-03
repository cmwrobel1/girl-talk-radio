package com.girltalkradio;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/**
 * This class is specifically for the viewholder for the recyclerview for the list of podcasts page for each author. (for RSSActivity)
 */
public class PodsListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;


        public PodsListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView4);
            image = itemView.findViewById(R.id.imageView3);
        }

        public void setItem(Podcast pod) {
            title.setText(pod.getTitle());
            Uri authorImage = Uri.parse(pod.getImage());
            Picasso.get().load(authorImage).into(image);
        }

}
