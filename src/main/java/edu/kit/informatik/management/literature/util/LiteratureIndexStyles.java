package edu.kit.informatik.management.literature.util;

import edu.kit.informatik.management.literature.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Enum to manage the different styles to
 * represent a LiteratureIndex.
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public enum LiteratureIndexStyles {
    /**
     * Constant for the IEEE Simplified representation
     * of a LiteratureIndex.
     */
    IEEE,

    /**
     * Constant for the Chicago representation of a LiteratureIndex.
     */
    CHICAGO;

    /**
     * Returns a stream of strings representing the given
     * {@link LiteratureIndex#getCitedArticles() literatureIndex} in
     * the specified style.
     *
     * @param styles
     *         Enum Constant of the style.
     * @param literatureIndex
     *         a stream of articles.
     * @param lm
     *         the lirature management the articles
     *         in the literatureIndex are part of.
     *
     * @return a stream of strings.
     */
    public static Stream<String> printInStyle(final LiteratureIndexStyles styles
            , final Stream<Article> literatureIndex
            , final LiteratureManagement lm) {
        ArrayList<String> result = new ArrayList<>();
        literatureIndex.forEach(article -> {
            result.add(printInStyle(styles, article, lm.getPublisher(article)));
        });
        return result.stream();
    }

    /**
     * Returns a string  representing the given
     * {@link Article} in
     * the specified style.
     *
     * @param styles
     *         Enum Constant of the style.
     * @param article
     *         the article.
     * @param publishers
     *         the publisher of the article.
     *
     * @return a string.
     *
     * @throws NoSuchElementException
     *         if the specified style wasn`t
     *         found this exception is thrown.
     */
    public static String printInStyle(final LiteratureIndexStyles styles
            , final Article article, final Publishers publishers) {
        String result;
        if (styles == IEEE) {
            result = printInIeee(1, article, publishers);
        } else if (styles == CHICAGO) {
            result = printInChicargo(article, publishers);
        } else {
            throw new NoSuchElementException(String.format("the style \"%s\" wasn`t found!", styles));
        }
        return result;
    }

    /**
     * Returns the IEEE Representation of the Article.
     *
     * @param order
     *         the position of the article in the literatureIndex.
     * @param article
     *         the article that should be represented.
     * @param publishers
     *         the publisher of the article.
     *
     * @return the representation of the article in IEEE format.
     *
     * @throws IllegalArgumentException
     *         If the publisher is unknown
     *         this exception is thrown.
     */
    private static String printInIeee(int order, Article article, Publishers publishers)
            throws IllegalArgumentException {

        Stream<Author> authorStream = article.getAuthors();

        ArrayList<String> authorsList = new ArrayList<>();
        String authors = "";

        authorStream.forEach(author -> {
            authorsList.add(String.format("%1s. %s", author
                            .getFirstName().substring(0, 1).toUpperCase()
                    , author.getLastName()));
        });

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

        if (publishers.getClass() == ConferenceSeries.class) {
            ConferenceSeries cs = (ConferenceSeries) publishers;
            Conference conf = cs.getConference(article.getYear()).get();

            return String.format("[%d] %s, \"%s,\" in Proceedings of %s, %s, %4d.",
                    order, authors, article.getTitle(), cs.getTitle(),
                    conf.getLocation(), conf.getYear());
        } else if (publishers.getClass() == Journal.class) {
            Journal j = (Journal) publishers;

            return String.format("[%d] %s, \"%s,\" %s, %4d.",
                    order, authors, article.getTitle(), j.getTitle(),
                    article.getYear());
        }
        throw new IllegalArgumentException("unexpected publishers found!");
    }

    /**
     * Returns the Chicago Representation of the Article.
     *
     * @param article
     *         the article that should be represented.
     * @param publishers
     *         the publisher of the article.
     *
     * @return the representation of the article in Chicago format.
     *
     * @throws IllegalArgumentException
     *         If the publisher is unknown
     *         this exception is thrown.
     */
    private static String printInChicargo(Article article, Publishers publishers)
            throws IllegalArgumentException {

        Stream<Author> authorStream = article.getAuthors();

        ArrayList<String> authorsList = new ArrayList<>();

        String firstAuthorLastName = authorStream.findFirst()
                .get().getLastName();

        authorStream = article.getAuthors();

        String authors = "";

        authorStream.forEach(author -> {
            authorsList.add(String.format("%s, %s"
                    , author.getLastName(), author
                            .getFirstName()));
        });

        switch (authorsList.size()) {
            case 1:
                authors = authors.concat(authorsList.get(0));
                break;
            case 2:
                authors = String.format("%s and %s", authorsList.get(0), authorsList.get(1));
                break;
            default: {
                for (int i = 0; i < authorsList.size() - 2; i++) {
                    authors += authorsList.get(i) + ", ";
                }
                authors += "and " + authorsList.get(authorsList.size() - 1);
            }
        }

        if (publishers.getClass() == ConferenceSeries.class) {
            ConferenceSeries cs = (ConferenceSeries) publishers;
            Conference conf = cs.getConference(article.getYear()).get();

            return String.format("(%s, %4d) %s. \"%s.\" Paper presented at %s, %4d, %s.",
                    firstAuthorLastName, article.getYear(), authors, article.getTitle(),
                    cs.getTitle(), article.getYear(), conf.getLocation());
        } else if (publishers.getClass() == Journal.class) {
            Journal j = (Journal) publishers;

            return String.format("(%s, %4d) %s. \"%s.\" %s, (%4d).",
                    firstAuthorLastName, article.getYear(), authors, article.getTitle(),
                    j.getTitle(), article.getYear());
        }
        throw new IllegalArgumentException("unexpected publishers found!");
    }

    /**
     * returns the enum constant for the string.
     *
     * @param style
     *         the string representation of the style.
     *
     * @return the enum.
     *
     * @throws NoSuchElementException
     *         if the string doesn`t represent
     *         a enum constant this exception is thrown.
     */
    public static LiteratureIndexStyles getStyle(final String style)
            throws NoSuchElementException {
        if (IEEE.toString().equals(style.toUpperCase())) {
            return IEEE;
        } else if (CHICAGO.toString().equals(style.toUpperCase())) {
            return CHICAGO;
        } else {
            throw new NoSuchElementException(String.format("the style \"%s\" wasn`t found!", style));
        }
    }
}
