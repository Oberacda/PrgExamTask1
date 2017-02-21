package edu.kit.informatik.management.literature.system.command;

/**
 * Generic super class for all command of the terminal based
 * literature management system.
 * <p>
 * All Commands available in the
 * {@linkplain edu.kit.informatik.management.literature.system.LiteratureManagementSystem
 * Application}
 * should be a subclass of this command. All subclasses should implement
 * {@linkplain Command#execute(String)} methods.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
@FunctionalInterface
public interface Command {

    /**
     * Executes the Command on the {@code CampusManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param userCommand
     *         String entered by the terminal user.
     *
     * @return true - execution was succesful.
     */
     boolean execute(String userCommand);
}
