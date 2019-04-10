package boryuh8266.gmail.com.missingnumber.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckTime {
    private static String mJSONURLString = "http://worldtimeapi.org/api/timezone/Atlantic/Reykjavik";

    public static void isLegal(Context mContext) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String utcTime = response.getString("unixtime");
                            String sysTime = String.valueOf(System.currentTimeMillis() / 1000);

                            long abs = Math.abs(Long.parseLong(utcTime) - Long.parseLong(sysTime));
                            Log.d("mDebug", String.valueOf(abs));
                        } catch (JSONException e) {
                            Log.d("mDebug", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.d("mDebug", String.valueOf(error));
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

}
