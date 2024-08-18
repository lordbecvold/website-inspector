package xyz.becvar.websiteinspector;

import java.util.Scanner;
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
    public static String url = null;

    /**
     * Main app method
     *
     * @param args arguments
     *
     * @return void
     */
    public static void main(String[] args)
    {
        // init scanner instance
        Scanner scanner = new Scanner(System.in);

        // clear console
        ConsoleUtils.clearConsole();

        if (args.length > 0) {
            url = args[0];
        } else {
            Logger.prompt("wfwfwf");

            url = scanner.nextLine();
        }

        // validate url
        url = Validator.validateUrl(url);

        // print url
        System.out.println(url);
    }
}
