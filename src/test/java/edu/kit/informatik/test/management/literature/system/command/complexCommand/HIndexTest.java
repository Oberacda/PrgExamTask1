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
public class HIndexTest {
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

        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1995,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference IAA,1996,Genf", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series TSA:id1,1995,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series IAA:id2,1996,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to journal CT:id3,1994,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal NYT:id4,1995,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add author David,Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Klug,Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Christian,Gruenhage", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Test,Author", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id2,Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id3,David Oberacker;Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id4,Christian Gruenhage;David Oberacker", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:swt;reference;trivial", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series IAA:swt;reference", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal CT:swt;testr", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal NYT:swt;testr", "Ok");
    }

    @Before
    public void prepare2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add journal j1,pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id5,1001,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id6,1001,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id7,1003,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id8,1004,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id9,1005,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id10,1006,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id11,1007,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id12,1008,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add author a,a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author b,b", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id5,b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id6,b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id7,b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id8,b b", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id9,a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id10,a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id11,a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id12,a a", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("cites id12,id5", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id6", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id7", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id8", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id9", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id10", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id12,id11", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("cites id11,id5", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id11,id6", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id11,id7", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id11,id8", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("cites id10,id5", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id10,id6", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id10,id7", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id10,id8", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("cites id9,id5", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id9,id6", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id9,id7", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("h-index David Oberacker","0");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("cites id2,id4","Ok");
        Terminal.addSingleLineOutputThatIsExactly("h-index David Oberacker","1");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("cites id2,id3","Ok");
        Terminal.addSingleLineOutputThatIsExactly("h-index David Oberacker","1");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest4() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("cites id2,id1","Ok");
        Terminal.addSingleLineOutputThatIsExactly("h-index David Oberacker","1");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }


    @Test
    public void validTest5() throws Exception {
        // a a: 1;1;1;0
        //Terminal.addSingleLineOutputThatIsExactly("h-index a a", "1");
        // b b: 4;4;4;3
        Terminal.addSingleLineOutputThatIsExactly("h-index b b", "3");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("h-index a a;", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("h-index a,a", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatMatches("H-index a a", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("h index a a", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatMatches("h-index c c", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}