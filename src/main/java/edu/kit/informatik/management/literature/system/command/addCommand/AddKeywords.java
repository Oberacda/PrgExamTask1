package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddKeywords extends Command {
    private static final Pattern ADDKEYWORD
            = Pattern.compile("add keywords ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDKEYWORD.pattern()
            + "\\S(.)+\\S");

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
        sc.skip(ADDKEYWORD);
        try {
            sc.useDelimiter(":");
            String entityId = sc.next(PatternHolder.TOENTITY);
            sc.skip(":");
            sc.useDelimiter(";");
            HashSet<String> paramSet = new HashSet<>();

            if (!sc.hasNext(PatternHolder.KEYWORDSPATTERN)) {
                throw new BadSyntaxException("there are no keywords given!");
            }

            while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
                paramSet.add(sc.next(PatternHolder.KEYWORDPATTERN));
            }
            if (sc.hasNext()) {
                throw new BadSyntaxException("invalid keywords!");
            }

            lms.addKeywords(entityId, paramSet);
            Terminal.printLine("Ok");
        } catch (BadSyntaxException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
        }
        return true;
    }
}
