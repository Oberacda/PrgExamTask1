package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.Publication;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.complexCommand.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class ComplexController implements Controller {
    private HashSet<Command> complexCommands;

    private LiteratureManagement literatureManagement;

    public ComplexController(final LiteratureManagement literatureManagement) {
        this.literatureManagement = literatureManagement;
        this.complexCommands = new HashSet<>();

        complexCommands.add(new CoauthorsOf(this));
        complexCommands.add(new DirectHIndex(this));
        complexCommands.add(new ForeignCitations(this));
        complexCommands.add(new FindKeywords(this));
        complexCommands.add(new HIndex(this));
        complexCommands.add(new Jaccard(this));
        complexCommands.add(new Similarity(this));
    }

    @Override
    public boolean execute(final String userCommand) {
        return complexCommands.stream().anyMatch(command -> command.execute(userCommand));
    }

    public Stream<String> coauthorsOf(final String firstName,
                                      final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = this.literatureManagement.getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        TreeSet<Author> coAuthors = new TreeSet<>();

        this.literatureManagement.getAllPublications()
                .filter(publication ->
                        publication.getAuthors()
                                .anyMatch(author::equals))
                .forEach(article -> article.getAuthors().forEach(coAuthors::add));
        coAuthors.remove(author);
        return coAuthors.stream().flatMap(author1 -> Stream.of(author1.toString()));
    }

    public String directHIndexOf(List<Integer> publicationList) {
        return LiteratureManagement.calculateHIndex(publicationList);
    }

    public Stream<String> findKeywords(Set<String> keywordSet) {
        return this.literatureManagement.getAllPublications()
                .filter(article -> article
                        .getKeywords()
                        .collect(Collectors.toSet())
                        .containsAll(keywordSet))
                .flatMap(article -> Stream.of(article.getId()));
    }

    public Stream<String> foreignCitations(final String firstName,
                                           final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = this.literatureManagement
                .getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        TreeSet<Author> coAuthors = new TreeSet<>();

        //Gets a coAuthors of the author
        this.literatureManagement.getAllPublications().forEach(article -> article.getAuthors()
                .filter(author::equals).forEach(coAuthors::add));

        HashSet<String> articleSet = new HashSet<>();

        this.literatureManagement.getAllPublications()
                // Find all article the author participated.
                .filter(article -> article.getAuthors().anyMatch(author::equals))
                .forEach(article -> this.literatureManagement.getAllPublications()
                        //Find all article where no coauthor participated.
                        .filter(article1 -> article1.getAuthors().noneMatch(coAuthors::contains))
                        //Find all articles that cite one of the authors articles
                        .filter(article1 -> article1.cites(article))
                        .forEach(article1 -> articleSet.add(article1.getId())));

        return articleSet.stream();
    }

    public String hIndex(final String firstName,
                         final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = this.literatureManagement.getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        ArrayList<Integer> citationCount = new ArrayList<>();

        this.literatureManagement.getAllPublications()
                .filter(article -> article.getAuthors().anyMatch(author::equals))
                .forEach(article -> {
                    Long citCount = this.literatureManagement.getAllPublications().filter(article1 ->
                            article1.cites(article)).count();
                    citationCount.add(citCount.intValue());
                });
        return LiteratureManagement.calculateHIndex(citationCount);
    }

    public String jaccardIndex(final Set<String> wordSet1,
                               final Set<String> wordSet2) {
        return LiteratureManagement.calculateJaccard(wordSet1, wordSet2);
    }

    public String similarity(final String articleId1,
                             final String articleId2)
            throws NoSuchElementException {
        Optional<Publication> publication1 = this.literatureManagement
                .getPublication(articleId1);
        if (!publication1.isPresent()) {
            throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId1));
        }
        Optional<Publication> publication2 = this.literatureManagement
                .getPublication(articleId2);
        if (!publication2.isPresent()) {
            throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId2));
        }
        Set<String> keySet1 = publication1.get().getKeywords().collect(Collectors.toSet());
        Set<String> keySet2 = publication2.get().getKeywords().collect(Collectors.toSet());

        return LiteratureManagement.calculateJaccard(keySet1, keySet2);
    }
}
