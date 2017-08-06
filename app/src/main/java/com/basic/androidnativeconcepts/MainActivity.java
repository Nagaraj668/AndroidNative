package com.basic.androidnativeconcepts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    }

    public void postRequestUsingVolley(View view) {

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
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            System.out.println(method + " response is : "+ response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
