package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.LiteratureIndexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectPrintJournal implements Command {
    private static final Pattern DIRECTPRINTJOURNAL
            = Pattern.compile("direct print journal ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(DIRECTPRINTJOURNAL.pattern()
            + "\\S(.)+\\S");

    private LiteratureIndexController lms;

    /**
     * Default constructor for literatureIndexController commands.
     * @param lms the literatureIndexController of the command.
     */
    public DirectPrintJournal(final LiteratureIndexController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }


        String style;
        String articleTitle;
        String journalTitle;
        int year;
        Set<String> authorList = new LinkedHashSet<>();
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(DIRECTPRINTJOURNAL);
            sc.useDelimiter(":");

            style = sc.next("[a-z]+");

            sc.skip(":");
            sc.useDelimiter(",");

            while (sc.hasNext(edu.kit.informatik.management.literature.system.command.
                    PatternHolder.AUTHORPATTERN)) {
                authorList.add(sc.next(edu.kit.informatik.management.literature.system.command.
                        PatternHolder.AUTHORPATTERN));
            }
            int cnt = authorList.size();

            if (cnt <= 0) {
                throw new NoSuchElementException();
            }

            while (cnt < 3) {
                sc.skip(",");
                cnt++;
            }

            articleTitle = sc.next(PatternHolder.ARTICLETITLEPATTERN);
            journalTitle = sc.next(PatternHolder.TITLEPATTERN);
            year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }


        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"direct print journal <style>:<author1>,<author2*>"
                    + ",<author3*>,<title>,<journal>,<year>\"!");
            return true;
        }
        try {
            Terminal.printLine(lms.directPrintJournal(journalTitle, year,
                    articleTitle, authorList, style));
        } catch (IllegalArgumentException | ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
