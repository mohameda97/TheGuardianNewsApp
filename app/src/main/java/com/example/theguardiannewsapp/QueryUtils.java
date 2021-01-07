package com.example.theguardiannewsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){
    }

    public static List<News> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
                jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<News> newsList = extractNews(jsonResponse);
        return newsList;
    }

    private static List<News> extractNews(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<News> newsList = new ArrayList<News>();
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");

            for (int i =0;i<resultsArray.length();i++){
                JSONObject results = resultsArray.getJSONObject(i);
                String type = results.getString("type");
                String sectionName = results.getString("sectionName");
                String webPublicationDate = results.getString("webPublicationDate");
                String webTitle = results.getString("webTitle");
                String webUrl = results.getString("webUrl");

                News news = new News(type,sectionName,webTitle,webUrl,webPublicationDate);
                newsList.add(news);

            }

        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing news JSON results", e);

        }
        return newsList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";

        if (url== null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection =null;
        InputStream inputStream =null;
        try {
            httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Error in response code" + httpURLConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the earthquake JSON results.",e);

        }finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);

        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error with creating the URL",e);

        }
        return url;
    }
}