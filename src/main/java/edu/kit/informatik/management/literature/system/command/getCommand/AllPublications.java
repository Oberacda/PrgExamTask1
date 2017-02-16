package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.system.command.Command;

import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AllPublications extends Command {

    private static final Pattern COMMANDPATTERN
            = Pattern.compile("all publications");

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
        lms.allPublications().forEach(Terminal::printLine);
        return true;
    }
}
