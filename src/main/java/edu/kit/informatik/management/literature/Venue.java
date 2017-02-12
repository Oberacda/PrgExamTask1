package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Modelling a venue.
 * <p>
 * This abstract class defines a venue which is able of
 * publishing articles and of having keywords.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public abstract class Venue implements Entity {

    //=================Fields==========================

    private final String title;

    private TreeSet<String> keywords;

    //=================Constructor======================

    /**
     * Creates a new Venue.
     * <p>
     * A new venue has no articles published by it.
     * </p>
     *
     * @param title
     *         the title of the venue (unique id).
     *
     * @throws IllegalArgumentException
     *         If the title doesn't match
     *         the requirements this error is thrown.
     */
    public Venue(final String title) throws IllegalArgumentException {
        if (title != null && PatternHolder.NAMEPATTERN.matcher(title).matches()) {
            this.title = title;
        } else {
            throw new IllegalArgumentException("The title of a venue"
                    + " can only be a sequence of the chars [a-zA-Z]");
        }

        this.keywords = new TreeSet<>();
    }

    //=================Getter===========================

    /**
     * Returns the title of the venue.
     *
     * @return title of the venue.
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

    /**
     * Returns a stream of all articles published by the venue.
     * <p>
     * This stream can contain no elements if nothing was published.
     * </p>
     *
     * @return Sorted stream of articles.
     */
    public abstract Stream<Article> getArticles();

    /**
     * Publishes a article by the venue its called on.
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
     *
     */
    public abstract void addArticle(final String id, final int year, final String title)
            throws IllegalArgumentException;

    //=================Override Methods=================

    @Override
    public Stream<String> getKeywords() {
        return this.getKeywordsTree().stream();
    }
}
