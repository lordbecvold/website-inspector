package xyz.becvar.websiteinspector;

import java.util.Scanner;
import xyz.becvar.websiteinspector.utils.Logger;
import xyz.becvar.websiteinspector.modules.ServerInfo;
import xyz.becvar.websiteinspector.modules.SiteMapInfo;

public class Main
{
    // define global variables
    public static final String APP_PREFIX = "WI";
    public static final String USER_AGENT = "website-inspector (becvar.xyz)";

    public static void main(String[] args)
    {
        String url = null;

        // init scanner instance
        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            url = args[0];
        } else {
            Logger.prompt("Enter URL");

            // get url from user input
            url = scanner.nextLine();
        }

        // validate url
        url = Validator.validateUrl(url);

        // print server info title header
        Logger.printSpacer();
        Logger.log("Server Info");
        Logger.printSpacer();

        // get server info
        String[] serverDetails = ServerInfo.getServerInfo(url);

        // print the default server info
        Logger.printColoredKeyValue("Server IP Address", serverDetails[0].split(": ")[1]);
        Logger.printColoredKeyValue("Server Type", serverDetails[1].split(": ")[1]);
        Logger.printColoredKeyValue("CMS", ServerInfo.detectCms(url));

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

        // print robots.txt summary
        Logger.printSpacer();
        Logger.log("Robots.txt Summary");
        Logger.printSpacer();
        String robotsSummary = SiteMapInfo.getRobotsTxtAnalyze(url);
        Logger.rawLog(robotsSummary);

        // print sitemap.xml summary
        Logger.printSpacer();
        Logger.log("Sitemap.xml Summary");
        Logger.printSpacer();
        String sitemapSummary = SiteMapInfo.getSitemapAnalyze(url);
        Logger.rawLog(sitemapSummary);

        // print ending spacer
        Logger.printSpacer();
    }
}
