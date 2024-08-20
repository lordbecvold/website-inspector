package xyz.becvar.websiteinspector.modules;

import java.net.*;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import xyz.becvar.websiteinspector.Main;
import xyz.becvar.websiteinspector.utils.WebsiteUtils;

public class ServerInfo
{
    public static String[] getServerInfo(String urlString)
    {
        String[] serverInfo = new String[3];

        try {
            // poarse the URL
            URL url = new URL(urlString);

            // get IP address of the server
            InetAddress address = InetAddress.getByName(url.getHost());
            String ip = address.getHostAddress();
            serverInfo[0] = "Server IP Address: " + ip;

            // establish connection to get server details
            HttpURLConnection connection;
            if (url.getProtocol().equalsIgnoreCase("https")) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setRequestMethod("HEAD");
            connection.connect();

            // get and print all header fields
            Map<String, List<String>> headers = connection.getHeaderFields();
            StringBuilder headersInfo = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey() != null ? entry.getKey() : "Status";
                String value = String.join(", ", entry.getValue());
                headersInfo.append(key).append(": ").append(value).append("\n");
            }
            serverInfo[2] = headersInfo.toString();

            // check for specific headers like 'Server'
            String serverType = connection.getHeaderField("Server");
            serverInfo[1] = "Server Type: " + serverType;

        } catch (MalformedURLException e) {
            serverInfo[0] = "Invalid URL format: " + e.getMessage();
        } catch (UnknownHostException e) {
            serverInfo[0] = "Unable to resolve host: " + e.getMessage();
        } catch (IOException e) {
            serverInfo[0] = "IOException: " + e.getMessage();
        }

        return serverInfo;
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
            e.fillInStackTrace();
        }

        return false;
    }

    public static String detectCms(String url)
    {
        // get HTML content
        String html = WebsiteUtils.getHtml(url);

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
