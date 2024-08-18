package xyz.becvar.websiteinspector.utils;

import java.net.URL;
import java.net.HttpURLConnection;

public class NetworkUtils
{
    public static boolean checkIsWebsiteAvailable(String url)
    {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400);
        } catch (Exception e) {
            return false;
        }
    }
}
