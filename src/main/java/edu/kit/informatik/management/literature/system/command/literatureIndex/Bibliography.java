package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.LiteratureIndexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Parser class for the bibliography command.
 *
 * @author David Oberacker
 * @version $Id: $Id
 */
public class Bibliography implements Command {
    private static final Pattern BIBLIOGRAPHY
            = Pattern.compile("print bibliography");

    private LiteratureIndexController lms;

    /**
     * Default constructor for literatureIndexController commands.
     *
     * @param lms the literatureIndexController of the command.
     */
    public Bibliography(final LiteratureIndexController lms) {
        this.lms = lms;
    }

    /**
     * {@inheritDoc}
     *
     * Executes the Command on the {@code CampusManagement} with the parameters
     * given in the {@code userCommand} parameter.
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(BIBLIOGRAPHY.pattern()))) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(BIBLIOGRAPHY + " ");

        String style;
        Set<String> articleList = new LinkedHashSet<>();
        sc.useDelimiter(":");
        try {
            style = sc.next("[a-z]+");

            sc.skip(":");
            sc.useDelimiter(";");

            while (sc.hasNext(PatternHolder.IDPATTERN)) {
                articleList.add(sc.next(PatternHolder.IDPATTERN));
            }

            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"print bibliography <style>:<id1>;<id2>;...\"!");
            return true;
        }
        try {
            lms.printBibliography(style, articleList).forEach(Terminal::printLine);
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
