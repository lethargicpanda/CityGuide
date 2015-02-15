    package com.thomasezan.lyft.activities;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ListView;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.thomasezan.lyft.R;
import com.thomasezan.lyft.adapters.PlaceAdapter;
import com.thomasezan.lyft.customviews.SliderSelector;
import com.thomasezan.lyft.models.Place;
import com.thomasezan.lyft.providers.BusProvider;
import com.thomasezan.lyft.providers.GeolocationProvider;
import com.thomasezan.lyft.providers.PlacesProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.place_listview) ListView listView;
    @InjectView(R.id.swipe_layout) SwipeRefreshLayout swipeLayout;
    @InjectView(R.id.slider_selector) SliderSelector sliderSelector;

    PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Init Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setElevation(0);

        // Init Otto
        Bus bus = BusProvider.getInstance();
        bus.register(this);


        placeAdapter = new PlaceAdapter(this);
        listView.setAdapter(placeAdapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PlacesProvider.fetchPlaces(GeolocationProvider.getInstance(MainActivity.this).getLocation(), sliderSelector.getCurrentPlaceType());
            }
        });

        PlacesProvider.fetchPlaces(GeolocationProvider.getInstance(this).getLocation(), Place.TYPE.BAR);

    }

    // Otto callbacks
    @Subscribe
    public void onPlaceTypeChanged(Place.TYPE placeType){
        PlacesProvider.fetchPlaces(GeolocationProvider.getInstance(this).getLocation(), placeType);
    }


    @Subscribe
    public void onPlacesFetched(Pair<Place.TYPE,ArrayList<Place>> typePlacesPair){
        swipeLayout.setRefreshing(false);
        placeAdapter.setPlaceList(typePlacesPair.second, typePlacesPair.first);
        placeAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onStartFetchingPlaces(Boolean refresh){
        swipeLayout.setRefreshing(refresh);
    }

}