package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.Article;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author David Oberacker
 */
public class Similarity extends Command {
    private static final Pattern SIMILARITY
            = Pattern.compile("similarity ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(SIMILARITY.pattern()
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
    public boolean execute(final LiteratureManagement lm,
                        final String userCommand) {
        if (!(this.matchesPattern(userCommand))) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(SIMILARITY);

        sc.useDelimiter(",");

        String articleId1 = sc.next(PatternHolder.IDPATTERN);
        String articleId2 = sc.next(PatternHolder.IDPATTERN);

        Optional<Article> article1 = lm.getArticle(articleId1);
        Optional<Article> article2 = lm.getArticle(articleId2);

        try {
            if (!article1.isPresent()) {
                throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId1));
            }
            if (!article2.isPresent()) {
                throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId2));
            }
            Set<String> keySet1 = article1.get().getKeywords().collect(Collectors.toSet());
            Set<String> keySet2 = article2.get().getKeywords().collect(Collectors.toSet());

            Terminal.printLine(LiteratureManagement.calculateJaccard(keySet1, keySet2));

        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
