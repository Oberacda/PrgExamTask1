package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a Conference Series which consists of one or
 * more {@link Conference Conferences}.
 * <p>
 * A conference series is a publisher that can publish articles
 * related to its conferences. There can only be one conference
 * a year per series.
 * </p>
 * <p>
 *     All conferences in the series have to publish their
 *     articles with the series.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class ConferenceSeries extends Publishers {

    //=================fields==========================

    private final TreeMap<Integer, Conference> conferenceList;

    //=================constructor======================

    /**
     * Creates a new empty conference series.
     * <p>
     * A empty conference series has no conferences and
     * as a consequence to that no articles.
     * </p>
     *
     * @param title
     *         The title of the conference
     *         (has to be a Sequence of chars only consisting of a-z and A-Z).
     *
     * @throws IllegalArgumentException
     *         if the title is containing illegal chars
     *         this exception is thrown.
     */
    public ConferenceSeries(final String title) throws IllegalArgumentException {
        super(title);
        this.conferenceList = new TreeMap<>(Comparator.naturalOrder());
    }

    //=================getter===========================

    /**
     * Gets a specific conference from the series.
     * <p>
     * This method returns the conference in the
     * specified year form this series.
     * </p>
     *
     * @param year
     *         The year the conference from the series
     *         should have been set.
     *
     * @return Optional containing the conference. If there is
     * no conference in this year the optional is empty.
     */
    public Optional<Conference> getConference(final int year) {
        return this.conferenceList.values().stream()
                .filter(conference -> year == conference.getYear())
                .findAny();
    }


    //=================methods==========================

    /**
     * Adds a conference to the series.
     * <p>
     * The conference that should be added can`t be in a year where there
     * already is a conference in this series.
     * </p>
     *
     * @param year
     *         the year the conference should be at
     * @param location
     *         the location of the conference
     *
     * @throws IllegalArgumentException
     *         if there already is a conference
     *         in this year this exception is thrown.
     */
    public void addConference(final int year, final String location)
            throws IllegalArgumentException {
        if (this.conferenceList.containsKey(year)) {
            throw new IllegalArgumentException(String.format("there already is"
                    + " a conference in %4d", year));
        } else {
            this.conferenceList.put(year, new Conference(year,
                    location,
                    this.getKeywordsTree().descendingSet()));
        }
    }

    //=================override methods=================

    @Override
    public void addKeyword(final String keyword)
            throws IllegalArgumentException {
        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.getKeywordsTree().add(keyword);
            for (Conference c : this.conferenceList.values()) {
                c.addKeyword(keyword);
            }
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements: <%s> !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }
    }

    /**
     * Publishes a article by the venue its called on.
     * <p>
     * While publishing a {@linkplain Article#Article(String, String,
     * int, SortedSet) incomplete} article is created.
     * </p>
     *
     * @param id
     *         the unique of the new article
     * @param year
     *         the year the article is published
     * @param title
     *         the title of the article.
     *
     * @throws IllegalArgumentException
     *         this exception is thrown
     *         if there already is a article with this id.
     * @throws NoSuchElementException
     *         If there is no conference in this year this exception is called.
     */
    @Override
    public void addArticle(final String id, final int year, final String title)
            throws IllegalArgumentException, NoSuchElementException {
        if (this.conferenceList.containsKey(year)) {
            this.conferenceList.get(year).addArticle(id, title);
        } else {
            throw new NoSuchElementException(String.format("no known conference of "
                    + "series \"%s\" in %4d!", this.getTitle(), year));
        }
    }

    /**
     * Returns a article form a conference form this series.
     *
     * @param id
     *         the id of the desired article.
     *
     * @return Optional containing the article. If there is no article
     * with this id the optional is empty.
     */
    @Override
    public Optional<Publication> getPublication(final String id) {
        return this.getPublications().
                findAny().filter(article -> id.equals(article.getId()));
    }

    @Override
    public Stream<Publication> getPublications() {
        TreeSet<Publication> publications = new TreeSet<>(Comparator.comparing(Publication::getId));
        for (Conference conference : this.conferenceList.values()) {
            publications.addAll(conference.getPublications()
                    .collect(Collectors.toSet()));
        }
        return publications.stream();
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     *
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return 31 * super.hashCode()
                + ConferenceSeries.class.hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj
     *         the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     *
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj) && (obj.getClass().equals(ConferenceSeries.class));
    }
}
