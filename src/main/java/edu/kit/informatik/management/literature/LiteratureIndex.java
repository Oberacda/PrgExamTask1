package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.LiteratureIndexStyles;

import java.util.*;
import java.util.stream.Stream;

/**
 * Manages a index of publications.
 * <p>
 * This class manages a collection of publications. They are
 * always sorted.
 * </p>
 * <p>
 * The content of a literature index can be printed out in different styles.
 * </p>
 * <p>
 * See: {@link LiteratureIndexStyles}.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class LiteratureIndex {

    //=================fields==========================

    private final TreeSet<Publication> literatureIndex;

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
        this.literatureIndex = new TreeSet<>((o1, o2) -> {

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
     * Adds a publication to the index.
     * <p>
     * Your only allowed to add
     * {@linkplain Publication#isComplete() complete}
     * publications.
     * </p>
     *
     * @param newPublication
     *         the publication that was added.
     *
     * @throws IllegalArgumentException
     *         if the publication is
     *         incomplete this exception is thrown!
     */
    public void addEntry(final Publication newPublication)
            throws IllegalArgumentException {
        if (newPublication.isComplete()) {
            this.literatureIndex.add(newPublication);
        } else {
            throw new IllegalArgumentException(String.format("article \"%s\" has"
                    + " no authors and therefore can't be added!", newPublication.getId()));
        }
    }

    /**
     * Adds all publications to the index.
     * <p>
     * Your only allowed to add
     * {@linkplain Publication#isComplete() complete}
     * publications.
     * </p>
     *
     * @param publicationSet
     *         the publications that should be added.
     *
     * @throws IllegalArgumentException
     *         if one of the publications is
     *         incomplete this exception is thrown!
     */
    public void addAllEntrys(Collection<Publication> publicationSet)
            throws IllegalArgumentException {
        if (publicationSet.stream().allMatch(Publication::isComplete)) {
            this.literatureIndex.addAll(publicationSet);
        } else {
            throw new IllegalArgumentException("one of the articles has"
                    + " no authors and therefore none can be added!");
        }
    }

    /**
     * Checks if this index contains the desired article.
     *
     * @param serachedPublication
     *         the serached article.
     *
     * @return true - the article is in the index.
     */
    public boolean hasEntry(final Publication serachedPublication) {
        return this.literatureIndex.stream()
                .anyMatch(serachedPublication::equals);
    }

    /**
     * Returns all publications in the index.
     *
     * @return stream of articles.
     */
    public Stream<Publication> getPublications() {
        return this.literatureIndex.stream();
    }

    /**
     * Returns a stream of string representations of all
     * publications in the index.
     * <p>
     * The style of the index is represented by the style parameter.
     * </p>
     *
     * @param style
     *         {@linkplain LiteratureIndexStyles}
     * @param lm
     *         the {@linkplain LiteratureManagement} the publications are part of.
     *
     * @return representation of all indexed articles in the specified style.
     *
     * @throws IllegalArgumentException
     *         if either the style isnt a valid enum constant or
     *         one of the articles wasn't found this exception is thrown!
     */
    public Stream<String> printIndexInStyle(final String style,
                                            final LiteratureManagement lm)
            throws IllegalArgumentException {

        LiteratureIndexStyles indexStyles = LiteratureIndexStyles.getStyle(style);

        int order = 1;
        List<String> result = new ArrayList<>();

        for (Publication publication : this.literatureIndex) {
            Publishers publishers = lm.getPublisher(publication);
            result.add(indexStyles.printInStyle(order, publication, publishers));
            order++;
        }
        return result.stream();
    }

    /**
     * Directly prints out a string representation
     * of a article with the specified style.
     *
     * @param style {@linkplain LiteratureIndexStyles}
     * @param article the instance of a article that
     *                should be printed out.
     * @param publishers the publisher of the article.
     * @return a string representation of the article in the specified style.
     * @throws IllegalArgumentException if either the style isnt a valid enum constant or
     *         one of the publisher type is unknown this exception is thrown!
     */
    public static String directPrintIndexInStyle(final String style,
                                                 final Article article,
                                                 final Publishers publishers)
            throws IllegalArgumentException {
        LiteratureIndexStyles indexStyles = LiteratureIndexStyles.getStyle(style);

        return indexStyles.printInStyle(1, article, publishers);
    }
}
