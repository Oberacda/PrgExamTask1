package edu.kit.informatik.test.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class FindKeywordsTest {
    private static final String[] NO_ARGS = {};

    @BeforeClass
    public static void enableTerminalTestingMode() {
        Terminal.enableTestingMode();
    }

    @After
    public void tearDown() throws Exception {
        Terminal.flush();
        Terminal.reset();
    }

    @Before
    public void prepare() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series TSA", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series IAA", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add journal CT,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal NYT,Pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1997,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference IAA,1996,Genf", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series TSA:id1,1997,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series IAA:id2,1996,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to journal CT:id3,1997,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal NYT:id4,1995,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add author David,Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Klug,Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Christian,Gruenhage", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id2,Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id3,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id4,Christian Gruenhage;David Oberacker", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:swt;reference;trivial", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series IAA:swt;reference", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal CT:swt;testr", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal NYT:swt;testr", "Ok");
    }

    @Before
    public void prepare2() throws Exception {
        String seriesCommand = "add conference series";
        Terminal.addSingleLineOutputThatIsExactly(seriesCommand + " s1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(seriesCommand + " s2", "Ok");

        String conferenceCommand = "add conference";
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " s1,1001,l1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " s1,1002,l1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " s2,1001,l1", "Ok");

        String journalCommand = "add journal";
        Terminal.addSingleLineOutputThatIsExactly(journalCommand + " j1,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(journalCommand + " j2,p", "Ok");

        String articleCommand = "add article to";
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s1:id5,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s1:id6,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s2:id7,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:id8,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j2:id9,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j2:id10,1001,title", "Ok");

        String addKeywordsToCommand = "add keywords to";
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " series s1:keysone", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " series s2:keystwo", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " conference s1,1001:keycfromsonefirst", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " conference s1,1002:keycfromsonesecond", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " conference s2,1001:keycfromstwo", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " journal j1:keyjone", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " journal j2:keyjtwo", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " pub id5:keypone", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " pub id5:keypcombine", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " pub id6:keyptwo", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " pub id6:keypcombine", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " pub id7:keypthree", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(addKeywordsToCommand + " pub id8:keypfour", "Ok");

    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("find keywords swt",
                Matchers.containsInAnyOrder("id1", "id2", "id3", "id4"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("find keywords swt;reference",
                Matchers.containsInAnyOrder("id1", "id2"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("find keywords swt;testr",
                Matchers.containsInAnyOrder("id3", "id4"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest4() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("find keywords swt;reference;trivial",
                Matchers.containsInAnyOrder("id1"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void byDirectPublicationKeyword() {
        Terminal.addSingleLineOutputThatIsExactly("find keywords keypone", "id5");
        Terminal.addSingleLineOutputThatIsExactly("find keywords keypone", "id5");
        Terminal.addSingleLineOutputThatIsExactly("find keywords keyptwo", "id6");
        Terminal.addSingleLineOutputThatIsExactly("find keywords keypthree", "id7");
        Terminal.addSingleLineOutputThatIsExactly("find keywords keypfour", "id8");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void byVenueKeyword() {
        Terminal.addSingleLineOutputThatIsExactly("find keywords keyjone", "id8");
        Terminal.addSingleLineOutputThatIsExactly("find keywords keycfromsonefirst", "id5");
        String[] publishedInJ2 = {
                "id9",
                "id10"
        };
        Terminal.addMultipleLineOutputThatMatches("find keywords keyjtwo",
                Matchers.containsInAnyOrder(publishedInJ2));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void bySeriesKeywords() {
        Terminal.addSingleLineOutputThatIsExactly("find keywords keystwo", "id7");
        String[] publishedInS1 = {
                "id5",
                "id6"
        };
        Terminal.addMultipleLineOutputThatMatches("find keywords keysone",
                Matchers.containsInAnyOrder(publishedInS1));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void requireAllKeywords() {
        Terminal.addNoOutput("find keywords keyjone;keyjtwo");
        Terminal.addSingleLineOutputThatIsExactly("find keywords keypone;keypcombine", "id5");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void unknownKeyword() {
        Terminal.addNoOutput("find keywords keyunknown");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                "find keywords keypone;",
                "find keywords keypone,keyptwo",
                "find Keywords keypone"
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}