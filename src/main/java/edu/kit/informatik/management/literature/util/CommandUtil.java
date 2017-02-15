package edu.kit.informatik.management.literature.util;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.interfaces.Entity;

import java.util.*;

/**
 * Class containing different uitilitys fo the
 * {@link edu.kit.informatik.management.literature.system.LiteratureManagementSystem}.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public final class CommandUtil {

    /**
     * Returns the the publisher specified in a command prefix.
     * <p>
     * <table>
     * <caption>examples:</caption>
     * <tr>
     * <td>
     * {@code ... to series TA -> Conferenceseries with the title TA}
     * </td>
     * </tr>
     * <tr>
     * <td>
     * {@code ... to journal TA -> Journal with the title TA}
     * </td>
     * </tr>
     * </table>
     *
     * @param lm
     *         LiteratureManagement where the publishers  are stored.
     * @param userInput
     *         the prefix entered by the user.
     *
     * @return if present the publisher specified by the prefix.
     *
     * @throws NoSuchElementException
     *         if there is no publisher with the
     *         specified title this eception is thrown.
     * @throws BadSyntaxException
     *         if there is a syntax error in the prefix
     *         this exception is thrown.
     */
    public static Publishers getPublisherFromPrefix(final LiteratureManagement lm,
                                                    final String userInput)
            throws NoSuchElementException, BadSyntaxException {
        if (PatternHolder.TOSERIESPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOSERIESPREFIX);
            Optional<ConferenceSeries> conferenceSeries = lm.getConferenceSeries(sc.next());
            if (conferenceSeries.isPresent()) {
                return conferenceSeries.get();
            } else {
                throw new NoSuchElementException("There is no conference series with this name!");
            }
        } else if (PatternHolder.TOJOURNALPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOJOURNALPREFIX);
            Optional<Journal> journal = lm.getJournal(sc.next());
            if (journal.isPresent()) {
                return journal.get();
            } else {
                throw new NoSuchElementException("There is no journal with this name!");
            }
        } else {
            throw new BadSyntaxException("Input doesnt match requred Patterns!");
        }
    }

    /**
     * Returns the the enitity specified in a command prefix.
     * <table>
     * <caption>examples:</caption>
     * <tr>
     * <td>
     * {@code ... to series TA -> Conferenceseries with the title TA}
     * </td>
     * </tr>
     * <tr>
     * <td>
     * {@code ... to journal TA -> Journal with the title TA}
     * </td>
     * </tr>
     * <tr>
     * <td>
     * {@code ... to conference TA,1997 -> Conference in the year 1997 }
     * </td>
     * </tr>
     * </table>
     *
     * @param lm
     *         LiteratureManagement where the publishers  are stored.
     * @param userInput
     *         the prefix entered by the user.
     *
     * @return if present the entity specified by the prefix.
     *
     * @throws NoSuchElementException
     *         if there is no publisher with the
     *         specified title this eception is thrown.
     * @throws BadSyntaxException
     *         if there is a syntax error in the prefix
     *         this exception is thrown.
     */
    public static Entity getEntityFormPrefix(final LiteratureManagement lm, final String userInput)
            throws NoSuchElementException, BadSyntaxException {
        if (PatternHolder.TOPUBPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOPUBPREFIX);
            Optional<Article> article = lm.getArticle(sc.next());
            if (article.isPresent()) {
                return article.get();
            } else {
                throw new NoSuchElementException("There is no article with this id!");
            }
        } else if (PatternHolder.TOPUBPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOSERIESPREFIX);
            sc.useDelimiter(",");
            String seriesName = sc.next(PatternHolder.TITLEPATTERN);
            int year = Integer.parseInt(sc.next(PatternHolder.YEARPATTERN));
            Optional<Conference> conference = lm.getConferenceFromSeries(seriesName, year);
            if (conference.isPresent()) {
                return conference.get();
            } else {
                throw new NoSuchElementException("There is no conference in this year!");
            }
        } else {
            return getPublisherFromPrefix(lm, userInput);
        }
    }
}
