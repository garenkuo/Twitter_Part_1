package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by kcguo on 6/26/17.
 */

@Parcel
public class User {

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public String backgroundImageUrl;

    public String tagLine;
    public int followersCount;
    public int followingCount;

    // deserialize JSON
    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        // extract and fill values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.backgroundImageUrl = json.getString("profile_banner_url");

        user.tagLine = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount = json.getInt("friends_count");


        return user;
    }
}
