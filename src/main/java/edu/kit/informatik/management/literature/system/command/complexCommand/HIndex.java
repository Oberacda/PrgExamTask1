package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Output/Parsing class for the h-index command.
 * <p>
 *     Syntax: {@literal "h-index <firstname> <lastname>"}!
 * </p>
 * @author David Oberacker
 * @version 1.0.0
 */
public class HIndex implements Command {
    private static final Pattern HINDEX
            = Pattern.compile("h-index");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     * @param lms the complexController of the command.
     */
    public HIndex(final ComplexController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(HINDEX.pattern()))) {
            return false;
        }

        String firstName;
        String lastName;

        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(HINDEX + " ");

            sc.useDelimiter(" ");
            firstName = sc.next(PatternHolder.NAMEPATTERN);
            lastName = sc.next(PatternHolder.NAMEPATTERN);
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"h-index <firstname> <lastname>\"!");
            return true;
        }
        try {
            Terminal.printLine(lms.hIndex(firstName, lastName));
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
