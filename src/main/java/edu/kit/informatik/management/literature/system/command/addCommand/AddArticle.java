package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing class for the add article command.
 * <p>
 *     This class parses the {@literal "add article}
 * </p>
 * @author David Oberacker
 * @version 1.0.1
 */
public class AddArticle implements Command {
    private static final Pattern ADDARTICLE
            = Pattern.compile("add article");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     * @param lms the addController of the command.
     */
    public AddArticle(final AddController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(ADDARTICLE.pattern()))) {
            return false;
        }

        String publisherTitle;
        String publicationId;
        int publicationYear;
        String publicationTitle;
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(ADDARTICLE + " ");
            sc.useDelimiter(":");

            publisherTitle = sc.next(edu.kit.informatik.management
                    .literature.system.command.PatternHolder.TOPUBLISHERPATTERN);
            sc.skip(":");
            sc.useDelimiter(",");
            publicationId = sc.next(PatternHolder.IDPATTERN);
            publicationYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            publicationTitle = sc.next(PatternHolder.ARTICLETITLEPATTERN);

        } catch (NoSuchElementException nse) {
            Terminal.printError(String.format("invalid token, expected: \"%s to pub <conference"
                            + " series/journal>:<id>,<year>,<title>\"!",
                    ADDARTICLE));
            return true;
        }
        try {
            lms.addPublication(publicationId, publicationYear, publicationTitle, publisherTitle);
            Terminal.printLine("Ok");
        } catch (IllegalArgumentException | NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
