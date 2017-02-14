package edu.kit.informatik.management.literature.util;

import edu.kit.informatik.management.literature.*;
import edu.kit.informatik.management.literature.exceptions.BadSyntaxException;
import edu.kit.informatik.management.literature.interfaces.Entity;

import java.util.*;

/**
 * @author David Oberacker
 */
public final class CommandUtil {
    public static Venue getVenueFromPrefix(final LiteratureManagement lm,
                                                          final String userInput)
            throws NoSuchElementException, BadSyntaxException {
        if (PatternHolder.TOSERIESPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOSERIESPREFIX);
            Optional<ConferenceSeries> conferenceSeries = lm.getConferenceSeries(sc.next());
            if (conferenceSeries.isPresent())
                return conferenceSeries.get();
            else
                throw new NoSuchElementException("There is no conference series with this name!");
        } else if (PatternHolder.TOJOURNALPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOJOURNALPREFIX);
            Optional<Journal> journal = lm.getJournal(sc.next());
            if (journal.isPresent())
                return journal.get();
            else
                throw new NoSuchElementException("There is no journal with this name!");
        } else {
            throw new BadSyntaxException("Input doesnt match requred Patterns!");
        }
    }
    public static Entity getEntityFormPrefix(final LiteratureManagement lm, final String userInput)
            throws NoSuchElementException, BadSyntaxException {
        if (PatternHolder.TOPUBPATTERN.matcher(userInput).matches()) {
            Scanner sc = new Scanner(userInput);
            sc.skip(PatternHolder.TOPUBPREFIX);
            Optional<Article> article = lm.getArticle(sc.next());
            if (article.isPresent())
                return article.get();
            else
                throw new NoSuchElementException("There is no article with this id!");
        } else {
            return getVenueFromPrefix(lm, userInput);
        }
    }

    public static double calculateJaccard(final Collection<String> list1,
                                          final Collection<String> list2) {
        if (list1.size() == 0 && list2.size() == 0) {
            return 1;
        }
        TreeSet<String> unification = new TreeSet<>();
        unification.addAll(list1);
        unification.addAll(list2);

        TreeSet<String> intersection = new TreeSet<>();
        list1.stream().filter(list2::contains).forEach(intersection::add);

        return (double) intersection.size() / (double) unification.size();
    }
}
