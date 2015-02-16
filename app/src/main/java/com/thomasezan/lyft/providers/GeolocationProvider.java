package com.thomasezan.lyft.providers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by thomas on 2/14/15.
 */
public class GeolocationProvider {

    private static GeolocationProvider instance;

    static private LocationManager locationManager;

    public static GeolocationProvider getInstance(Context context) {

        if (instance==null){
            instance = new GeolocationProvider(context);
        }

        return instance;
    }

    private GeolocationProvider(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLocation() {
        // We use LocationManager.NETWORK_PROVIDER because, in our case we need
        // location info as fast as possible and it doesn't have to be the most
        // accurate possible
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

}
