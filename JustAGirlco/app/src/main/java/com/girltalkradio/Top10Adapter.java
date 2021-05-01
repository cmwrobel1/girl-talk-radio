package com.girltalkradio;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Top10Adapter extends FirebaseRecyclerAdapter<Podcast,Top10Adapter.myviewholder> {

        LayoutInflater inflater;
        private onPodcastListener onPodcastListener;

        //constructor
        public Top10Adapter(@NonNull FirebaseRecyclerOptions<Podcast> options, onPodcastListener onPodcastListener, Context ctx){
            super(options);
            //inherits titles and url from the Podcast class
            this.onPodcastListener = onPodcastListener;
            this.inflater = LayoutInflater.from(ctx);
        }

        @Override
        protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Podcast model) {
            holder.name.setText(model.getTitle());      //model is the podcast object
            // holder.url.setText(model.getRss());
            holder.order.setText(String.valueOf(model.getOrder()));
            Uri authorImage = Uri.parse(model.getImage());
            Picasso.get().load(authorImage).into(holder.image);
        }

        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_10_grid,parent,false);
            return new myviewholder(view,onPodcastListener);
        }

        public class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            TextView name;
            TextView url;
            TextView order;
            ImageView image;
            onPodcastListener onPodcastListener;

            public myviewholder(@NonNull View itemView,  onPodcastListener onPodcastListener)
            {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.textView2);
                //url = (TextView)itemView.findViewById(R.id.url);
                order = (TextView)itemView.findViewById(R.id.num);
                image = (ImageView)itemView.findViewById(R.id.imageView);
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
