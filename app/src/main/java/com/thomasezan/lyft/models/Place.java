package com.thomasezan.lyft.models;

import com.thomasezan.lyft.R;
import org.json.JSONObject;

/**
 * Created by thomas on 2/13/15.
 */
public class Place {

    private static final String JSON_NAME = "name";
    private static final String JSON_RATING = "rating";

    public static enum TYPE {
//        BAR ("bar", null, R.drawable.ic_bar),
//        BISTRO ("restaurant", null, R.drawable.ic_bistro),
        CAFE ("cafe", null, R.drawable.ic_cafe),
        STARBUCKS ("cafe", "starbucks", R.drawable.ic_cafe),
        PEETS_COFFEE("cafe", "peets+coffee", R.drawable.ic_cafe);


        TYPE(String requestParam, String keywordParam, int iconResource) {
            this.requestParam = requestParam;
            this.keywordParam = keywordParam;
            this.iconResource = iconResource;
        }

        public final String requestParam;
        public final String keywordParam;
        public final int iconResource;
    }

    private String name;
    private float rating;

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    private Place(String name, float rating) {
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
                "name='" + name +
                ", rating=" + rating +
                '}';
    }

}
