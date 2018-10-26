package edu.kit.informatik.test.management.literature.system.command.literatureIndex;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class DirectPrintJournalTest {
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
        Terminal.addSingleLineOutputThatIsExactly("direct print journal ieee:Edsger Dijkstra,,,Go To Statement Considered Harmful," +
                "Comm. of the ACM,1968","[1] E. Dijkstra, \"Go To Statement Considered Harmful,\" Comm. of the ACM, 1968.");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("direct print journal chicago:Edsger Dijkstra,,,Go To Statement Considered Harmful," +
                "Comm. of the ACM,1968","(Dijkstra, 1968) Dijkstra, Edsger. \"Go To Statement Considered Harmful.\" Comm. of the ACM (1968).");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    private static final String commandName = "direct print journal";

    @Test
    public void validInput() {
        String[][] ioPairs = {
                // official example
                {" ieee:Edsger Dijkstra,,,Go To Statement Considered Harmful,"
                        + "Comm. of the ACM,1968",
                        "[1] E. Dijkstra, \"Go To Statement Considered Harmful,\" Comm. of the ACM, 1968."
                },
                {" chicago:Edsger Dijkstra,,,Go To Statement Considered Harmful,"
                        + "Comm. of the ACM,1968",
                        "(Dijkstra, 1968) Dijkstra, Edsger. \"Go To Statement Considered Harmful.\" "
                                + "Comm. of the ACM (1968)."
                }
        };

        for (String[] ioPair : ioPairs)
            Terminal.addSingleLineOutputThatIsExactly(commandName
                    + ioPair[0], ioPair[1]);

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStyle() {
        String input = commandName + " ieeee:Edsger Dijkstra,,,Go To Statement Considered Harmful,"
                + "Comm. of the ACM,1968";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void noAuthors() {
        String input = commandName + " ieee:,,,Go To Statement Considered Harmful,"
                + "Comm. of the ACM,1968";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String input = commandName + " ieee,Edsger Dijkstra,,,Go To Statement Considered Harmful,"
                + "Comm. of the ACM,1968";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}