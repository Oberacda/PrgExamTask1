package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Output/Parsing class for the similarity command.
 * <p>
 *     Syntax: {@literal "similarity <id1>,<id2>"}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class Similarity implements Command {
    private static final Pattern SIMILARITY
            = Pattern.compile("similarity");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     *
     * @param lms the complexController of the command.
     */
    public Similarity(final ComplexController lms) {
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
        if (!(userCommand.startsWith(SIMILARITY.pattern()))) {
            return false;
        }

        String articleId1;
        String articleId2;

        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(SIMILARITY + " ");

            sc.useDelimiter(",");
            articleId1 = sc.next(PatternHolder.IDPATTERN);
            articleId2 = sc.next(PatternHolder.IDPATTERN);

            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"similarity <id1>,<id2>\"!");
            return true;
        }

        try {
            Terminal.printLine(lms.similarity(articleId1, articleId2));
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
