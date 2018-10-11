package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kamal Dev Sharma on 6/29/2017.
 */

public class QueryUtils {

    private static final String SAMPLE_JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private QueryUtils() {
    }

    public static ArrayList<Earthquakes> extractEarthquakes(String jsonResponse) {

        if (jsonResponse==null) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquakes> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray features = jsonObject.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) features.get(i);
                JSONObject properties = jsonObject1.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = (String) properties.get("place");
                Long time = properties.getLong("time");
                String url = properties.getString("url");
                Earthquakes e = new Earthquakes(mag, place, time, url);
                earthquakes.add(e);
            }
            return earthquakes;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

    public static ArrayList<Earthquakes> fetchEarthQuakeData() throws IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("fetchEarthquakeData","working");
        URL url = createUrl(SAMPLE_JSON_RESPONSE);
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = getJsonResponse(inputStream);
            }
        } catch (IOException e) {
            Log.e("Problem retrieving the earthquake JSON results.", String.valueOf(e));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return extractEarthquakes(jsonResponse);

    }

    private static String getJsonResponse(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String sampleJsonResponse) {
        if (sampleJsonResponse == null)
            return null;
        else try {
            return new URL(sampleJsonResponse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
