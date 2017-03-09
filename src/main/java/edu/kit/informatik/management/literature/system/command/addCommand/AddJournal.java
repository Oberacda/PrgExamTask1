package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/Output class for the add journal command.
 * <p>
 *     Syntax: {@literal "add journal <title>,<publisher>"}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class AddJournal implements Command {
    private static final Pattern ADDJOURNAL
            = Pattern.compile("add journal");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     *
     * @param lms the addController of the command.
     */
    public AddJournal(final AddController lms) {
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
        if (!(userCommand.startsWith(ADDJOURNAL.pattern()))) {
            return false;
        }
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(ADDJOURNAL + " ");
            sc.useDelimiter(",");
            String journalTitle = sc.next(PatternHolder.TITLEPATTERN);
            String journalPublisher = sc.next(PatternHolder.TITLEPATTERN);
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }

            lms.addJournal(journalTitle, journalPublisher);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"add journal <title>,<publisher>\"!");
        }
        return true;
    }
}
