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
public class WrittenByTest {
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
    }

    @Before
    public void prepare2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add author a,a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author b,b", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference series s1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1001,l1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id5,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id6,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id7,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id8,1001,title", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id2,Christian Gruenhage;David Oberacker", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("written-by id5,a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id5,b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id6,a a;b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id7,b b;a a", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("written-by id1,Chrisssstian Gruenhage", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-by id1,Christian Gruenhage;David", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatMatches("written-by id1,Christian Gruenhage;David Oberacker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-by id1,Christian Gruenhage", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("written-by id5:a a", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-by id6,a a;", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-by id7,a a;b b;", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-by id7,a a;b ", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-by id7,a ;b b", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("written-By id8,a a", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}