package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class PublicationsBy extends Command {
    private static final Pattern PUBICATIONSBY
            = Pattern.compile("publications by ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(PUBICATIONSBY.pattern()
            + "\\S(.)+\\S");

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param lms
     *         Literature management system that should be worked on.
     */
    @Override
    public boolean execute(final LiteratureManagementSystem lms,
                           final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(PUBICATIONSBY);

        Set<String> paramSet = new HashSet<>();

        sc.useDelimiter(";");
        while (sc.hasNext(PatternHolder.AUTHORPATTERN)) {
            paramSet.add(sc.next(PatternHolder.AUTHORPATTERN));
        }
        try {
            lms.publicationsBy(paramSet).forEach(Terminal::printLine);
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
