package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * This class represents a controller for
 * a set of commands or other controllers.
 * <p>
 * This class should provide methods to work with the
 * input provided by the commands and should access the
 * model and add or provide data to or from it.
 * </p>
 * <p>
 * To improve some workflow this class can also manage
 * other controllers due to the fact they are (indirect)
 * Commands to. But a controller can only work as
 * wrapper class and not represent a actual command.
 * The execution method of this class only runs the
 * {@link Command#execute(String)} command in its
 * managed instances.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public abstract class Controller implements Command {

    //=================fields==========================

    private LiteratureManagement literatureManagement;
    private LinkedHashSet<Command> controlledCommands;

    //=================constructor======================

    /**
     * Creates a new controller.
     * <p>
     * A new controller has no commands managed by it.
     * </p>
     * <p>
     * The literature management is the instance the commands
     * managed by the controller will work on.
     * </p>
     *
     * @param literatureManagement
     *         the literature management
     *         the commands should work on.
     */
    public Controller(final LiteratureManagement literatureManagement) {
        this.controlledCommands = new LinkedHashSet<>();
        this.literatureManagement = literatureManagement;
    }

    //=================getter===========================

    /**
     * Returns the instance of the literature management this
     * controller works on.
     * <p>
     * This is the way the commands should access the model!
     * </p>
     *
     * @return the literature management the commands have to work with.
     */
    public LiteratureManagement getLiteratureManagement() {
        return this.literatureManagement;
    }


    /**
     * Returns a stream of all commands the controller manages.
     * <p>
     * Returns a stream of commands managed by the controller.
     * </p>
     *
     * @return managed command stream.
     */
    public Set<Command> getControlledCommands() {
        return controlledCommands;
    }

    //=================methods==========================

    /**
     * Adds a new command type to be managed by the controller.
     * <p>
     * This has to be a instance of a class implementing the
     * {@link Command command interaface}.
     * </p>
     *
     * @param newCommand
     *         a instance of a subclass of {@link Command}.
     */
    public void addCommand(final Command newCommand) {
        controlledCommands.add(newCommand);
    }

    //=================override methods=================

    /**
     * Runs the {@link Command#execute(String)} method on all
     * managed commands(including other controllers).
     *
     * @param userInput
     *         the input entered by the user.
     *
     * @return true - one of the managed classes successfully
     * matched and executed the command provided by the user.
     */
    public final boolean execute(final String userInput) {
        return getControlledCommands().stream()
                .anyMatch(command -> command.execute(userInput));
    }
}
