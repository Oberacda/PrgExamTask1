package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.getCommand.AllPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.InProceedings;
import edu.kit.informatik.management.literature.system.command.getCommand.ListInvalidPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.PublicationsBy;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class GetController implements Controller {

    private HashSet<Command> getCommands;

    private LiteratureManagement literatureManagement;

    public GetController(final LiteratureManagement literatureManagement) {
        this.literatureManagement = literatureManagement;
        this.getCommands = new HashSet<>();

        getCommands.add(new AllPublications(this));
        getCommands.add(new InProceedings(this));
        getCommands.add(new ListInvalidPublications(this));
        getCommands.add(new PublicationsBy(this));
    }

    @Override
    public boolean execute(final String userCommand) {
        return getCommands.stream().anyMatch(command -> command.execute(userCommand));
    }

    public Stream<String> allPublications() {
        return this.literatureManagement
                .getAllPublications()
                .flatMap(article -> Stream.of(article.getId()));
    }

    public Stream<String> inProceedings(final String conferenceSeries,
                                        final int year)
            throws NoSuchElementException {
        Optional<ConferenceSeries> c = this.literatureManagement.getConferenceSeries(conferenceSeries);
        if (c.isPresent()) {
            Optional<Conference> conference = c.get().getConference(year);
            if (conference.isPresent()) {
                return conference.get()
                        .getPublications()
                        .flatMap(article -> Stream.of(article.getId()));
            } else {
                throw new NoSuchElementException(String.format("conference"
                        + " in hte year \"%4d\" wasn't found!", year));
            }
        } else {
            throw new NoSuchElementException(String.format("conference with"
                    + " the title \"%s\" wasn`t found!", conferenceSeries));
        }
    }

    public Stream<String> listInvalidPublications() {
        return this.literatureManagement
                .getAllPublications()
                .filter(article -> !(article.isComplete()))
                .flatMap(article -> Stream.of(article.getId()));
    }

    public Stream<String> publicationsBy(final Set<String> authorSet)
            throws NoSuchElementException {
        Stream<Publication> publicationStream = this.literatureManagement.getAllPublications();
        Stream<Author> authorStream = this.literatureManagement.getAuthors(authorSet);
        Set<Author> authorsSet = authorStream.collect(Collectors.toSet());

        return publicationStream.filter(publication ->
                publication.getAuthors().anyMatch(authorsSet::contains))
                .flatMap(article -> Stream.of(article.getId()));
    }

}
