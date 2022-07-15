package com.example.memesshare;

import static android.view.View.GONE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    public ImageView imageView;
    public ProgressBar progressBar;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar3);
        loadMeme();
    }

    private void loadMeme() {

        progressBar.setVisibility(View.VISIBLE); //

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, " https://meme-api.herokuapp.com/gimme", null,
                response -> {
                    try {
                        url= response.getString("url");
                        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(GONE);
                                return false;
                            }


                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(GONE);
                                return false;
                            }
                        }).into(imageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error -> {
            Log.d("memesapp","Something is wrong.");

        });


// Add the request to the RequestQueue.
        queue.add(jsonRequest);


    }


    public void shareMeme(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT,"hello"+url);
        i.setType("text/plain");

        Intent shareIntent = Intent.createChooser(i,"Share this meme using...");
        startActivity(shareIntent);
    }

    public void nextMeme(View view) {
        loadMeme();
    }
}