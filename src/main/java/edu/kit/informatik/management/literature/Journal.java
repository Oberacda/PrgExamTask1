package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class Journal extends Venue {

    private TreeMap<String, Article> articleList;

    public Journal(final String title) throws IllegalArgumentException {
        super(title);
        this.articleList = new TreeMap<>();
    }

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

            for (Article a:this.articleList.values()) {
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
     * While publishing a {@linkplain Article#Article(String, String, * int, SortedSet) incomplete} article is created.
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
    public void addArticle(final String id, final int year, final String title) throws IllegalArgumentException {

    }

    @Override
    public Stream<Article> getArticles() {
        return null;
    }
}