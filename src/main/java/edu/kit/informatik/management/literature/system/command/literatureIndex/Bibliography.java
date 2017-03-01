package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.LiteratureIndexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Parser class for the bibliography command.
 *
 * @author David Oberacker
 */
public class Bibliography implements Command {
    private static final Pattern BIBLIOGRAPHY
            = Pattern.compile("print bibliography ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(BIBLIOGRAPHY.pattern()
            + "\\S(.)+\\S");

    private LiteratureIndexController lms;

    /**
     * Default constructor for literatureIndexController commands.
     * @param lms the literatureIndexController of the command.
     */
    public Bibliography(final LiteratureIndexController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code CampusManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param userCommand
     *         String entered by the terminal user.
     * @return true - execution was succesful.
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(BIBLIOGRAPHY);

        String style;
        Set<String> articleList = new HashSet<>();
        sc.useDelimiter(":");
        try {
            style = sc.next("[a-z]+");

            sc.skip(":");
            sc.useDelimiter(";");

            while (sc.hasNext(PatternHolder.IDPATTERN)) {
                articleList.add(sc.next(PatternHolder.IDPATTERN));
            }

        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
            return true;
        }
        try {
            lms.printBibliography(style, articleList).forEach(Terminal::printLine);
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
