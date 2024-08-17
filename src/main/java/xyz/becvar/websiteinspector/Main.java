package xyz.becvar.websiteinspector;

import xyz.becvar.websiteinspector.utils.console.Logger;
import xyz.becvar.websiteinspector.utils.console.ConsoleUtils;

/**
 * Class Main
 *
 * @package xyz.becvar.websiteinspector
 */
public class Main
{
    // define global variables
    public static final String APP_PREFIX = "WI";

    /**
     * Main app method
     *
     * @param args arguments
     */
    public static void main(String[] args)
    {
        System.out.println(Validator.validateUrl("https://www.google.com"));
        ConsoleUtils.clearConsole();

        Logger.log("Hello World!");
        Logger.errorLog("Error: Something went wrong!");
    }
}
