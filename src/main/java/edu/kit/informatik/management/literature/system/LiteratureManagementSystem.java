package edu.kit.informatik.management.literature.system;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.CommandLoader;
import edu.kit.informatik.management.literature.system.command.controller.Controller;
import edu.kit.informatik.management.literature.util.CommandUtil;
import edu.kit.informatik.terminal.Terminal;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public final class LiteratureManagementSystem {

    //=================fields==========================

    private static final Pattern QUIT = Pattern.compile("quit");

    private final LiteratureManagement literatureManagement;

    private final Collection<Controller> commandControllerList;

    //=================constructor======================

    /**
     * Creates a new literature management system.
     * <p>
     * A new literature management system knows all
     * commands specified in
     * {@linkplain CommandLoader#loadCommands(LiteratureManagement)}.
     * </p>
     */
    private LiteratureManagementSystem() {
        this.literatureManagement = new LiteratureManagement();
        commandControllerList = CommandLoader.loadCommands(this.literatureManagement);
    }

    //=================main method======================

    /**
     * The main method of the {@linkplain LiteratureManagementSystem}.
     *
     * @param args
     *         the command line arguments.
     */
    public static void main(String[] args) {
        LiteratureManagementSystem lms = new LiteratureManagementSystem();

        String userInput = Terminal.readLine();
        while (!(QUIT.matcher(userInput).matches())) {
            boolean result = false;
            for (Controller c : lms.commandControllerList) {
                if (c.execute(userInput)) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                Terminal.printError("invalid syntax! no command detected!");
            }
            userInput = Terminal.readLine();
        }
        Terminal.printLine("Ok");
    }
}
