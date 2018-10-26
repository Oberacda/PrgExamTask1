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
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add journal j,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal Name With Spaces ,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add journal n4me w/ symbols-.:*+~#'1234567890ß?ß\\\"\\t,p", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
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

    @Test
    public void invalidTest3() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add journal TSA,Pub,", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal TSA", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal TSA:Pub", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal TSA.Pub", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal TSA--", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add journal TSA,Pub;", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}