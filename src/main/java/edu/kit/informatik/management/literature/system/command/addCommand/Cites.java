package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/Output class for the cites command.
 * <p>
 *     Syntax: {@literal "cites <id1>,<id2>"}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class Cites implements Command {
    private static final Pattern CITES
            = Pattern.compile("cites");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     *
     * @param lms the addController of the command.
     */
    public Cites(final AddController lms) {
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
        if (!(userCommand.startsWith(CITES.pattern()))) {
            return false;
        }

        String articleId;
        String citedArticleId;

        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(CITES + " ");

            sc.useDelimiter(",");
            articleId = sc.next(PatternHolder.IDPATTERN);
            citedArticleId = sc.next(PatternHolder.IDPATTERN);

            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"cites <id>,<id>\"!");
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
