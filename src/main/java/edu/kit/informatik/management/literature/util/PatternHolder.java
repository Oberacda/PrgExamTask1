package edu.kit.informatik.management.literature.util;

import java.util.regex.Pattern;

/**
 * Wrapper class for regex patterns used by the
 * litrature management system.
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public final class PatternHolder {

    /**
     * Regex Pattern for names.
     * <p>
     *     Only the chars a-z and A-Z (excluding whitespaces)
     *     with the minimum length of 1.
     * </p>
     */
    public static final Pattern NAMEPATTERN = Pattern.compile("[a-zA-Z]+");

    /**
     * Regex Pattern for keywords.
     * <p>
     *     Only the chars a-z (excluding whitespaces)
     *     with the minimum length of 1.
     * </p>
     */
    public static final Pattern KEYWORDPATTERN = Pattern.compile("[a-z]+");

    /**
     * Regex Pattern for article id's.
     * <p>
     *     Only the chars a-z and 0-9 (excluding whitespaces)
     *     with the minimum length of 1.
     * </p>
     */
    public static final Pattern IDPATTERN = Pattern.compile("[a-z0-9]+");
}
