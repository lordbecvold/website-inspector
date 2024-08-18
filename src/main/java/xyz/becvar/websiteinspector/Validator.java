package xyz.becvar.websiteinspector;

import xyz.becvar.websiteinspector.utils.Logger;
import xyz.becvar.websiteinspector.utils.NetworkUtils;

public class Validator
{
    public static String validateUrl(String url)
    {
        String httpsUrl;
        String httpUrl;

        if (url == null || url.trim().isEmpty()) {
            Logger.error("URL is null or empty.");
        }

        if (url == null || url.trim().isEmpty()) {
            Logger.error("URL is null or empty.");
            return null;
        }

        // remove last slash
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        // construct https and http URLs
        if (url.startsWith("https://")) {
            httpsUrl = url;
            httpUrl = "http://" + url.substring(8);
        } else if (url.startsWith("http://")) {
            httpsUrl = "https://" + url.substring(7);
            httpUrl = url;
        } else {
            httpsUrl = "https://" + url;
            httpUrl = "http://" + url;
        }

        // check if HTTPS is available
        if (NetworkUtils.checkIsWebsiteAvailable(httpsUrl)) {
            return httpsUrl;
        }

        // check if HTTP is available
        if (NetworkUtils.checkIsWebsiteAvailable(httpUrl)) {
            return httpUrl;
        } else {
            Logger.error("Website is not available.");
            return null;
        }
    }
}
