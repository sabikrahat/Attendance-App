package com.sabikrahat.attendanceapp;


import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

@SuppressWarnings("ALL")
public class JSONParser {

    private String TAG = "JSONParser";
    private static JSONParser instance = new JSONParser();
    private JSONParser() {}
    public static JSONParser getInstance() {
        return instance;
    }

    public String makeHttpRequest(String url, String method, List<NameValuePair> params) {

        HttpURLConnection http = null;
        InputStream is = null;
        String data = "";
        // Making HTTP request
        try {
            // check for request method
            if (method == "POST") {
                //httpClient = new DefaultHttpClient();
                if(params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
            }
            System.out.println("@JSONParser-"+": "+ url);
            URL urlc = new URL(url);
            http = (HttpURLConnection) urlc.openConnection();
            //System.out.println("Here 2");
            http = (HttpURLConnection) urlc.openConnection();
            //System.out.println("Here 3");
            http.connect();
            is = http.getInputStream();
            //System.out.println("Here 4");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            //System.out.println("@JSONParser_later-"+": "+ sb.toString());
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Found: " + e.getMessage());
        }
        try {
            http.disconnect();
        } catch (Exception e) {
        }
        return null;
    }
}
