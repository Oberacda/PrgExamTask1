package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/Output class for the add article command.
 * <p>
 *     Syntax: {@literal "add article to series/journal <title>:<id>,<year>,<article title>"}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class AddArticle implements Command {
    private static final Pattern ADDARTICLE
            = Pattern.compile("add article");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     *
     * @param lms the addController of the command.
     */
    public AddArticle(final AddController lms) {
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
           // sc.useDelimiter(":");

            Optional<String> optional = Optional.ofNullable(sc.findInLine(edu.kit.informatik.management
                    .literature.system.command.PatternHolder.TOPUBLISHERPATTERN));
            if (optional.isPresent()) {
                publisherTitle = optional.get();
                publisherTitle = publisherTitle.substring(0, publisherTitle.length() - 1);
            } else {
                throw new NoSuchElementException();
            }
            //sc.skip(":");
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
        } catch (ElementAlreadyPresentException | NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
