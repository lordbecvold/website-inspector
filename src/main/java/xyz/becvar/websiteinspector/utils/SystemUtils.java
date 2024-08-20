package xyz.becvar.websiteinspector.utils;

public class SystemUtils
{
    public static void shutdown(String msg)
    {
        Logger.error(msg);
        System.exit(0);
    }
}
