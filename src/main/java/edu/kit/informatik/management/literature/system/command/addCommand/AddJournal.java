package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddJournal extends Command {
    private static final Pattern ADDJOURNAL
            = Pattern.compile("add journal ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDJOURNAL.pattern()
            + "\\S((.)+\\S)*");

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
        sc.skip(ADDJOURNAL);
        sc.useDelimiter(",");
        try {
            String journalTitle = sc.next(PatternHolder.TITLEPATTERN);
            String journalPublisher = sc.next();
            lms.addJournal(journalTitle, journalPublisher);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
        }
        return true;
    }
}
