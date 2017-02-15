package edu.kit.informatik.management.literature;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Manages the literature index of a publication.
 * <p>
 * This class manages a collection of articles cited in the
 * article its a filed of.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class LiteratureIndex {

    //=================fields==========================

    private TreeSet<Article> litratureIndex;

    //=================constructor======================

    /**
     * Creates a new empty literature index.
     * <p>
     * A empty literature index has no articles in it.
     * </p>
     * The literature index is sorted with the schema:
     * <table>
     * <caption>examples:</caption>
     * <thead>
     * <tr>
     * <td>| ID</td> <td>| 1st Author</td> <td>| 2nd Author</td> <td>| 3rd Author</td>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     * <td>| 1</td> <td>| A</td> <td>| -</td> <td>| -</td>
     * </tr>
     * <tr>
     * <td>| 2</td> <td>| A</td> <td>| B</td> <td>| -</td>
     * </tr>
     * <tr>
     * <td>| 3</td> <td>| B</td> <td>| -</td> <td>| -</td>
     * </tr>
     * <tr>
     * <td>| 4</td> <td>| B</td> <td>| C</td> <td>| D</td>
     * </tr>
     * <tr>
     * <td>| 5</td> <td>| B</td> <td>| D</td> <td>| -</td>
     * </tr>
     * </tbody>
     * </table>
     */
    public LiteratureIndex() {
        this.litratureIndex = new TreeSet<>((o1, o2) -> {

            Iterator<Author> iterO1 = o1.getAuthors().iterator();
            Iterator<Author> iterO2 = o2.getAuthors().iterator();

            while (iterO1.hasNext() && iterO2.hasNext()) {
                Author a = iterO1.next();
                Author b = iterO2.next();

                if (a.compareTo(b) != 0) {
                    return a.compareTo(b);
                }
            }

            if (iterO1.hasNext()) {
                return 1;
            }

            if (iterO2.hasNext()) {
                return - 1;
            }

            return - 1;
        });
    }

    //=================methods==========================

    /**
     * Adds a citation to the list.
     *
     * @param citedArticle
     *         the article that was cited.
     */
    public void addCitation(final Article citedArticle) {
        if (citedArticle.isComplete()
                && ! (this.litratureIndex.contains(citedArticle))) {
            this.litratureIndex.add(citedArticle);
        }
    }

    public boolean cites(final Article citedArticle) {
        return this.litratureIndex.stream()
                .anyMatch(article -> article.equals(citedArticle));
    }

    public Stream<Article> getCitedArticles() {
        return this.litratureIndex.stream();
    }
}
