package com.example.fbinsta.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_ID = "objectId";
    public static final String KEY_CREATED_AT  = "createdAt";
    public static final String KEY_PROFILE_IMAGE = "profileImage";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void  setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public void setProfileImage(ParseFile image){
        put(KEY_PROFILE_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    /*
    public String getObjectID(){
        return (String) getString(KEY_ID);
    }

    */

    public String getKeyCreatedAt(){
        return (String) KEY_CREATED_AT;
    }


    // for parceler
    public Post(){}


    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop(){
            setLimit(20);
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }



    }

}
