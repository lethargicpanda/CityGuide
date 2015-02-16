# CityGuide

City Guide is an Android App which shows different places nearby and lets the user select which type of place they want to see.

### Libraries

City Guide uses:

  * the event bus library [Otto](http://square.github.io/otto/) for inter-module communications,
  * the Http library [OkHttp](square.github.io/okhttp/) for network requests,
  * the view injection library [ButterKnife](http://jakewharton.github.io/butterknife/) to simplify the UI implementation.

### Architecture

The application is organized in 5 packages:

 * [**activities**](https://github.com/lethargicpanda/CityGuide/tree/master/app/src/main/java/com/thomasezan/lyft/activities) which contains ```MainActivity```, the only activity of the application;
 * [**adapters**](https://github.com/lethargicpanda/CityGuide/tree/master/app/src/main/java/com/thomasezan/lyft/adapters) which contains ```PlaceAdapter``` used for ```MainActivity```'s ```ListView```;
 * [**customviews**](https://github.com/lethargicpanda/CityGuide/tree/master/app/src/main/java/com/thomasezan/lyft/customviews) which contains 2 custom views used in the application:
   * ```StarRow``` rendering the rating of the place with pink and grey stars,
   * ```SliderSelector``` displayed as the top of the list and used for the place type selection.
   
 * [**models**](https://github.com/lethargicpanda/CityGuide/tree/master/app/src/main/java/com/thomasezan/lyft/models) which contains ```Place```, the only model object in the application.
 
 *Note: the types of place ("Bar", "Bistro" or "Café") is implemented by the enum ```Place.TYPE```.*
 * [**providers**](https://github.com/lethargicpanda/CityGuide/tree/master/app/src/main/java/com/thomasezan/lyft/providers) which contains helper classes being either Singletons or utility classes with static methods:
 
  * ```BusProvider``` is a singleton used as a single point of access to the application's Event Bus,
  * ```GeolocationProvider``` is a singleton used to query the device geolocation,
  * ```PlacesProvider``` is a utility class with a static method fetching the places from Google API.
