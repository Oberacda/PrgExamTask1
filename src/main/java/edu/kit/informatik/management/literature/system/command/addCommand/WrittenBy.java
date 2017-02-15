package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.Article;
import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class WrittenBy extends Command {
    private static final Pattern WRITTENBY
            = Pattern.compile("add article ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(WRITTENBY.pattern()
            + "\u0020"
            + PatternHolder.IDPATTERN
            + ","
            + PatternHolder.AUTHORLISTPATTERN);

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
        sc.skip(WRITTENBY);

        String articleId = sc.next(PatternHolder.IDPATTERN);
        ArrayList<String> paramList = new ArrayList<>();

        sc.useDelimiter(";");
        while (sc.hasNext(PatternHolder.AUTHORPATTERN)) {
            paramList.add(sc.next(PatternHolder.AUTHORPATTERN));
        }

        try {
            if (!lm.hasArticle(articleId)) {
                throw new NoSuchElementException("There is no article with this id!");
            }
            Article a = lm.getArticle(articleId).get();
            Stream<Author> as = lm.getAuthors(paramList);
            as.forEach(a::addAuthor);
            Terminal.printLine("OK");
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
