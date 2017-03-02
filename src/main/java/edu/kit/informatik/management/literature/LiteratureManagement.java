package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manages a collection of publications (articles).
 * <p>
 * This class allows to organize a set of articles
 * published by different publishers.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.5
 */
public class LiteratureManagement {

    private static final int JACCARD_FLOATINGPOINTS = 3;

    //=================fields==========================

    private final ArrayList<ConferenceSeries> conferenceSeriesList;

    private final ArrayList<Journal> journalsList;

    private final ArrayList<Author> authorsList;

    //=================constructor======================

    /**
     * Creates a new LiteratureManagement.
     * <p>
     * A new LiteratureManagement has empty fields, this means
     * every get operation will return empty streams/optionals.
     * </p>
     */
    public LiteratureManagement() {
        this.authorsList = new ArrayList<>();
        this.journalsList = new ArrayList<>();
        this.conferenceSeriesList = new ArrayList<>();
    }

    //=================add methods======================

    /**
     * Adds a new Journal to the LiteratureManagement.
     *
     * @param name
     *         the title of the journal
     *         (See {@link PatternHolder#TITLEPATTERN}).
     * @param publisher
     *         the publisher of the journal
     *         (See {@code [a-zA-Z]+}).
     *
     * @throws ElementAlreadyPresentException
     *         This exception is
     *         thrown if there already is a journal with this name.
     */
    public void addJournal(final String name, final String publisher)
            throws ElementAlreadyPresentException {
        Journal newJournal = new Journal(name, publisher);
        if (!(this.journalsList.contains(newJournal))) {
            this.journalsList.add(newJournal);
        } else {
            throw new ElementAlreadyPresentException("This journal is already present!");
        }
    }

    /**
     * Adds a conference to a conference series.
     *
     * @param conferenceSeriesTitle
     *         the title of the series
     *         conference should be added to (See {@link PatternHolder#TITLEPATTERN}).
     * @param conferenceLocation
     *         the location of the conference
     *         (See {@link PatternHolder#LOCATIONPATTERN}).
     * @param conferenceYear
     *         the year the conference took place
     *         (See {@link PatternHolder#YEARPATTERN}):
     *
     * @throws NoSuchElementException
     *         if the conference series doesn't exist
     *         this exception is thrown.
     * @throws ElementAlreadyPresentException
     *         if the already is a conference
     *         in this year in this series this exception is thrown.
     */
    public void addConferenceToSeries(final String conferenceSeriesTitle,
                                      final String conferenceLocation,
                                      final int conferenceYear)
            throws NoSuchElementException, ElementAlreadyPresentException {
        Optional<ConferenceSeries> conferenceSeriesOptional
                = this.getConferenceSeries(conferenceSeriesTitle);

        if (!(conferenceSeriesOptional.isPresent())) {
            throw new NoSuchElementException("There is no conference series with this title!");
        }

        ConferenceSeries conferenceSeries = conferenceSeriesOptional.get();
        if (!(conferenceSeries.getConference(conferenceYear).isPresent())) {
            conferenceSeries.addConference(conferenceYear, conferenceLocation);
        } else {
            throw new ElementAlreadyPresentException(String.format("There already is a conference"
                    + " in the year %4d in this series!", conferenceYear));
        }
    }

    /**
     * Adds a conference series to the LiteratureManagement.
     *
     * @param title
     *         the title of the series (See {@link PatternHolder#TITLEPATTERN}).
     *
     * @throws ElementAlreadyPresentException
     *         if the already is a conference series
     *         with this title this exception is thrown.
     */
    public void addConferenceSeries(final String title)
            throws ElementAlreadyPresentException {
        ConferenceSeries newConferenceSeries = new ConferenceSeries(title);
        if (!(this.conferenceSeriesList
                .contains(newConferenceSeries))) {
            this.conferenceSeriesList.add(newConferenceSeries);
        } else {
            throw new ElementAlreadyPresentException(String.format("series \"%s\" is"
                    + " already present!", newConferenceSeries.getTitle()));
        }
    }

    /**
     * Adds a author to the LiteratureManagement.
     *
     * @param firstName
     *         the first name of the author (See {@link PatternHolder#NAMEPATTERN}).
     * @param lastName
     *         the last name of the author (See {@link PatternHolder#NAMEPATTERN}).
     *
     * @throws ElementAlreadyPresentException
     *         if the already is a author
     *         with this name this exception is thrown.
     */
    public void addAuthor(final String firstName, final String lastName)
            throws ElementAlreadyPresentException {
        Author newAuthor = new Author(firstName, lastName);
        if (this.authorsList.stream().anyMatch(author
                -> (newAuthor.equals(author)))) {
            throw new ElementAlreadyPresentException(String.format("author \"%s\""
                    + " is already present!", newAuthor.toString()));
        } else {
            this.authorsList.add(newAuthor);
        }
    }

    //=================get methods======================

    /**
     * Returns a Optional for the conferenceSeries with this title.
     * <p>
     * If the optional is empty there is no conference with this title.
     * </p>
     *
     * @param title
     *         the title of the series (See {@link PatternHolder#TITLEPATTERN}).
     *
     * @return if present {@link ConferenceSeries}.
     */
    public Optional<ConferenceSeries> getConferenceSeries(final String title) {
        return this.conferenceSeriesList.stream().filter(conferenceSeries ->
                title.equals(conferenceSeries.getTitle())).findFirst();
    }

    /**
     * Returns a Optional for the journal with this title.
     * <p>
     * If the optional is empty there is no journal with this title.
     * </p>
     *
     * @param title
     *         the title of the journal (See {@link PatternHolder#TITLEPATTERN}).
     *
     * @return if present {@link Journal}.
     */
    public Optional<Journal> getJournal(final String title) {
        return this.journalsList.stream().filter(journal ->
                title.equals(journal.getTitle())).findFirst();
    }

    /**
     * Returns a optional conference from a series in this year.
     *
     * @param seriesTitle
     *         the title of the series (See {@link PatternHolder#TITLEPATTERN}).
     * @param conferenceYear
     *         the year of the conference (See {@link PatternHolder#YEARPATTERN}).
     *
     * @return if present {@link Conference}.
     */
    public Optional<Conference> getConferenceFromSeries(final String seriesTitle,
                                                        final int conferenceYear) {
        Optional<ConferenceSeries> seriesOptional = this.getConferenceSeries(seriesTitle);
        if (seriesOptional.isPresent()) {
            return seriesOptional.get().getConference(conferenceYear);
        } else {
            return Optional.empty();
        }

    }

    /**
     * Returns a stream of all published articles.
     * <p>
     * For this method there is no difference between
     * articles published by conferences or journals.
     * </p>
     *
     * @return stream of articles.
     */
    public Stream<Publication> getAllPublications() {
        HashSet<Publication> publicationHashSet = new HashSet<>();
        HashSet<Publishers> publishers = new HashSet<>();
        publishers.addAll(this.conferenceSeriesList);
        publishers.addAll(this.journalsList);
        for (Publishers p : publishers) {
            publicationHashSet.addAll(p.getPublications()
                    .collect(Collectors.toSet()));
        }
        return publicationHashSet.stream();
    }

    /**
     * Returns a Optional for a article with this unique id.
     *
     * @param id
     *         unique id of the article (See {@link PatternHolder#IDPATTERN}).
     *
     * @return if present {@link Article}.
     */
    public Optional<Publication> getPublication(final String id) {
        return this.getAllPublications().filter(publication ->
                id.equals(publication.getId())).findFirst();
    }

    /**
     * Returns the publisher of a publication.
     *
     * @param publication
     *         instanceof {@link Publication}.
     *
     * @return publisher of the Publication.
     *
     * @throws IllegalArgumentException
     *         if the publication isnt found in
     *         the system this exception is thrown
     */
    public Publishers getPublisher(final Publication publication)
            throws IllegalArgumentException {
        Stream<Publishers> publisherStream = Stream.concat(this.journalsList.stream()
                , this.conferenceSeriesList.stream());
        Optional<Publishers> publishers = publisherStream.filter(publisher ->
                publisher.getPublication(publication.getId()).isPresent()).findFirst();

        if (publishers.isPresent()) {
            return publishers.get();
        } else {
            throw new IllegalArgumentException(String.format("article  \"%s\" not found!",
                    publication.getId()));
        }
    }

    /**
     * Returns a optional containing a author if he is known to the system.
     *
     * @param firstName
     *         the first name of the author (See {@link PatternHolder#NAMEPATTERN}).
     * @param lastName
     *         the last name of the author (See {@link PatternHolder#NAMEPATTERN}).
     *
     * @return if present {@link Author}.
     */
    public Optional<Author> getAuthor(final String firstName, final String lastName) {
        Author newAuthor = new Author(firstName, lastName);
        return this.authorsList.stream().filter(author ->
                author.equals(newAuthor)).findAny();
    }

    /**
     * Returns a stream of the instance of author
     * if he is known by the system.
     * <p>
     * This method works similar to {@link LiteratureManagement#getAuthor(String, String)}.
     * </p>
     *
     * @param authorNames
     *         a collection of author
     *         names (See
     *         {@link edu.kit.informatik.management.literature.system.command.PatternHolder#AUTHORPATTERN}).
     *
     * @return Stream of authors.
     *
     * @throws NoSuchElementException
     *         if one of the authors in the collection doesn't exist, this
     *         exception is thrown.
     */
    public Stream<Author> getAuthors(Collection<String> authorNames)
            throws NoSuchElementException {

        LinkedHashSet<Author> authors = new LinkedHashSet<>();
        for (String s : authorNames) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter(" ");
            String firstName = sc.next(PatternHolder.NAMEPATTERN);
            String lastName = sc.next(PatternHolder.NAMEPATTERN);
            Author listAuthor = new Author(firstName, lastName);
            if (this.authorsList.contains(listAuthor)) {
                authors.add(this.authorsList.get(this.authorsList.indexOf(listAuthor)));
            } else {
                throw new NoSuchElementException(String.format("author"
                        + " \"%s\" not found", listAuthor.toString()));
            }
        }
        return authors.stream();
        //return this.authorsList.stream().filter(authors::contains);
    }

    //=================has methods======================

    /**
     * Checks if a article with this id was published before.
     *
     * @param id
     *         the unique id of the article (See {@link PatternHolder#IDPATTERN}).
     *
     * @return true - the article was published temporally before.
     */
    public boolean hasPublication(final String id) {
        return this.getAllPublications()
                .anyMatch(publication ->
                        id.equals(publication.getId()));
    }

    //=================static methods====================

    /**
     * Calculates the jaccard index for two string collections.
     * <p>
     * See <a href="https://en.wikipedia.org/wiki/Jaccard_index">Jaccard-Index</a>
     * </p>
     *
     * @param list1
     *         a list of strings.
     * @param list2
     *         a list of strings.
     *
     * @return the jaccard index of the two sets.
     */
    public static String calculateJaccard(final Collection<String> list1,
                                          final Collection<String> list2) {
        if (list1.size() == 0 && list2.size() == 0) {
            return "1.000";
        }
        TreeSet<String> unification = new TreeSet<>();
        unification.addAll(list1);
        unification.addAll(list2);

        TreeSet<String> intersection = new TreeSet<>();
        list1.stream().filter(list2::contains).forEach(intersection::add);

        Double result = (double) intersection.size() / (double) unification.size();
        return String.format("%f", result).substring(0, JACCARD_FLOATINGPOINTS + 2).replace(",", ".");
    }

    /**
     * Calculates the h index for a number of cited publications.
     * <p>
     * See <a href="https://en.wikipedia.org/wiki/H-index">h-Index</a>
     * </p>
     *
     * @param citations
     *         a collection of integers that each represent a publication
     *         and their value represents the citation count for the publication.
     *
     * @return the jaccard index of the two sets.
     */
    public static String calculateHIndex(final Collection<Integer> citations) {
        ArrayList<Integer> sortedCitations = new ArrayList<>(citations);
        sortedCitations.sort(Collections.reverseOrder());
        int hIndex = 0;
        int numOfArticles = 0;
        for (Integer i : sortedCitations) {
            numOfArticles++;
            if (i >= numOfArticles) {
                hIndex = numOfArticles;
            }
        }
        return String.valueOf(hIndex);
    }
}
