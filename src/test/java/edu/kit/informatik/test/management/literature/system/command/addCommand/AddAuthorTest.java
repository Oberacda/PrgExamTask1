package edu.kit.informatik.test.management.literature.system.command.addCommand;

import edu.kit.informatik.management.literature.LiteratureManagement;
import edu.kit.informatik.management.literature.system.command.addCommand.AddAuthor;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class AddAuthorTest {
    @Test
    public void addTest1() throws Exception {
        LiteratureManagement l1 = new LiteratureManagement();
        AddAuthor a = new AddAuthor();
        a.execute(l1, "add author David,Oberacker");
        l1.toString();
        a.execute(l1, "add-author David,Oberacker");
        a.execute(l1, "add author ,Oberacker");
        a.execute(l1, "add author David,");
        a.execute(l1, "add author Alex,Klug");
        l1.toString();
    }
}