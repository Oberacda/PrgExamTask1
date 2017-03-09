package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Output/Parsing class for coauthors of command.
 * <p>
 *     Syntax: {@literal "coauthors of <firstname> <lastname>"}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class CoauthorsOf implements Command {
    private static final Pattern COAUTHORSOF
            = Pattern.compile("coauthors of");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     *
     * @param lms the complexController of the command.
     */
    public CoauthorsOf(final ComplexController lms) {
        this.lms = lms;
    }

    /**
     * {@inheritDoc}
     *
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(COAUTHORSOF.pattern()))) {
            return false;
        }
        String firstName;
        String lastName;
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(COAUTHORSOF + " ");

            sc.useDelimiter(" ");
            firstName = sc.next(PatternHolder.NAMEPATTERN);
            lastName = sc.next(PatternHolder.NAMEPATTERN);
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"coauthors of <firstname> <lastname>\"!");
            return true;
        }
        try {
            lms.coauthorsOf(firstName, lastName).forEach(Terminal::printLine);
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
