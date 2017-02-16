package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.management.literature.system.command.addCommand.AddAuthor;
import edu.kit.informatik.terminal.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * @author David Oberacker
 */
public class AddAuthorTest {
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
        Terminal.addSingleLineOutputThatIsExactly("add author David,Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Klug,Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author David,oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author klug,Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author klug,klexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Klug,alexander", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add author David,", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add author ,Oberacker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add author $,Oberacker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add author 0100,Oberacker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add author David, Oberacker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add author  David,Oberacker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add author David, Ober acker", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest2() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add author David,Oberacker", is("Ok"));
        Terminal.addSingleLineOutputThatMatches("add author David,Oberacker", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}