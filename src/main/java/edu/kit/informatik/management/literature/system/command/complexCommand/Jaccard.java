package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class Jaccard implements Command {
    private static final Pattern JACCARD
            = Pattern.compile("jaccard ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(JACCARD.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     *
     * @param lms
     *         the complexController of the command.
     */
    public Jaccard(final ComplexController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        String list1;
        String list2;
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(JACCARD);

            sc.useDelimiter("\u0020");

            list1 = sc.next();

            list2 = sc.next();

            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"jaccard <keywords> <keywords>\"!");
            return true;
        }

        Set<String> paramList1 = new HashSet<>();
        Set<String> paramList2 = new HashSet<>();

        try {
            Scanner listScanner1 = new Scanner(list1);
            listScanner1.useDelimiter(";");


            while (listScanner1.hasNext(PatternHolder.KEYWORDPATTERN)) {
                paramList1.add(listScanner1.next(PatternHolder.KEYWORDPATTERN));
            }

            listScanner1.reset();

            Scanner listScanner2 = new Scanner(list2);
            listScanner2.useDelimiter(";");

            while (listScanner2.hasNext(PatternHolder.KEYWORDPATTERN)) {
                paramList2.add(listScanner2.next(PatternHolder.KEYWORDPATTERN));
            }

            listScanner2.reset();

            if (listScanner1.hasNext() || listScanner2.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"<keyword>;<keyword>;...\"!");
            return true;
        }


        Terminal.printLine(lms.jaccardIndex(paramList1, paramList2));
        return true;
    }
}
