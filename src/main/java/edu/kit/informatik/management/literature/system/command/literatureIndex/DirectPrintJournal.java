package edu.kit.informatik.management.literature.system.command.literatureIndex;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.util.LiteratureIndexStyles;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectPrintJournal extends Command {
    private static final Pattern DIRECTPRINTJOURNAL
            = Pattern.compile("direct print conference ");


    private static final Pattern COMMANDPATTERN
            = Pattern.compile(DIRECTPRINTJOURNAL.pattern()
            + Pattern.compile("[a-z]+")
            + ":"
            + PatternHolder.AUTHORPATTERN.pattern()
            + ",["
            + PatternHolder.AUTHORPATTERN.pattern()
            + "]*,["
            + PatternHolder.AUTHORPATTERN.pattern()
            + "]*,"
            + PatternHolder.ARTICLETITLEPATTERN.pattern()
            + ","
            + PatternHolder.TITLEPATTERN.pattern()
            + ","
            + PatternHolder.YEARPATTERN.pattern());


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
        sc.skip(DIRECTPRINTJOURNAL);

        sc.useDelimiter(":");

        String style = sc.next("[a-z]+");

        sc.skip(":");
        sc.useDelimiter(",");
        ArrayList<String> authorList = new ArrayList<>();
        while (sc.hasNext(PatternHolder.AUTHORPATTERN)) {
            authorList.add(sc.next(PatternHolder.AUTHORPATTERN));
        }
        int cnt = authorList.size();

        while (cnt < 3) {
            sc.skip(",");
            cnt++;
        }

        String articleTitel = sc.next(PatternHolder.ARTICLETITLEPATTERN);
        String journalTitle = sc.next(PatternHolder.TITLEPATTERN);
        int year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

        Article a = new Article("1", articleTitel, year, new TreeSet<String>());
        Journal j = new Journal(journalTitle, "empty");
        for (String s:authorList) {
            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(" ");
            a.addAuthor(new Author(scanner.next(), scanner.next()));
        }
        try {
            Terminal.printLine(LiteratureIndexStyles.printInStyle(LiteratureIndexStyles
                    .getStyle(style), a, (Publishers) j));
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}