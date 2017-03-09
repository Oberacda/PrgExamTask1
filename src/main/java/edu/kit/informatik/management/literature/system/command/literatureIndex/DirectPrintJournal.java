package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.LiteratureIndexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Output/Parsing class for the direct print journal command.
 * <p>
 *     Syntax: "direct print journal {@code&lt;style&gt;}&lt;style&gt;:&lt;author1&gt;,&lt;author2*&gt;
 *     ,&lt;author3*&gt;,&lt;title&gt;,&lt;journal&gt;,&lt;year&gt;".
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class DirectPrintJournal implements Command {
    private static final Pattern DIRECTPRINTJOURNAL
            = Pattern.compile("direct print journal");

    private LiteratureIndexController lms;

    /**
     * Default constructor for literatureIndexController commands.
     *
     * @param lms the literatureIndexController of the command.
     */
    public DirectPrintJournal(final LiteratureIndexController lms) {
        this.lms = lms;
    }

    /**
     * {@inheritDoc}
     *
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(DIRECTPRINTJOURNAL.pattern()))) {
            return false;
        }

        String style;
        String articleTitle;
        String journalTitle;
        int year;
        Set<String> authorList = new LinkedHashSet<>();
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(DIRECTPRINTJOURNAL + " ");
            sc.useDelimiter(":");

            style = sc.next("[a-z]+");

            sc.skip(":");
            sc.useDelimiter(",");

            while (sc.hasNext(edu.kit.informatik.management.literature.system.command.
                    PatternHolder.AUTHORPATTERN) && authorList.size() < 3) {
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
