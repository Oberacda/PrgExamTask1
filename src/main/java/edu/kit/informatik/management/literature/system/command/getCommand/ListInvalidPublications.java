package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.terminal.Terminal;

import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class ListInvalidPublications implements Command {

    private static final Pattern LISTINVALIDPUBLICATIONS
            = Pattern.compile("list invalid publications");

    private GetController lms;

    public ListInvalidPublications(final GetController lms) {
        this.lms = lms;
    }


    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(LISTINVALIDPUBLICATIONS.matcher(userCommand).matches())) {
            return false;
        }
        lms.listInvalidPublications().forEach(Terminal::printLine);
        return true;
    }
}
