package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);
        ft.commit();

        client = TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = null;
                try {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.screenName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
