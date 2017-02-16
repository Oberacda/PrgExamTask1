package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.LiteratureIndexStyles;

import java.util.*;
import java.util.stream.Stream;

/**
 * Manages the literature index of a publication.
 * <p>
 * This class manages a collection of articles.
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
                return -1;
            }
            if (o1.getTitle().compareTo(o2.getTitle()) != 0) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (Integer.compare(o1.getYear(), o2.getYear()) != 0) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            return o1.getId().compareTo(o2.getId());


        });
    }

    //=================methods==========================

    /**
     * Adds a article to the list.
     *
     * @param newArticle
     *         the article that was added.
     */
    public void addEntry(final Article newArticle) {
        if (newArticle.isComplete()
                && !(this.litratureIndex.contains(newArticle))) {
            this.litratureIndex.add(newArticle);
        }
    }

    public void addAllEntrys(Collection<Article> articleSet) {
        this.litratureIndex.addAll(articleSet);
    }

    public boolean hasEntry(final Article serachedArticle) {
        return this.litratureIndex.stream()
                .anyMatch(serachedArticle::equals);
    }

    public Stream<Article> getArticles() {
        return this.litratureIndex.stream();
    }

    public Stream<String> printIndexInStyle(final String style,
                                            final LiteratureManagement lm)
            throws NoSuchElementException {

        LiteratureIndexStyles indexStyles = LiteratureIndexStyles.getStyle(style);

        int order = 1;
        List<String> result = new ArrayList<>();

        for (Article article : this.litratureIndex) {
            Publishers publishers = lm.getPublisher(article);
            result.add(indexStyles.printInStyle(order, article, publishers));
            order++;
        }
        return result.stream();
    }

    public static String directPrintIndexInStyle(final String style,
                                                 final Article article,
                                                 final Publishers publishers)
            throws NoSuchElementException {
        LiteratureIndexStyles indexStyles = LiteratureIndexStyles.getStyle(style);

        return indexStyles.printInStyle(1, article, publishers);
    }


}
