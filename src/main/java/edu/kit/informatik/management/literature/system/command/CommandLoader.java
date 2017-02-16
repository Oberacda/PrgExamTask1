package edu.kit.informatik.management.literature.system.command;

import edu.kit.informatik.management.literature.system.command.addCommand.*;
import edu.kit.informatik.management.literature.system.command.complexCommand.*;
import edu.kit.informatik.management.literature.system.command.getCommand.AllPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.InProceedings;
import edu.kit.informatik.management.literature.system.command.getCommand.ListInvalidPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.PublicationsBy;
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
    public static Collection<Command> loadCommands() {
        ArrayList<Command> commands = new ArrayList<>();

        commands.add(new AddArticle());
        commands.add(new AddAuthor());
        commands.add(new AddConference());
        commands.add(new AddConferenceSeries());
        commands.add(new AddJournal());
        commands.add(new AddKeywords());
        commands.add(new Cites());
        commands.add(new WrittenBy());

        commands.add(new CoauthorsOf());
        commands.add(new DirectHIndex());
        commands.add(new ForeignCitations());
        commands.add(new FindKeywords());
        commands.add(new HIndex());
        commands.add(new Jaccard());
        commands.add(new Similarity());

        commands.add(new AllPublications());
        commands.add(new InProceedings());
        commands.add(new ListInvalidPublications());
        commands.add(new PublicationsBy());

        commands.add(new DirectPrintConference());
        commands.add(new DirectPrintJournal());

        return commands;
    }
}
