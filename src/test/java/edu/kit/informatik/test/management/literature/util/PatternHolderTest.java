package edu.kit.informatik.test.management.literature.util;

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
}