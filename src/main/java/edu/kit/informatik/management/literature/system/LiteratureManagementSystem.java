package edu.kit.informatik.management.literature.system;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.CommandUtil;
import edu.kit.informatik.management.literature.util.LiteratureIndexStyles;
import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.CommandLoader;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class LiteratureManagementSystem {

    //=================fields==========================

    private static final Pattern QUIT = Pattern.compile("quit");

    private final LiteratureManagement literatureManagement;

    private final Collection<Command> commandList;

    //=================constructor======================

    /**
     * Creates a new literature management system.
     * <p>
     * A new literature management system knows all
     * commands specified in
     * {@linkplain CommandLoader#loadCommands()}.
     * </p>
     */
    private LiteratureManagementSystem() {

        commandList = CommandLoader.loadCommands();
        this.literatureManagement = new LiteratureManagement();
    }

    //=================main method======================

    /**
     * The main method of the {@linkplain LiteratureManagementSystem}.
     *
     * @param args
     *         the command line arguments.
     */
    public static void main(String[] args) {
        LiteratureManagementSystem lms = new LiteratureManagementSystem();

        String userInput = Terminal.readLine();
        while (! (QUIT.matcher(userInput).matches())) {
            boolean result = false;
            for (Command c : lms.commandList) {
                if (c.execute(lms.literatureManagement, userInput)) {
                    result = true;
                    break;
                }
            }
            if (! result) {
                Terminal.printError("invalid syntax! no command detected!");
            }
            userInput = Terminal.readLine();
        }
        Terminal.printLine("Ok");
    }

    //=================add method=======================

    private void addArticle(final String articleId,
                            final int articleYear,
                            final String articleTitle,
                            final String publisherTitle)
            throws NoSuchElementException,
            BadSyntaxException,
            ElementAlreadyPresentException,
            IllegalArgumentException {
        Publishers publishers = CommandUtil.getPublisherFromPrefix(this.literatureManagement, publisherTitle);
        if (this.literatureManagement.hasArticle(articleId)) {
            throw new ElementAlreadyPresentException("There already is a article with this id!");
        }
        publishers.addArticle(articleId, articleYear, articleTitle);
    }

    private void addAuthor(final String firstName,
                           final String lastName)
            throws ElementAlreadyPresentException {
        this.literatureManagement.addAuthor(firstName, lastName);
    }

    private void addConference(final String conferenceSeriesTitle,
                               final int conferenceYear,
                               final String confernceLocation)
            throws ElementAlreadyPresentException,
            NoSuchElementException {
        this.literatureManagement.addConferenceToSeries(conferenceSeriesTitle, confernceLocation, conferenceYear);
    }

    private void addConferenceSeries(final String conferenceSeriesTitel)
            throws ElementAlreadyPresentException {
        this.literatureManagement.addConferenceSeries(conferenceSeriesTitel);
    }

    private void addJournal(final String journalTitel,
                            final String journalPublisher)
            throws ElementAlreadyPresentException {
        this.literatureManagement.addJournal(journalTitel, journalPublisher);
    }

    private void addKeywords(final String entityId,
                             final Set<String> keywords)
            throws BadSyntaxException{
        Entity e = CommandUtil.getEntityFormPrefix(this.literatureManagement, entityId);
        for (String keyword:keywords) {
            e.addKeyword(keyword);
        }
    }

    private void cites(final String articleId,
                       final String citedArticleId)
            throws IllegalArgumentException {
        if (!this.literatureManagement.hasArticle(articleId) || !this.literatureManagement.hasArticle(citedArticleId)) {
            throw new NoSuchElementException("There is no article with this id!");
        }

        Article article = this.literatureManagement.getArticle(articleId).get();
        Article citedArticle = this.literatureManagement.getArticle(citedArticleId).get();
        article.addCitation(citedArticle);
    }

    private void writtenBy(final String articleId,
                           final List<String> authorsList)
            throws NoSuchElementException{
        if (!this.literatureManagement.hasArticle(articleId)) {
            throw new NoSuchElementException("There is no article with this id!");
        }
        Article article = this.literatureManagement.getArticle(articleId).get();
        Stream<Author> authorStream = this.literatureManagement.getAuthors(authorsList);
        authorStream.forEach(article::addAuthor);
    }

    //=================get method=======================

    private Stream<String> allPublications() {
        return this.literatureManagement
                .getAllArticles()
                .flatMap(article -> Stream.of(article.getId()));
    }

    private Stream<String> inProceedings(final String conferenceSeries,
                                         final int year)
            throws NoSuchElementException {
        Optional<ConferenceSeries> c = this.literatureManagement.getConferenceSeries(conferenceSeries);
        if (c.isPresent()) {
            Optional<Conference> conference = c.get().getConference(year);
            if (conference.isPresent()) {
                return conference.get()
                        .getArticles()
                        .flatMap(article -> Stream.of(article.getId()));
            } else {
                throw new NoSuchElementException(String.format("conference"
                        + " in hte year \"%4d\" wasn't found!", year));
            }
        } else {
            throw new NoSuchElementException(String.format("conference with"
                    + " the title \"%s\" wasn`t found!", conferenceSeries));
        }
    }

    private Stream<String> listInvalidPublications() {
        return this.literatureManagement
                .getAllArticles()
                .filter(article -> !(article.isComplete()))
                .flatMap(article -> Stream.of(article.getId()));
    }

    private Stream<String> publicationsBy(final Set<String> authorSet) {
        Stream<Article> articleStream = this.literatureManagement.getAllArticles();
        Stream<Author> authorStream = this.literatureManagement.getAuthors(authorSet);
        return articleStream.filter(article -> article.getAuthors()
                .anyMatch(author -> authorStream.anyMatch(author::equals)))
                .flatMap(article -> Stream.of(article.getId()));
    }

    //=================complex method=====================

    private Stream<String> coauthorsOf(final String firstName,
                                       final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = this.literatureManagement.getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        TreeSet<Author> coAuthors = new TreeSet<>();

        this.literatureManagement.getAllArticles()
                .forEach(article -> article.getAuthors()
                .filter(author::equals).forEach(coAuthors::add));
        coAuthors.remove(author);
        return coAuthors.stream().flatMap(author1 -> Stream.of(author1.toString()));
    }

    private String directHIndexOf(List<Integer> publicationList) {
        return LiteratureManagement.calculateHIndex(publicationList);
    }

    private Stream<String> findKeywords(Set<String> keywordSet) {
        return this.literatureManagement.getAllArticles()
                .filter(article -> article.getKeywords()
                .allMatch(keywordSet::contains))
                .flatMap(article -> Stream.of(article.getId()));
    }

    private Stream<String> foreiginCitations(final String firstName,
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
        this.literatureManagement.getAllArticles().forEach(article -> article.getAuthors()
                .filter(author::equals).forEach(coAuthors::add));

        HashSet<String> articleSet = new HashSet<>();

        this.literatureManagement.getAllArticles()
                // Find all article the author paricipated.
                .filter(article -> article.getAuthors().anyMatch(author::equals))
                .forEach(article -> this.literatureManagement.getAllArticles()
                        //Find all article where no coauthor participated.
                        .filter(article1 -> article1.getAuthors().noneMatch(coAuthors::contains))
                        //Find all articles that cite one of the authors articles
                        .filter(article1 -> article1.cites(article))
                        .forEach(article1 -> articleSet.add(article1.getId())));

        return articleSet.stream();
    }

    private String hIndex(final String firstName,
                          final String lastName)
            throws NoSuchElementException {
        Optional<Author> authorOptional = this.literatureManagement.getAuthor(firstName, lastName);
        if (!authorOptional.isPresent()) {
            throw new NoSuchElementException(String.format("author \"%s\" wasn't found!",
                    authorOptional.toString()));
        }
        Author author = authorOptional.get();

        ArrayList<Integer> citationCount = new ArrayList<>();

        this.literatureManagement.getAllArticles()
                .filter(article -> article.getAuthors().anyMatch(author::equals))
                .forEach(article -> {
                    Long citCount = this.literatureManagement.getAllArticles().filter(article1 ->
                            article1.cites(article)).count();
                    citationCount.add(citCount.intValue());
                });
        return LiteratureManagement.calculateHIndex(citationCount);
    }

    private String jaccardIndex(final Set<String> wordSet1,
                                final Set<String> wordSet2) {
         return LiteratureManagement.calculateJaccard(wordSet1, wordSet2);
    }

    private String simiarity(final String articleId1,
                             final String articleId2)
            throws NoSuchElementException {
        Optional<Article> article1 = this.literatureManagement
                .getArticle(articleId1);
        if (!article1.isPresent()) {
            throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId1));
        }
        Optional<Article> article2 = this.literatureManagement
                .getArticle(articleId2);
        if (!article2.isPresent()) {
            throw new NoSuchElementException(String.format("article \"%s\" not found!", articleId2));
        }
        Set<String> keySet1 = article1.get().getKeywords().collect(Collectors.toSet());
        Set<String> keySet2 = article2.get().getKeywords().collect(Collectors.toSet());

        return LiteratureManagement.calculateJaccard(keySet1, keySet2);
    }

    //=================literature index method===============

    private String directPrintConference(final String conferenceSeriesTitle,
                                         final String conferenceLocation,
                                         final int conferenceYear,
                                         final String articleTitle,
                                         final Set<String> authorList,
                                         final String style)
            throws NoSuchElementException {
        Article article = new Article("1", articleTitle, conferenceYear, new TreeSet<>());
        ConferenceSeries conferenceSeries = new ConferenceSeries(conferenceSeriesTitle);
        conferenceSeries.addConference(conferenceYear, conferenceLocation);
        for (String s:authorList) {
            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(" ");
            article.addAuthor(new Author(scanner.next(), scanner.next()));
        }
        return LiteratureIndex.directPrintIndexInStyle(style, article, conferenceSeries);
    }

    private String directPrintJournal (final String journalTitle,
                                       final int year,
                                       final String articleTitle,
                                       final Set<String> authorList,
                                       final String style)
            throws NoSuchElementException {
        Article article = new Article("1", articleTitle, year, new TreeSet<>());
        Journal journal = new Journal(journalTitle, "empty");
        for (String s:authorList) {
            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(" ");
            article.addAuthor(new Author(scanner.next(), scanner.next()));
        }
        return LiteratureIndex.directPrintIndexInStyle(style, article, journal);
    }

    private Stream<String> printBibliography(final String style,
                                             final Set<String> articleId)
            throws NoSuchElementException {
        Set<Optional<Article>> optionalArticle = new HashSet<>();
        articleId.forEach(s -> optionalArticle.add(this.literatureManagement.getArticle(s)));
        if(! optionalArticle.stream().allMatch(Optional::isPresent)) {
            throw new NoSuchElementException("article not found!");
        }
        Set<Article> articleSet = optionalArticle.stream()
                .flatMap(article -> Stream.of(article.get()))
                .collect(Collectors.toSet());

        if(! articleSet.stream().allMatch(Article::isComplete)) {
            throw new NoSuchElementException("incomplete article found!");
        }

        LiteratureIndex bibiliography = new LiteratureIndex();
        bibiliography.addAllEntrys(articleSet);

        return bibiliography.printIndexInStyle(style, this.literatureManagement);
    }
}
