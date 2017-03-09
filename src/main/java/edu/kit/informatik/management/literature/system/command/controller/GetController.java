package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.system.command.getCommand.AllPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.InProceedings;
import edu.kit.informatik.management.literature.system.command.getCommand.ListInvalidPublications;
import edu.kit.informatik.management.literature.system.command.getCommand.PublicationsBy;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for all get commands of the Literature Management system.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class GetController extends Controller {

    /**
     * Creates a new GetController.
     *
     * @param literatureManagement
     *         the literature management
     *         the commands should work on.
     */
    public GetController(final LiteratureManagement literatureManagement) {
        super(literatureManagement);

        super.addCommand(new AllPublications(this));
        super.addCommand(new InProceedings(this));
        super.addCommand(new ListInvalidPublications(this));
        super.addCommand(new PublicationsBy(this));
    }

    /**
     * Returns the id of all publications in the literature management.
     *
     * @return stream of publication ids.
     */
    public Stream<String> allPublications() {
        return getLiteratureManagement()
                .getAllPublications()
                .flatMap(article -> Stream.of(article.getId()));
    }

    /**
     * Returns all publications, published by a conference
     * in a series in a specified year.
     *
     * @param conferenceSeries
     *         the title of the conference series.
     * @param year
     *         the year of the conference.
     * @return a stream of publication ids.
     * @throws java.util.NoSuchElementException
     *         If either the conference series or the
     *         conference itself dosn't exist this exception is thrown.
     */
    public Stream<String> inProceedings(final String conferenceSeries,
                                        final int year)
            throws NoSuchElementException {
        Optional<ConferenceSeries> seriesOptional = getLiteratureManagement().getConferenceSeries(conferenceSeries);
        if (seriesOptional.isPresent()) {
            Optional<Conference> conference = seriesOptional.get().getConference(year);
            if (conference.isPresent()) {
                return conference.get()
                        .getPublications()
                        .flatMap(article -> Stream.of(article.getId()));
            } else {
                throw new NoSuchElementException(String.format("no conference"
                        + " in the series \"%s\" in the year \"%4d\" found!", conferenceSeries, year));
            }
        } else {
            throw new NoSuchElementException(String.format("conference"
                    + " \"%s\" wasn`t found!", conferenceSeries));
        }
    }

    /**
     * Returns a stream of all publication id`s from invalid publications in
     * the literature management.
     *
     * @return all ids of incomplete publications.
     */
    public Stream<String> listInvalidPublications() {
        return getLiteratureManagement()
                .getAllPublications()
                .filter(article -> !(article.isComplete()))
                .flatMap(article -> Stream.of(article.getId()));
    }

    /**
     * Returns all publications one of the specified author participated.
     *
     * @param authorSet
     *         a list of authors.
     * @return all publications one of the authors was participating.
     * @throws java.util.NoSuchElementException
     *         If one of the authors doesn't
     *         exist this exception is thrown.
     */
    public Stream<String> publicationsBy(final Set<String> authorSet)
            throws NoSuchElementException {
        Stream<Publication> publicationStream = getLiteratureManagement().getAllPublications();
        Stream<Author> authorStream = getLiteratureManagement().getAuthors(authorSet);
        Set<Author> authorsSet = authorStream.collect(Collectors.toSet());

        return publicationStream.filter(publication ->
                publication.getAuthors().anyMatch(authorsSet::contains))
                .flatMap(article -> Stream.of(article.getId()));
    }

}
