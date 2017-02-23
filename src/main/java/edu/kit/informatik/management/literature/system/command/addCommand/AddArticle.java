package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddArticle implements Command {
    private static final Pattern ADDARTICLE
            = Pattern.compile("add article ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDARTICLE.pattern()
            + "\\S((.)+\\S)*");

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
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDARTICLE);
        sc.useDelimiter(":");

        String publisherTitle;
        String publicationId;
        int publicationYear;
        String publicationTitle;
        try {
            publisherTitle = sc.next(PatternHolder.TOPUBLISHERPATTERN);
            sc.skip(":");
            sc.useDelimiter(",");
            publicationId = sc.next(PatternHolder.TITLEPATTERN);
            publicationYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            publicationTitle = sc.next(PatternHolder.ARTICLETITLEPATTERN);

        } catch (NoSuchElementException nse) {
            Terminal.printError(String.format("invalid command token : %s%s:%s,%s,%s",
                    ADDARTICLE, PatternHolder.TOPUBPATTERN, PatternHolder.IDPATTERN,
                    PatternHolder.YEARPATTERN, PatternHolder.ARTICLETITLEPATTERN));
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
