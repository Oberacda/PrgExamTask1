package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class Article implements Entity {

    //=================Fields==========================

    private final String id;
    private final String title;
    private final int year;
    private ArrayList<Author> authorList;
    private LiteratureIndex literatureIndex;
    private TreeSet<String> keywords;

    //=================Constructor======================

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
        this.id = id;
        this.title = title;
        this.year = year;
        this.authorList = new ArrayList<>();
        this.literatureIndex = new LiteratureIndex();
        this.keywords = new TreeSet<>(keywords);
    }

    //=================Getter===========================

    /**
     * Returns the year the article was published.
     * <p>
     * Integer between 1000 and 9999.
     * </p>
     *
     * @return the publication year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the unique id of the article.
     *
     * @return id of the article.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the title of the article.
     *
     * @return title of the article.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns a stream of all authors that participated in the
     * writing of the article.
     *
     * @return Stream of all authors.
     */
    public Stream<Author> getAuthors() {
        return this.authorList.stream();
    }

    //=================Methods==========================

    /**
     * Adds a author to the article.
     * <p>
     * This methode can be called multible times on an
     * article to add more than one author.
     * </p>
     *
     * @param author
     *         the instance of a author that should be added.
     */
    public void addAuthor(Author author) {
        this.authorList.add(author);
    }

    /**
     * Adds a article to the litratureindex of this article.
     * <p>
     * This action is only possible if the citedArticle's publication year,
     * is absolute before the publication year of this article.
     * </p>
     *
     * @param citedArticle
     *         Another article which was published
     *         before this article.
     */
    public void addCitation(final Article citedArticle) {
        this.literatureIndex.addCitation(citedArticle);
    }
    //=================Override Methods=================

    @Override
    public void addKeyword(final String keyword) throws IllegalArgumentException {
        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.keywords.add(keyword);
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements : %s !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }

    }

    @Override
    public Stream<String> getKeywords() {
        return this.keywords.stream();
    }

    @Override
    public String toString() {
        return String.format("%s:%4d-%s", this.getId(), this.getYear(), this.getTitle());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;

        return getId().equals(article.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
