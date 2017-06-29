package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by kcguo on 6/26/17.
 */

public class User implements Serializable {

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    // deserialize JSON
    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        // extract and fill values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");

        return user;
    }
}