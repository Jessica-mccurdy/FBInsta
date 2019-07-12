package com.example.fbinsta.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.fbinsta.R;
import com.example.fbinsta.model.Post;

public class DetailsFragment extends Fragment {


    Post post;
    String id;

    public ImageView ivProfileImage;
    public TextView tvUserName;
    public TextView tvTimeStamp;
    public TextView tvDescription;
    public ImageView ivPostImage;

    Context context;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.activity_details, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ivProfileImage = (ImageView) view.findViewById((R.id.ivProfileImage));
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvTimeStamp = (TextView) view.findViewById(R.id.tvTimeStamp);
        tvDescription = (TextView) view.findViewById((R.id.tvDescription));
        ivPostImage = (ImageView) view.findViewById((R.id.ivPostImage));

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
                            .placeholder(R.drawable.ic_home)
                            .error(R.drawable.ic_home))
                    .into(ivPostImage);
        }
        else{
            Toast.makeText(getContext(), "woops", Toast.LENGTH_LONG).show();
        }



    }

}
