package edu.kit.informatik.test.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class DirectHIndexTest {
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
        Terminal.addSingleLineOutputThatIsExactly("direct h-index 17;3;1;5","3");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("direct h-index 8;6;8;4;8;6","5");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        String[][] ioPairs = {
                {" 17;3;1;5", "3"}, // official example
                {" 8;6;8;4;8;6", "5"}, // official example
                {" 1;1;1;1;1;1;1;1", "1"},
                {" 1;2;1;1;1", "1"},
                {" 9;9;9", "3"},
                {" 5;5;5;5;4", "4"},
                {" 1", "1"},
                {" 0;0;0;0", "0"}
        };

        for (String[] ioPair : ioPairs)
            Terminal.addSingleLineOutputThatIsExactly("direct h-index"
                    + ioPair[0], ioPair[1]);

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("direct h-index 1,1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("direct h-index 1;1;", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("direct h-index 1;;1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("direct H-Index 1;1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("direct h index 1;1", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}