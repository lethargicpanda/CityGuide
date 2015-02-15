package com.thomasezan.lyft.models;

import com.thomasezan.lyft.R;

import org.json.JSONObject;

/**
 * Created by thomas on 2/13/15.
 */
public class Place {

    public static String JSON_NAME = "name";
    public static String JSON_RATING = "rating";

    public static enum TYPE {
        BAR ("bar", R.drawable.ic_bar, "Bar"),
        BISTRO ("restaurant", R.drawable.ic_bistro, "Bistro"),
        CAFE ("cafe", R.drawable.ic_cafe, "Café");

        TYPE(String requestParam, int iconResource, String label) {
            this.requestParam = requestParam;
            this.iconResource = iconResource;
            this.label = label;
        }

        public final String requestParam;
        public final int iconResource;
        public final String label;
    }

    String name;
    float rating;

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public Place(String name, float rating) {
        this.name = name;
        this.rating = rating;
    }

    public static Place parsePlaceFromJson(JSONObject placeJsonObject){
        String name = placeJsonObject.optString(JSON_NAME);

        float rating;
        if (placeJsonObject.has(JSON_RATING)){
            rating = (float) placeJsonObject.optDouble(JSON_RATING);
        } else {
            rating = -1;
        }


        return new Place(name, rating);
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }

}
