package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.terminal.Terminal;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectHIndex extends Command {
    private static final Pattern DIRECTHINDEX
            = Pattern.compile("direct h-index ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(DIRECTHINDEX.pattern()
            + "\\S(.)+\\S");

    /**
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param lms
     *         Literature management that should be worked on.
     */
    @Override
    public boolean execute(final LiteratureManagementSystem lms,
                           final String userCommand) {
        if (!(DIRECTHINDEX.matcher(userCommand).matches())) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(DIRECTHINDEX);

        sc.useDelimiter(";");
        ArrayList<Integer> paramList1 = new ArrayList<>();

        while (sc.hasNextInt()) {
            paramList1.add(sc.nextInt());
        }

        Terminal.printLine(lms.directHIndexOf(paramList1));
        return true;
    }
}
