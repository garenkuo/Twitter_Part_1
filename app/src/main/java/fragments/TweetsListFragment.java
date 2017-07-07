package fragments;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kcguo on 7/3/17.
 */


public class TweetsListFragment extends Fragment {

    TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;


    // on some click or some loading we need to wait for...

    public SwipeRefreshLayout swipeContainer;

    MenuItem miActionProgressItem;

    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        client = TwitterApp.getRestClient();
        // inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        // find RecyclerView
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        // init arraylist
        tweets = new ArrayList<>();
        // construct adapter from datasource
        tweetAdapter = new TweetAdapter(tweets);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        /*public void showProgressBar () {
            // Show progress item
            miActionProgressItem.setVisible(true);
        }

        public void hideProgressBar() {
            // Hide progress item
            miActionProgressItem.setVisible(false);
        }*/

            @Override
            public void onRefresh() {
                refreshTimeline();
            }
        });
        // RecyclerView setup
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        // set adapter
        rvTweets.setAdapter(tweetAdapter);

        ProgressBar pb = (ProgressBar) v.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);
        // run a background job and once complete
        pb.setVisibility(ProgressBar.INVISIBLE);

        return v;

    }
    public void refreshTimeline () {

    }
    public void addItems(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            // convert object to a Tweet
            try {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                // add Tweet to data source
                tweets.add(tweet);
                // notify adapter that new item is added

                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
