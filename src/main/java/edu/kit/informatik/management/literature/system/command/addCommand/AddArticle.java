package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddArticle extends Command {

    private static final Pattern ADDARTICLE
            = Pattern.compile("add article ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(ADDARTICLE.pattern()
            + "\\S((.)+\\S)*");

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param lms
     *         Literature management system that should be worked on.
     */
    @Override
    public boolean execute(final LiteratureManagementSystem lms,
                           final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDARTICLE);
        sc.useDelimiter(":");

        try {
            String publisherTitle = sc.next(PatternHolder.TOPUBLISHERPATTERN);
            sc.skip(":");
            sc.useDelimiter(",");
            String articleId = sc.next(PatternHolder.TITLEPATTERN);
            int articleYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            String articleTitle = sc.next(PatternHolder.ARTICLETITLEPATTERN);
            lms.addArticle(articleId, articleYear, articleTitle, publisherTitle);
            Terminal.printLine("Ok");
        } catch (ElementAlreadyPresentException
                | IllegalArgumentException
                | BadSyntaxException exc) {
            Terminal.printError(exc.getMessage());
        } catch (NoSuchElementException nse) {
            Terminal.printError(String.format("invalid command token : \"%s%s:%s,%s,%s",
                    ADDARTICLE, PatternHolder.TOPUBPATTERN, PatternHolder.IDPATTERN,
                    PatternHolder.YEARPATTERN, PatternHolder.ARTICLETITLEPATTERN));
        }
        return true;
    }
}
