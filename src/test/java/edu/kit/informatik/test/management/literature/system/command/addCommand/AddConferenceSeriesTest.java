package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * @author David Oberacker
 */
public class AddConferenceSeriesTest {
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
        Terminal.addSingleLineOutputThatIsExactly("add conference series TSA", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series tsa", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series Tsa", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series 01010", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series ta01", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        //Terminal.addSingleLineOutputThatIsExactly("add conference series s", "Ok");
        //Terminal.addSingleLineOutputThatIsExactly("add conference series S", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series series name with spaces and all that", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add conference series ,", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add conference series TSA", is("Ok"));
        Terminal.addSingleLineOutputThatMatches("add conference series TSA", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add conference series TSA,", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add Conference series TSA", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference series TSA;", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);

    }
}