package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.terminal.Terminal;

import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class ListInvalidPublications extends Command {

    private static final Pattern LISTINVALIDPUBLICATIONS
            = Pattern.compile("list invalid publications");

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
        if (!(LISTINVALIDPUBLICATIONS.matcher(userCommand).matches())) {
            return false;
        }
        lms.listInvalidPublications().forEach(Terminal::printLine);
        return true;
    }
}
