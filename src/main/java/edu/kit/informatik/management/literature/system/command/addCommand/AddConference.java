package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/Output class for the add conference command.
 * <p>
 *     Syntax: {@literal "add conference <series title>,<year>,<location>"}!
 * </p>
 * @author David Oberacker
 * @version 1.0.0
 */
public class AddConference implements Command {
    private static final Pattern ADDCONFERENCE
            = Pattern.compile("add conference");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     * @param lms the addController of the command.
     */
    public AddConference(final AddController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(ADDCONFERENCE.pattern()))
                || Pattern.matches("add conference series " + PatternHolder.TITLEPATTERN,
                userCommand)) {
            return false;
        }

        String conferenceSeriesName;
        int conferenceYear;
        String conferenceLocation;
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(ADDCONFERENCE + " ");
            sc.useDelimiter(",");
            conferenceSeriesName = sc.next(PatternHolder.TITLEPATTERN);
            conferenceYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            conferenceLocation = sc.next(PatternHolder.LOCATIONPATTERN);
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException exc) {
            Terminal.printError(String.format("invalid syntax, expected:\"%s <conference series>,<year>,<location>\"!",
                    ADDCONFERENCE));
            return true;
        }
        try {
            lms.addConference(conferenceSeriesName,
                    conferenceYear,
                    conferenceLocation);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException | NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
