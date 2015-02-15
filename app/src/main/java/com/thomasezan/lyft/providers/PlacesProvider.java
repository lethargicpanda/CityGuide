package com.thomasezan.lyft.providers;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.thomasezan.lyft.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by thomas on 2/14/15.
 */
public class PlacesProvider {

    private static String BASE_URL = "https://maps.googleapis.com/maps/api/place/search/json?radius=1000&sensor=true&key=AIzaSyDwvIbB3QBPN4VbC5i3Dbu7Cg-C9LTSxjU";
    private static String TYPE_KEY = "&types=";
    private static String LOCATION_KEY = "&location=";

    private static OkHttpClient client = new OkHttpClient();
    private static AsyncTask<String, String, Boolean> fetchTask;


   public static void fetchPlaces(final Location location, final Place.TYPE placeType){

       // Cancel pending task
       if(fetchTask!=null && fetchTask.getStatus() == AsyncTask.Status.RUNNING){
           fetchTask.cancel(true);
       }

       fetchTask = new AsyncTask<String, String, Boolean>() {

           ArrayList<Place> places;

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               BusProvider.getInstance().post(new Boolean(true));
           }

           @Override
           protected Boolean doInBackground(String... params) {
               Request request = new Request.Builder()
                       .url(createUrl(location, placeType))
                       .build();

               try {
                   Response response = client.newCall(request).execute();
                   String responseString = response.body().string();
                   JSONObject responseJson = new JSONObject(responseString);
                   JSONArray resultsJson = responseJson.optJSONArray("results");

                   places = new ArrayList<>();
                   for (int i=0; i<resultsJson.length(); i++){
                       Place place = Place.parsePlaceFromJson(resultsJson.getJSONObject(i));
                       places.add(place);
                   }

               } catch (IOException e) {
                   e.printStackTrace();
               } catch (JSONException e) {
                   e.printStackTrace();
               }

               return null;
           }

           @Override
           protected void onPostExecute(Boolean bool){
               super.onPostExecute(bool);
               Pair<Place.TYPE,ArrayList<Place>> typePlacesPair = new Pair<>(placeType, places);
               BusProvider.getInstance().post(typePlacesPair);
           }
       };

       fetchTask.execute();
   }


   public static String createUrl(Location location, Place.TYPE type){
       StringBuffer urlBuffer = new StringBuffer(BASE_URL);
       urlBuffer.append(LOCATION_KEY);
       urlBuffer.append(location.getLatitude());
       urlBuffer.append(",");
       urlBuffer.append(location.getLongitude());

       urlBuffer.append(TYPE_KEY);
       urlBuffer.append(type.requestParam);

       return urlBuffer.toString();
   }

}
