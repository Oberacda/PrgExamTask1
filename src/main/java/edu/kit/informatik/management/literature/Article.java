package edu.kit.informatik.management.literature;

import java.util.SortedSet;

/**
 * A article is a representation of a publication.
 * <p>
 *     Articles are publications published by
 *     either a {@link edu.kit.informatik.management.literature.Conference}
 *     or a {@link edu.kit.informatik.management.literature.Journal}.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.5
 */
public class Article extends Publication {

    //=================constructor======================

    /**
     * Creates a new incomplete article.
     * <p>
     * A incomplete article has no authors assigned
     * to it. It always has a unique id and a title,
     * as well as a year. The keywords parameter specifies
     * the keywords the venue has the article is published with.
     * </p>
     * <b>No Parameter should be null!</b>
     *
     * @param id
     *         the unique id of the article.
     * @param title
     *         the title of the article.
     * @param year
     *         the year the article was published.
     * @param keywords
     *         the keywords of the venue which
     *         publishes the article
     */
    public Article(final String id,
                   final String title,
                   final int year,
                   final SortedSet<String> keywords) {
        super(id, title, year, keywords);
    }

    //=================override methods=================

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("%s:%4d-%s", this.getId(), this.getYear(), this.getTitle());
    }
}
