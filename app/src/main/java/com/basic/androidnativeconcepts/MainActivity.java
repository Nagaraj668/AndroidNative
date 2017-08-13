package com.basic.androidnativeconcepts;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void getRequestUsingHTTP(View view) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute("http://192.168.225.201/http/get.php", "GET", null);
    }

    public void postRequestUsingHTTP(View view) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute("http://192.168.225.201/http/post.php", "POST", "name=nagaraj&id=12345");
    }

    public void getRequestUsingVolley(View view) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://192.168.225.201/http/getJson.php", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsObjRequest);
    }

    public void postRequestUsingVolley(View view) {
        JSONObject requestObject = null;
        try {
            requestObject = new JSONObject();
            requestObject.put("name", "Nagaraj N");
            requestObject.put("ID", 12345);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, "http://192.168.225.201/http/postJson.php", requestObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsObjRequest);
    }


    private class BackgroundTask extends AsyncTask<Object, String, String> {
        @Override
        protected String doInBackground(Object... objects) {
            String response = null;
            try {
                response = sendRequest((String) objects[0], (String) objects[1], (String) objects[2]);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            // process response
        }
    }

    // HTTP GET request
    private String sendRequest(String url, String method, String request) {
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod(method);

            // Send post request
            if (request != null) {
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(request);
                wr.flush();
                wr.close();
            }

            //add request header
            int responseCode = con.getResponseCode();
            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            System.out.println(method + " response is : " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public void openSecondActivity (View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void openSQLiteActivity (View view) {
        Intent intent = new Intent(this, SQLiteActivity.class);
        startActivity(intent);
    }
}
