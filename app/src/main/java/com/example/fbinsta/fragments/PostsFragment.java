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

import com.example.fbinsta.FeedAdapter;
import com.example.fbinsta.R;
import com.example.fbinsta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    FeedAdapter feedAdapter;
    ArrayList<Post> posts;
    RecyclerView rvFeed;
    private SwipeRefreshLayout swipeContainer;


    //

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

    }



    private void queryPosts(){
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
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

