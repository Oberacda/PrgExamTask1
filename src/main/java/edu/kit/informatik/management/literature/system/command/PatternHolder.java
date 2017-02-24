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
     */
    public static final Pattern TOSERIESPATTERN = Pattern.compile(TOSERIESPREFIX.pattern()
            + edu.kit.informatik.management.literature.util.PatternHolder.TITLEPATTERN.pattern());

    /**
     * Regex Pattern for the to journal prefix.
     */
    public static final Pattern TOJOURNALPREFIX = Pattern.compile("to journal ");

    /**
     * Regex Pattern for the to journal.
     */
    public static final Pattern TOJOURNALPATTERN = Pattern.compile(TOJOURNALPREFIX.pattern()
            + edu.kit.informatik.management.literature.util.PatternHolder.TITLEPATTERN.pattern());

    /**
     * Regex Pattern for to publisher.
     */
    public static final Pattern TOPUBLISHERPATTERN = Pattern.compile(String.format("%s|%s"
            , TOJOURNALPATTERN.pattern(), TOSERIESPATTERN.pattern()));

    /**
     * Regex Pattern for author names.
     */
    public static final Pattern AUTHORPATTERN =
            Pattern.compile(edu.kit.informatik.management.literature.util.PatternHolder.NAMEPATTERN
            + " " + edu.kit.informatik.management.literature.util.PatternHolder.NAMEPATTERN);

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
            + edu.kit.informatik.management.literature.util.PatternHolder.IDPATTERN.pattern());

    /**
     * Regex Pattern for to conference prefix.
     */
    public static final Pattern TOCONFERENCEPREFIX = Pattern.compile("to conference ");

    /**
     * Regex Pattern for to conference.
     */
    public static final Pattern TOCONFERENCEPATTERN = Pattern.compile(
            TOCONFERENCEPREFIX.pattern()
                    + edu.kit.informatik.management.literature.util.PatternHolder.TITLEPATTERN.pattern()
                    + ","
                    + edu.kit.informatik.management.literature.util.PatternHolder.YEARPATTERN);

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
            , edu.kit.informatik.management.literature.util.PatternHolder.KEYWORDPATTERN, edu.kit.informatik.management.literature.util.PatternHolder.KEYWORDPATTERN));
}
