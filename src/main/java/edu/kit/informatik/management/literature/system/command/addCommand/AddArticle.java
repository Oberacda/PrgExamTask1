package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.Venue;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.CommandUtil;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class AddArticle extends Command {

    private static final Pattern ADDARTICLE
            = Pattern.compile("add article ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(ADDARTICLE.pattern()
            + PatternHolder.TOVENUEPATTERN
            + ":"
            + PatternHolder.IDPATTERN
            + ","
            + PatternHolder.YEARPATTERN
            + ","
            + PatternHolder.ARTICLETITLEPATTERN);

    /**
     * Checks if the input string {@code userInput} matches the
     * Pattern of the command.
     * <p>
     * Is called by the
     * {@linkplain Command#execute(LiteratureManagement, String)}
     * method to check if command is valid. No need to call
     * it directly.
     * </p>
     *
     * @param userInput
     *         Input string by the terminal application user.
     *
     * @return true - the input is a command of this Class.
     */
    @Override
    protected boolean matchesPattern(final String userInput) {
        return COMMANDPATTERN.matcher(userInput).matches();
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     * <p>
     * This method calls the method
     * {@linkplain Command#matchesPattern(String)}
     * only if it has returned true, the command
     * will be executed.
     * </p>
     *
     * @param lm
     *         Literature management that should be worked on.
     */
    @Override
    public void execute(final LiteratureManagement lm,
                        final String userCommand) {
        if (!(this.matchesPattern(userCommand))) {
            return;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(ADDARTICLE);
        sc.useDelimiter(":");

        String venueTitle = sc.next(PatternHolder.TOVENUEPATTERN);
        String articleId = sc.next(PatternHolder.TITLEPATTERN);
        int articleYear = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
        String articleTitle = sc.next(PatternHolder.ARTICLETITLEPATTERN);
        try {
            Venue venue = CommandUtil.getVenueFromPrefix(lm, venueTitle);
            if (lm.hasArticle(articleId)) {
                throw new ElementAlreadyPresentException("There already is a article with this id!");
            }
            venue.addArticle(articleId, articleYear, articleTitle);
            Terminal.printLine("OK");
        } catch (ElementAlreadyPresentException | NoSuchElementException | BadSyntaxException exc) {
            Terminal.printError(exc.getMessage());
        }
    }
}
