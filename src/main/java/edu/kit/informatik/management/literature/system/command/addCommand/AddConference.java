package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

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

    private static final Pattern COMMANDPATTERN
            = Pattern.compile(ADDCONFERENCE.pattern()
            + PatternHolder.TITLEPATTERN
            + ","
            + PatternHolder.YEARPATTERN
            + ","
            + PatternHolder.LOCATIONPATTERN);

    /**
     * Checks if the input string {@code userInput} matches the
     * Pattern of the command.
     * <p>
     * Is called by the
     * {@linkplain Command#execute(LiteratureManagement, String)}
     * method to check if command is valid. No need to call
     * it directly.
     * </p>
     *
     * @param userInput
     *         Input string by the terminal application user.
     *
     * @return true - the input is a command of this Class.
     */
    @Override
    protected boolean matchesPattern(final String userInput) {
        return COMMANDPATTERN.matcher(userInput).matches();
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     * <p>
     * This method calls the method
     * {@linkplain Command#matchesPattern(String)}
     * only if it has returned true, the command
     * will be executed.
     * </p>
     *
     * @param lm
     *         Literature management that should be worked on.
     */
    @Override
    public boolean execute(final LiteratureManagement lm,
                        final String userCommand) {
        if (!(this.matchesPattern(userCommand))) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDCONFERENCE);
        ArrayList<String> parameterList = new ArrayList<>();
        if (sc.hasNext(PatternHolder.TITLEPATTERN)) {
            parameterList.add(sc.next(PatternHolder.TITLEPATTERN));
        }
        if (sc.hasNext(PatternHolder.YEARPATTERN)) {
            parameterList.add(sc.next(PatternHolder.YEARPATTERN));
        }
        if (sc.hasNext(PatternHolder.LOCATIONPATTERN)) {
            parameterList.add(sc.next(PatternHolder.LOCATIONPATTERN));
        }
        try {
            lm.addConferenceToSeries(parameterList.get(0)
                    , parameterList.get(2)
                    , Integer.parseInt(parameterList.get(1)));
            Terminal.printLine("OK");
        } catch (ElementAlreadyPresentException | NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
