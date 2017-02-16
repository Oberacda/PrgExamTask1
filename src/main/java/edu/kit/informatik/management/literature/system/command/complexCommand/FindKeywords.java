package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class FindKeywords extends Command {
    private static final Pattern FINDKEYWORDS
            = Pattern.compile("find keywords ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(FINDKEYWORDS.pattern()
            + "\\S(.)+\\S");


    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param lms
     *         Literature management that should be worked on.
     */
    @Override
    public boolean execute(final LiteratureManagementSystem lms,
                           final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(FINDKEYWORDS);

        sc.useDelimiter(";");
        Set<String> paramSet = new HashSet<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramSet.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        lms.findKeywords(paramSet).forEach(Terminal::printLine);
        return true;
    }
}
