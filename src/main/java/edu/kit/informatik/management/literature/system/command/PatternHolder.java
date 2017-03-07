package edu.kit.informatik.management.literature.system.command;

import java.util.regex.Pattern;

/**
 * Wrapper class for regex patterns used by the
 * literature management system command parsers.
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public final class PatternHolder {

    /**
     * Regex Pattern for the to series prefix.
     */
    public static final Pattern TOSERIESPREFIX = Pattern.compile("to series ");

    /**
     * Regex Pattern for to series.
     * <p>
     *     Pattern: {@literal "to series <conference series title>"}
     * </p>
     */
    public static final Pattern TOSERIESPATTERN = Pattern.compile(TOSERIESPREFIX.pattern()
            + edu.kit.informatik.management.literature.util.PatternHolder.TITLEPATTERN.pattern());

    /**
     * Regex Pattern for the to journal prefix.
     */
    public static final Pattern TOJOURNALPREFIX = Pattern.compile("to journal ");

    /**
     * Regex Pattern for the to journal.
     *  <p>
     *     Pattern: {@literal "to journal <journal title>"}
     * </p>
     */
    public static final Pattern TOJOURNALPATTERN = Pattern.compile(TOJOURNALPREFIX.pattern()
            + edu.kit.informatik.management.literature.util.PatternHolder.TITLEPATTERN.pattern());

    /**
     * Regex Pattern for to publisher.
     * <p>
     *     Pattern: {@literal "to series | to journal <publisher title>"}
     * </p>
     */
    public static final Pattern TOPUBLISHERPATTERN = Pattern.compile(String.format("%s:|%s:"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern()));

    /**
     * Regex Pattern for author names.
     * <p>
     *     Pattern: {@literal "<author first name> <author last name>"}
     * </p>
     */
    public static final Pattern AUTHORPATTERN
            = Pattern.compile(edu.kit.informatik.management.literature.util.PatternHolder.NAMEPATTERN
            + " " + edu.kit.informatik.management.literature.util.PatternHolder.NAMEPATTERN);

    /**
     * Regex Pattern for to publication prefix.
     */
    public static final Pattern TOPUBPREFIX = Pattern.compile("to pub ");

    /**
     * Regex Pattern for to publication.
     * <p>
     *     Pattern: {@literal "to pub <publication id>"}
     * </p>
     */
    public static final Pattern TOPUBPATTERN = Pattern.compile(TOPUBPREFIX.pattern()
            + edu.kit.informatik.management.literature.util.PatternHolder.IDPATTERN.pattern());

    /**
     * Regex Pattern for to conference prefix.
     */
    public static final Pattern TOCONFERENCEPREFIX = Pattern.compile("to conference ");

    /**
     * Regex Pattern for to conference.
     * <p>
     *     Pattern: {@literal "to conference <conference series title>,<year of the conference>"}
     * </p>
     * <p>
     *     year: {@link edu.kit.informatik.management.literature.util.PatternHolder#YEARPATTERN}
     * </p>
     * <p>
     *     title: {@link edu.kit.informatik.management.literature.util.PatternHolder#TITLEPATTERN}
     * </p>
     */
    public static final Pattern TOCONFERENCEPATTERN = Pattern.compile(
            TOCONFERENCEPREFIX.pattern()
                    + edu.kit.informatik.management.literature.util.PatternHolder.TITLEPATTERN.pattern()
                    + ","
                    + edu.kit.informatik.management.literature.util.PatternHolder.YEARPATTERN);

    /**
     * Regex Pattern for to entity.
     * <p>
     *     Pattern: {@literal "to conference <conference series title>,<year of the conference>
     *         | to publication <publication id>
     *         | to series <conference series title>
     *         | to journal <journal series title>"}
     * </p>
     */
    public static final Pattern TOENTITY = Pattern.compile(String.format("%s:|%s:|%s:|%s:"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern()
            , TOPUBPATTERN.pattern(), TOCONFERENCEPATTERN.pattern()));

    /**
     * Regex Pattern for a list of keywords.
     * <p>
     * Regex Pattern for a list of keywords with the delimiter {@literal ";"}.
     * </p>
     */
    public static final Pattern KEYWORDSPATTERN = Pattern.compile(String.format("%s([;]{1}[%s]{1})*"
            , edu.kit.informatik.management.literature.util.PatternHolder.KEYWORDPATTERN
            , edu.kit.informatik.management.literature.util.PatternHolder.KEYWORDPATTERN));
}
