package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.HashMap;
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

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     *
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return 31 * super.hashCode()
                + Journal.class.hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj
     *         the reference object with which to compare.
     *
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     *
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj) && (obj.getClass().equals(Journal.class));
    }
}
