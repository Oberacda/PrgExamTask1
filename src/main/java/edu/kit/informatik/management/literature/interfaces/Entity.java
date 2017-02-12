package edu.kit.informatik.management.literature.interfaces;

import java.util.SortedSet;

/**
 * @author David Oberacker
 */
public interface Entity {

    /**
     * Adds a keyword to the entity.
     * <p>
     *     a keyword is string only consisting of lowercase chars
     *     and no special chars or numbers.
     * </p>
     *
     * @param keyword the keyword that should be added.
     *
     * @throws IllegalArgumentException if the keyword that
     *          should be added does not match the requirements
     *          for keywords this exception is thrown.
     */
    void addKeyword(String keyword) throws IllegalArgumentException;

    /**
     * Returns a sorted set of all keywords of a entity.
     * <p>
     *     the keywords in the set have to be sorted by the
     *     natural order of chars!
     * </p>
     *
     * @return Sorted set of keywords.
     */
    SortedSet<String> getKeywords();
}
