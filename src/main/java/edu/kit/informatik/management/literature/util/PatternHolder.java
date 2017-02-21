package edu.kit.informatik.management.literature.util;

import java.util.regex.Pattern;

/**
 * Wrapper class for regex patterns used by the
 * literature management system.
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
    public static final Pattern TITLEPATTERN = Pattern.compile("([^,;]+)([\\u0020][^,;])*");


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
    public static final Pattern LOCATIONPATTERN = Pattern.compile("([a-zA-Z]+)([\\u0020][a-zA-Z]+)*");

    /**
     * Regex Pattern for article titles.
     * <p>
     * Only the chars a-z and A-Z (including one whitespace between to words)
     * with the minimum length of 1 are accepted.
     * </p>
     */
    public static final Pattern ARTICLETITLEPATTERN = Pattern.compile("([\\w]+)([\\u0020][\\w]+)*");

    /**
     * Regex Pattern for the to series prefix.
     */
    public static final Pattern TOSERIESPREFIX = Pattern.compile("to series ");

    /**
     * Regex Pattern for to series.
     */
    public static final Pattern TOSERIESPATTERN = Pattern.compile(TOSERIESPREFIX.pattern()
            + TITLEPATTERN.pattern());

    /**
     * Regex Pattern for the to journal prefix.
     */
    public static final Pattern TOJOURNALPREFIX = Pattern.compile("to journal ");

    /**
     * Regex Pattern for the to journal.
     */
    public static final Pattern TOJOURNALPATTERN = Pattern.compile(TOJOURNALPREFIX.pattern()
            + TITLEPATTERN.pattern());

    /**
     * Regex Pattern for to publisher.
     */
    public static final Pattern TOPUBLISHERPATTERN = Pattern.compile(String.format("%s|%s"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern()));

    /**
     * Regex Pattern for author names.
     */
    public static final Pattern AUTHORPATTERN = Pattern.compile(NAMEPATTERN + " " + NAMEPATTERN);

    /**
     * Regex Pattern for a list of authors with the delimiter {@literal ";"}.
     */
    public static final Pattern AUTHORLISTPATTERN = Pattern.compile(String.format("%s([;]{1}[%s]{1})*"
            , AUTHORPATTERN, AUTHORPATTERN));

    /**
     * Regex Pattern for to publication prefix.
     */
    public static final Pattern TOPUBPREFIX = Pattern.compile("to pub ");

    /**
     * Regex Pattern for to publication.
     */
    public static final Pattern TOPUBPATTERN = Pattern.compile(TOPUBPREFIX.pattern()
            + IDPATTERN.pattern());

    /**
     * Regex Pattern for to conference prefix.
     */
    public static final Pattern TOCONFERENCEPREFIX = Pattern.compile("to conference ");

    /**
     * Regex Pattern for to conference.
     */
    public static final Pattern TOCONFERENCEPATTERN = Pattern.compile(
            TOCONFERENCEPREFIX.pattern()
                    + TITLEPATTERN.pattern()
                    + ","
                    + YEARPATTERN);

    /**
     * Regex Pattern for to entity.
     */
    public static final Pattern TOENTITY = Pattern.compile(String.format("%s|%s|%s|%s"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern()
            , TOPUBPATTERN.pattern(), TOCONFERENCEPATTERN.pattern()));

    /**
     * Regex Pattern for a list of keywords.
     * <p>
     * Regex Pattern for a list of keywords with the delimiter {@literal ";"}.
     * </p>
     */
    public static final Pattern KEYWORDSPATTERN = Pattern.compile(String.format("%s([;]{1}[%s]{1})*"
            , KEYWORDPATTERN, KEYWORDPATTERN));
}
