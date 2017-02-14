package edu.kit.informatik.management.literature.system.command;

import edu.kit.informatik.management.literature.LiteratureManagement;

/**
 * Generic super class for all command of the terminal based
 * literature management system.
 * <p>
 * All Commands available in the
 * {@linkplain edu.kit.informatik.management.literature.system.LiteratureManagementSystem
 * Application}
 * should be a subclass of this command. All subclasses should implement
 * the {@linkplain Command#matchesPattern(String)} and
 * {@linkplain Command#execute(LiteratureManagement, String)} methods.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public abstract class Command {

    /**
     * Checks if the input string {@code userInput} matches the
     * Pattern of the command.
     * <p>
     * Is called by the
     * {@linkplain Command#execute(LiteratureManagement, String)}
     * method to check if command is valid. No need to call
     * it directly.
     * </p>
     *
     * @param userInput
     *         Input string by the terminal application user.
     *
     * @return true - the input is a command of this Class.
     */
    protected abstract boolean matchesPattern(String userInput);

    /**
     * Executes the Command on the {@code CampusManagement} with the parameters
     * given in the {@code userCommand} parameter.
     * <p>
     * This method calls the method
     * {@linkplain Command#matchesPattern(String)}
     * only if it has returned true, the command
     * will be executed.
     * </p>
     *
     * @param lm
     *         Literature management that should be worked on.
     * @param userCommand
     *         String entered by the terminal user.
     */
    public abstract void execute(LiteratureManagement lm, String userCommand);
}
