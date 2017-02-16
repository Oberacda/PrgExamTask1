package edu.kit.informatik.management.literature.system.command;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;

/**
 * Generic super class for all command of the terminal based
 * literature management system.
 * <p>
 * All Commands available in the
 * {@linkplain edu.kit.informatik.management.literature.system.LiteratureManagementSystem
 * Application}
 * should be a subclass of this command. All subclasses should implement
 * {@linkplain Command#execute(LiteratureManagementSystem, String)} methods.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public abstract class Command {

    /**
     * Executes the Command on the {@code CampusManagement} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param lms
     *         Literature management system that should be worked on.
     * @param userCommand
     *         String entered by the terminal user.
     *
     * @return true - execution was succesful.
     */
    public abstract boolean execute(LiteratureManagementSystem lms, String userCommand);
}
