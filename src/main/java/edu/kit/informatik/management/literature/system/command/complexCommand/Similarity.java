package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class Similarity implements Command {
    private static final Pattern SIMILARITY
            = Pattern.compile("similarity ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(SIMILARITY.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     * @param lms the complexController of the command.
     */
    public Similarity(final ComplexController lms) {
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
        sc.skip(SIMILARITY);

        sc.useDelimiter(",");

        String articleId1;
        String articleId2;

        try {
            articleId1 = sc.next(PatternHolder.IDPATTERN);
            articleId2 = sc.next(PatternHolder.IDPATTERN);
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token: \""
                    + SIMILARITY
                    + PatternHolder.IDPATTERN
                    + "," + PatternHolder.IDPATTERN
                    + "\"");
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
