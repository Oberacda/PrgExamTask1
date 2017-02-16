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
public class Biblography extends Command {
    private static final Pattern BILIOGRAPHY
            = Pattern.compile("print bibliography ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(BILIOGRAPHY.pattern()
            + "\\S(.)+\\S");

    /**
     * Executes the Command on the {@code CampusManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param userCommand
     *         String entered by the terminal user.
     * @param lms
     *         Literature management system that should be worked on.
     *
     * @return true - execution was succesful.
     */
    @Override
    public boolean execute(final LiteratureManagementSystem lms, final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(BILIOGRAPHY);

        String style;
        Set<String> articleList = new HashSet<>();

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
            lms.printBibliography(style, articleList);
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
