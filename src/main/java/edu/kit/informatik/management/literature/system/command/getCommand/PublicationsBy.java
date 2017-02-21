package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.GetController;
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
public class PublicationsBy implements Command {
    private static final Pattern PUBICATIONSBY
            = Pattern.compile("publications by ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(PUBICATIONSBY.pattern()
            + "\\S(.)+\\S");

    private GetController lms;

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
