package edu.kit.informatik.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.controller.AddController;
import edu.kit.informatik.management.literature.util.PatternHolder;
import edu.kit.informatik.Terminal;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parsing/outpit class for the add keywords commmand.
 * <p>
 *     Syntax: {@literal "add keywords to journal/series <title> | to conference <title>:<year> | to pub <id>"}!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class AddKeywords implements Command {
    private static final Pattern ADDKEYWORD
            = Pattern.compile("add keywords");

    private AddController lms;

    /**
     * Default constructor for addController commands.
     *
     * @param lms the addController of the command.
     */
    public AddKeywords(final AddController lms) {
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
        if (!(userCommand.startsWith(ADDKEYWORD.pattern()))) {
            return false;
        }
        String entityId;
        HashSet<String> paramSet = new HashSet<>();
        try {
            Scanner sc = new Scanner(userCommand);
            sc.skip(ADDKEYWORD + " ");
            //sc.useDelimiter(":");
            Optional<String> optional = Optional.ofNullable(sc.findInLine(edu.kit.informatik.management
                    .literature.system.command.PatternHolder.TOENTITY));
            if (optional.isPresent()) {
                entityId = optional.get();
                entityId = entityId.substring(0, entityId.length() - 1);
            } else {
                throw new NoSuchElementException();
            }
            //sc.skip(":");
            sc.useDelimiter(";");
            if (!sc.hasNext(edu.kit.informatik.management.literature.system.command.PatternHolder.KEYWORDSPATTERN)) {
                throw new NoSuchElementException("there are no keywords given!");
            }
            while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
                paramSet.add(sc.next(PatternHolder.KEYWORDPATTERN));
            }
            sc.reset();
            if (sc.hasNext()) {
                throw new NoSuchElementException("invalid keywords!");
            }
        } catch (NoSuchElementException nse) {
            Terminal.printError("invalid syntax, expected: \"add keywords to journal/series <title>"
                    + " | to conference <title>:<year> | to pub <id>\"!");
            return true;
        }
        try {
            lms.addKeywords(entityId, paramSet);
            Terminal.printLine("Ok");
        } catch (IllegalArgumentException | NoSuchElementException exc) {
            Terminal.printError(exc.getMessage());
        }
        return true;
    }
}
