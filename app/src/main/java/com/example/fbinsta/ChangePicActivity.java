package com.example.fbinsta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;

public class ChangePicActivity extends AppCompatActivity {

    private Button takePicButton;
    private Button submitButton;
    private Button logInButton;
    private Button selectPicButton;
    private Button feedButton;
    private ImageView ivPostImage;
    private final String TAG = "ComposeFragment";
    private ProgressBar pb;

    // copied and looks wrong
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int SELECT_IMAGE_REQUEST_CODE = 1111;
    public String photoFileName = "photo.jpg";
    File photoFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pic);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Instagram"); // set the top title



        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        takePicButton = findViewById(R.id.takePic_bt);
        submitButton = findViewById(R.id.submit_bt);
        ivPostImage = findViewById(R.id.ivPostImage);
        logInButton = findViewById(R.id.logOut);
        pb = (ProgressBar) findViewById(R.id.pbLoading);
        selectPicButton = findViewById(R.id.btSelect);
        //feedButton = view.findViewById(R.id.btFeed);



        // queryPosts();

        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSelect();
            }
        });


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                final Intent intent = new Intent(ChangePicActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();

            }
        });

        /*
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getContext(), FeedActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        */


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                if (photoFile == null || ivPostImage.getDrawable() == null){
                    Log.e(TAG, "No photo to submit");
                    Toast.makeText(ChangePicActivity.this, "There is no photo!", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(user, photoFile, view);

            }




        });

    }

    public void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(ChangePicActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(ChangePicActivity.this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }


    public void launchSelect(){

        //photoFile = getPhotoFileUri(photoFileName);

        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Create a File reference to access to future access

        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(ChangePicActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == SELECT_IMAGE_REQUEST_CODE ){
            if (resultCode == Activity.RESULT_OK) {
                Uri photoUri = data.getData();

                //photoFile = new File(photoUri.getPath());
                photoFile = new File(getRealPathFromURI(ChangePicActivity.this, photoUri));

                // Do something with the photo based on Uri
                //Bitmap selectedImage = null;
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(ChangePicActivity.this.getContentResolver(), photoUri);

                    //photoFile = new File(photoUri.getPath());
                    ivPostImage.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
                // ivPostImage.setImageBitmap(selectedImage);
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(ChangePicActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    private void savePost( ParseUser parseUser, final File photoFile, View view) {

        pb.setVisibility(ProgressBar.VISIBLE);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");


        // Retrieve the object by id
        query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject user, ParseException e) {
                        if (e == null) {
                            user.put("profileImage", new ParseFile(photoFile));
                            user.saveInBackground(new SaveCallback() {
                                                      @Override
                                                      public void done(ParseException e) {
                                                          if (e != null){
                                                              e.printStackTrace();
                                                              pb.setVisibility(ProgressBar.INVISIBLE);
                                                              return;
                                                          }else{
                                                              // run a background job and once complete
                                                              ivPostImage.setImageResource(0);
                                                              pb.setVisibility(ProgressBar.INVISIBLE);
                                                          }}}
                                                              );


                        }


        /*
        Post post = new Post();
        post.setDescription(description);
        post.setUser(parseUser);
        post.setImage(new ParseFile(photoFile));
        */


                    }
                }
        );
    }


            public String getRealPathFromURI(Context context, Uri contentUri) {
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }}}




