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
public class PublicationsByTest {
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
        Terminal.addSingleLineOutputThatIsExactly("written-by id3,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id2,Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id4,Christian Gruenhage;David Oberacker", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("publications by David Oberacker",
                Matchers.containsInAnyOrder("id1", "id4", "id3"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    private static final String commandName = "publications by";

    @Before
    public void createArticles() {
        String authorCommand = "add author";
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " a,a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " b,b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " c,c", "Ok");

        String journalCommand = "add journal";
        Terminal.addSingleLineOutputThatIsExactly(journalCommand + " j1,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(journalCommand + " j2,p", "Ok");

        String articleCommand = "add article to";
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:id5,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:id6,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:id7,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j2:id8,1002,title", "Ok");

        String writtenByCommand = "written-by";
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " id5,a a;b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " id6,a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " id6,c c", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " id7,a a;b b;c c", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " id8,c c", "Ok");
    }

    @Test
    public void validInput() {
        String[] writtenByA = {
                "id5",
                "id6",
                "id7"
        };
        Terminal.addMultipleLineOutputThatMatches(commandName + " a a",
                Matchers.containsInAnyOrder(writtenByA));

        String[] writtenByB = {
                "id5",
                "id7"
        };
        Terminal.addMultipleLineOutputThatMatches(commandName + " b b",
                Matchers.containsInAnyOrder(writtenByB));

        String[] writtenByC = {
                "id6",
                "id7",
                "id8"
        };
        Terminal.addMultipleLineOutputThatMatches(commandName + " c c",
                Matchers.containsInAnyOrder(writtenByC));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void onlyOneMatchRequired() {
        String[] writtenByAandC = {
                "id5",
                "id6",
                "id7",
                "id8"
        };
        Terminal.addMultipleLineOutputThatMatches(commandName + " a a;c c",
                Matchers.containsInAnyOrder(writtenByAandC));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                commandName + " a a;",
                commandName + " a a,b b",
                "Publications by a a"
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));


        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);}

    @Test
    public void unknownAuthor() {
        Terminal.addSingleLineOutputThatMatches(commandName + " id1,d d", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches(commandName + " id2,a a;b b;d d", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);}
}