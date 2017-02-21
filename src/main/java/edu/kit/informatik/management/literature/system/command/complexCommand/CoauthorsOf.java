package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class CoauthorsOf implements Command {
    private static final Pattern COAUTHORSOF
            = Pattern.compile("coauthors of ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(COAUTHORSOF.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    public CoauthorsOf(final ComplexController lms) {
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
        sc.skip(COAUTHORSOF);

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
            lms.coauthorsOf(firstName, lastName).forEach(Terminal::printLine);
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
