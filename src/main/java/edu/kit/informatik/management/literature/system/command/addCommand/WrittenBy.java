package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/Output class for the written-by command.
 * <p>
 *     Syntax: {@literal "written-by <author1>;<author2*>;..."}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class WrittenBy implements Command {
    private static final Pattern WRITTENBY
            = Pattern.compile("written-by");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     *
     * @param lms the addController of the command.
     */
    public WrittenBy(final AddController lms) {
        this.lms = lms;
    }


    /**
     * {@inheritDoc}
     *
     * Executes the Command on the {@code LiteratureManagement} with the parameters
     * given in the {@code userCommand} parameter.
     */
    @Override
    public boolean execute(final String userCommand) {
        if (!(userCommand.startsWith(WRITTENBY.pattern()))) {
            return false;
        }
        Scanner sc = new Scanner(userCommand);
        sc.skip(WRITTENBY + " ");

        String articleId;
        ArrayList<String> paramList = new ArrayList<>();

        sc.useDelimiter(",");

        try {
            articleId = sc.next(PatternHolder.IDPATTERN);
            sc.skip(",");
            sc.useDelimiter(";");
            while (sc.hasNext(edu.kit.informatik.management.literature.
                    system.command.PatternHolder.AUTHORPATTERN)) {
                paramList.add(sc.next(edu.kit.informatik.management.
                        literature.system.command.PatternHolder.AUTHORPATTERN));
            }
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"written-by <author1>;<author2*>;...\"!");
            return true;
        }
        try {
            lms.writtenBy(articleId, paramList);
            Terminal.printLine("Ok");
        } catch (NoSuchElementException | ElementAlreadyPresentException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
