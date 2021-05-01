//package com.girltalkradio;
//
//import android.content.Context;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.firebase.ui.database.paging.DatabasePagingOptions;
//import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
//import com.firebase.ui.database.paging.LoadingState;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.squareup.picasso.Picasso;
//
///*implements pagination
//    diff between this and the Firebase Recycler without pagination is that this one
//    will not auto detect changes made to the database and load them in - the page will need to be reloaded
//    in order to reflect those changes
// */
//
//public class AuthorAdapter extends FirebaseRecyclerPagingAdapter<Podcast, AuthorAdapter.ViewHolder> {
//
//    LayoutInflater inflater;
//    Context context;
//    private onPodcastListener onPodcastListener;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    //pagination variables
//    private boolean isLoadingAdded = false;
//
//
//    public AuthorAdapter(@NonNull DatabasePagingOptions<Podcast> options, onPodcastListener onPodcastListener, Context ctx){
//        super(options);
//        this.context = ctx;
//        this.onPodcastListener = onPodcastListener;
//        this.inflater = LayoutInflater.from(ctx);
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
////        return new ViewHolder(view,onPodcastListener);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_10_grid,parent,false);
//        return new AuthorAdapter.ViewHolder(view,onPodcastListener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Podcast model) {
//      //  DatabaseReference reference = getRef(position);
//        holder.title.setText(model.getTitle());
//        //convert the images to urls and use picasso to load it into the app
//        Uri authorImage = Uri.parse(model.getImage());
//        Picasso.get().load(authorImage).into(holder.image);
//    }
//
//    @Override
//    protected void onLoadingStateChanged(@NonNull LoadingState state) {
//        switch(state){
//            case LOADING_INITIAL:
//                //the initial load has begun
//
//            case LOADING_MORE:
//                //the adapter has started to load an additional page
//                mSwipeRefreshLayout.setRefreshing(true);
//                break;
//
//            case LOADED:
//                //the previous load (either initial or additional) completed
//                mSwipeRefreshLayout.setRefreshing(false);
//                break;
//
//            case FINISHED:
//                mSwipeRefreshLayout.setRefreshing(false);
//                break;
//
//            case ERROR:
//                //the previous load (either initial or additional) failed. call the
//                //retry() method in order to retry the load operation
//                retry();
//                break;
//
//        }
//    }
//
//
//    @Override
//    protected void onError(@NonNull DatabaseError databaseError) {
//        super.onError(databaseError);
//        mSwipeRefreshLayout.setRefreshing(false);
//        databaseError.toException().printStackTrace();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView title;
//        ImageView image;
//        onPodcastListener onPodcastListener;
//
//        public ViewHolder(@NonNull View itemView, onPodcastListener onPodcastListener) {
//            super(itemView);
//            title = itemView.findViewById(R.id.textView2);
//            image = itemView.findViewById(R.id.imageView2);
//            this.onPodcastListener = onPodcastListener;
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            onPodcastListener.onPodcastListner(getAdapterPosition());
//        }
//    }
//
//    public interface onPodcastListener{
//        void onPodcastListner(int position);
//    }
//
//
//
//}