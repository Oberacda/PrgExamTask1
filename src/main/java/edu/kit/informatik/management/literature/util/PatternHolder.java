package edu.kit.informatik.management.literature.util;

import java.util.regex.Pattern;

/**
 * Wrapper class for regex patterns used by the
 * literature management.
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public final class PatternHolder {

    /**
     * Regex Pattern for names.
     * <p>
     * Only the chars a-z and A-Z (excluding whitespaces)
     * with the minimum length of 1.
     * </p>
     */
    public static final Pattern NAMEPATTERN = Pattern.compile("[a-zA-Z]+");

    /**
     * Regex Pattern for keywords.
     * <p>
     * Only the chars a-z (excluding whitespaces)
     * with the minimum length of 1.
     * </p>
     */
    public static final Pattern KEYWORDPATTERN = Pattern.compile("[a-z]+");

    /**
     * Regex Pattern for article id's.
     * <p>
     * Only the chars a-z and 0-9 (excluding whitespaces)
     * with the minimum length of 1.
     * </p>
     */
    public static final Pattern IDPATTERN = Pattern.compile("[a-z0-9]+");

    /**
     * Regex Pattern for venue titles.
     * <p>
     * Only the chars a-z, A-Z and 0-9 (excluding whitespaces)
     * with the minimum length of 1.
     * </p>
     */
    public static final Pattern TITLEPATTERN = Pattern.compile("(([^,;]+)([\\u0020][^,;])*)");


    /**
     * Regex Pattern for years.
     * <p>
     * Only integers with {@code 1000 <= x <= 9999}
     * , are accepted.
     * </p>
     */
    public static final Pattern YEARPATTERN = Pattern.compile("([1-9][0-9][0-9][0-9])");

    /**
     * Regex Pattern for locations.
     * <p>
     * Only the chars a-z and A-Z (including one whitespace between to words)
     * with the minimum length of 1 are accepted.
     * </p>
     */
    public static final Pattern LOCATIONPATTERN = Pattern.compile("([^,;]+)([\\u0020][^;,]+)*");

    /**
     * Regex Pattern for article titles.
     * <p>
     * Only the chars a-z and A-Z (including one whitespace between to words)
     * with the minimum length of 1 are accepted.
     * </p>
     */
    public static final Pattern ARTICLETITLEPATTERN = Pattern.compile("([^,;]+)([\\u0020][^;,]+)*");

}
