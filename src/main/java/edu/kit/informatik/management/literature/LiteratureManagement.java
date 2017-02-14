package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class LiteratureManagement {
    private ArrayList<ConferenceSeries> conferenceSeriesList;

    private ArrayList<Journal> journalsList;

    private ArrayList<Author> authorsList;

    public LiteratureManagement() {
        this.authorsList = new ArrayList<>();
        this.journalsList = new ArrayList<>();
        this.conferenceSeriesList = new ArrayList<>();
    }

    public void addJournal(final String name, final String publisher)
            throws ElementAlreadyPresentException {
        Journal newJournal = new Journal(name, publisher);
        if (! (this.journalsList.contains(newJournal))) {
            this.journalsList.add(newJournal);
        } else {
            throw new ElementAlreadyPresentException("This journal is already present!");
        }
    }

    public void addConferenceToSeries(final String conferenceSeriesTitle,
                                      final String conferenceLocation,
                                      final int conferenceYear)
            throws NoSuchElementException, ElementAlreadyPresentException {
        Optional<ConferenceSeries> conferenceSeriesOptional
                = this.getConferenceSeries(conferenceSeriesTitle);

        if (! (conferenceSeriesOptional.isPresent())) {
            throw new NoSuchElementException("There is no conference series with this title!");
        }

        ConferenceSeries conferenceSeries = conferenceSeriesOptional.get();
        if (! (conferenceSeries.getConference(conferenceYear).isPresent())) {
            conferenceSeries.addConference(conferenceYear, conferenceLocation);
        } else {
            throw new ElementAlreadyPresentException(String.format("There already is a conference"
                    + " in the year %4d in this series!", conferenceYear));
        }
    }

    public void addConferenceSeries(final String title)
            throws ElementAlreadyPresentException {
        ConferenceSeries newConferenceSeries = new ConferenceSeries(title);
        if (! (this.conferenceSeriesList
                .contains(newConferenceSeries))) {
            this.conferenceSeriesList.add(newConferenceSeries);
        } else {
            throw new ElementAlreadyPresentException("This conference series is already present!");
        }
    }

    public void addAuthor(final String firstName, final String lastName)
            throws ElementAlreadyPresentException {
        Author newAuthor = new Author(firstName, lastName);
        if (this.authorsList.stream().anyMatch(author
                -> (newAuthor.equals(author)))) {
            throw new ElementAlreadyPresentException("There already a author with this name!");
        } else {
            this.authorsList.add(newAuthor);
        }
    }

    public Optional<ConferenceSeries> getConferenceSeries(final String title) {
        return this.conferenceSeriesList.stream().filter(conferenceSeries ->
                title.equals(conferenceSeries.getTitle())).findFirst();
    }

    public Optional<Journal> getJournal(final String title) {
        return this.journalsList.stream().filter(journal ->
                title.equals(journal.getTitle())).findFirst();
    }

    public Optional<Conference> getConferenceFromSeries(final String seriesTitle,
                                                         final int conferenceYear) {
        Optional<ConferenceSeries> seriesOptional = this.getConferenceSeries(seriesTitle);
        if (seriesOptional.isPresent()) {
            return seriesOptional.get().getConference(conferenceYear);
        } else {
            return Optional.empty();
        }

    }
    public Stream<Article> getAllArticles() {
        HashSet<Article> articleHashSet = new HashSet<>();
        HashSet<Venue> venues = new HashSet<>();
        venues.addAll(this.conferenceSeriesList);
        venues.addAll(this.journalsList);
        for (Venue v: venues) {
            articleHashSet.addAll(v.getArticles().collect(Collectors.toSet()));
        }
        return articleHashSet.stream();
    }

    public Optional<Article> getArticle(final String id) {
        return this.getAllArticles().filter(article ->
                id.equals(article.getId())).findFirst();
    }

    public boolean hasArticle(final String id) {
        return this.getAllArticles().anyMatch(article -> id.equals(article.getId()));
    }

    public Stream<Author> getAuthors(Collection<String> authorNames)
            throws NoSuchElementException {

        ArrayList<Author> authors = new ArrayList<>();
        for (String s : authorNames) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter(" ");
            String firstName = sc.next(PatternHolder.NAMEPATTERN);
            String lastName = sc.next(PatternHolder.NAMEPATTERN);
            Author listAuthor  = new Author(firstName, lastName);
            if (this.authorsList.contains(listAuthor)) {
                authors.add(listAuthor);
            } else {
                throw new NoSuchElementException(String.format("author"
                        + " \"%s\" not found", listAuthor.toString()));
            }
        }
        return this.authorsList.stream().filter(authors::contains);
    }
}
