package com.codepath.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private EditText etCaption;
    private Button btnPicture;
    private ImageView ivPhoto;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCaption = findViewById(R.id.etCaption);
        btnPicture = findViewById(R.id.btnPicture);
        ivPhoto = findViewById(R.id.ivPhoto);
        btnPost = findViewById(R.id.btnPost);

        queryPosts();
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
                }
            }
        });
    }
}