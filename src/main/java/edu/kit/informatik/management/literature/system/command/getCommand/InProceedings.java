package edu.kit.informatik.management.literature.system.command.getCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class InProceedings extends Command {
    private static final Pattern INPROCEEDINGS
            = Pattern.compile("in proceedings ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(INPROCEEDINGS.pattern()
            + PatternHolder.TITLEPATTERN
            + ","
            + PatternHolder.YEARPATTERN);

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
        sc.skip(INPROCEEDINGS);

        sc.useDelimiter(",");

        String seriesTitle = sc.next(PatternHolder.TITLEPATTERN);

        int year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

        try {
            Optional<ConferenceSeries> c = lm.getConferenceSeries(seriesTitle);
            if (c.isPresent()) {
                Optional<Conference> conference = c.get().getConference(year);
                if (conference.isPresent()) {
                    conference.get().getArticles().forEach(article ->
                            Terminal.printLine(article.getId()));
                } else {
                    throw new NoSuchElementException(String.format("There is no"
                            + " conference in this series in %4d!", year));
                }
            } else {
                throw new NoSuchElementException(String.format("conference with"
                        + " the title \"%s\" wasn`t found!", seriesTitle));
            }
        } catch (NoSuchElementException | IllegalArgumentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
