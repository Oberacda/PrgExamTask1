package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.CommandUtil;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class HIndex extends Command {
    private static final Pattern HINDEX
            = Pattern.compile("h-index ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(HINDEX.pattern()
            + PatternHolder.AUTHORPATTERN);

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
        sc.skip(HINDEX);

        sc.useDelimiter(" ");
        String firstName = sc.next(PatternHolder.NAMEPATTERN);
        String lastName = sc.next(PatternHolder.NAMEPATTERN);

        try {
            Optional<Author> authorOptional = lm.getAuthor(firstName, lastName);
            if (!authorOptional.isPresent()) {
                throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                        authorOptional.toString()));
            }
            Author author = authorOptional.get();

            ArrayList<Integer> citationCount = new ArrayList<>();

            lm.getAllArticles().filter(article -> article.getAuthors().anyMatch(author::equals))
                    .forEach(article -> {
                Long citCount = lm.getAllArticles().filter(article1 ->
                        article1.cites(article)).count();
                citationCount.add(citCount.intValue());
            });
            Terminal.printLine(LiteratureManagement.calculateHIndex(citationCount));
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}