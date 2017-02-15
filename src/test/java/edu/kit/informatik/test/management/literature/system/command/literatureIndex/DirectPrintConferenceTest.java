package edu.kit.informatik.test.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.literatureIndex.DirectPrintConference;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class DirectPrintConferenceTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test1() throws Exception {
        DirectPrintConference d = new DirectPrintConference();
        LiteratureManagement l = new LiteratureManagement();
        d.execute(l, "direct print conference ieee:Sergey Brin,Lawrence Page,,The Anatomy of " +
                "a Large Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998");

    }

    @Test
    public void test2() throws Exception {
        DirectPrintConference d = new DirectPrintConference();
        LiteratureManagement l = new LiteratureManagement();
        d.execute(l, "direct print conference chicago:Sergey Brin,Lawrence Page,,The Anatomy of " +
                "a Large Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998");

    }

    @Test
    public void test3() throws Exception {
        DirectPrintConference d = new DirectPrintConference();
        LiteratureManagement l = new LiteratureManagement();
        d.execute(l, "direct print conference lolol:Sergey Brin,Lawrence Page,,The Anatomy of " +
                "a Large Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998");

    }
}