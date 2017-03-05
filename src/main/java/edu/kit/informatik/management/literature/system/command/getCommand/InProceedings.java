package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.GetController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class InProceedings implements Command {
    private static final Pattern INPROCEEDINGS
            = Pattern.compile("in proceedings ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(INPROCEEDINGS.pattern()
            + "\\S(.)+\\S");

    private GetController lms;

    /**
     * Default constructor for getController commands.
     * @param lms the gerController of the command.
     */
    public InProceedings(final GetController lms) {
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
        String seriesTitle;
        int year;

        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(INPROCEEDINGS);

            sc.useDelimiter(",");

            seriesTitle = sc.next(PatternHolder.TITLEPATTERN);

            year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: "
                    + "\"in proceedings <conferenceSeries>,<year>\"");
            return true;
        }
        try {
            lms.inProceedings(seriesTitle, year).forEach(Terminal::printLine);
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
