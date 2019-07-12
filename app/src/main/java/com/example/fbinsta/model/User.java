package com.example.fbinsta.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;




@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ID = "objectId";
    public static final String KEY_CREATED_AT  = "createdAt";
    public static final String KEY_PROFILE_IMAGE = "profileImage";

    public String getUsername(){
        return getString(KEY_USERNAME);
    }

    public void  setUsername(String description){
        put(KEY_USERNAME, description);
    }

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PROFILE_IMAGE);
    }


    public void setProfileImage(ParseFile image){
        put(KEY_PROFILE_IMAGE, image);
    }


    public ParseUser getID() {
        return getParseUser(KEY_ID);
    }

    public void setId(String id){
        put(KEY_ID, id);
    }



    public String getKeyCreatedAt(){
        return (String) KEY_CREATED_AT;
    }


    // for parceler
    public User(){}


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

    /*

    public static List<Post> createContactsList(int numPosts, int offset) {
        List<Post> contacts = new ArrayList<Post>();

        for (int i = 1; i <= numPosts; i++) {
            contacts.add(new Contact("Person " + ++lastContactId + " offset: " + offset, i <= numContacts / 2));
        }

        return contacts;
    }

*/
}
