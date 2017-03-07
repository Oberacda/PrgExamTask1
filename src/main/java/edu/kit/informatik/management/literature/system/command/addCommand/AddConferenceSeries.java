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
 * Parsing/Output class for the add conference series command.
 * <p>
 *     Syntax: {@literal "add conference series <title>"}!
 * </p>
 * @author David Oberacker
 * @version 1.0.0
 */
public class AddConferenceSeries implements Command {
    private static final Pattern ADDCONFERENCESERIES
            = Pattern.compile("add conference series ");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     * @param lms the addController of the command.
     */
    public AddConferenceSeries(final AddController lms) {
        this.lms = lms;
    }


    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(ADDCONFERENCESERIES.pattern()))) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDCONFERENCESERIES);
        sc.useDelimiter(";");
        try {
            String conferenceSeriesTitle = sc.next(PatternHolder.TITLEPATTERN);
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
            lms.addConferenceSeries(conferenceSeriesTitle);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError(String.format("invalid command token :\"%s%s",
                    ADDCONFERENCESERIES, PatternHolder.TITLEPATTERN));
        }
        return true;
    }
}
