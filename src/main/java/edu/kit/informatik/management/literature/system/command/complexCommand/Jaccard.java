package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class Jaccard extends Command {
    private static final Pattern JACCARD
            = Pattern.compile("jaccard ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(JACCARD.pattern()
            + PatternHolder.KEYWORDSPATTERN
            + " "
            + PatternHolder.KEYWORDSPATTERN);

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
        sc.skip(JACCARD);

        sc.useDelimiter(";");
        ArrayList<String> paramList1 = new ArrayList<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList1.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        sc.skip(" ");

        ArrayList<String> paramList2 = new ArrayList<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList2.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        Terminal.printLine(LiteratureManagement.calculateJaccard(paramList1, paramList2));
        return true;
    }
}
