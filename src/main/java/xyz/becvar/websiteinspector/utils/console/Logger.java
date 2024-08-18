package xyz.becvar.websiteinspector.utils.console;

import xyz.becvar.websiteinspector.Main;
import xyz.becvar.websiteinspector.utils.SystemUtils;

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
     * Prompt a message
     *
     * @param msg message to prompt
     *
     * @return void
     */
    public static void prompt(String msg)
    {
        System.out.print(Prefix + msg + ": " + ConsoleColors.CODES.ANSI_RESET);
    }

    /**
     * Log a message
     *
     * @param msg message to log
     *
     * @return void
     */
    public static void log(String msg)
    {
        System.out.println(Prefix + msg);
    }

    /**
     * Log an error message
     *
     * @param msg message to log
     *
     * @return void
     */
    public static void error(String msg)
    {
        System.out.println(Prefix + ConsoleColors.CODES.ANSI_RED + msg);
        SystemUtils.shutdown();
    }
}
