package edu.kit.informatik.management.literature.system.command;

import edu.kit.informatik.management.literature.LiteratureIndex;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.addCommand.*;
import edu.kit.informatik.management.literature.system.command.complexCommand.*;
import edu.kit.informatik.management.literature.system.command.controller.*;
import edu.kit.informatik.management.literature.system.command.getCommand.AllPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.InProceedings;
import edu.kit.informatik.management.literature.system.command.getCommand.ListInvalidPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.PublicationsBy;
import edu.kit.informatik.management.literature.system.command.literatureIndex.Bibliography;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintConference;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintJournal;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class to create instances of all
 * executable command classes.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public final class CommandLoader {

    /**
     * Createa a instance of all command classes
     * and adds them to a collection.
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
