package edu.kit.informatik.test.management.literature;

import edu.kit.informatik.management.literature.Conference;
import edu.kit.informatik.management.literature.ConferenceSeries;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class ConferenceSeriesTest {

    private ConferenceSeries c1;
    private ConferenceSeries c2;
    private ConferenceSeries c3;
    private ConferenceSeries c4;


    @Before
    public void setUp() throws Exception {
        c1 = new ConferenceSeries("ICSA");
        c2 = new ConferenceSeries("UEEA");
        c3 = new ConferenceSeries("laLAla");
        c4 = new ConferenceSeries("tests");
    }

    @Test(expected = NoSuchElementException.class)
    public void getConferenceInvalidTest1() throws Exception {
        c1.getConference(1997);
    }

    @Test(expected = NoSuchElementException.class)
    public void getConferenceInvalidTest2() throws Exception {
        c2.addConference(1997, "Karlsruhe");
        c2.addConference(1999, "Karlsruhe");
        c2.getConference(1998);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addConferenceInvalidTest1() throws Exception {
        c1.addConference(1997, "Karlsruhe");
        c1.addConference(1997, "Karlsruhe");
    }

    @Test
    public void getConferenceValidTest1() throws Exception {
        c2.addConference(1997, "Karlsruhe");
        c2.addConference(1999, "Karlsruhe");
        assertThat(c2.getConference(1997), isA(Conference.class));
        Conference c97 = c2.getConference(1997);
        assertThat(c97.getLocation(), is("Karlsruhe"));
        assertThat(c2.getConference(1999), isA(Conference.class));
    }

    @Test
    public void keywordsTest1() throws Exception {
        c1.addKeyword("TestKeyword");
        c1.addConference(1997, "München");
        c1.addConference(1998, "München");
        c1.addKeyword("TestKeyword2");
        c1.addConference(1999, "München");
        c1.getConference(1999).addArticle("idp1","TestArticle");
        c1.addKeyword("TestKeyword2");
        c1.addKeyword("TestKeyword3");
    }
}