package edu.kit.informatik.management.literature.util;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.interfaces.Entity;
import edu.kit.informatik.management.literature.Publication;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class containing different utility's fo the
 * {@link edu.kit.informatik.management.literature.system.LiteratureManagementSystem}.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public final class CommandUtil {

    /**
     * Returns the the publisher specified in a command prefix.
     *
     * <table>
     * <caption>examples:</caption>
     * <tr>
     * <td>
     * {@code ... to series TA -> Conference Series with the title TA}
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
     *         specified title this exception is thrown.
     * @throws IllegalArgumentException
     *         if there is a syntax error in the prefix
     *         this exception is thrown.
     */
    public static Publishers getPublisherFromPrefix(final LiteratureManagement lm,
                                                    final String userInput)
            throws NoSuchElementException, IllegalArgumentException {
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
            throw new IllegalArgumentException("unsupported command pattern!");
        }
    }

    /**
     * Returns the the entity specified in a command prefix.
     * <table>
     * <caption>examples:</caption>
     * <tr>
     * <td>
     * {@code ... to series TA -> Conference Series with the title TA}
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
     *         specified title this exception is thrown.
     * @throws IllegalArgumentException
     *         if there is a syntax error in the prefix
     *         this exception is thrown.
     */
    public static Entity getEntityFormPrefix(final LiteratureManagement lm, final String userInput)
            throws NoSuchElementException, IllegalArgumentException {
        if (PatternHolder.TOPUBPATTERN.matcher(userInput).matches()) {

            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOPUBPREFIX);

            Optional<Publication> publication = lm.getPublication(sc.next());

            if (publication.isPresent()) {
                return publication.get();
            } else {
                throw new NoSuchElementException("there is no publication with this id!");
            }
        } else if (PatternHolder.TOCONFERENCEPATTERN.matcher(userInput).matches()) {

            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOCONFERENCEPREFIX);
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
