package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.system.command.Command;
import edu.kit.informatik.management.literature.system.command.complexCommand.*;
import edu.kit.informatik.management.literature.system.command.literatureIndex.Bibliography;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintConference;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintJournal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Oberacker
 */
public class LiteratureIndexController implements Controller {

    private HashSet<Command> literatureIndexCommands;

    private LiteratureManagement literatureManagement;

    public LiteratureIndexController(final LiteratureManagement literatureManagement) {
        this.literatureManagement = literatureManagement;
        this.literatureIndexCommands = new HashSet<>();

        literatureIndexCommands.add(new DirectPrintConference(this));
        literatureIndexCommands.add(new DirectPrintJournal(this));
        literatureIndexCommands.add(new Bibliography(this));
    }

    @Override
    public boolean execute(final String userCommand) {
        return literatureIndexCommands.stream().anyMatch(command -> command.execute(userCommand));
    }

    public String directPrintConference(final String conferenceSeriesTitle,
                                        final String conferenceLocation,
                                        final int conferenceYear,
                                        final String articleTitle,
                                        final Set<String> authorList,
                                        final String style)
            throws NoSuchElementException {
        Article article = new Article("1", articleTitle, conferenceYear, new TreeSet<>());
        ConferenceSeries conferenceSeries = new ConferenceSeries(conferenceSeriesTitle);
        conferenceSeries.addConference(conferenceYear, conferenceLocation);
        for (String s : authorList) {
            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(" ");
            article.addAuthor(new Author(scanner.next(), scanner.next()));
        }
        return LiteratureIndex.directPrintIndexInStyle(style, article, conferenceSeries);
    }

    public String directPrintJournal(final String journalTitle,
                                     final int year,
                                     final String articleTitle,
                                     final Set<String> authorList,
                                     final String style)
            throws NoSuchElementException {
        Article article = new Article("1", articleTitle, year, new TreeSet<>());
        Journal journal = new Journal(journalTitle, "empty");
        for (String s : authorList) {
            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(" ");
            article.addAuthor(new Author(scanner.next(), scanner.next()));
        }
        return LiteratureIndex.directPrintIndexInStyle(style, article, journal);
    }

    public Stream<String> printBibliography(final String style,
                                            final Set<String> articleId)
            throws NoSuchElementException {
        Set<Optional<Publication>> optionalPublication = new HashSet<>();
        articleId.forEach(s -> optionalPublication.add(this.literatureManagement.getPublication(s)));
        if (!optionalPublication.stream().allMatch(Optional::isPresent)) {
            throw new NoSuchElementException("article not found!");
        }
        Set<Publication> publicationSet = optionalPublication.stream()
                .flatMap(article -> Stream.of(article.get()))
                .collect(Collectors.toSet());

        if (!publicationSet.stream().allMatch(Publication::isComplete)) {
            throw new NoSuchElementException("incomplete article found!");
        }

        LiteratureIndex bibliography = new LiteratureIndex();
        bibliography.addAllEntrys(publicationSet);

        return bibliography.printIndexInStyle(style, this.literatureManagement);
    }
}
