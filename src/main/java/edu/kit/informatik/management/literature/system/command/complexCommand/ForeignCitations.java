package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/Output class for the foreigin citations command.
 * <p>
 *     Syntax: {@literal "foreign citations of <firstname> <lastname>"}!
 * </p>
 *
 * @author David Oberacker
 * @version $Id: $Id
 */
public class ForeignCitations implements Command {
    private static final Pattern FOREIGNCITATIONS
            = Pattern.compile("foreign citations of ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(FOREIGNCITATIONS.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     *
     * @param lms the complexController of the command.
     */
    public ForeignCitations(final ComplexController lms) {
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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }

        String firstName;
        String lastName;

        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(FOREIGNCITATIONS);

            sc.useDelimiter(" ");

            firstName = sc.next(PatternHolder.NAMEPATTERN);
            lastName = sc.next(PatternHolder.NAMEPATTERN);

            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"foreign citations of <firstname> <lastname>\"!");
            return true;
        }
        try {
            lms.foreignCitations(firstName, lastName).forEach(Terminal::printLine);
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
