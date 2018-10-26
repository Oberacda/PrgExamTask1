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
public class SimilarityTest {
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
        Terminal.addSingleLineOutputThatIsExactly("add article to series TSA:id5,1995,Test Title", "Ok");
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
        Terminal.addSingleLineOutputThatIsExactly("add conference series s1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add journal j2,p", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1001,l1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id6,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id7,1001,title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to journal j2:id8,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j2:id9,1001,title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add keywords to series s1:keysone", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to conference s1,1001:keycfromsonefirst", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to pub id6:keypone", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to pub id7:keyptwo", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to pub id8:keypfive;keypcombine", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to pub id9:keypsix;keypcombine", "Ok");
    }

    @Test
    public void validTest4() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("similarity id1,id5","1.000");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("similarity id1,id2","0.666");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("similarity id1,id4","0.250");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("similarity id3,id4","1.000");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest5() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("similarity id6,id8", "0.000");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest6() {
        Terminal.addSingleLineOutputThatIsExactly("similarity id8,id9", "0.333");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest7() {
        Terminal.addSingleLineOutputThatIsExactly("similarity id6,id7", "0.500");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("similarity id1,id2,id3", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("similarity id1;id2", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("Similarity id1,id2", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}