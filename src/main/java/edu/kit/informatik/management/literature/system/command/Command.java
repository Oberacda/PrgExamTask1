package edu.kit.informatik.management.literature.system.command;

import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;

/**
 * Interface for all command of the terminal based
 * literature management system.
 * <p>
 * All Commands available in the
 * {@linkplain edu.kit.informatik.management.literature.system.LiteratureManagementSystem
 * Application}
 * should be a subclass of this command. All subclasses should implement
 * {@linkplain Command#execute(String)} methods.
 * </p>
 * <p>
 *     Classes that implement the interface are only used as parsing
 *     classes. The data logic should be implemented in the
 *     {@link edu.kit.informatik.management.literature.system.command.controller.Controller}.
 * </p>
 * <p>
 *     A exception is the {@link edu.kit.informatik.management.literature.system.command.controller.Controller}
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
@FunctionalInterface
public interface Command {

    /**
     * Executes the Command on the {@code Literature Management} with the parameters
     * given in the {@code userCommand} parameter.
     *
     * @param userCommand
     *         String entered by the terminal user.
     *
     * @return true - execution was succesful.
     */
     boolean execute(String userCommand);
}
