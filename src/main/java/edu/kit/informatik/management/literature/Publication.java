package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * A publication is a base element of the literature management.
 * <p>
 * All classes that represent something that can be published
 * should extend this class.
 * </p>
 * <p>
 * A publication always has to have a publisher managing it.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public abstract class Publication implements Entity {

    //=================fields==========================

    private final String id;
    private final String title;
    private final int year;
    private final LinkedHashSet<Author> authorList;
    private final LiteratureIndex literatureIndex;
    private final TreeSet<String> keywords;

    /**
     * Creates a new incomplete publication.
     * <p>
     * A incomplete publication has no authors assigned
     * to it. It always has a unique id and a title,
     * as well as a year. The keywords parameter specifies
     * the keywords the venue has the article is published with.
     * </p>
     * <b>No Parameter should be null!</b>
     *
     * @param id
     *         the unique id of the publication.
     * @param title
     *         the title of the publication.
     * @param year
     *         the year the publication was published.
     * @param keywords
     *         the keywords of the publisher which
     *         publishes the article
     */
    public Publication(final String id,
                       final String title,
                       final int year,
                       final SortedSet<String> keywords) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.authorList = new LinkedHashSet<>();
        this.literatureIndex = new LiteratureIndex();
        this.keywords = new TreeSet<>(keywords);
    }

    //=================getter===========================

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

    //=================methods==========================

    /**
     * Adds a author to the article.
     * <p>
     * This method can be called multiple times on an
     * article to add more than one author.
     * </p>
     *
     * @param author
     *         the instance of a author that should be added.
     * @throws edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException
     *         if the given author already wites this publication this exception is thrown.
     */
    public void addAuthor(Author author) throws ElementAlreadyPresentException {
        if (!this.authorList.contains(author)) {
            this.authorList.add(author);
        } else {
            throw new ElementAlreadyPresentException(String.format("the author \"%s\" already writes \"%s\"!"
                    , author.toString(), this.getId()));
        }

    }

    /**
     * Adds a article to the litratureindex of this article.
     * <p>
     * This action is only possible if the citedArticle's publication year,
     * is absolute before the publication year of this article.
     * </p>
     *
     * @param citedPublication
     *         Another publication which was published
     *         before this article.
     * @throws java.lang.IllegalArgumentException
     *         if the cited articles publication year isn`t absolute
     *         before this articles publication year this exception is thrown.
     */
    public void addCitation(final Publication citedPublication)
            throws IllegalArgumentException {
        if (this.literatureIndex.hasEntry(citedPublication)) {
            throw new IllegalArgumentException(String.format("\"%s\""
                    + " is already referenced by \"%s\"!", citedPublication.getId(), this.getId()));
        }
        if (citedPublication.getYear() < this.getYear()) {
            this.literatureIndex.addEntry(citedPublication);
        } else {
            throw new IllegalArgumentException(String.format("\"%s(%4d)\" wasn`t released before \"%s(%4d)\"!"
                    , citedPublication.getId(), citedPublication.getYear()
                    , this.getId(), this.getYear()));
        }
    }

    /**
     * Checks if the article is complete.
     * <p>
     * A article is complete if it has one or more authors.
     * </p>
     *
     * @return true - article is complete.
     */
    public boolean isComplete() {
        return !(this.authorList.isEmpty());
    }

    /**
     * Checks if this article cites another one.
     *
     * @param publication
     *         the article that should be checked if cited.
     * @return true - this article cites the other article.
     */
    public boolean cites(final Publication publication) {
        return this.literatureIndex.hasEntry(publication);
    }

    //=================override methods=================

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public Stream<String> getKeywords() {
        return this.keywords.stream();
    }


    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
