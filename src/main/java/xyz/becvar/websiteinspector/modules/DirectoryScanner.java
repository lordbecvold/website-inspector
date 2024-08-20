package xyz.becvar.websiteinspector.modules;

import java.net.URL;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import xyz.becvar.websiteinspector.Main;
import java.util.concurrent.ExecutorService;
import xyz.becvar.websiteinspector.utils.Logger;

public class DirectoryScanner
{
    private static final int THREAD_POOL_SIZE = Main.SCANNER_THREAD_POOL_SIZE;
    private static Set<String> foundDirectories = new HashSet<>();

    public static List<Future<?>>scanRoutes(String urlString)
    {
        if (!urlString.endsWith("/")) {
            urlString += "/";
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<?>> futures = new ArrayList<>();

        try (InputStream inputStream = Main.class.getResourceAsStream("/routes.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String route;
            while ((route = br.readLine()) != null) {
                String fullUrl = urlString + route;
                futures.add(executor.submit(() -> checkUrl(fullUrl)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return futures;
    }

    public static void checkUrl(String urlString)
    {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Main.CONNECTION_TIMEOUT);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", Main.USER_AGENT);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                synchronized (foundDirectories) {
                    foundDirectories.add(urlString);
                }
                Logger.log("Directory found: " + urlString);
            } else {
                Logger.log("Directory not found: " + urlString + " (Response Code: " + responseCode + ")");
            }

        } catch (IOException e) {
            Logger.log("Error checking URL: " + urlString);
            e.printStackTrace();
        }
    }

    public static Set<String> getFoundDirectories()
    {
        return foundDirectories;
    }
}
