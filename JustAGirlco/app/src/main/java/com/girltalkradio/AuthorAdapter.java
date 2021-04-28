package com.girltalkradio;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {

    List<String> titles;
    List<String>imageStrings;
    List<String>descriptions;
    LayoutInflater inflater;
    Context context;
    private onPodcastListener onPodcastListener;

    //pagination variables
    private boolean isLoadingAdded = false;

    public AuthorAdapter(Context ctx, List<String> titles, List<String> imagesStr, onPodcastListener onPodcastListener){
        this.titles = titles;
        this.imageStrings = imagesStr;
        this.context = ctx;
        this.onPodcastListener = onPodcastListener;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ViewHolder(view,onPodcastListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        //convert the images to urls and use picasso to load it into the app
        Uri authorImage = Uri.parse(imageStrings.get(position));
        Picasso.get().load(authorImage).into(holder.gridIcon);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView gridIcon;
        onPodcastListener onPodcastListener;

        public ViewHolder(@NonNull View itemView, onPodcastListener onPodcastListener) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);
            this.onPodcastListener = onPodcastListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPodcastListener.onPodcastListner(getAdapterPosition());
        }
    }

    public interface onPodcastListener{
        void onPodcastListner(int position);
    }


}