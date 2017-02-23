package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.Publication;
import edu.kit.informatik.management.literature.system.command.complexCommand.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for the complex commands of the Literature Management System.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class ComplexController extends Controller {

    /**
     * Creates a new Instance of the complex controller.
     *
     * @param literatureManagement
     *         the literature management the commands should operate on.
     */
    public ComplexController(final LiteratureManagement literatureManagement) {
        super(literatureManagement);

        super.addCommand(new CoauthorsOf(this));
        super.addCommand(new DirectHIndex(this));
        super.addCommand(new ForeignCitations(this));
        super.addCommand(new FindKeywords(this));
        super.addCommand(new HIndex(this));
        super.addCommand(new Jaccard(this));
        super.addCommand(new Similarity(this));
    }

    /**
     * Returns a stream of strings representing authors.
     * <p>
     * All author names in the resulting string have participated
     * in a publication alongside with the mentioned author.
     * </p>
     * <p>
     * The return stream may be empty if there are no coauthors.
     * </p>
     *
     * @param firstName
     *         the first name of the author you search the coauthors.
     * @param lastName
     *         the last name of the author you search the coauthors.
     *
     * @return stream of author names who are coauthors to the given author.
     *
     * @throws NoSuchElementException
     *         If the specified author doesn't exist
     *         this exception is thrown.
     */
    public Stream<String> coauthorsOf(final String firstName,
                                      final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = getLiteratureManagement().getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        TreeSet<Author> coAuthors = new TreeSet<>();

        getLiteratureManagement().getAllPublications()
                .filter(publication ->
                        publication.getAuthors()
                                .anyMatch(author::equals))
                .forEach(article -> article.getAuthors().forEach(coAuthors::add));
        coAuthors.remove(author);
        return coAuthors.stream().flatMap(author1 -> Stream.of(author1.toString()));
    }

    /**
     * Prints out the Hirsch-Index of the given integer list.
     * <p>
     * Every integer represents a publication and the value
     * of the integer represents the citation count of the publication.
     * </p>
     * <p>
     * See: {@link LiteratureManagement#calculateHIndex(Collection)}.
     * </p>
     *
     * @param publicationList
     *         a list of integers with the specified meaning.
     *
     * @return {@link LiteratureManagement#calculateHIndex(Collection) h-index}
     * of the integers.
     */
    public String directHIndexOf(List<Integer> publicationList) {
        return LiteratureManagement.calculateHIndex(publicationList);
    }

    /**
     * Returns a stream with the ids of every publication that has
     * all the specified keywords.
     *
     * @param keywordSet
     *         a set of keywords that all publications
     *         that are returned should have.
     *
     * @return ids of all publications with all the specified keywords.
     */
    public Stream<String> findKeywords(Set<String> keywordSet) {
        return getLiteratureManagement().getAllPublications()
                .filter(article -> article
                        .getKeywords()
                        .collect(Collectors.toSet())
                        .containsAll(keywordSet))
                .flatMap(article -> Stream.of(article.getId()));
    }

    /**
     * Returns a stream of all publications that fulfil the later specified requirements.
     * <p>
     * A publication is a foreign citation, if it cites a article the specified
     * author participated and no author that ever
     * cowrote with the specified author is participating in writing the publication.
     * </p>
     *
     * @param firstName
     *         the first name of the author you want to find the foreign citations.
     * @param lastName
     *         the last name of the author you want to find the foreign citations.
     *
     * @return the articles that are foreign citations of the author.
     *
     * @throws NoSuchElementException
     *         If the specified author doesn`t exist this
     *         exception is thrown.
     */
    public Stream<String> foreignCitations(final String firstName,
                                           final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = getLiteratureManagement()
                .getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        TreeSet<Author> coAuthors = new TreeSet<>();

        //Gets a coAuthors of the author
        getLiteratureManagement().getAllPublications().forEach(article -> article.getAuthors()
                .filter(author::equals).forEach(coAuthors::add));

        HashSet<String> articleSet = new HashSet<>();

        getLiteratureManagement().getAllPublications()
                // Find all article the author participated.
                .filter(article -> article.getAuthors().anyMatch(author::equals))
                .forEach(article -> getLiteratureManagement().getAllPublications()
                        //Find all article where no coauthor participated.
                        .filter(article1 -> article1.getAuthors().noneMatch(coAuthors::contains))
                        //Find all articles that cite one of the authors articles
                        .filter(article1 -> article1.cites(article))
                        .forEach(article1 -> articleSet.add(article1.getId())));

        return articleSet.stream();
    }

    /**
     * Returns a string containing the h-index of the author.
     * <p>
     * The h-index is calculated for all publications
     * the author participated.
     * </p>
     * <p>
     * See : {@link LiteratureManagement#calculateHIndex(Collection)}.
     * </p>
     *
     * @param firstName
     *         the first name of the author you want the h-index of.
     * @param lastName
     *         the last name of the author you want the h-index of.
     *
     * @return the h-index of a author.
     *
     * @throws NoSuchElementException
     *         If the specified author doesn`t exist this
     *         exception is thrown.
     */
    public String hIndex(final String firstName,
                         final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = getLiteratureManagement().getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        ArrayList<Integer> citationCount = new ArrayList<>();

        getLiteratureManagement().getAllPublications()
                .filter(article -> article.getAuthors().anyMatch(author::equals))
                .forEach(article -> {
                    Long citCount = getLiteratureManagement().getAllPublications().filter(article1 ->
                            article1.cites(article)).count();
                    citationCount.add(citCount.intValue());
                });
        return LiteratureManagement.calculateHIndex(citationCount);
    }

    /**
     * Returns the jaccard-index of two word sets.
     * <p>
     * See: {@link LiteratureManagement#calculateJaccard(Collection, Collection)}.
     * </p>
     * <p>
     * The result string always contains a floating point number,
     * that is cut after three floating points.
     * </p>
     *
     * @param wordSet1
     *         the first set of words.
     * @param wordSet2
     *         the second set of words.
     *
     * @return the jaccard index of the two sets (0.000-1.000).
     */
    public String jaccardIndex(final Set<String> wordSet1,
                               final Set<String> wordSet2) {
        return LiteratureManagement.calculateJaccard(wordSet1, wordSet2);
    }

    /**
     * Returns the similarity between two articles by calculating
     * the jaccard index for their keywords.
     * <p>
     * See: {@link LiteratureManagement#calculateJaccard(Collection, Collection)}.
     * </p>
     * <p>
     * The result string always contains a floating point number,
     * that is cut after three floating points.
     * </p>
     *
     * @param articleId1
     *         the first artilce.
     * @param articleId2
     *         the second article.
     *
     * @return the similarity between the artilces (0.000-1.000).
     *
     * @throws NoSuchElementException
     *         If one of the articles doesn't
     *         exist this exception is thrown.
     */
    public String similarity(final String articleId1,
                             final String articleId2)
            throws NoSuchElementException {
        Optional<Publication> publication1 = getLiteratureManagement()
                .getPublication(articleId1);
        if (!publication1.isPresent()) {
            throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId1));
        }
        Optional<Publication> publication2 = getLiteratureManagement()
                .getPublication(articleId2);
        if (!publication2.isPresent()) {
            throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId2));
        }
        Set<String> keySet1 = publication1.get().getKeywords().collect(Collectors.toSet());
        Set<String> keySet2 = publication2.get().getKeywords().collect(Collectors.toSet());

        return LiteratureManagement.calculateJaccard(keySet1, keySet2);
    }
}
