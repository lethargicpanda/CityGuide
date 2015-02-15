package com.thomasezan.lyft.providers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by thomas on 2/14/15.
 */
public class GeolocationProvider {

    LocationManager locationManager;
    private static GeolocationProvider instance;

    public static GeolocationProvider getInstance(Context context){

        if (instance==null){
            instance = new GeolocationProvider(context);
        }

        return instance;
    }

    private GeolocationProvider(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLocation(){
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

}
