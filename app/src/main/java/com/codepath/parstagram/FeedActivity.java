package com.codepath.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class FeedActivity extends AppCompatActivity {
    public static final String TAG = "FeedActivity";
    private ImageView ivPicture;
    private TextView tvUsername;
    private TextView tvCaption;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ivPicture = findViewById(R.id.ivPicture);
        tvUsername = findViewById(R.id.tvUsername);
        tvCaption = findViewById(R.id.tvCaption);
        btnLogout = findViewById(R.id.btnLogout);

        // Display the most recently saved Post
        queryPosts();

        // User can Logout after they see their post
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedActivity.this, "Alright, bye", Toast.LENGTH_SHORT).show();
                ParseUser.logOut();
            }
        });

    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting post", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getKeyDescription() + ", username: " + post.getKeyUser().getUsername());
                    // TODO: RV feed with all the saved posts
                }
                // Only displaying the more recent post
                ParseFile picObject = posts.get(posts.size() - 1).getKeyImage();
                picObject.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Bitmap pic = BitmapFactory
                                    .decodeByteArray(
                                            data, 0,
                                            data.length);
                            ivPicture.setImageBitmap(pic);
                        } else {
                            Log.e(TAG, "Image didn't load");
                        }
                    }
                });

                tvUsername.setText(posts.get(posts.size() - 1).getKeyUser().getUsername());
                tvCaption.setText(posts.get(posts.size() - 1).getKeyDescription());
            }
        });
    }
}