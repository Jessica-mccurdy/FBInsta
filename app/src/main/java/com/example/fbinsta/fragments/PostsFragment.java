package com.example.fbinsta.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fbinsta.EndlessRecyclerViewScrollListener;
import com.example.fbinsta.FeedAdapter;
import com.example.fbinsta.R;
import com.example.fbinsta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    protected FeedAdapter feedAdapter;
    protected ArrayList<Post> posts;
    protected RecyclerView rvFeed;
    protected  SwipeRefreshLayout swipeContainer;

    // Store a member variable for the listener
    protected EndlessRecyclerViewScrollListener scrollListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //find the RecyclerView
        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);

        // initialize the array list (datasource
        posts = new ArrayList<>();
        //construct the adapter from this datasource
        feedAdapter = new FeedAdapter(posts);

        //RecyclerView setup (layout manager, use adapter)

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setReverseLayout(true);
        rvFeed.setLayoutManager(linearLayoutManager);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.scrollToPosition(0);

        // Adds lines between posts
        //rvFeed.addItemDecoration(new DividerItemDecoration(this,
        //DividerItemDecoration.VERTICAL));


        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                //query for data older than current
                ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
                postQuery.include(Post.KEY_USER);
                postQuery.setLimit(20);
                postQuery.whereGreaterThan(Post.KEY_CREATED_AT, posts.get(page).getKeyCreatedAt());
                postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
                postQuery.findInBackground(new FindCallback<Post>() {
                    //iterate through query
                    @Override
                    public void done(List<Post> objects, ParseException e) {
                        if (e == null){
                            int start = posts.size();
                            int end = start + 20;
                            for (int i = start + 1; i < end; ++i){
                                //add items one by one
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


        };

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

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

        // Adds the scroll listener to RecyclerView
        rvFeed.addOnScrollListener(scrollListener);


    }


    protected void populate(){
        //get query
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        //TODO set this back to 20
        postQuery.setLimit(5);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
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

