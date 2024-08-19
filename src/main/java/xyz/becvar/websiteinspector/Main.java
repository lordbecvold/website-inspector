package xyz.becvar.websiteinspector;

import java.util.Scanner;
import xyz.becvar.websiteinspector.utils.Logger;
import xyz.becvar.websiteinspector.utils.WebsiteUtils;

public class Main
{
    // define global variables
    public static final String APP_PREFIX = "WI";
    public static String url = null;

    public static void main(String[] args)
    {
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

        // print server info data
        ServerInfo.printServerInfo(url);

        // print robots.txt summary
        Logger.printSpacer();
        Logger.log("Robots.txt Summary");
        Logger.printSpacer();
        String robotsSummary = WebsiteUtils.analyzeRobotsTxt(url);
        Logger.rawLog(robotsSummary);

        // print sitemap.xml summary
        Logger.printSpacer();
        Logger.log("Sitemap.xml Summary");
        Logger.printSpacer();
        String sitemapSummary = WebsiteUtils.analyzeSitemap(url);
        Logger.rawLog(sitemapSummary);

        // print ending spacer
        Logger.printSpacer();
    }
}
