package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddConference extends Command {
    private static final Pattern ADDCONFERENCE
            = Pattern.compile("add conference ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDCONFERENCE.pattern()
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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())
                || Pattern.matches("add conference series \\S((.)+\\S)*",
                userCommand)) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDCONFERENCE);
        ArrayList<String> parameterList = new ArrayList<>();
        String conferencerSeriesName;
        int conferencerYear;
        String conferencerLocation;
        try {
            sc.useDelimiter(",");
            conferencerSeriesName = sc.next(PatternHolder.TITLEPATTERN);
            conferencerYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            conferencerLocation = sc.next(PatternHolder.LOCATIONPATTERN);
        } catch (NoSuchElementException exc) {
            Terminal.printError(String.format("invalid command token :\"%s%s%s%s\"",
                    ADDCONFERENCE, PatternHolder.TITLEPATTERN,
                    PatternHolder.YEARPATTERN, PatternHolder.LOCATIONPATTERN));
            return true;
        }
        try {
            lms.addConference(conferencerSeriesName,
                    conferencerYear,
                    conferencerLocation);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException | NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
