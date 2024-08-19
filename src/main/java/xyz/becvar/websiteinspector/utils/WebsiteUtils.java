package xyz.becvar.websiteinspector.utils;

import java.net.URL;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import xyz.becvar.websiteinspector.Main;

public class WebsiteUtils
{
    public static String getHtml(String urlString)
    {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set custom user agent
            conn.setRequestProperty("User-Agent", Main.USER_AGENT);

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

            // set custom user agent
            conn.setRequestProperty("User-Agent", Main.USER_AGENT);

            // connect to server
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

    public static String downloadFileContent(String urlString)
    {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // set custom user agent
            conn.setRequestProperty("User-Agent", Main.USER_AGENT);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append("\n");
            }
            in.close();
        } catch (Exception e) {
            Logger.error("Error fetching file: " + e.getMessage());
            return null;
        }
        return content.toString();
    }

    public static String analyzeRobotsTxt(String url)
    {
        String robotsUrl = url + "/robots.txt";
        String content = downloadFileContent(robotsUrl);

        if (content == null || content.isEmpty()) {
            return "robots.txt file not found or is empty.";
        }

        StringBuilder summary = new StringBuilder();
        List<String> sitemaps = new ArrayList<>();
        List<String> disallows = new ArrayList<>();
        List<String> allows = new ArrayList<>();

        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("Sitemap:")) {
                sitemaps.add(line.split(":", 2)[1].trim());
            } else if (line.startsWith("Disallow:")) {
                disallows.add(line.split(":", 2)[1].trim());
            } else if (line.startsWith("Allow:")) {
                allows.add(line.split(":", 2)[1].trim());
            }
        }

        if (!disallows.isEmpty()) {
            summary.append("Disallowed paths:\n");
            for (String path : disallows) {
                summary.append("  - ").append(path).append("\n");
            }
        }

        if (!allows.isEmpty()) {
            summary.append("Allowed paths:\n");
            for (String path : allows) {
                summary.append("  - ").append(path).append("\n");
            }
        }

        if (!sitemaps.isEmpty()) {
            summary.append("Sitemaps found:\n");
            for (String sitemap : sitemaps) {
                summary.append("  - ").append(sitemap);
            }
        }

        return summary.toString();
    }

    public static String analyzeSitemap(String url)
    {
        String sitemapUrl = url + "/sitemap.xml";
        String content = downloadFileContent(sitemapUrl);

        if (content == null || content.isEmpty()) {
            return "sitemap.xml file not found or is empty.";
        }

        StringBuilder summary = new StringBuilder();
        int urlCount = content.split("<loc>").length - 1;
        summary.append("Number of URLs: ").append(urlCount).append("\n");

        if (urlCount > 0) {
            int firstLocStart = content.indexOf("<loc>") + 5;
            int firstLocEnd = content.indexOf("</loc>", firstLocStart);
            String firstUrl = content.substring(firstLocStart, firstLocEnd);
            summary.append("First URL: ").append(firstUrl);
        }

        return summary.toString();
    }
}
