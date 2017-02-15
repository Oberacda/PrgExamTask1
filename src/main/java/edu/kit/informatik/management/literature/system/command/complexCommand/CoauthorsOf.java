package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class CoauthorsOf extends Command {
    private static final Pattern COAUTHORSOF
            = Pattern.compile("coauthors of ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(COAUTHORSOF.pattern()
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
        sc.skip(COAUTHORSOF);

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

            TreeSet<Author> coAuthors = new TreeSet<>();

            lm.getAllArticles().forEach(article -> article.getAuthors()
                    .filter(author::equals).forEach(coAuthors::add));
            coAuthors.remove(author);
            coAuthors.forEach(author1 -> Terminal.printLine(author1.toString()));
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
