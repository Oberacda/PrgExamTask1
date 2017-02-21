package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddAuthor implements Command {

    private static final Pattern ADDAUTHOR
            = Pattern.compile("add author ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDAUTHOR.pattern()
            + "\\S((.)+\\S)*");

    private AddController lms;

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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDAUTHOR);
        sc.useDelimiter(",");
        try {
            String firstName = sc.next(PatternHolder.NAMEPATTERN);
            String lastName = sc.next(PatternHolder.NAMEPATTERN);
            lms.addAuthor(firstName, lastName);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError(String.format("missing command token: \"%s%s,%s\"!", ADDAUTHOR,
                    PatternHolder.NAMEPATTERN, PatternHolder.NAMEPATTERN));
        }
        return true;
    }
}
