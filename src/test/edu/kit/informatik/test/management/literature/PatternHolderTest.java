package edu.kit.informatik.test.management.literature;

import edu.kit.informatik.management.literature.util.PatternHolder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class PatternHolderTest {

    @Test
    public void namePatternTest1() throws Exception {
        assertTrue(PatternHolder.NAMEPATTERN.matcher("David").matches());
        assertTrue(PatternHolder.NAMEPATTERN.matcher("christian").matches());
        assertTrue(PatternHolder.NAMEPATTERN.matcher("Alexander").matches());

    }

    @Test
    public void namePatternTest2() throws Exception {
        assertFalse(PatternHolder.NAMEPATTERN.matcher("").matches());
        assertFalse(PatternHolder.NAMEPATTERN.matcher("00141").matches());
        assertFalse(PatternHolder.NAMEPATTERN.matcher("Alexa nder").matches());
    }

    @Test
    public void namePatternTest3() throws Exception {
        assertFalse(PatternHolder.NAMEPATTERN.matcher(" ").matches());
    }

    @Test
    public void yearPatternTest1() throws Exception {
        assertTrue(PatternHolder.YEARPATTERN.matcher("1000").matches());
        assertTrue(PatternHolder.YEARPATTERN.matcher("9999").matches());
        assertTrue(PatternHolder.YEARPATTERN.matcher("1509").matches());
        assertTrue(PatternHolder.YEARPATTERN.matcher("1997").matches());
        assertTrue(PatternHolder.YEARPATTERN.matcher("2014").matches());
    }

    @Test
    public void yearPatternTest2() throws Exception {
        assertFalse(PatternHolder.YEARPATTERN.matcher("999").matches());
        assertFalse(PatternHolder.YEARPATTERN.matcher("10000").matches());
        assertFalse(PatternHolder.YEARPATTERN.matcher("0").matches());
        assertFalse(PatternHolder.YEARPATTERN.matcher("").matches());
        assertFalse(PatternHolder.YEARPATTERN.matcher("99").matches());
    }

}