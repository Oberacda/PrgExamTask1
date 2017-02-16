package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.interfaces.Venue;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Modelling a publisher.
 * <p>
 * This abstract class defines a publisher which is able of
 * publishing articles and of having keywords.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public abstract class Publishers implements Entity, Venue {

    //=================fields==========================

    private final String title;

    private TreeSet<String> keywords;

    //=================constructor======================

    /**
     * Creates a new Publishers.
     * <p>
     * A new publisher has no articles published by it.
     * </p>
     *
     * @param title
     *         the title of the publisher (unique id).
     *
     * @throws IllegalArgumentException
     *         If the title doesn't match
     *         the requirements this error is thrown.
     */
    public Publishers(final String title) throws IllegalArgumentException {
        if (title != null && PatternHolder.TITLEPATTERN.matcher(title).matches()) {
            this.title = title;
        } else {
            throw new IllegalArgumentException(String.format("only chars \"%s\""
                    + " are allowed as title!", PatternHolder.TITLEPATTERN));
        }

        this.keywords = new TreeSet<>();
    }

    //=================getter===========================

    /**
     * Returns the title of the publisher.
     *
     * @return title of the publisher.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the TreeSet containing all keywords of the venue.
     *
     * @return Tree set of keywords.
     */
    protected TreeSet<String> getKeywordsTree() {
        return this.keywords;
    }

    //=================abstract methods=================

    /**
     * Publishes a article by the publisher its called on.
     * <p>
     * While publishing a {@linkplain Article#Article(String, String,
     * int, java.util.SortedSet) incomplete} article is created.
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
     */
    public abstract void addArticle(String id, int year, String title)
            throws IllegalArgumentException;

    //=================override methods=================

    @Override
    public Stream<String> getKeywords() {
        return this.getKeywordsTree().stream();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Publishers publishers = (Publishers) o;

        return getTitle().equals(publishers.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().hashCode();
    }
}
