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
public class DirectPrintConferenceTest {
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
        Terminal.addSingleLineOutputThatIsExactly("direct print conference ieee:Sergey Brin,Lawrence Page,,The Anatomy of" +
                " a Large-Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998","[1] S. Brin and L. Page, \"The Anatomy of a Large-Scale Hypertextual" +
                " Web Search Engine,\" in Proceedings of WWW, Brisbane Australia, 1998.");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("direct print conference chicago:Sergey Brin,Lawrence Page,,The Anatomy of" +
                " a Large-Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998",
                "(Brin, 1998) Brin, Sergey, and Page, Lawrence. \"The Anatomy of a Large-Scale Hypertextual" +
                " Web Search Engine.\" Paper presented at WWW, 1998, Brisbane Australia.");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }


    private static final String commandName = "direct print conference";

    @Test
    public void validInput() {
        String[][] ioPairs = {
                // official example
                {" ieee:Sergey Brin,Lawrence Page,,The Anatomy of a Large-Scale "
                        + "Hypertextual Web Search Engine,WWW,Brisbane Australia,1998",
                        "[1] S. Brin and L. Page, \"The Anatomy of a Large-Scale Hypertextual "
                                + "Web Search Engine,\" in Proceedings of WWW, Brisbane Australia, 1998."
                },
                {" chicago:Sergey Brin,Lawrence Page,,The Anatomy of a Large-Scale "
                        + "Hypertextual Web Search Engine,WWW,Brisbane Australia,1998",
                        "(Brin, 1998) Brin, Sergey, and Page, Lawrence. \"The Anatomy of a Large-Scale Hypertextual "
                                + "Web Search Engine.\" Paper presented at WWW, 1998, Brisbane Australia."
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
        String input = commandName + " ieeee:Sergey Brin,Lawrence Page,,The Anatomy of a Large-Scale "
                + "Hypertextual Web Search Engine,WWW,Brisbane Australia,1998";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void noAuthors() {
        String input = commandName + " ieee:,,,The Anatomy of a Large-Scale "
                + "Hypertextual Web Search Engine,WWW,Brisbane Australia,1998";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));


        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String input = commandName + " ieee,Sergey Brin,Lawrence Page,,The Anatomy of a Large-Scale "
                + "Hypertextual Web Search Engine,WWW,Brisbane Australia,1998";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}