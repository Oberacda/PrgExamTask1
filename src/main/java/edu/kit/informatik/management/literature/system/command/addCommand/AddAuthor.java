package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddAuthor extends Command {

    private static final int PARAMETER_COUNT = 2;

    private static final Pattern ADDAUTHOR
            = Pattern.compile("add author ");

    private static final Pattern COMMANDPATTERN
            = Pattern.compile(ADDAUTHOR.pattern()
            + PatternHolder.NAMEPATTERN
            + ","
            + PatternHolder.NAMEPATTERN);

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
    public void execute(final LiteratureManagement lm, final String userCommand) {
        if (!(this.matchesPattern(userCommand))) {
            return;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDAUTHOR);
        sc.useDelimiter(",");
        ArrayList<String> parameterList = new ArrayList<>();
        while (sc.hasNext(PatternHolder.NAMEPATTERN)) {
            parameterList.add(sc.next(PatternHolder.NAMEPATTERN));
        }
        try {
            if (parameterList.size() == PARAMETER_COUNT) {
                lm.addAuthor(parameterList.get(0), parameterList.get(1));
                Terminal.printLine("OK");
            }
        } catch (ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        }
    }
}