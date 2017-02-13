package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class Journal extends Venue {

    //=================Fields==========================

    private TreeMap<String, Article> articleList;

    private final String publisher;

    //=================Constructor======================

    /**
     * Creates a empty journal.
     * <p>
     * A empty journal has no articles published by it.
     * </p>
     *
     * @param title
     *         the title of the journal.
     * @param publisher
     *         the publisher of the journal.
     *
     * @throws IllegalArgumentException
     *         If the title contains illegal
     *         chars this exception is thrown.
     */
    public Journal(final String title, final String publisher) throws IllegalArgumentException {
        super(title);
        this.publisher = publisher;
        this.articleList = new TreeMap<>();
    }

    //=================Getter===========================

    /**
     * Returns the publisher of the journal.
     *
     * @return publisher.
     */
    public String getPublisher() {
        return this.publisher;
    }


    //=================Override Methods=================

    /**
     * Adds a keyword to the entity.
     * <p>
     * a keyword is string only consisting of lowercase chars
     * and no special chars or numbers.
     * </p>
     *
     * @param keyword
     *         the keyword that should be added.
     *
     * @throws IllegalArgumentException
     *         if the keyword that
     *         should be added does not match the requirements
     *         for keywords this exception is thrown.
     */
    @Override
    public void addKeyword(final String keyword) throws IllegalArgumentException {

        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.getKeywordsTree().add(keyword);

            for (Article a : this.articleList.values()) {
                a.addKeyword(keyword);
            }
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements : %s !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }
    }

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
     */
    @Override
    public void addArticle(final String id, final int year, final String title)
            throws IllegalArgumentException {
        if (this.articleList.containsKey(id)) {
            throw new IllegalArgumentException("There already is a article with "
                    + "this id in this journal!");
        } else {
            this.articleList.put(id, new Article(id, title,
                    year, this.getKeywordsTree().descendingSet()));
        }
    }

    /**
     * Returns a article form this journal.
     *
     * @param id
     *         the id of the desired article.
     *
     * @return Optional containing the desired article.
     * Optional may be empty if there is no article with this id.
     */
    @Override
    public Optional<Article> getArticle(final String id) {
        return this.getArticles()
                .filter(article -> id.equals(article.getId()))
                .findAny();
    }

    @Override
    public Stream<Article> getArticles() {
        return this.articleList.values().stream();
    }
}
