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
public class AddJournalTest {
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

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add journal TSA,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal tsa,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal Tsa,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal 01010,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal ta01,Pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add journal TS A,Pub", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal &%,Pub", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal ,", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add journal TSA,Pub", is("Ok"));
        Terminal.addSingleLineOutputThatMatches("add journal TSA,Pub", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}