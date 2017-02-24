package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.LiteratureIndexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectPrintConference implements Command {
    private static final Pattern DIRECTPRINTCONFERENCE
            = Pattern.compile("direct print conference ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(DIRECTPRINTCONFERENCE.pattern()
            + "\\S(.)+\\S");

    private LiteratureIndexController lms;

    /**
     * Default constructor for literatureIndexController commands.
     * @param lms the literatureIndexController of the command.
     */
    public DirectPrintConference(final LiteratureIndexController lms) {
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
        Scanner sc = new Scanner(userCommand);
        sc.skip(DIRECTPRINTCONFERENCE);

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

            while (sc.hasNext(edu.kit.informatik.management.literature.system.command.PatternHolder.AUTHORPATTERN)) {
                authorSet.add(sc.next(edu.kit.informatik.management.literature.system.command.PatternHolder.AUTHORPATTERN));
            }
            int cnt = authorSet.size();

            while (cnt < 3) {
                sc.skip(",");
                cnt++;
            }

            articleTitel = sc.next();
            conferenceTitle = sc.next(PatternHolder.TITLEPATTERN);
            location = sc.next(PatternHolder.LOCATIONPATTERN);
            year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
            return true;
        }
        try {

            Terminal.printLine(lms.directPrintConference(conferenceTitle,
                    location, year, articleTitel, authorSet, style));
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
