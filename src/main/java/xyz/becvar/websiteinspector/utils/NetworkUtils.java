package xyz.becvar.websiteinspector.utils;

import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Class NetworkUtils
 * 
 * @package xyz.becvar.websiteinspector.utils
*/
public class NetworkUtils
{
    /**
     * Check if website is available
     *
     * @param url to check
     *
     * @return boolean is available
     */
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
