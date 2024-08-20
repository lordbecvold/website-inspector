package xyz.becvar.websiteinspector.modules;

import java.util.List;
import java.util.ArrayList;
import xyz.becvar.websiteinspector.utils.WebsiteUtils;

public class SiteMapInfo
{
    public static String getRobotsTxtAnalyze(String url)
    {
        String robotsUrl = url + "/robots.txt";
        String content = WebsiteUtils.downloadFileContent(robotsUrl);

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

    public static String getSitemapAnalyze(String url)
    {
        String sitemapUrl = url + "/sitemap.xml";
        String content = WebsiteUtils.downloadFileContent(sitemapUrl);

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
