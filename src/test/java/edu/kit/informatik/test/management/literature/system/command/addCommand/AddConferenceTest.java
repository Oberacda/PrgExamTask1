package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.Terminal;
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
        Terminal.addSingleLineOutputThatIsExactly("add conference series s1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series s2", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series s3", "Ok");
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
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1020,leer (ostfriesland)", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s2,9000,Genf", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s3,2522,Kalrstuhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1001,n4me w/ symbols-.:*+~#'1234567890ß?ß\\\"\\t", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series series", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series,1001,l1", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void yearRange() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,1000,l1", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference s1,9999,l1", "Ok");

        Terminal.addSingleLineOutputThatMatches("add conference s1,999,l1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference s1,10000,l1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference s1,0999,l1", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference s1,0000,l1", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidTest1() throws Exception {
        Terminal.addSingleLineOutputThatMatches("add conference TS A,1997,Karlsruhe", Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatMatches("add conference TSA,990,Karlsruhe", Matchers.startsWith("Error, "));
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