package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.Article;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class Cites extends Command {
    private static final Pattern CITES
            = Pattern.compile("cites ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(CITES.pattern()
            + PatternHolder.IDPATTERN
            + ","
            + PatternHolder.IDPATTERN);

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
        sc.skip(CITES);

        sc.useDelimiter(",");
        String articleId = sc.next(PatternHolder.IDPATTERN);
        String citedArticleId = sc.next(PatternHolder.IDPATTERN);

        try {
            if (!lm.hasArticle(articleId) || !lm.hasArticle(citedArticleId)) {
                throw new NoSuchElementException("There is no article with this id!");
            }
            Article a = lm.getArticle(articleId).get();
            Article b = lm.getArticle(citedArticleId).get();
            a.addCitation(b);
            Terminal.printLine("OK");
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
    }
}
