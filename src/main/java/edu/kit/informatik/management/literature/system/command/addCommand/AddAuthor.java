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
 * Parsing class for the add author command.
 * <p>
 *     Syntax: {@literal "add author <firstname>,<lastname>"}.
 * </p>
 * @author David Oberacker
 * @version 1.0.0
 */
public class AddAuthor implements Command {

    private static final Pattern ADDAUTHOR
            = Pattern.compile("add author");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     * @param lms the addController of the command.
     */
    public AddAuthor(final AddController lms) {
        this.lms = lms;
    }


    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(ADDAUTHOR.pattern()))) {
            return false;
        }
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(ADDAUTHOR + " ");
            sc.useDelimiter(",");
            String firstName = sc.next(PatternHolder.NAMEPATTERN);
            String lastName = sc.next(PatternHolder.NAMEPATTERN);
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }

            lms.addAuthor(firstName, lastName);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError(String.format("invalid command token, expected:"
                    + " \"%s <firstname>,<lastname>\"!", ADDAUTHOR));
        }
        return true;
    }
}
