package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.terminal.Terminal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class WrittenBy implements Command {
    private static final Pattern WRITTENBY
            = Pattern.compile("written by ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(WRITTENBY.pattern()
            + "\\S((.)+\\S)*");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     * @param lms the addController of the command.
     */
    public WrittenBy(final AddController lms) {
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
        sc.skip(WRITTENBY);

        String articleId;
        ArrayList<String> paramList = new ArrayList<>();

        sc.useDelimiter(",");

        try {
            articleId = sc.next(PatternHolder.IDPATTERN);
            sc.skip(",");
            sc.useDelimiter(";");
            while (sc.hasNext(PatternHolder.AUTHORPATTERN)) {
                paramList.add(sc.next(PatternHolder.AUTHORPATTERN));
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("missing command token :" + nse.getMessage());
            return true;
        }
        try {
            lms.writtenBy(articleId, paramList);
            Terminal.printLine("Ok");
        } catch (NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
