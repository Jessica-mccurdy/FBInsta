package com.example.fbinsta;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fbinsta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    FeedAdapter feedAdapter;
    ArrayList<Post> posts;
    RecyclerView rvFeed;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        //find the RecyclerView
        rvFeed = (RecyclerView) findViewById(R.id.rvFeed);

        // initialize the array list (datasource
        posts = new ArrayList<>();
        //construct the adapter from this datasource
        feedAdapter = new FeedAdapter(posts);

        //RecyclerView setup (layout manager, use adapter)
        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        rvFeed.setAdapter(feedAdapter);
        rvFeed.scrollToPosition(0);

        // Adds lines between posts
        //rvFeed.addItemDecoration(new DividerItemDecoration(this,
                //DividerItemDecoration.VERTICAL));


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedAdapter.clear();
                feedAdapter.addAll(posts);
                populate();
                swipeContainer.setRefreshing(false);
            }

        });


        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        populate();

    }



    private void queryPosts(){
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for (int i = 0; i < objects.size(); ++i){
                        // from old vid
                        Log.d("FeedActivity", "Post[" + i + "] = " + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername());

                    }
                }else {
                    Log.e("FeedActivity", "Can't get post");
                    e.printStackTrace();
                }
            }
        });
    }

    protected void populate(){
        //get query
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
            postQuery.include(Post.KEY_USER);
            postQuery.findInBackground(new FindCallback<Post>() {
            //iterate through query
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for (int i = 0; i < objects.size(); ++i){
                        posts.add(objects.get(i));
                        feedAdapter.notifyItemInserted(posts.size() - 1);

                        // from old vid example of getting things
                        Log.d("FeedActivity", "Post[" + i + "] = " + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername());
                    }
                }else {
                    Log.e("FeedActivity", "Can't get post");
                    e.printStackTrace();
                }
            }
    });}



}
