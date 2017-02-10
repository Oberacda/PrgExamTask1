package edu.kit.informatik.test;

import edu.kit.informatik.TestClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by oberacda on 30.01.2017.
 */
public class ClassTest {
    @Test
    public void testGetName() throws Exception {
        TestClass test = new TestClass("Test");
        assertThat("Test1", test.getTestMessage(), is("Test"));
    }

    @Test
    public void testSetName() throws Exception {
        TestClass test = new TestClass("Test");
        assertThat("Test1", test.getTestMessage(), is("Test"));
        test.setTestMessage("Test2");
        assertThat("Test2", test.getTestMessage(), is("Test2"));
    }
}