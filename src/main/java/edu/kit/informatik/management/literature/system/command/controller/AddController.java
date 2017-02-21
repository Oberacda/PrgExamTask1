package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.Publication;
import edu.kit.informatik.management.literature.Publishers;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.addCommand.*;
import edu.kit.informatik.management.literature.util.CommandUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class AddController implements Controller {

    private HashSet<Command> addCommands;

    private LiteratureManagement literatureManagement;

    public AddController(final LiteratureManagement literatureManagement) {
        this.literatureManagement = literatureManagement;
        this.addCommands = new HashSet<>();

        this.addCommands.add(new AddArticle(this));
        this.addCommands.add(new AddAuthor(this));
        this.addCommands.add(new AddConference(this));
        this.addCommands.add(new AddConferenceSeries(this));
        this.addCommands.add(new AddJournal(this));
        this.addCommands.add(new AddKeywords(this));
        this.addCommands.add(new Cites(this));
        this.addCommands.add(new WrittenBy(this));
    }

    @Override
    public boolean execute(final String userCommand) {
        return addCommands.stream().anyMatch(command -> command.execute(userCommand));
    }

    /**
     * Adds a publication to a specified publisher.
     *
     * @param publicationId
     *         a string representating the publication id.
     * @param publicationYear
     *         a string representating the publication year.
     * @param publicationTitle
     *         a string representating the publication title.
     * @param publisherTitle
     *         a prefix representating the publisher.
     *
     * @throws NoSuchElementException
     *         if there is no known prefix for this publisher this exception is thrown.
     * @throws IllegalArgumentException
     *         if the publication is already present
     *         or there is a syntax error in the publisher prefix.
     */
    public void addPublication(final String publicationId,
                               final int publicationYear,
                               final String publicationTitle,
                               final String publisherTitle)
            throws NoSuchElementException,
            IllegalArgumentException {
        Publishers publishers = CommandUtil
                .getPublisherFromPrefix(this.literatureManagement, publisherTitle);
        publishers.addArticle(publicationId, publicationYear, publicationTitle);
    }

    /**
     * Adds a author to the system.
     *
     * @param firstName
     *         first name of the author.
     * @param lastName
     *         last name of the author.
     *
     * @throws ElementAlreadyPresentException
     *         if there already is a author with this name
     *         this exception is thrown.
     */
    public void addAuthor(final String firstName,
                          final String lastName)
            throws ElementAlreadyPresentException {
        this.literatureManagement.addAuthor(firstName, lastName);
    }

    /**
     * Adds a conference to a series in the system.
     *
     * @param conferenceSeriesTitle
     *         the title of the conference series.
     * @param conferenceYear
     *         the year the conference took place.
     * @param conferenceLocation
     *         the location of the conference.
     *
     * @throws ElementAlreadyPresentException
     *         If there already is  a conference in this year in the series this exception is thrown,
     * @throws NoSuchElementException
     *         if the conference series doesn't exist, this exception is thrown.
     */
    public void addConference(final String conferenceSeriesTitle,
                              final int conferenceYear,
                              final String conferenceLocation)
            throws ElementAlreadyPresentException,
            NoSuchElementException {
        this.literatureManagement.addConferenceToSeries(conferenceSeriesTitle,
                conferenceLocation, conferenceYear);
    }

    /**
     * Adds a conference series to the system.
     *
     * @param conferenceSeriesTitel
     *         the title of the conference series.
     *
     * @throws ElementAlreadyPresentException
     *         if there already is a conference series
     *         with this title this exception is thrown.
     */
    public void addConferenceSeries(final String conferenceSeriesTitel)
            throws ElementAlreadyPresentException {
        this.literatureManagement.addConferenceSeries(conferenceSeriesTitel);
    }

    /**
     * Adds a journal to the system.
     *
     * @param journalTitel
     *         the title of the journal.
     * @param journalPublisher
     *         the publisher of the journal.
     *
     * @throws ElementAlreadyPresentException
     *         if there already is a
     *         journal with this name this exception is thrown.
     */
    public void addJournal(final String journalTitel,
                           final String journalPublisher)
            throws ElementAlreadyPresentException {
        this.literatureManagement.addJournal(journalTitel, journalPublisher);
    }

    /**
     * Adds a keyword to a entity in the system.
     *
     * @param entityId
     *         prefix containg the enitiy
     *         the keyword should be added to.
     *         (See: {@link CommandUtil#getEntityFormPrefix(LiteratureManagement, String)}).
     * @param keywords
     *         a set of keywords that should be added.
     *
     * @throws IllegalArgumentException
     *         if the prefix doesn't match any known prefix this exception is thrown.
     * @throws NoSuchElementException
     *         if there is no enitity with the id given in the prefix.
     */
    public void addKeywords(final String entityId,
                            final Set<String> keywords)
            throws BadSyntaxException, NoSuchElementException {
        Entity e = CommandUtil.getEntityFormPrefix(this.literatureManagement, entityId);
        for (String keyword : keywords) {
            e.addKeyword(keyword);
        }
    }

    /**
     * Sets that the first publication cites the second given publication.
     *
     * @param publicationId
     *         the id of the publication that cites another.
     * @param citedPublicationId
     *         the id of the publication that is cited by the first one.
     *
     * @throws IllegalArgumentException
     *         if the release of the cited article wasn't before the release
     *         of the article that cites, this exception is thrown.
     * @throws NoSuchElementException
     *         if one of the articles don't exist in the system this exception
     *         is thrown.
     */
    public void cites(final String publicationId,
                      final String citedPublicationId)
            throws IllegalArgumentException, NoSuchElementException {

        Optional<Publication> publicationOptional = this.literatureManagement
                .getPublication(publicationId);

        Optional<Publication> citedPublicationOptional = this.literatureManagement
                .getPublication(citedPublicationId);

        Publication publication;
        if (publicationOptional.isPresent()) {
            publication = publicationOptional.get();
        } else {
            throw new NoSuchElementException(String.format("publication with id \"%s\""
                    + " not found!", publicationId));
        }

        Publication citedPublication;

        if (citedPublicationOptional.isPresent()) {
            citedPublication = citedPublicationOptional.get();
        } else {
            throw new NoSuchElementException(String.format("publication with id \"%s\""
                    + " not found!", citedPublicationId));
        }
        publication.addCitation(citedPublication);
    }

    /**
     * Sets that a publication has been written by a list of one or more authors.
     *
     * @param publicationId the id of the publication.
     * @param authorsList a list of one or more authors.
     * @throws NoSuchElementException if the publication with this
     * id doesn't exist or one of the authors does't exist this error is thrown.
     */
    public void writtenBy(final String publicationId,
                          final List<String> authorsList)
            throws NoSuchElementException {
        Optional<Publication> publicationOptional = this.literatureManagement
                .getPublication(publicationId);

        Publication publication;
        if (publicationOptional.isPresent()) {
            publication = publicationOptional.get();
        } else {
            throw new NoSuchElementException(String.format("publication with"
                    + " id \"%s\" not found!", publicationId));
        }
        Stream<Author> authorStream = this.literatureManagement
                .getAuthors(authorsList);
        authorStream.forEach(publication::addAuthor);
    }
}
