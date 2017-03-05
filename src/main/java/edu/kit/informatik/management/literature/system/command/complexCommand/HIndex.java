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
public class HIndex implements Command {
    private static final Pattern HINDEX
            = Pattern.compile("h-index ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(HINDEX.pattern()
            + "\\S(.)+\\S");

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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }

        String firstName;
        String lastName;

        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(HINDEX);

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
