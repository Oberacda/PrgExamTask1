package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * A journal is a publisher for articles.
 * <p>
 *     A journal can publish articles. It can publish
 *     multiple articles in a year and has no subordinate
 *     classes like a conference series.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class Journal extends Publishers {

    //=================fields==========================

    private final TreeMap<String, Publication> publicationMap;

    private final String publisher;

    //=================constructor======================

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
     * @throws java.lang.IllegalArgumentException
     *         If the title contains illegal
     *         chars this exception is thrown.
     */
    public Journal(final String title, final String publisher) throws IllegalArgumentException {
        super(title);
        this.publisher = publisher;
        this.publicationMap = new TreeMap<>();
    }

    //=================getter===========================

    /**
     * Returns the publisher of the journal.
     *
     * @return publisher.
     */
    public String getPublisher() {
        return this.publisher;
    }


    //=================override methods=================

    /**
     * {@inheritDoc}
     *
     * Adds a keyword to the entity.
     * <p>
     * a keyword is string only consisting of lowercase chars
     * and no special chars or numbers.
     * </p>
     */
    @Override
    public void addKeyword(final String keyword) throws IllegalArgumentException {

        if (PatternHolder.KEYWORDPATTERN.matcher(keyword).matches()) {
            this.getKeywordsTree().add(keyword);

            for (Publication publication : this.publicationMap.values()) {
                publication.addKeyword(keyword);
            }
        } else {
            throw new IllegalArgumentException(String.format("keyword does not match"
                            + " requirements : %s !",
                    PatternHolder.KEYWORDPATTERN.pattern()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * Publishes a article by the venue its called on.
     * <p>
     * While publishing a {@linkplain Article#Article(String, String,
     * int, java.util.SortedSet) incomplete} article is created.
     * </p>
     */
    @Override
    public void addArticle(final String id, final int year, final String title)
            throws IllegalArgumentException {
        if (this.publicationMap.containsKey(id)) {
            throw new IllegalArgumentException(String.format("article \"%s\" "
                    + "already present in the journal \"%s\"!", id, this.getTitle()));
        } else {
            this.publicationMap.put(id, new Article(id, title,
                    year, this.getKeywordsTree().descendingSet()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * Returns a publication form this journal.
     */
    @Override
    public Optional<Publication> getPublication(final String id) {
        return this.getPublications()
                .filter(publication -> id.equals(publication.getId()))
                .findAny();
    }

    /** {@inheritDoc} */
    @Override
    public Stream<Publication> getPublications() {
        return this.publicationMap.values().stream();
    }

    /**
     * {@inheritDoc}
     *
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by.
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
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return 31 * super.hashCode()
                + Journal.class.hashCode();
    }

    /**
     * {@inheritDoc}
     *
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
     * @see #hashCode()
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj) && (obj.getClass().equals(Journal.class));
    }
}
