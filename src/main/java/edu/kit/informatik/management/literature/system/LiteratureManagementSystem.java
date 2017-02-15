package edu.kit.informatik.management.literature.system;

import edu.kit.informatik.management.literature.Article;
import edu.kit.informatik.management.literature.Author;
import edu.kit.informatik.management.literature.Publishers;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.exceptions.ElementAlreadyPresentException;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.util.CommandUtil;
import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.CommandLoader;

import java.util.*;
import java.util.regex.Pattern;
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
}
