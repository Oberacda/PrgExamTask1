package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.Article;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class ListInvalidPublications extends Command {

    private static final Pattern COMMANDPATTERN
            = Pattern.compile("list invalid publications");

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
    public boolean execute(final LiteratureManagement lm,
                        final String userCommand) {
        if (!(this.matchesPattern(userCommand))) {
            return false;
        }
        try {
            Stream<Article> articleStream = lm.getAllArticles();
            articleStream.filter(article -> !(article.isComplete())).forEach(article
                    -> Terminal.printLine(article.getId()));
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
