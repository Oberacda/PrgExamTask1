package edu.kit.informatik.management.literature.system.command;

import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.controller.*;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Class to create instances of all
 * executable command controller classes.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public final class CommandLoader {

    /**
     * Createa a instance of all command controller classes
     * and adds them to a collection.
     *
     * @param lm the literature management the controllers should work on.
     *
     * @return Collection of Instances.
     */
    public static Collection<Controller> loadCommands(final LiteratureManagement lm) {
        ArrayList<Controller> commands = new ArrayList<>();

        commands.add(new AddController(lm));
        commands.add(new GetController(lm));
        commands.add(new ComplexController(lm));
        commands.add(new LiteratureIndexController(lm));

        return commands;
    }
}
