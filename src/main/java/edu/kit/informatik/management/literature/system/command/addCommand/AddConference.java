package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddConference implements Command {
    private static final Pattern ADDCONFERENCE
            = Pattern.compile("add conference ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDCONFERENCE.pattern()
            + "\\S((.)+\\S)*");

    private AddController lms;

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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())
                || Pattern.matches("add conference series \\S((.)+\\S)*",
                userCommand)) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDCONFERENCE);
        ArrayList<String> parameterList = new ArrayList<>();
        String conferenceSeriesName;
        int conferenceYear;
        String conferenceLocation;
        try {
            sc.useDelimiter(",");
            conferenceSeriesName = sc.next(PatternHolder.TITLEPATTERN);
            conferenceYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            conferenceLocation = sc.next(PatternHolder.LOCATIONPATTERN);
        } catch (NoSuchElementException exc) {
            Terminal.printError(String.format("invalid command token :\"%s%s%s%s\"",
                    ADDCONFERENCE, PatternHolder.TITLEPATTERN,
                    PatternHolder.YEARPATTERN, PatternHolder.LOCATIONPATTERN));
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
