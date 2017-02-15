package edu.kit.informatik.management.literature.system;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.CommandLoader;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * @author David Oberacker
 */
public class LiteratureManagementSystem {

    //=================fields==========================

    private static final Pattern QUIT = Pattern.compile("quit");

    private LiteratureManagement literatureManagement;

    private Collection<Command> commandList;

    //=================constructor======================

    public LiteratureManagementSystem() {

        commandList = CommandLoader.loadCommands();
        this.literatureManagement = new LiteratureManagement();
    }

    //=================main method======================

    public static void main(String[] args) {
        LiteratureManagementSystem lms = new LiteratureManagementSystem();

        String userInput = Terminal.readLine();
        while (!(QUIT.matcher(userInput).matches())) {
            boolean result = false;
            for (Command c:lms.commandList) {
                if (c.execute(lms.literatureManagement, userInput)) {
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
