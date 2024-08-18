package xyz.becvar.websiteinspector.utils;

import xyz.becvar.websiteinspector.Main;

public class Logger
{
    // ANSI message color codes
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // log prefix
    public static String Prefix = ANSI_YELLOW + "[" + ANSI_GREEN + Main.APP_PREFIX + ANSI_YELLOW + "]" + ANSI_YELLOW + ": " + ANSI_CYAN;

    public static void printSpacer()
    {
        System.out.println(ANSI_CYAN + "========================================================================================");
    }

    public static void prompt(String msg)
    {
        System.out.print(Prefix + msg + ": " + ANSI_RESET);
    }

    public static void log(String msg)
    {
        System.out.println(Prefix + msg);
    }

    public static void printColoredKeyValue(String key, String value)
    {
        System.out.println(ANSI_YELLOW + key + ANSI_RESET + ": " + ANSI_GREEN + value + ANSI_RESET);
    }

    public static void error(String msg)
    {
        System.out.println(Prefix + ANSI_RED + msg);
        SystemUtils.shutdown();
    }
}
