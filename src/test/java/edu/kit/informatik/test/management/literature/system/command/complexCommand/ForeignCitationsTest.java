package edu.kit.informatik.test.management.literature.system.command.complexCommand;

import edu.kit.informatik.management.literature.system.LiteratureManagementSystem;
import edu.kit.informatik.Terminal;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class ForeignCitationsTest {

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

    private static final String commandName = "foreign citations of";

    @Before
    public void createArticles() {
        // official example
        String authorCommand = "add author";
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " a,a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " b,b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " c,c", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " d,d", "Ok");

        String journalCommand = "add journal";
        Terminal.addSingleLineOutputThatIsExactly(journalCommand + " j1,p", "Ok");

        String articleCommand = "add article to";
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:p0,1001,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:p1,1002,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:p2,1003,title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal j1:p3,1004,title", "Ok");

        String writtenByCommand = "written-by";
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " p0,a a", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " p1,a a;b b", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " p2,b b;c c", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " p3,c c;d d", "Ok");

        String citesCommand = "cites";
        Terminal.addSingleLineOutputThatIsExactly(citesCommand + " p1,p0", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(citesCommand + " p2,p0", "Ok");
        Terminal.addSingleLineOutputThatIsExactly(citesCommand + " p3,p0", "Ok");
    }

    @Test
    public void validInput() {
        Terminal.addSingleLineOutputThatIsExactly(commandName + " a a", "p3");
        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                commandName + " a a;",
                commandName + " a,a",
                "Foreign citations of a a",
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);}

    @Test
    public void unknownAuthor() {
        Terminal.addSingleLineOutputThatMatches(commandName + " z z", Matchers.startsWith("Error, "));

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);}

}