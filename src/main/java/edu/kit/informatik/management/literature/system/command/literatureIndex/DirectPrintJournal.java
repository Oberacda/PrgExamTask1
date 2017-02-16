package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectPrintJournal extends Command {
    private static final Pattern DIRECTPRINTJOURNAL
            = Pattern.compile("direct print conference ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(DIRECTPRINTJOURNAL.pattern()
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
        sc.skip(DIRECTPRINTJOURNAL);

        String style;
        String articleTitle;
        String journalTitle;
        int year;
        Set<String> authorList = new HashSet<>();

        try {
            style = sc.next("[a-z]+");

            sc.skip(":");
            sc.useDelimiter(",");

            while (sc.hasNext(PatternHolder.AUTHORPATTERN)) {
                authorList.add(sc.next(PatternHolder.AUTHORPATTERN));
            }
            int cnt = authorList.size();

            while (cnt < 3) {
                sc.skip(",");
                cnt++;
            }

            articleTitle = sc.next(PatternHolder.ARTICLETITLEPATTERN);
            journalTitle = sc.next(PatternHolder.TITLEPATTERN);
            year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
            return true;
        }
        try {
            Terminal.printLine(lms.directPrintJournal(journalTitle, year,
                    articleTitle, authorList, style));
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
