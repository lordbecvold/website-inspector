package xyz.becvar.websiteinspector.modules;

import java.net.*;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import xyz.becvar.websiteinspector.Main;
import xyz.becvar.websiteinspector.utils.Logger;
import xyz.becvar.websiteinspector.utils.WebsiteUtils;

public class ServerInfo
{
    public static String[] getServerInfo(String urlString)
    {
        String[] serverInfo = new String[3];

        try {
            URL url = new URL(urlString);

            InetAddress address = InetAddress.getByName(url.getHost());
            String ip = address.getHostAddress();
            serverInfo[0] = "Server IP Address: " + ip;

            HttpURLConnection connection;
            if (url.getProtocol().equalsIgnoreCase("https")) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setRequestMethod("HEAD");
            connection.setRequestProperty("User-Agent", Main.USER_AGENT);
            connection.connect();

            Map<String, List<String>> headers = connection.getHeaderFields();
            StringBuilder headersInfo = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey() != null ? entry.getKey() : "Status";
                String value = String.join(", ", entry.getValue());
                headersInfo.append(key).append(": ").append(value).append("\n");
            }
            serverInfo[2] = headersInfo.toString();

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
            conn.setRequestProperty("User-Agent", Main.USER_AGENT);
            conn.connect();

            Map<String, List<String>> headers = conn.getHeaderFields();

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
        String html = WebsiteUtils.getHtml(url);

        if (html.contains("WordPress")) {
            return "WordPress";
        }
        if (html.contains("Joomla")) {
            return "Joomla";
        }
        if (html.contains("Drupal")) {
            return "Drupal";
        }
        if (html.contains("Magento")) {
            return "Magento";
        }
        if (html.contains("TYPO3")) {
            return "Typo3";
        }
        if (html.contains("PrestaShop")) {
            return "PrestaShop";
        }
        if (html.contains("concrete5")) {
            return "Concrete5";
        }
        if (html.contains("Ghost-Admin")) {
            return "Ghost";
        }
        if (html.contains("Umbraco")) {
            return "Umbraco";
        }
        if (html.contains("MODX")) {
            return "MODX";
        }
        if (html.contains("Shopify")) {
            return "Shopify";
        }
        if (html.contains("Squarespace")) {
            return "Squarespace";
        }
        if (html.contains("X-Wix-Meta-Site")) {
            return "Wix";
        }

        return "Unknown or unsupported CMS";
    }

    public static void printServerInfo(String url)
    {
        // get server info
        String[] serverDetails = ServerInfo.getServerInfo(url);

        // print the default server info
        Logger.printColoredKeyValue("Server IP Address", serverDetails[0].split(": ")[1]);
        Logger.printColoredKeyValue("Server Type", serverDetails[1].split(": ")[1]);
        Logger.printColoredKeyValue("CMS", detectCms(url));

        // check if website is using https
        if (url.contains("https://")) {
            Logger.printColoredKeyValue("Protocol", "HTTPS");
        } else {
            Logger.printColoredKeyValue("Protocol", "HTTP");
        }

        // check if website is using cloudflare
        if (ServerInfo.isUsingCloudflare(url)) {
            Logger.printColoredKeyValue("Cloudflare", "Yes");
        }

        // print the server headers formatted with colors
        String[] headers = serverDetails[2].split("\n");
        for (String header : headers) {
            if (header.contains(": ")) {
                String[] parts = header.split(": ", 2);
                Logger.printColoredKeyValue(parts[0], parts[1]);
            }
        }
    }
}
