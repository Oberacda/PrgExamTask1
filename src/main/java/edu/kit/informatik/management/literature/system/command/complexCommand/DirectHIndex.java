package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectHIndex extends Command {
    private static final Pattern DIRECTHINDEX
            = Pattern.compile("direct h-index ");

    private static final Pattern INTEGERSPATTERN
            = Pattern.compile("[0-9]+([;][0-9]+)*");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(DIRECTHINDEX.pattern()
            + INTEGERSPATTERN);

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
        sc.skip(DIRECTHINDEX);

        sc.useDelimiter(";");
        ArrayList<Integer> paramList1 = new ArrayList<>();

        while (sc.hasNextInt()) {
            paramList1.add(sc.nextInt());
        }

        Terminal.printLine(LiteratureManagement.calculateHIndex(paramList1));
        return true;
    }
}
