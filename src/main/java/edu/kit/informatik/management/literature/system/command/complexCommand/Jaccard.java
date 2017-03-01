package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.HashSet;
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
     * @param lms the complexController of the command.
     */
    public Jaccard(final ComplexController lms) {
        this.lms = lms;
    }

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(COMMANDPATTERN.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(JACCARD);

        sc.useDelimiter("\u0020");

        String list1 = sc.next();

        String list2 = sc.next();

        Scanner listScanner1 = new Scanner(list1);
        listScanner1.useDelimiter(";");
        Set<String> paramList1 = new HashSet<>();

        while (listScanner1.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList1.add(listScanner1.next(PatternHolder.KEYWORDPATTERN));
        }

        Scanner listScanner2 = new Scanner(list2);
        listScanner2.useDelimiter(";");
        Set<String> paramList2 = new HashSet<>();

        while (listScanner2.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramList2.add(listScanner2.next(PatternHolder.KEYWORDPATTERN));
        }

        Terminal.printLine(lms.jaccardIndex(paramList1, paramList2));
        return true;
    }
}
