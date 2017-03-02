package edu.kit.informatik.management.literature.util;

import edu.kit.informatik.management.literature.*;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Enum to manage the different styles to
 * represent a LiteratureIndex.
 * <p>
 *     All enum constants should declare a anonymous class
 *     implementing the
 *     {@linkplain LiteratureIndexStyles#printInStyle(int, Publication, Publishers)}
 *     method.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public enum LiteratureIndexStyles {
    /**
     * Constant for the IEEE Simplified representation
     * of a LiteratureIndex.
     */
    IEEE {
        /**
         * Returns the IEEE Representation of the Article.
         *
         * @param order
         *         the position of the article in the literatureIndex.
         * @param publication
         *         the publication that should be represented.
         * @param publisher
         *         the publisher of the article.
         *
         * @return the representation of the article in IEEE format.
         *
         * @throws IllegalArgumentException
         *         If the publisher is unknown
         *         this exception is thrown.
         */
        @Override
        public String printInStyle(final int order,
                                   final Publication publication,
                                   final Publishers publisher)
                throws IllegalArgumentException {
            Stream<Author> authorStream = publication.getAuthors();

            ArrayList<String> authorsList = new ArrayList<>();
            String authors = "";

            authorStream.forEach(author -> authorsList.add(String.format("%1s. %s", author
                            .getFirstName().substring(0, 1).toUpperCase()
                    , author.getLastName())));

            switch (authorsList.size()) {
                case 1:
                    authors = authors.concat(authorsList.get(0));
                    break;
                case 2:
                    authors = String.format("%s and %s", authorsList.get(0), authorsList.get(1));
                    break;
                default:
                    authors = String.format("%s et al.", authorsList.get(0));
            }

            if (publisher.getClass() == ConferenceSeries.class) {
                ConferenceSeries cs = (ConferenceSeries) publisher;
                Conference conf = cs.getConference(publication.getYear()).get();

                return String.format("[%d] %s, \"%s,\" in Proceedings of %s, %s, %4d.",
                        order, authors, publication.getTitle(), cs.getTitle(),
                        conf.getLocation(), conf.getYear());
            } else if (publisher.getClass() == Journal.class) {
                Journal j = (Journal) publisher;

                return String.format("[%d] %s, \"%s,\" %s, %4d.",
                        order, authors, publication.getTitle(), j.getTitle(),
                        publication.getYear());
            }
            throw new IllegalArgumentException("unexpected publishers found!");
        }
    },

    /**
     * Constant for the Chicago representation of a LiteratureIndex.
     */
    CHICAGO {
        /**
         * Returns the Chicago Representation of the Article.
         *
         * @param order the index of the article in the index.
         *
         * @param publication
         *         the publication that should be represented.
         * @param publisher
         *         the publisher of the article.
         *
         * @return the representation of the article in Chicago format.
         *
         * @throws IllegalArgumentException
         *         If the publisher is unknown
         *         this exception is thrown.
         */
        @Override
        public String printInStyle(final int order,
                                   final Publication publication,
                                   final Publishers publisher)
                throws IllegalArgumentException {
            Stream<Author> authorStream = publication.getAuthors();

            ArrayList<String> authorsList = new ArrayList<>();

            String firstAuthorLastName = authorStream.findFirst()
                    .get().getLastName();

            authorStream = publication.getAuthors();

            String authors = "";

            authorStream.forEach(author -> authorsList.add(String.format("%s, %s"
                    , author.getLastName(), author
                            .getFirstName())));

            switch (authorsList.size()) {
                case 1:
                    authors = authors.concat(authorsList.get(0));
                    break;
                case 2:
                    authors = String.format("%s, and %s", authorsList.get(0), authorsList.get(1));
                    break;
                default: {
                    for (int i = 0; i < authorsList.size() - 2; i++) {
                        authors += authorsList.get(i) + ", ";
                    }
                    authors += "and " + authorsList.get(authorsList.size() - 1);
                }
            }

            if (publisher.getClass() == ConferenceSeries.class) {
                ConferenceSeries cs = (ConferenceSeries) publisher;
                Conference conf = cs.getConference(publication.getYear()).get();

                return String.format("(%s, %4d) %s. \"%s.\" Paper presented at %s, %4d, %s.",
                        firstAuthorLastName, publication.getYear(), authors, publication.getTitle(),
                        cs.getTitle(), publication.getYear(), conf.getLocation());
            } else if (publisher.getClass() == Journal.class) {
                Journal j = (Journal) publisher;

                return String.format("(%s, %4d) %s. \"%s.\" %s (%4d).",
                        firstAuthorLastName, publication.getYear(), authors, publication.getTitle(),
                        j.getTitle(), publication.getYear());
            }
            throw new IllegalArgumentException("unexpected publishers found!");
        }
    };

    /**
     * Returns a string  representing the given
     * {@link Publication} in
     * the specified style.
     *
     * @param order
     *         the index of the article in the index.
     * @param publication
     *         the publication that should be represented..
     * @param publishers
     *         the publisher of the article.
     *
     * @return a string.
     *
     * @throws IllegalArgumentException
     *         if the specified publisher has no known representation style
     *         found this exception is thrown.
     */
    public abstract String printInStyle(int order, Publication publication, Publishers publishers)
            throws IllegalArgumentException;

    /**
     * returns the enum constant for the string.
     *
     * @param style
     *         the string representation of the style.
     *
     * @return the enum constant represented by the string.
     *
     * @throws IllegalArgumentException
     *         if the string doesn`t represent
     *         a enum constant this exception is thrown.
     */
    public static LiteratureIndexStyles getStyle(final String style)
            throws IllegalArgumentException {
        if (IEEE.toString().equals(style.toUpperCase())) {
            return IEEE;
        } else if (CHICAGO.toString().equals(style.toUpperCase())) {
            return CHICAGO;
        } else {
            throw new IllegalArgumentException(String.format("the style \"%s\" wasn`t found!", style));
        }
    }
}
