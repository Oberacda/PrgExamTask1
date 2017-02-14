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

    /**
     * Regex Pattern for venue titles.
     * <p>
     *     Only the chars a-z, A-Z and 0-9 (excluding whitespaces)
     *     with the minimum length of 1.
     * </p>
     */
    public static final Pattern TITLEPATTERN = Pattern.compile("[a-zA-Z0-9]+");


    public static final Pattern YEARPATTERN = Pattern.compile("([1-9][0-9][0-9][0-9])");


    public static final Pattern LOCATIONPATTERN = Pattern.compile("[a-zA-Z]+");

    public static final Pattern ARTICLETITLEPATTERN = Pattern.compile("\\w(\\w| (?! )){2,10}\\w");

    public static final Pattern TOSERIESPREFIX = Pattern.compile("to series ");

    public static final Pattern TOSERIESPATTERN = Pattern.compile(TOSERIESPREFIX.pattern()
            + TITLEPATTERN.pattern());

    public static final Pattern TOJOURNALPREFIX = Pattern.compile("to journal ");

    public static final Pattern TOJOURNALPATTERN = Pattern.compile(TOJOURNALPREFIX.pattern()
            + TITLEPATTERN.pattern());

    public static final Pattern TOVENUEPATTERN = Pattern.compile(String.format("%s|%s"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern()));

    public static final Pattern AUTHORPATTERN = Pattern.compile(NAMEPATTERN + " " + NAMEPATTERN);

    public static final Pattern AUTHORLISTPATTERN = Pattern.compile(String.format("%s([;]{1}[%s]{1})*"
            , AUTHORPATTERN, AUTHORPATTERN));

    public static final Pattern TOPUBPREFIX = Pattern.compile("to pub ");

    public static final Pattern TOPUBPATTERN = Pattern.compile(TOPUBPREFIX.pattern()
            + IDPATTERN.pattern());

    public static final Pattern TOENTITY = Pattern.compile(String.format("%s|%s|%s"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern(), TOPUBPATTERN.pattern()));

    public static final Pattern KEYWORDSPATTERN = Pattern.compile(String.format("%s([;]{1}[%s]{1})*"
            , KEYWORDPATTERN, KEYWORDPATTERN));
}
