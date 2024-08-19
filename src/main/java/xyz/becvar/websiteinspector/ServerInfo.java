package xyz.becvar.websiteinspector;

import java.net.*;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import xyz.becvar.websiteinspector.utils.Logger;
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

    public static void printServerInfo(String url)
    {
        // get server info
        String[] serverDetails = getServerInfo(url);

        // print the default server info
        Logger.printColoredKeyValue("Server IP Address", serverDetails[0].split(": ")[1]);
        Logger.printColoredKeyValue("Server Type", serverDetails[1].split(": ")[1]);
        Logger.printColoredKeyValue("CMS", WebsiteUtils.detectCms(url));

        if (WebsiteUtils.isUsingCloudflare(url)) {
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
