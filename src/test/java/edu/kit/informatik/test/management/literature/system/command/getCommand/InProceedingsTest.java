package edu.kit.informatik.test.management.literature.system.command.getCommand;

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
public class InProceedingsTest {
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
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("in proceedings TSA,1997",
                Matchers.containsInAnyOrder("id1"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("in proceedings IAA,1996",
                Matchers.containsInAnyOrder("id2"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    private static final String commandName = "in proceedings";

    @Before
    public void createArticles() {
        String seriesCommand = "add conference series";
        Terminal.addSingleLineOutputThatIsExactly(seriesCommand + " s1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(seriesCommand + " s2", "Ok");

        String conferenceCommand = "add conference";
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " s1,1001,l1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " s1,1002,l1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " s2,1001,l1", "Ok");

        String articleCommand = "add article to";
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s1:id5,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s1:id6,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s2:id7,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series s2:id8,1001,title", "Ok");
    }

    @Test
    public void validInput() {
        Terminal.addSingleLineOutputThatIsExactly(commandName + " s1,1001", "id5");
        Terminal.addSingleLineOutputThatIsExactly(commandName + " s1,1002", "id6");

        String[] publishedIn1001S2 = {
                "id7",
                "id8"
        };
        Terminal.addMultipleLineOutputThatMatches(commandName + " s2,1001",
                Matchers.containsInAnyOrder(publishedIn1001S2));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                commandName + " s1;1001",
                commandName + " s1,1001,",
                commandName + " s1",
                commandName + " s1:1001",
                "in Proceedings s1,1001"
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void unknownSeries() {
        Terminal.addSingleLineOutputThatMatches(commandName + " s4,1001", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void noConferenceInYear() {
        Terminal.addSingleLineOutputThatMatches(commandName + " s1,1009", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}