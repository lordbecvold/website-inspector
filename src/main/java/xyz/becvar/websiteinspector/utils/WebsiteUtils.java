package xyz.becvar.websiteinspector.utils;

import java.net.URL;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class WebsiteUtils
{
    public static String getHtml(String urlString)
    {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            Logger.error("Error while getting HTML content from URL: " + urlString);
            return "";
        }
        return result.toString();
    }

    public static boolean isUsingCloudflare(String urlString)
    {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();

            // get http headers
            Map<String, List<String>> headers = conn.getHeaderFields();

            // check for cloudflare headers
            if (headers.containsKey("Server") && headers.get("Server").contains("cloudflare")) {
                return true;
            }
            if (headers.containsKey("CF-RAY")) {
                return true;
            }
            if (headers.containsKey("CF-Cache-Status")) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String detectCms(String url)
    {
        // get HTML content
        String html = getHtml(url);

        // check for WordPress
        if (html.contains("WordPress")) {
            return "WordPress";
        }

        // check for Joomla
        if (html.contains("Joomla")) {
            return "Joomla";
        }

        // check for Drupal
        if (html.contains("Drupal")) {
            return "Drupal";
        }

        // check for Magento
        if (html.contains("Magento")) {
            return "Magento";
        }

        // check for Typo3
        if (html.contains("TYPO3")) {
            return "Typo3";
        }

        // check for PrestaShop
        if (html.contains("PrestaShop")) {
            return "PrestaShop";
        }

        // check for Concrete5
        if (html.contains("concrete5")) {
            return "Concrete5";
        }

        // check for Ghost
        if (html.contains("Ghost-Admin")) {
            return "Ghost";
        }

        // check for Umbraco
        if (html.contains("Umbraco")) {
            return "Umbraco";
        }

        // check for MODX
        if (html.contains("MODX")) {
            return "MODX";
        }

        // check for Shopify
        if (html.contains("Shopify")) {
            return "Shopify";
        }

        // check for Squarespace
        if (html.contains("Squarespace")) {
            return "Squarespace";
        }

        // check for WIX
        if (html.contains("X-Wix-Meta-Site")) {
            return "Wix";
        }

        // unknown CMS
        return "Unknown or unsupported CMS";
    }
}
