package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.regex.Matcher;

/**
 * Modeling a author with a first and a last name.
 * <p>
 *     A instance of a author is identified by the
 *     combination of first and last name. This
 *     identification is case sensitive for both
 *     first and last name.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class Author {

    //=================Fields==========================

    private final String firstName;
    private final String lastName;

    //=================Constructor======================

    /**
     * Creates a new author with a first and last name.
     * <p>
     *     first/last Name - Combination of chars without
     *     special chars or whitespaces.
     * </p>
     * No Parameter should be null!
     * @param firstName lirst name of the author.
     * @param lastName last name of the author.
     *
     * @throws IllegalArgumentException If the first or last name
     *         contain illegal chars (special chars or whitespaces) this
     *         exception is thrown.
     * @throws NullPointerException this exception is thrown if a parameter
     *         is null.
     */
    public Author(final String firstName, final String lastName)
                  throws IllegalArgumentException {
        if (firstName == null || lastName == null)
            throw new NullPointerException("Names should not be null!");

        Matcher firstNameMatcher = PatternHolder.NAMEPATTERN.matcher(firstName);
        Matcher lastNameMatcher = PatternHolder.NAMEPATTERN.matcher(lastName);

        if (firstNameMatcher.matches() && lastNameMatcher.matches()) {
            this.firstName = firstName;
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("First/last name containing illegal chars!");
        }
    }

    //=================Getter===========================

    /**
     * Returns the first name of the author.
     * <p>
     *     name is combination of the chars {@literal [a-zA-Z]}.
     * </p>
     * @return first name of the author.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the last name of the author.
     * <p>
     *     name is combination of the chars {@literal [a-zA-Z]}.
     * </p>
     * @return last name of the author.
     */
    public String getLastName() {
        return this.lastName;
    }

    //=================Override Methods=================

    /**
     * Returns a string representation of the author.
     * <p>
     *     The format is {@code "<firstName> <lastName>"}.
     *     (Without {@literal the <> and the "" chars}.
     * </p>
     *
     * @return string representation of the author
     */
    @Override
    public String toString() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }

    /**
     * Checks if the other instance and this instance of
     * author identify the same author.
     * <p>
     *     Two authors are equal if the first and last name
     *     are the same (case sensitive)!
     * </p>
     *
     * @param o instance that should be checked.
     * @return true - this and o describe the same author.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        return getFirstName().equals(author.getFirstName())
                && getLastName().equals(author.getLastName());
    }

    /**
     * Calculates the hashCode for this author instance.
     *
     * @return hashCode of this.
     */
    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        return result;
    }
}
