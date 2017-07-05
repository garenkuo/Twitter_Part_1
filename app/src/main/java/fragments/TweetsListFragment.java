package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kcguo on 7/3/17.
 */


public class TweetsListFragment extends Fragment {

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        // find RecyclerView
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        // init arraylist
        tweets = new ArrayList<>();
        // construct adapter from datasource
        tweetAdapter = new TweetAdapter(tweets);

        // RecyclerView setup
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        // set adapter
        rvTweets.setAdapter(tweetAdapter);
        return v;

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
