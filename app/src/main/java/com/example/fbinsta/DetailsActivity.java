package com.example.fbinsta;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fbinsta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    String id;

    public ImageView ivProfileImage;
    public TextView tvUserName;
    public TextView tvTimeStamp;
    public TextView tvDescription;
    public ImageView ivPostImage;

    Context context;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // unwrap the movie passed in via intent, using its simple name as a key
       // post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        // get objectID
        id = getIntent().getStringExtra("id");


        // query post
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.whereEqualTo("objectId", id);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
               //iterate through query
               @Override
               public void done(List<Post> objects, ParseException e) {
                   if (e == null && objects.size() == 1) {
                       post = objects.get(0);
                   } else {
                       Log.e("DetailsActivity", "Can't get post");
                       e.printStackTrace();
                   }
               }
           });




        ivProfileImage = (ImageView) findViewById((R.id.ivProfileImage));
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvDescription = (TextView) findViewById((R.id.tvDescription));
        ivPostImage = (ImageView) findViewById((R.id.ivPostImage));

        if(post != null){
            tvUserName.setText(post.getUser().getUsername());
            tvTimeStamp.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());

            // Handles images
            Glide.with(context)
                    .load(post.getImage()
                            .getUrl())
                    .apply(new RequestOptions()
                            .transforms(new CenterCrop(), new RoundedCorners(20))
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background))
                    .into(ivPostImage);
        }
        else{
            Toast.makeText(DetailsActivity.this, "woops", Toast.LENGTH_LONG).show();
        }




        }


}
