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

import com.girltalkradio.ui.multimedia.MultimediaPost;
import com.squareup.picasso.Picasso;

public class MultimediaAdapter extends RecyclerView.Adapter<MultimediaAdapter.ViewHolder> {

    List<MultimediaPost> multimediaPosts;
    LayoutInflater inflater;
    Context context;

    public MultimediaAdapter (Context ctx, List<MultimediaPost> multimediaPosts){
        this.multimediaPosts = multimediaPosts;
        this.context = ctx;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.multimedia_post_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(multimediaPosts.get(position).getTitle());
        //convert the images to urls and use picasso to load it into the app
        Uri image = Uri.parse(multimediaPosts.get(position).getImageUrl());
        Picasso.get().load(image).into(holder.gridIcon);
    }

    @Override
    public int getItemCount() {
        return multimediaPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
