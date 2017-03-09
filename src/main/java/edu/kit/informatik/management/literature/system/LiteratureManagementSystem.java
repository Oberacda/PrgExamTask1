package edu.kit.informatik.management.literature.system;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.controller.*;

import java.util.regex.Pattern;

/**
 * The main class of the terminal based literature management system.
 * <p>
 * This class starts an manages the terminal based literature management.
 * </p>
 *
 * @author David Oberacker
 * @version $Id: $Id
 */
public final class LiteratureManagementSystem extends Controller {

    //=================fields==========================

    private static final Pattern QUIT = Pattern.compile("quit");

    //=================constructor======================

    /**
     * Creates a new literature management system.
     * <p>
     * A new literature management system knows all
     * commands specified in it's constructor.
     * </p>
     */
    private LiteratureManagementSystem() {
        super(new LiteratureManagement());
        super.addCommand(new AddController(getLiteratureManagement()));
        super.addCommand(new ComplexController(getLiteratureManagement()));
        super.addCommand(new GetController(getLiteratureManagement()));
        super.addCommand(new LiteratureIndexController(getLiteratureManagement()));
    }

    //=================main method======================

    /**
     * The main method of the {@linkplain LiteratureManagementSystem}.
     *
     * @param args
     *         the command line arguments.
     */
    public static void main(String[] args) {
        LiteratureManagementSystem lms = new LiteratureManagementSystem();

        String userInput = Terminal.readLine();
        while (!(QUIT.matcher(userInput).matches())) {
            boolean result = false;
            if (userInput.matches("\\s+.")) {
                Terminal.printError("input starts with whitespace!");
                result = true;
            }
            /*if (userInput.matches("\\S((.)+)*\\s+")) {
                Terminal.printError("input has tailing whitespaces!");
                result = true;
            }*/
            if (!result && lms.execute(userInput)) {
                result = true;
            }
            if (!result) {
                Terminal.printError(String.format("invalid syntax! no command detected in input: \"%s\"!", userInput));
            }
            userInput = Terminal.readLine();
        }
        Terminal.printLine("Ok");
    }
}
