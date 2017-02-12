package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;

/**
 * Represents a Conference Series which consists of one or
 * more {@link Conference Conferences}.
 * <p>
 *     A conference series is a venue that can publish articles
 *     related to a conference. There can only be one conference
 *     a year per series.
 * </p>
 * @author David Oberacker
 * @version 1.0.1
 */
public class ConferenceSeries extends Venue  {

    //=================Fields==========================

    private TreeMap<Integer, Conference> conferenceList;

    //=================Constructor======================

    /**
     * Creates a new empty conference series.
     * <p>
     *     A empty conference series has no conferences and
     *     as a consequence to that no articles.
     * </p>
     * @param title The title of the conference
     *              (has to be a Sequnce of chars only consisting of a-z and A-Z).
     * @throws IllegalArgumentException if the title is containing illegal chars
     *              this exception is thrown.
     */
    public ConferenceSeries(final String title) throws IllegalArgumentException {
        super(title);
        this.conferenceList = new TreeMap<>(Comparator.naturalOrder());
    }

    //=================Getter===========================

    /**
     * Gets a specific conference from the series.
     * <p>
     *     This method returns the conference in the
     *     specified year form this series.
     *
     * </p>
     * @param year The year the conference from the series
     *             should have been set.
     * @return conference in the specific year.
     * @throws NoSuchElementException if there is no conference in this
     *          year this exception is thrown.
     */
    public Conference getConference(final int year) throws NoSuchElementException {
        if (this.conferenceList.containsKey(year)) {
            return this.conferenceList.get(year);
        } else {
            throw new NoSuchElementException("There is no conference in this year in this series!");
        }
    }

    /**
     * Retuns a article form a conference form this series.
     *
     * @param year the year the conference where the article was
     *             published took place.
     * @param id the id of the desired article.
     *
     * @return article from the conference with this id.
     *
     * @throws NoSuchElementException If there was no conference in this
     *          year or no article with this id was published during the
     *          conference this exception is thrown.
     */
    public Article getArticle(final int year, final String id)
            throws NoSuchElementException {
        return this.getConference(year).getArticle(id);
    }

    //=================Methods==========================

    /**
     * Adds a conference to the series.
     * <p>
     *      The conference that should be added can`t be in a year where there
     *      already is a conference in this series.
     * </p>
     * @param year the year the conference should be at
     * @param location the location of the conference
     *
     * @throws IllegalArgumentException if there already is a conference
     *          in this year this exception is thrown.
     */
    public void addConference(final int year, final String location)
            throws IllegalArgumentException {
        if (this.conferenceList.containsKey(year)) {
                throw new IllegalArgumentException(String.format("There is already"
                        + " a conference in the year %4d", year));
        } else {
            this.conferenceList.put(year, new Conference(year,
                    location,
                    this.getKeywordsTree().descendingSet()));
        }
    }

    //=================Override Methods=================

    @Override
    public void addKeyword(final String keyword)
            throws IllegalArgumentException {
        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.getKeywordsTree().add(keyword);
            for (Conference c: this.conferenceList.values()) {
                c.addKeyword(keyword);
            }
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements : %s !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }
    }

    @Override
    public SortedSet<Article> getArticles() {
        return null;
    }
}
