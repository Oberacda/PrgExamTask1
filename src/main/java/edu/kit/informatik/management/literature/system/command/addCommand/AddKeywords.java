package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.CommandUtil;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddKeywords extends Command {
    private static final Pattern ADDKEYWORD
            = Pattern.compile("add keywords ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(ADDKEYWORD.pattern()
            + PatternHolder.TOENTITY
            + ":"
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
        sc.skip(ADDKEYWORD);

        sc.useDelimiter(":");

        String entityId = sc.next(PatternHolder.TOENTITY);

        sc.useDelimiter(";");
        ArrayList<String> paramList = new ArrayList<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        try {
            Entity e = CommandUtil.getEntityFormPrefix(lm, entityId);
            for (String keyword:paramList) {
                e.addKeyword(keyword);
            }
            Terminal.printLine("OK");
        } catch (NoSuchElementException | IllegalArgumentException | BadSyntaxException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
