package edu.kit.informatik.test.management.literature.system.command.literatureIndex;

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
public class BiblographyTest {
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

    @Before
    public void prepare() throws Exception {
        Terminal.addSingleLineOutputThatIsExactly("add conference series TSA", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference series IAA", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add journal CT,Pub", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add journal NYT,Pub", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add conference TSA,1995,Karlsruhe", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add conference IAA,1996,Genf", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to series TSA:id1,1995,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to series IAA:id2,1996,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add article to journal CT:id3,1994,Test Title", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add article to journal NYT:id4,1995,Test Title", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add author David,Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Klug,Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Christian,Gruenhage", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add author Test,Author", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("written-by id1,Christian Gruenhage;David Oberacker", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id2,Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id3,David Oberacker;Klug Alexander", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("written-by id4,Christian Gruenhage;David Oberacker", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("add keywords to series TSA:swt;reference;trivial", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to series IAA:swt;reference", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal CT:swt;testr", "Ok");
        Terminal.addSingleLineOutputThatIsExactly("add keywords to journal NYT:swt;testr", "Ok");

        Terminal.addSingleLineOutputThatIsExactly("cites id2,id4","Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id2,id3","Ok");
        Terminal.addSingleLineOutputThatIsExactly("cites id2,id1","Ok");
    }

    private static final String commandName = "print bibliography";

    @Before
    public void createArticles() {
        // official example
        String authorCommand = "add author";
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " Shashi,Afolabi","Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " Emiola,Lowry","Ok");
        Terminal.addSingleLineOutputThatIsExactly(authorCommand + " Richard,Rhinelander","Ok");

        String journalCommand = "add journal";
        Terminal.addSingleLineOutputThatIsExactly(journalCommand + " TSE,p","Ok");

        String seriesCommand = "add conference series";
        Terminal.addSingleLineOutputThatIsExactly(seriesCommand + " ICSA","Ok");

        String conferenceCommand = "add conference";
        Terminal.addSingleLineOutputThatIsExactly(conferenceCommand + " ICSA,2017,Gothenburg","Ok");

        String articleCommand = "add article to";
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal TSE:mvp2015,2015,Model Consistency","Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " journal TSE:mvp2016,2016,Better Model Consistency","Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series ICSA:rr2017,2017,Components still have no interfaces","Ok");
        Terminal.addSingleLineOutputThatIsExactly(articleCommand + " series ICSA:invalid,2017,title","Ok");

        String writtenByCommand = "written-by";
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " mvp2015,Shashi Afolabi;Richard Rhinelander","Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " mvp2016,Shashi Afolabi;Emiola Lowry","Ok");
        Terminal.addSingleLineOutputThatIsExactly(writtenByCommand + " rr2017,Richard Rhinelander","Ok");
    }



    @Test
    public void validTest1() throws Exception {
        Terminal.addMultipleLinesOutputThatIsExactly("print bibliography ieee:id1;id2;id3;id4"
                , "[1] K. Alexander, \"Test Title,\" in Proceedings of IAA, Genf, 1996."
                , "[2] C. Gruenhage and D. Oberacker, \"Test Title,\" in Proceedings of TSA, Karlsruhe, 1995."
                , "[3] C. Gruenhage and D. Oberacker, \"Test Title,\" NYT, 1995."
                , "[4] D. Oberacker and K. Alexander, \"Test Title,\" CT, 1994.");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validTest2() throws Exception {
        Terminal.addMultipleLinesOutputThatIsExactly("print bibliography chicago:id1;id2;id3;id4"
                , "(Alexander, 1996) Alexander, Klug. \"Test Title.\" Paper presented at IAA, 1996, Genf."
                , "(Gruenhage, 1995) Gruenhage, Christian, and Oberacker, David. \"Test Title.\" Paper presented at TSA, 1995, Karlsruhe."
                , "(Gruenhage, 1995) Gruenhage, Christian, and Oberacker, David. \"Test Title.\" NYT (1995)."
                , "(Oberacker, 1994) Oberacker, David, and Alexander, Klug. \"Test Title.\" CT (1994).");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
    @Test
    public void validTest3() throws Exception {
        Terminal.addMultipleLinesOutputThatIsExactly("print bibliography chicago:id1;id2;id1;id2"
                , "(Alexander, 1996) Alexander, Klug. \"Test Title.\" Paper presented at IAA, 1996, Genf."
                , "(Gruenhage, 1995) Gruenhage, Christian, and Oberacker, David. \"Test Title.\" Paper presented at TSA, 1995, Karlsruhe.");

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void validInput() {
        String[] outputIEEE = {
                "[1] S. Afolabi and E. Lowry, \"Better Model Consistency,\" TSE, 2016.",
                "[2] S. Afolabi and R. Rhinelander, \"Model Consistency,\" TSE, 2015.",
                "[3] R. Rhinelander, \"Components still have no interfaces,\" in Proceedings of ICSA, "
                        + "Gothenburg, 2017."
        };
        Terminal.addMultipleLinesOutputThatIsExactly(commandName + " ieee:mvp2016;mvp2015;rr2017", outputIEEE);
        String[] outputChicago = {
                "(Afolabi, 2016) Afolabi, Shashi, and Lowry, Emiola. \"Better Model Consistency.\" "
                        + "TSE (2016).",
                "(Afolabi, 2015) Afolabi, Shashi, and Rhinelander, Richard. \"Model Consistency.\" "
                        + "TSE (2015).",
                "(Rhinelander, 2017) Rhinelander, Richard. \"Components still have no interfaces.\" "
                        + "Paper presented at ICSA, 2017, Gothenburg."
        };
        Terminal.addMultipleLinesOutputThatIsExactly(commandName + " chicago:rr2017;mvp2016;mvp2015;rr2017", outputChicago);

        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStructure() {
        String[] input = {
                commandName + " ieee:mvp2015;",
                commandName + " ieee:mvp2015,mvp2016",
                "Print bibliography ieee:mvp2015;mvp2016",
        };

        for (String s : input)
            Terminal.addSingleLineOutputThatMatches(s, Matchers.startsWith("Error, "));


        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void invalidStyle() {
        String input = commandName + " ieeee:mvp2015";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));


        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }

    @Test
    public void noAuthors() {
        String input = commandName + " ieee:invalid";
        Terminal.addSingleLineOutputThatMatches(input, Matchers.startsWith("Error, "));


        Terminal.addSingleLineOutputThatIsExactly("quit", "Ok");
        LiteratureManagementSystem.main(NO_ARGS);
    }
}