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

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {



    //pass in the Tweets array in the constructor
    private List<Post> posts;
    public FeedAdapter(ArrayList<Post> tweets) {
        posts = tweets;
    }
    Context context;

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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvUserName2;
        public TextView tvDescription;
        public ImageView ivPostImage;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById((R.id.ivProfileImage));
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvUserName2 = (TextView) itemView.findViewById(R.id.tvUserName2);
            tvDescription = (TextView) itemView.findViewById((R.id.tvDescription));
            ivPostImage = (ImageView) itemView.findViewById((R.id.iv_PostImage));

            itemView.setOnClickListener(this);

        }

        // on click listener for later


        @Override
        public void onClick(View v) {


            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = posts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, DetailsActivity.class);
                // serialize the tweet using parceler, use its short name as a key
               // intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                intent.putExtra( "id" , Post.KEY_ID);
                // show the activity
                context.startActivity(intent);
            }


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