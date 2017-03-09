package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.Terminal;

import java.util.regex.Pattern;

/**
 * Parsing/Output class for the list invalid publications command.
 * <p>
 *     Syntax: {@literal "list invalid publications"}.
 * </p>
 *
 * @author David Oberacker
 * @version $Id: $Id
 */
public class ListInvalidPublications implements Command {

    private static final Pattern LISTINVALIDPUBLICATIONS
            = Pattern.compile("list invalid publications");

    private GetController lms;

    /**
     * Default constructor for getController commands.
     *
     * @param lms the gerController of the command.
     */
    public ListInvalidPublications(final GetController lms) {
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
        if (!(userCommand.startsWith(LISTINVALIDPUBLICATIONS.pattern()))) {
            return false;
        }
        if (LISTINVALIDPUBLICATIONS.matcher(userCommand).matches()) {
            lms.listInvalidPublications().forEach(Terminal::printLine);
        } else {
            Terminal.printError("invalid syntax, expected: \"list invalid publications\"!");
        }

        return true;
    }
}
