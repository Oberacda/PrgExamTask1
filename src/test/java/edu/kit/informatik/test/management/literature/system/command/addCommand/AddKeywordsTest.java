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
public class AddKeywordsTest {
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
        Terminal.addSingleLineOutputThatIsExactly("add conference series Ia:a", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add journal CT,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal NYT,Pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1997,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference IAA,1996,Genf", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference Ia:a,1996,Genf", "Ok");
    }

    @Before
    public void prepare2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series s1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add journal j1,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal j2,p", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1001,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1002,Karlsruhe", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id5,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series s1:id6,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j1:id7,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal j2:id8,1002,title", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series IAA:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:testdrei", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series Ia:a:test;testr;teste", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal CT:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal NYT:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal CT:testdrei", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add keywords to conference TSA,1997:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to conference IAA,1996:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to conference TSA,1997:testtestdrei", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest4() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add keywords to pub id5:kone;ktwo;kthree;kfour", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series s1:kone;ktwo;kthree;kfour", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal j1:kone", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to conference s1,1001:kone;ktwo", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add keywords to series TSA:testUU", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to series TSA:", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to series TSA:test;test,r;teste", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add keywords to conference s1:kone;ktwo", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to id5:kone;ktwo", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to series :kone;ktwo", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to pub id5:kone;", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to pub id5:ktwo,kthree", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to author a,a:kone;ktwo", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add Keywords to pub id1;kfour", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add keywords to pub id5:one1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to pub id5:ONE", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}