package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.terminal.Terminal;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class AddConferenceTest {

    private static final String[] NO_ARGS = {};

    @BeforeClass
    public static void enableTerminalTestingMode() {
        Terminal.enableTestingMode();
    }

    @Before
    public void prepare() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series TSA", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series SAAA", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series tsa", "Ok");
    }

    @After
    public void tearDown() throws Exception {
        Terminal.flush();
        Terminal.reset();
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1997,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1996,Genf", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference tsa,1997,Kalrstuhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference SAAA,1874,Muenchen", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add conference TS A,1997,Karlsruhe", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference TSA,990,Karlsruhe", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference TSA,1997,Karls  ruhe", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1997,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatMatches("add conference TSA,1997,Karlsruhe", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add conference USA,1997,Karlsruhe", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}