package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
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
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        Terminal.flush();
        Terminal.reset();
    }

    @Test
    public void validTest1() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series TSA", "OK");
        Terminal.addSingleLineOutputThatIsExactly("add conference series ISAA", "OK");
        Terminal.addSingleLineOutputThatIsExactly("add conference series QpA", "OK");
        Terminal.addSingleLineOutputThatIsExactly("add conference series LOLOLOL", "OK");
        Terminal.addSingleLineOutputThatIsExactly("add conference series tsa", "OK");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}