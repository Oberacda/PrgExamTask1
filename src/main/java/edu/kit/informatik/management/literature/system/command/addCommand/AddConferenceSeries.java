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
public class AddConferenceSeries implements Command {
    private static final Pattern ADDCONFERENCESERIES
            = Pattern.compile("add conference series ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDCONFERENCESERIES.pattern()
            + "\\S((.)+\\S)*");

    private AddController lms;

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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDCONFERENCESERIES);
        try {
            String conferenceSeriesTitle = sc.next(PatternHolder.TITLEPATTERN);
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
