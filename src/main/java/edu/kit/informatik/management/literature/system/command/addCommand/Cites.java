package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class Cites implements Command {
    private static final Pattern CITES
            = Pattern.compile("cites ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(CITES.pattern()
            + "\\S(.)+\\S");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     * @param lms the addController of the command.
     */
    public Cites(final AddController lms) {
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
        sc.skip(CITES);

        sc.useDelimiter(",");
        String articleId;
        String citedArticleId;

        try {
            articleId = sc.next(PatternHolder.IDPATTERN);
            citedArticleId = sc.next(PatternHolder.IDPATTERN);
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
            return true;
        }

        try {
            lms.cites(articleId, citedArticleId);
            Terminal.printLine("Ok");
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
