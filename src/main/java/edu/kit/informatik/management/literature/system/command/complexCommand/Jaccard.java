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
public class Jaccard extends Command {
    private static final Pattern JACCARD
            = Pattern.compile("jaccard ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(JACCARD.pattern()
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
        sc.skip(JACCARD);

        sc.useDelimiter(";");
        Set<String> paramList1 = new HashSet<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList1.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        sc.skip(" ");

        sc.useDelimiter(";");
        Set<String> paramList2 = new HashSet<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList2.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        Terminal.printLine(lms.jaccardIndex(paramList1, paramList2));
        return true;
    }
}
