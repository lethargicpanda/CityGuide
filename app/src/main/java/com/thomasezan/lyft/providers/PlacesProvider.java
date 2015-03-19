package com.thomasezan.lyft.providers;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Pair;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.thomasezan.lyft.models.Place;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by thomas on 2/14/15.
 */
public class PlacesProvider {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/search/json?radius=1000&sensor=true&key=AIzaSyDwvIbB3QBPN4VbC5i3Dbu7Cg-C9LTSxjU";
    private static final String TYPE_KEY = "&types=";
    private static final String KEYWORD_KEY = "&keyword=";
    private static final String LOCATION_KEY = "&location=";

    private static OkHttpClient client = new OkHttpClient();
    private static AsyncTask<String, String, Boolean> fetchTask;


   public static void fetchPlaces(final Location location, final Place.TYPE placeType){

       // Cancel pending task if currently running
       if(fetchTask!=null && fetchTask.getStatus() == AsyncTask.Status.RUNNING){
           fetchTask.cancel(true);
       }

       fetchTask = new AsyncTask<String, String, Boolean>() {

           ArrayList<Place> places;

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               BusProvider.getInstance().post(Boolean.valueOf(true));
           }

           @Override
           protected Boolean doInBackground(String... params) {
               Request request = new Request.Builder()
                       .url(buildUrl(location, placeType))
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


   private static String buildUrl(Location location, Place.TYPE type){
       StringBuilder urlBuilder = new StringBuilder(BASE_URL);
       urlBuilder.append(LOCATION_KEY);
       urlBuilder.append(location.getLatitude());
       urlBuilder.append(",");
       urlBuilder.append(location.getLongitude());
       urlBuilder.append(TYPE_KEY);
       urlBuilder.append(type.requestParam);

       if (type.keywordParam != null) {
           urlBuilder.append(KEYWORD_KEY);
           urlBuilder.append(type.keywordParam);
       }
       return urlBuilder.toString();
   }

}
