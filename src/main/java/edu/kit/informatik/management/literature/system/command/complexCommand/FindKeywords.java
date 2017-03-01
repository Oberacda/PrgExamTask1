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
public class FindKeywords implements Command {
    private static final Pattern FINDKEYWORDS
            = Pattern.compile("find keywords ");

    private static final Pattern COMMANDPATTERN = Pattern.compile(FINDKEYWORDS.pattern()
            + "\\S(.)+\\S");

    private ComplexController lms;

    /**
     * Default constructor for complexController commands.
     * @param lms the complexController of the command.
     */
    public FindKeywords(final ComplexController lms) {
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
        sc.skip(FINDKEYWORDS);

        sc.useDelimiter(";");
        Set<String> paramSet = new HashSet<>();

        while (sc.hasNext(PatternHolder.KEYWORDPATTERN)) {
            paramSet.add(sc.next(PatternHolder.KEYWORDPATTERN));
        }

        lms.findKeywords(paramSet).forEach(Terminal::printLine);
        return true;
    }
}
