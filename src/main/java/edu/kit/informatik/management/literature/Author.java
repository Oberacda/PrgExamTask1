package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;

import java.util.regex.Matcher;

/**
 * Modeling a author with a first and a last name.
 * <p>
 * A instance of a author is identified by the
 * combination of first and last name. This
 * identification is case sensitive for both
 * first and last name.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class Author implements Comparable<Author> {

    //=================fields==========================

    private final String firstName;
    private final String lastName;

    //=================constructor======================

    /**
     * Creates a new author with a first and last name.
     * <p>
     * first/last Name - Combination of chars without
     * special chars or whitespaces.
     * </p>
     * <b>No Parameter should be null!</b>
     *
     * @param firstName
     *         list name of the author.
     * @param lastName
     *         last name of the author.
     * @throws java.lang.IllegalArgumentException
     *         If the first or last name
     *         contain illegal chars (special chars or whitespaces) this
     *         exception is thrown.
     */
    public Author(final String firstName, final String lastName)
            throws IllegalArgumentException {

        Matcher firstNameMatcher = PatternHolder.NAMEPATTERN.matcher(firstName);
        Matcher lastNameMatcher = PatternHolder.NAMEPATTERN.matcher(lastName);

        if (firstNameMatcher.matches() && lastNameMatcher.matches()) {
            this.firstName = firstName;
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("First/last name containing illegal chars!");
        }
    }

    //=================getter===========================

    /**
     * Returns the first name of the author.
     * <p>
     * name is combination of the chars {@literal [a-zA-Z]}.
     * </p>
     *
     * @return first name of the author.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the last name of the author.
     * <p>
     * name is combination of the chars {@literal [a-zA-Z]}.
     * </p>
     *
     * @return last name of the author.
     */
    public String getLastName() {
        return this.lastName;
    }

    //=================override methods=================


    /**
     * {@inheritDoc}
     *
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * </p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * </p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * </p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * </p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     * </p>
     */
    @Override
    public int compareTo(final Author o) {
        if (this.getLastName().compareTo(o.getLastName()) == 0) {
            if (this.getFirstName().compareTo(o.getFirstName()) == 0) {
                return 0;
            } else {
                return this.getFirstName().compareTo(o.getFirstName());
            }
        } else {
            return this.getLastName().compareTo(o.getLastName());
        }
    }

    /**
     * {@inheritDoc}
     *
     * Returns a string representation of the author.
     * <p>
     * The format is {@code "<firstName> <lastName>"}.
     * (Without {@literal the <> and the "" chars}.
     * </p>
     */
    @Override
    public String toString() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }

    /**
     * {@inheritDoc}
     *
     * Checks if the other instance and this instance of
     * author identify the same author.
     * <p>
     * Two authors are equal if the first and last name
     * are the same (case sensitive)!
     * </p>
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
     * {@inheritDoc}
     *
     * Calculates the hashCode for this author instance.
     */
    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        return result;
    }
}
