package edu.kit.informatik.test.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.*;

/**
 * @author David Oberacker
 */
public class CoauthorsOfTest {
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
        Terminal.addSingleLineOutputThatIsExactly("add author Test,Author", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id2,Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id3,David Oberacker;Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id4,Christian Gruenhage;David Oberacker", "Ok");
    }

    @Before
    public void prepare2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add journal j1,p", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id5,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id6,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id7,1003,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id8,1004,title", "Ok");


        Terminal.addSingleLineOutputThatIsExactly("add author a,a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author b,b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author c,c", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author d,d", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id5,a a;d d", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id6,a a;b b;c c", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id7,b b;a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id8,c c;a a", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("coauthors of David Oberacker",
                Matchers.containsInAnyOrder("Christian Gruenhage", "Klug Alexander"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("coauthors of Klug Alexander",
                Matchers.containsInAnyOrder("David Oberacker"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("coauthors of Christian Gruenhage",
                Matchers.containsInAnyOrder("David Oberacker"));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest4() throws Exception {
        Terminal.addMultipleLineOutputThatMatches("coauthors of Test Author",
                Matchers.containsInAnyOrder());

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest5() {
        // a -> b;c;d
        // b -> a;c
        // c -> a;b
        // d -> a
        String[] coAuthorsOfA = {
                "b b",
                "c c",
                "d d"
        };
        Terminal.addMultipleLineOutputThatMatches("coauthors of a a",
                Matchers.containsInAnyOrder(coAuthorsOfA));

        String[] coAuthorsOfB = {
                "a a",
                "c c"
        };
        Terminal.addMultipleLineOutputThatMatches("coauthors of b b",
                Matchers.containsInAnyOrder(coAuthorsOfB));

        String[] coAuthorsOfC = {
                "a a",
                "b b"
        };
        Terminal.addMultipleLineOutputThatMatches("coauthors of c c",
                Matchers.containsInAnyOrder(coAuthorsOfC));

        String[] coAuthorsOfD = {
                "a a",
        };
        Terminal.addMultipleLineOutputThatMatches("coauthors of d d",
                Matchers.containsInAnyOrder(coAuthorsOfD));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                "coauthors of a a;",
                "coauthors of a,a",
                "Coauthors of a a",
                "coauthors-of a a"
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void unknownAuthor() {
        Terminal.addSingleLineOutputThatMatches("coauthors of z z", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}