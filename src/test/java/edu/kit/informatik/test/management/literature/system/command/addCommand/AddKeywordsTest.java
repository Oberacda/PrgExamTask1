package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.terminal.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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

        Terminal.addSingleLineOutputThatIsExactly("add journal CT,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal NYT,Pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1997,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference IAA,1996,Genf", "Ok");
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series IAA:test;testr;teste", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:testdrei", "Ok");

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
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add keywords to series TSA:testUU", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to series TSA:", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add keywords to series TSA:test;test,r;teste", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}