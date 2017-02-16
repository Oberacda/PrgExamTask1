package edu.kit.informatik.management.literature.system.command.addCommand;

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
public class WrittenBy extends Command {
    private static final Pattern WRITTENBY
            = Pattern.compile("written by ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(WRITTENBY.pattern()
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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(WRITTENBY);

        String articleId;
        ArrayList<String> paramList = new ArrayList<>();

        sc.useDelimiter(",");

        try {
            articleId = sc.next(PatternHolder.IDPATTERN);
            while (sc.hasNext(PatternHolder.AUTHORPATTERN)) {
                paramList.add(sc.next(PatternHolder.AUTHORPATTERN));
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
            return true;
        }
        try {
            lms.writtenBy(articleId, paramList);
            Terminal.printLine("Ok");
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
