package xyz.becvar.websiteinspector.utils.console;

/**
 * Class ConsoleUtils
 *
 * @package xyz.becvar.websiteinspector.utils.console
 */
public class ConsoleUtils
{
    /**
     * Clear the console
     *
     * @return void
     */
    public static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
