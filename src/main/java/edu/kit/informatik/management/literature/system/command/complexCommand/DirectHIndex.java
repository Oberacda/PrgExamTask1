package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.terminal.Terminal;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class DirectHIndex implements Command {
    private static final Pattern DIRECTHINDEX
            = Pattern.compile("direct h-index ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(DIRECTHINDEX.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     * @param lms the complexController of the command.
     */
    public DirectHIndex(final ComplexController lms) {
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
