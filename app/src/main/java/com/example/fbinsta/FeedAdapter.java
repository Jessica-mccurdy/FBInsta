package com.example.fbinsta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fbinsta.model.Post;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    //private PostsFragment.OnItemSelectedListener listener;



    //pass in the Tweets array in the constructor
    private List<Post> posts;
    public FeedAdapter(ArrayList<Post> tweets) {
        posts = tweets;
    }
    Context context;


/*

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface MyCustomObjectListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        // TODO change these
        public void onSelected(Post post);
    }

    // Step 2 - This variable represents the listener passed in by the owning object
    // The listener must implement the events interface and passes messages up to the parent.
    private MyCustomObjectListener listener;

    // Constructor where listener events are ignored
    public FeedAdapter() {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
        onClick();
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }

    // ... setter defined here as shown earlier

    public void loadDataAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://mycustomapi.com/data/get.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Networking is finished loading data, data is processed
                SomeData data = SomeData.processData(response.get("data"));
                // Do some other stuff as needed....
                // Now let's trigger the event
                if (listener != null)
                    listener.onDataLoaded(data); // <---- fire listener here
            }
        });


    }


*/

    // for each row inflate the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void  onBindViewHolder(ViewHolder holder, int position) {
        // get data according to position.
        Post post = posts.get(position);

        //populate the views according to this data
        holder.tvUserName.setText(post.getUser().getUsername());
        holder.tvUserName2.setText(post.getUser().getUsername());
        holder.tvDescription.setText(post.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        holder.tvCreatedAt.setText(sdf.format( post.getCreatedAt()));

        // we don't have profile pic yet
        /*
        // Handles images
        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(20)))
                .into(holder.ivProfileImage);
                */


        // Handles images
        Glide.with(context)
                .load(post.getImage()
                .getUrl())
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(20))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
                .into(holder.ivPostImage);




    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    // create ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvUserName2;
        public TextView tvDescription;
        public ImageView ivPostImage;
        public TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById((R.id.ivProfileImage));
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvUserName2 = (TextView) itemView.findViewById(R.id.tvUserName2);
            tvDescription = (TextView) itemView.findViewById((R.id.tvDescription));
            ivPostImage = (ImageView) itemView.findViewById((R.id.iv_PostImage));
            tvCreatedAt = (TextView) itemView.findViewById((R.id.tvCreatedAt));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // gets item position
                    int position = getAdapterPosition();
                    // make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, this won't work if the class is static
                        Post post = posts.get(position);
                        // create intent for the new activity
                        Intent intent = new Intent(context, DetailsActivity.class);
                        // serialize the tweet using parceler, use its short name as a key
                        intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                        //intent.putExtra( "id" , post.getObjectId());
                        // show the activity
                        context.startActivity(intent);
                    }
                }
            });
        }

    }


    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }




}