package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class ForeignCitations implements Command {
    private static final Pattern FOREIGNCITATIONS
            = Pattern.compile("foreign citations ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(FOREIGNCITATIONS.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     * @param lms the complexController of the command.
     */
    public ForeignCitations(final ComplexController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(FOREIGNCITATIONS);

        sc.useDelimiter(" ");

        String firstName;
        String lastName;

        try {
            firstName = sc.next(PatternHolder.NAMEPATTERN);
            lastName = sc.next(PatternHolder.NAMEPATTERN);
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
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
