package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * @author David Oberacker
 */
public class AddArticleTest {

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
        Terminal.addSingleLineOutputThatIsExactly("add journal Ia:a,Pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1997,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference IAA,1996,Genf", "Ok");
    }

    @Before
    public void prepare2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series s1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series s2", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series s3", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add journal j1,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal j2,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal j3,p", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1001,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s2,1001,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s3,1001,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1002,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s2,1002,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s3,1002,Karlsruhe", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add article to series TSA:id1,1997,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series IAA:id2,1996,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series TSA:id3,1997,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add article to journal CT:id1,1997,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal NYT:id2,1997,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal CT:id3,1997,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal Ia:a:id4,1997,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id1,1001,n4me w/ symbols-.:*+~#'1234567890ß?ß\\\"\\t", "Ok");;

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add article to series TSA:id1,1997,Test Title", is("Ok"));
        Terminal.addSingleLineOutputThatMatches("add article to series TSA:id1,1997,Test Title", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add article to series USA:id1,1997,Test Title", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add article to journal CT:id1,1997,Test Title", is("Ok"));
        Terminal.addSingleLineOutputThatMatches("add article to journal CT:id1,1997,Test Title", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest4() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add article to journal TSA:id1,1997,Test Title", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest5() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add article to series s1,id1,1002,n", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add article to series s2;id1,1001,n", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add Article to series s2:id1,1002,n", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest6() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add article to series s1:id1-,1001,n", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add Article to series s1:ID1,1001,n", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);

    }
}