package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.system.command.addCommand.*;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;
import java.util.stream.Stream;

/**
 * Controller for add commands in
 * the literature management system.
 * @author David Oberacker
 * @version 1.0.1
 */
public class AddController extends Controller {

    /**
     * Creates a new addController.
     *
     * @param literatureManagement the literature management
     *                             the commands should work on.
     */
    public AddController(final LiteratureManagement literatureManagement) {
        super(literatureManagement);

        super.addCommand(new AddArticle(this));
        super.addCommand(new AddAuthor(this));
        super.addCommand(new AddConference(this));
        super.addCommand(new AddConferenceSeries(this));
        super.addCommand(new AddJournal(this));
        super.addCommand(new AddKeywords(this));
        super.addCommand(new Cites(this));
        super.addCommand(new WrittenBy(this));
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
        Publishers publishers
                = getPublisherFromPrefix(publisherTitle);
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
        getLiteratureManagement().addAuthor(firstName, lastName);
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
        getLiteratureManagement().addConferenceToSeries(conferenceSeriesTitle,
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
        getLiteratureManagement().addConferenceSeries(conferenceSeriesTitel);
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
        getLiteratureManagement().addJournal(journalTitel, journalPublisher);
    }

    /**
     * Adds a keyword to a entity in the system.
     *
     * @param entityId
     *         prefix containg the enitiy
     *         the keyword should be added to.
     *         (See: {@link AddController#getEntityFormPrefix(String)}).
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
            throws IllegalArgumentException, NoSuchElementException {
        Entity e = getEntityFormPrefix(entityId);
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

        Optional<Publication> publicationOptional = getLiteratureManagement()
                .getPublication(publicationId);

        Optional<Publication> citedPublicationOptional = getLiteratureManagement()
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
        Optional<Publication> publicationOptional = getLiteratureManagement()
                .getPublication(publicationId);

        Publication publication;
        if (publicationOptional.isPresent()) {
            publication = publicationOptional.get();
        } else {
            throw new NoSuchElementException(String.format("publication with"
                    + " id \"%s\" not found!", publicationId));
        }
        Stream<Author> authorStream = getLiteratureManagement()
                .getAuthors(authorsList);
        authorStream.forEach(publication::addAuthor);
    }


    /**
     * Returns the the publisher specified in a command prefix.
     *
     * <table>
     * <caption>examples:</caption>
     * <tr>
     * <td>
     * {@code ... to series TA -> Conference Series with the title TA}
     * </td>
     * </tr>
     * <tr>
     * <td>
     * {@code ... to journal TA -> Journal with the title TA}
     * </td>
     * </tr>
     * </table>
     *
     * @param userInput
     *         the prefix entered by the user.
     *
     * @return if present the publisher specified by the prefix.
     *
     * @throws NoSuchElementException
     *         if there is no publisher with the
     *         specified title this exception is thrown.
     * @throws IllegalArgumentException
     *         if there is a syntax error in the prefix
     *         this exception is thrown.
     */
    public Publishers getPublisherFromPrefix(final String userInput)
            throws NoSuchElementException, IllegalArgumentException {
        if (edu.kit.informatik.management.literature.system.command.
                PatternHolder.TOSERIESPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(edu.kit.informatik.management.literature.system.command.
                    PatternHolder.TOSERIESPREFIX);
            Optional<ConferenceSeries> conferenceSeries = getLiteratureManagement().getConferenceSeries(sc.next());
            if (conferenceSeries.isPresent()) {
                return conferenceSeries.get();
            } else {
                throw new NoSuchElementException("There is no conference series with this name!");
            }
        } else if (edu.kit.informatik.management.literature.system.command.
                PatternHolder.TOJOURNALPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(edu.kit.informatik.management.literature.system.command.PatternHolder.TOJOURNALPREFIX);
            Optional<Journal> journal = getLiteratureManagement().getJournal(sc.next());
            if (journal.isPresent()) {
                return journal.get();
            } else {
                throw new NoSuchElementException("There is no journal with this name!");
            }
        } else {
            throw new IllegalArgumentException("unsupported command pattern!");
        }
    }

    /**
     * Returns the the entity specified in a command prefix.
     * <table>
     * <caption>examples:</caption>
     * <tr>
     * <td>
     * {@code ... to series TA -> Conference Series with the title TA}
     * </td>
     * </tr>
     * <tr>
     * <td>
     * {@code ... to journal TA -> Journal with the title TA}
     * </td>
     * </tr>
     * <tr>
     * <td>
     * {@code ... to conference TA,1997 -> Conference in the year 1997 }
     * </td>
     * </tr>
     * </table>
     *
     * @param userInput
     *         the prefix entered by the user.
     *
     * @return if present the entity specified by the prefix.
     *
     * @throws NoSuchElementException
     *         if there is no publisher with the
     *         specified title this exception is thrown.
     * @throws IllegalArgumentException
     *         if there is a syntax error in the prefix
     *         this exception is thrown.
     */
    private Entity getEntityFormPrefix(final String userInput)
            throws NoSuchElementException, IllegalArgumentException {
        if (edu.kit.informatik.management.literature.system.command.
                PatternHolder.TOPUBPATTERN.matcher(userInput).matches()) {

            Scanner sc = new Scanner(userInput);
            sc.skip(edu.kit.informatik.management.literature.system.command.PatternHolder.TOPUBPREFIX);

            Optional<Publication> publication = getLiteratureManagement().getPublication(sc.next());

            if (publication.isPresent()) {
                return publication.get();
            } else {
                throw new NoSuchElementException("there is no publication with this id!");
            }
        } else if (edu.kit.informatik.management.literature.system.command.
                PatternHolder.TOCONFERENCEPATTERN.matcher(userInput).matches()) {

            Scanner sc = new Scanner(userInput);
            sc.skip(edu.kit.informatik.management.literature.system.command.
                    PatternHolder.TOCONFERENCEPREFIX);
            sc.useDelimiter(",");

            String seriesName = sc.next(PatternHolder.TITLEPATTERN);
            int year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));

            Optional<Conference> conference = getLiteratureManagement().getConferenceFromSeries(seriesName, year);
            if (conference.isPresent()) {
                return conference.get();
            } else {
                throw new NoSuchElementException("There is no conference in this year!");
            }
        } else {
            return getPublisherFromPrefix(userInput);
        }
    }
}
