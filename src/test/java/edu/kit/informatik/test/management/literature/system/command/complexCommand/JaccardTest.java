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
public class JaccardTest {
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
        Terminal.addSingleLineOutputThatIsExactly("jaccard a;b;c d;e","0.000");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("jaccard a;b;c;d;e b;c;d;e;f","0.666");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest3() throws Exception {
        String[][] ioPairs = {
                {" one;two;three one", "0.333"},
                {" one;two one", "0.500"},
                {" one two", "0.000"},
                {" one one", "1.000"},
                {" a;b;c;d;e b;c;d;e;f", "0.666"}, // official example
                {" a;b;c d;e", "0.000"} // official example
        };

        for (String[] ioPair : ioPairs)
            Terminal.addSingleLineOutputThatIsExactly("jaccard"
                    + ioPair[0], ioPair[1]);

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void noRounding() {
        Terminal.addSingleLineOutputThatIsExactly("jaccard one;two;three one;two", "0.666");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                "jaccard one,two one",
                "jaccard one;two,one",
                "Jaccard one one"
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidKeywordSyntax() {
        String[] input = {
                "jaccard ONE ONE",
                "jaccard one two2"
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}