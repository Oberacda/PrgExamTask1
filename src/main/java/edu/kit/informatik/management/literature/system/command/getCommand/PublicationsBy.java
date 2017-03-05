package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.Terminal;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class PublicationsBy implements Command {
    private static final Pattern PUBICATIONSBY
            = Pattern.compile("publications by ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(PUBICATIONSBY.pattern()
            + "\\S(.)+\\S");

    private GetController lms;

    /**
     * Default constructor for getController commands.
     * @param lms the gerController of the command.
     */
    public PublicationsBy(final GetController lms) {
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

        Set<String> paramSet;
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(PUBICATIONSBY);

            paramSet = new HashSet<>();

            sc.useDelimiter(";");
            while (sc.hasNext(edu.kit.informatik.management.literature.system.command.PatternHolder.AUTHORPATTERN)) {
                paramSet.add(sc.next(edu.kit.informatik.management.literature.system.command.PatternHolder.AUTHORPATTERN));
            }
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException("invalid syntax, expected: \"publications by <list of auhtors>\"!");
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError(nse.getMessage());
            return true;
        }
        try {
            lms.publicationsBy(paramSet).forEach(Terminal::printLine);
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
