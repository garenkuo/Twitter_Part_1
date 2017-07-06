package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import fragments.UserTimelineFragment;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String screenName = getIntent().getStringExtra("screen_name");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);
        ft.commit();

        client = TwitterApp.getRestClient();
        if (screenName == null) {
            client.getMyInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        else {
            client.getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }



    public void populateUserHeadline(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ImageView ivBackgroundImage = (ImageView) findViewById(R.id.ivBackgroundImage);
        tvName.setText(user.name);

        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + "");
        tvFollowing.setText(user.followingCount + "");

        Glide.with(this)
                .load(user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 5, 0))
                .into(ivProfileImage);

        Glide.with(this)
                .load(user.backgroundImageUrl)
                .centerCrop()
                .into(ivBackgroundImage);
    }
}
