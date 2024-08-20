package xyz.becvar.websiteinspector.modules;

import java.net.URL;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import xyz.becvar.websiteinspector.Main;
import java.util.concurrent.ExecutorService;
import xyz.becvar.websiteinspector.utils.Logger;

public class SubdomainScanner
{
    private static final int THREAD_POOL_SIZE = Main.SCANNER_THREAD_POOL_SIZE;
    private static Set<String> foundSubdomains = new HashSet<>();

    public static List<Future<?>> scanSubdomains(String baseUrl)
    {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<?>> futures = new ArrayList<>();
        String baseDomain = baseUrl.replaceAll("^(http[s]?://)", "");
        baseDomain = baseDomain.replaceAll("/$", "");

        try (InputStream inputStream = Main.class.getResourceAsStream("/subdomains.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String subdomain;
            while ((subdomain = br.readLine()) != null) {
                if (subdomain.trim().isEmpty()) {
                    continue;
                }

                String httpUrl = "http://" + subdomain + "." + baseDomain;
                String httpsUrl = "https://" + subdomain + "." + baseDomain;

                futures.add(executor.submit(() -> checkUrl(httpUrl)));
                futures.add(executor.submit(() -> checkUrl(httpsUrl)));
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
            connection.setConnectTimeout(Main.CONNECTION_TIMEOUT * 1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", Main.USER_AGENT);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                synchronized (foundSubdomains) {
                    foundSubdomains.add(urlString);
                }
                Logger.log("Subdomain found: " + urlString);
            } else {
                Logger.log("Subdomain not found: " + urlString + " (Response Code: " + responseCode + ")");
            }

        } catch (IOException e) {
            if (e instanceof java.net.UnknownHostException) {
                Logger.log("Subdomain does not exist: " + urlString);
            } else {
                Logger.log("Error checking subdomain: " + urlString);
                e.printStackTrace();
            }
        }
    }

    public static Set<String> getFoundSubdomains()
    {
        return foundSubdomains;
    }
}
