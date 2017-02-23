package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.system.command.Command;

import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AllPublications implements Command {

    private static final Pattern COMMANDPATTERN
            = Pattern.compile("all publications");

    private GetController lms;

    /**
     * Default constructor for getController commands.
     * @param lms the gerController of the command.
     */
    public AllPublications(final GetController lms) {
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
        lms.allPublications().forEach(Terminal::printLine);
        return true;
    }
}
