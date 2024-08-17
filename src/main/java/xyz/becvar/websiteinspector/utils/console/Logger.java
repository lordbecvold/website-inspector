package xyz.becvar.websiteinspector.utils.console;

import xyz.becvar.websiteinspector.Main;

/**
 * Class Logger
 *
 * @package xyz.becvar.websiteinspector.utils.console
 */
public class Logger
{
    /**
     * Prefix for console output
     *
     * @var string
     */
    public static String Prefix = ConsoleColors.CODES.ANSI_YELLOW + "[" + ConsoleColors.CODES.ANSI_GREEN + Main.APP_PREFIX + ConsoleColors.CODES.ANSI_YELLOW + "]" + ConsoleColors.CODES.ANSI_YELLOW + ": " + ConsoleColors.CODES.ANSI_CYAN;

    /**
     * Log a message
     *
     * @param msg message to log
     */
    public static void log(String msg)
    {
        System.out.println(Prefix + msg);
    }

    /**
     * Log an error message
     *
     * @param msg message to log
     */
    public static void errorLog(String msg)
    {
        System.out.println(Prefix + ConsoleColors.CODES.ANSI_RED + msg);
        System.exit(1);
    }
}
