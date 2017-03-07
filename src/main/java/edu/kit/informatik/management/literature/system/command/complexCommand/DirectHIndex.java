package edu.kit.informatik.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.ComplexController;
import edu.kit.informatik.Terminal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Output/Parsing class for the direct h index command.
 * <p>
 *     Syntax: {@literal "direct h-index <list of integers>"}!
 * </p>
 * @author David Oberacker
 * @version 1.0.1
 */
public class DirectHIndex implements Command {
    private static final Pattern DIRECTHINDEX
            = Pattern.compile("direct h-index");

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
        if (!(userCommand.startsWith(DIRECTHINDEX.pattern()))) {
            return false;
        }
        ArrayList<Integer> paramList1 = new ArrayList<>();
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(DIRECTHINDEX + " ");

            sc.useDelimiter(";");
            while (sc.hasNextInt()) {
                paramList1.add(sc.nextInt());
            }
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"direct h-index <listOfIntgers>\"!");
            return true;
        }

        Terminal.printLine(lms.directHIndexOf(paramList1));
        return true;
    }
}
