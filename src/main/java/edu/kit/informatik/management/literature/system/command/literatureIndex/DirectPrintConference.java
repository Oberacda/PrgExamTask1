package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.LiteratureIndexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Output/Parsing class for the direct print conference command.
 * <p>
 *     Syntax: "direct print conference {@code&lt;style&gt;}&lt;style&gt;:&lt;author1&gt;,&lt;author2*&gt;
 *     ,&lt;author3*&gt;,&lt;title&gt;,&lt;series&gt;,&lt;year&gt;"}.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class DirectPrintConference implements Command {
    private static final Pattern DIRECTPRINTCONFERENCE
            = Pattern.compile("direct print conference");

    private LiteratureIndexController lms;

    /**
     * Default constructor for literatureIndexController commands.
     *
     * @param lms the literatureIndexController of the command.
     */
    public DirectPrintConference(final LiteratureIndexController lms) {
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
        if (!(userCommand.startsWith(DIRECTPRINTCONFERENCE.pattern()))) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(DIRECTPRINTCONFERENCE + " ");

        sc.useDelimiter(":");

        String style;
        String articleTitel;
        String conferenceTitle;
        String location;
        int year;
        Set<String> authorSet = new LinkedHashSet<>();

        try {
            style = sc.next("[a-z]+");

            sc.skip(":");
            sc.useDelimiter(",");

            while (sc.hasNext(edu.kit.informatik.management.literature.system.command.
                    PatternHolder.AUTHORPATTERN)) {
                authorSet.add(sc.next(edu.kit.informatik.management.literature.system.command.
                        PatternHolder.AUTHORPATTERN));
            }
            int cnt = authorSet.size();

            if (cnt <= 0) {
                throw new NoSuchElementException();
            }

            while (cnt < 3) {
                sc.skip(",");
                cnt++;
            }

            articleTitel = sc.next();
            conferenceTitle = sc.next(PatternHolder.TITLEPATTERN);
            location = sc.next(PatternHolder.LOCATIONPATTERN);
            year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }

        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"direct print conference <style>:<author1>,<author2*>"
                    + ",<author3*>,<title>,<series>,<year>\"!");
            return true;
        }
        try {

            Terminal.printLine(lms.directPrintConference(conferenceTitle,
                    location, year, articleTitel, authorSet, style));
        } catch (IllegalArgumentException | ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
