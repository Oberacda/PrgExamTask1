package edu.kit.informatik.management.literature.system.command.controller;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.system.command.literatureIndex.Bibliography;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintConference;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintJournal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A controller managing all literature index commands of the
 * {@link edu.kit.informatik.management.literature.system.LiteratureManagementSystem}.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class LiteratureIndexController extends Controller {

    /**
     * Creates a new controller for the literature index commands.
     *
     * @param literatureManagement
     *         the literature management the commands should work on.
     */
    public LiteratureIndexController(final LiteratureManagement literatureManagement) {
        super(literatureManagement);

        super.addCommand(new DirectPrintConference(this));
        super.addCommand(new DirectPrintJournal(this));
        super.addCommand(new Bibliography(this));
    }

    /**
     * Command to directly print out a article published by a
     * conference in a certain style.
     * <p>
     * For the styles see:
     * {@link edu.kit.informatik.management.literature.util.LiteratureIndexStyles}
     * </p>
     * <p>
     * While executing nothing is dependent to a {@link LiteratureManagement}.
     * </p>
     *
     * @param conferenceSeriesTitle
     *         the title of the conference.
     * @param conferenceLocation
     *         the location the conference took place.
     * @param conferenceYear
     *         the year the conference took place.
     * @param articleTitle
     *         the title of the aricle.
     * @param authorList
     *         a list of author names (max 3.).
     * @param style
     *         the output format
     *         ({@link edu.kit.informatik.management.literature.util.LiteratureIndexStyles}).
     *
     * @return a representation of the article published by a conference in a certain format.
     *
     * @throws IllegalArgumentException
     *         If either the output format ins unknown or the publisher is of a type
     *         that has no specified output style this exception is thrown.
     */
    public String directPrintConference(final String conferenceSeriesTitle,
                                        final String conferenceLocation,
                                        final int conferenceYear,
                                        final String articleTitle,
                                        final Set<String> authorList,
                                        final String style)
            throws IllegalArgumentException {
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

    /**
     * Command to directly print out a article published by a
     * journal in a certain style.
     * <p>
     * For the styles see:
     * {@link edu.kit.informatik.management.literature.util.LiteratureIndexStyles}
     * </p>
     * <p>
     * While executing nothing is dependent to a {@link LiteratureManagement}.
     * </p>
     *
     * @param journalTitle
     *         the title of the journal
     * @param year
     *         the year the article should be published.
     * @param articleTitle
     *         the title of the article.
     * @param authorList
     *         a
     *         list
     *         of author names (max 3.).
     * @param style
     *         the output format
     *         ({@link edu.kit.informatik.management.literature.util.LiteratureIndexStyles}).
     *
     * @return a representation of the article published by a conference in a certain format.
     *
     * @throws IllegalArgumentException
     *         If either the output format ins unknown or the publisher is of a type
     *         that has no specified output style this exception is thrown.
     */
    public String directPrintJournal(final String journalTitle,
                                     final int year,
                                     final String articleTitle,
                                     final Set<String> authorList,
                                     final String style)
            throws IllegalArgumentException {
        Article article = new Article("1", articleTitle, year, new TreeSet<>());
        Journal journal = new Journal(journalTitle, "empty");
        for (String s : authorList) {
            Scanner scanner = new Scanner(s);
            scanner.useDelimiter(" ");
            article.addAuthor(new Author(scanner.next(), scanner.next()));
        }
        return LiteratureIndex.directPrintIndexInStyle(style, article, journal);
    }

    /**
     * Methode to get the representation of a set of articles in a specified output format.
     * <p>
     * <p>
     * For the styles see:
     * {@link edu.kit.informatik.management.literature.util.LiteratureIndexStyles}
     * </p>
     * <p>
     * This method is dependent to a {@link LiteratureManagement}.
     * </p>
     *
     * @param style
     *         the output format
     *         ({@link edu.kit.informatik.management.literature.util.LiteratureIndexStyles}).
     * @param publicationId
     *         a list of publication ids.
     *
     * @return a stream of string representations.
     *
     * @throws IllegalArgumentException
     *         If either the output format ins unknown, the publisher is of a type
     *         that has no specified output style or one of the articles is
     *         incomplete this exception is thrown.
     */
    public Stream<String> printBibliography(final String style,
                                            final Set<String> publicationId)
            throws IllegalArgumentException {
        Set<Optional<Publication>> optionalPublication = new HashSet<>();
        publicationId.forEach(s -> optionalPublication.add(getLiteratureManagement().getPublication(s)));
        if (!optionalPublication.stream().allMatch(Optional::isPresent)) {
            throw new IllegalArgumentException("one publication not found!");
        }
        Set<Publication> publicationSet = optionalPublication.stream()
                .flatMap(article -> Stream.of(article.get()))
                .collect(Collectors.toSet());

        LiteratureIndex bibliography = new LiteratureIndex();
        bibliography.addAllEntrys(publicationSet);

        return bibliography.printIndexInStyle(style, getLiteratureManagement());
    }
}
