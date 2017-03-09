package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.system.command.Command;

import java.util.regex.Pattern;

/**
 * Parsing/Output class for the all publications command!
 *
 * @author David Oberacker
 * @version $Id: $Id
 */
public class AllPublications implements Command {

    private static final Pattern ALLPUBLICATIONS
            = Pattern.compile("all publications");

    private GetController lms;

    /**
     * Default constructor for getController commands.
     *
     * @param lms the gerController of the command.
     */
    public AllPublications(final GetController lms) {
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
        if (!(userCommand.startsWith(ALLPUBLICATIONS.pattern()))) {
            return false;
        }
        if (ALLPUBLICATIONS.matcher(userCommand).matches()) {
            lms.allPublications().forEach(Terminal::printLine);
        } else {
            Terminal.printError("invalid syntax, expected: \"all publications\"!");
        }
        return true;
    }
}
