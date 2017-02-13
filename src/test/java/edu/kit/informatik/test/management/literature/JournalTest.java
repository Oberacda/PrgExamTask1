package edu.kit.informatik.test.management.literature;

import edu.kit.informatik.management.literature.ConferenceSeries;
import edu.kit.informatik.management.literature.Journal;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class JournalTest {
    @Test
    public void hashCodeTest() throws Exception {
        Journal j1 = new Journal("IEEE", "Test");
        Journal j2 = new Journal("IEEEA", "Test");
        Journal j3 = new Journal("IEEE", "Test");
        assertTrue(j1.hashCode() == j1.hashCode());
        assertFalse(j1.hashCode() == j2.hashCode());
        assertFalse(j2.hashCode() == j3.hashCode());
        assertTrue(j3.hashCode() == j1.hashCode());
    }

    @Test
    public void equalsTest1() throws Exception {
        Journal j1 = new Journal("IEEE", "Test");
        Journal j2 = new Journal("IEEEA", "Test");
        Journal j3 = new Journal("IEEE", "Test");
        assertTrue(j1.equals(j1));
        assertFalse(j1.equals(j2));
        assertFalse(j2.equals(j3));
        assertTrue(j3.equals(j1));
    }
    @Test
    public void equalsTest2() throws Exception {
        Journal j1 = new Journal("IEEE", "Test");
        ConferenceSeries cs1 = new ConferenceSeries("IEEE");
        Journal j3 = new Journal("IEEE", "Test");
        assertTrue(j1.equals(j1));
        assertFalse(j1.equals(cs1));
        assertFalse(cs1.equals(j3));
        assertTrue(j3.equals(j1));
    }

}