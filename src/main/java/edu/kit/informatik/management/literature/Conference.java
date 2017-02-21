package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.interfaces.Venue;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Class representing a Conference.
 * <p>
 * A conference is always part of a
 * {@link ConferenceSeries conference series}.
 * </p>
 * <p>
 *     A conference has his Series assigned as
 *     publisher. So it can't publish articles directly,
 *     only by its series.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */

public class Conference implements Entity, Venue {

    //=================fields==========================

    private final int year;

    private final String location;

    private final TreeMap<String, Publication> conferencePublications;

    private final TreeSet<String> keywordsList;

    //=================constructor======================

    /**
     * Creates a new empty conference.
     * <p>
     * <b>This constructor should only be called
     * by a ConferenceSeries.</b>
     * </p>
     * <p>
     * A empty conference has no articles published by it.
     * its unique identified by ist year and the
     * conference series it is a part of.
     * </p>
     * <b>No Parameter should be null!</b>
     *
     * @param year
     *         the year the conference was at.
     * @param location
     *         the location of the conference.
     * @param keywords
     *         the keywords of the conference series
     *         that are inherited to the conference.
     */
    public Conference(final int year, final String location, final SortedSet<String> keywords) {
        this.year = year;
        this.location = location;
        this.conferencePublications = new TreeMap<>(String::compareTo);
        this.keywordsList = new TreeSet<>(keywords);
    }

    //=================getter===========================

    /**
     * Returns the year of the conference.
     * <p>
     * Integer between 1000 and 9999.
     * </p>
     *
     * @return year the conference took place.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the location of the conference.
     *
     * @return location of the conference.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Returns a special article published by this conference.
     *
     * @param id
     *         the unique id of the desired article.
     *
     * @return the article with the specified id published by
     * this conference.
     */
    public Optional<Publication> getPublication(final String id) {
        return this.conferencePublications.values().stream()
                .filter(article -> article.getId().equals(id)).findFirst();
    }

    /**
     * Returns a stream of all articles published by this conference.
     *
     * @return Stream of articles. If there is nothing published this stream is empty.
     */
    public Stream<Publication> getPublications() {
        return this.conferencePublications.values().stream();
    }

    //=================methods==========================

    /**
     * Publishes a article by the conference its called on.
     * <p>
     * While publishing a {@linkplain Article#Article(String, String,
     * int, SortedSet) incomplete} article is created.
     * </p>
     *
     * @param id
     *         the unique of the new article
     * @param title
     *         the title of the article.
     *
     * @throws IllegalArgumentException
     *         this exception is thrown
     *         if there already is a article with this id.
     */
    public void addArticle(final String id, final String title)
            throws IllegalArgumentException {
        if (this.conferencePublications.containsKey(id)) {
            throw new IllegalArgumentException(String.format("article \"%s\" is already present!", id));
        } else {
            this.conferencePublications.put(id, new Article(id, title,
                    this.getYear(),
                    this.keywordsList.descendingSet()));
        }
    }

    //=================override methods=================

    @Override
    public void addKeyword(final String keyword) throws IllegalArgumentException {
        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.keywordsList.add(keyword);

            for (Publication publication : this.conferencePublications.values()) {
                publication.addKeyword(keyword);
            }
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements: <%s> !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }
    }

    @Override
    public Stream<String> getKeywords() {
        return this.keywordsList.stream();
    }
}

