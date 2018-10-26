package edu.kit.informatik.test.management.literature.system.command.addCommand;

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
public class CitesTest {
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

    @Before
    public void prepare2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series s1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1001,l1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1002,l1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id11,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id12,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id13,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id14,1002,title", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("cites id1,id2", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id3,id2", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id2,id4", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id11", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id13", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id14,id11", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id14,id13", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("cites id2,id2", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id2,id1", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("cites id1,id2", "Ok");
        Terminal.addSingleLineOutputThatMatches("cites id1,id2", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("cites id12,", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id2,id1,", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id2;id3", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id4,id1 ", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("Cites id4,id3", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest4() throws Exception {
        Terminal.addSingleLineOutputThatMatches("cites id11,id12", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id11,id13", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id11,id14", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id13,id14", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest5() throws Exception {
        Terminal.addSingleLineOutputThatMatches("cites id1,id1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id2,id2", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id3,id3", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("cites id4,id4", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}