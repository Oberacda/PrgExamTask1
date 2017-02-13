package edu.kit.informatik.test.management.literature;

import edu.kit.informatik.management.literature.Author;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class AuthorTest {

    private Author a1;
    private Author a2;
    private Author a3;
    private Author a4;
    private Author a5;
    private Author a6;
    private Author a7;

    @Before
    public void setUp() throws Exception {
        a1 = new Author("David", "Oberacker");
        a2 = new Author("Alexander", "klug");
        a3 = new Author("christian", "gruenhage");
        a4 = new Author("rene", "Schmied");
        a5 = new Author("Test", "Test");
        a6 = new Author("Anna", "Oberacker");
        a7 = new Author("Alexander", "Klug");
    }

    @Test
    public void validConstructorTest() throws Exception {
        a1 = new Author("David", "Oberacker");
        a2 = new Author("Alexander", "klug");
        a3 = new Author("christian", "gruenhage");
        a4 = new Author("rene", "Schmied");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructurTest1() throws Exception {
        a1 = new Author("4412312", "redne");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructurTest2() throws Exception {
        a2 = new Author("redne", "4412312");
    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void invalidConstructorTest3() throws Exception {
        a3 = new Author(null, null);
    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void invalidConstructorTest4() throws Exception {
        a4 = new Author(null, "test");
    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void invalidConstructorTest5() throws Exception {
        a5 = new Author("test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructorTest6() throws Exception {
        a6 = new Author("", "David");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructurTest7() throws Exception {
        a7 = new Author("David", "");
    }

    @Test
    public void getFirstName() throws Exception {
        assertThat("First name is David", a1.getFirstName(), is("David"));
        assertThat("First name is Alexander", a2.getFirstName(), is("Alexander"));
        assertThat("First name is christian", a3.getFirstName(), is("christian"));
        assertThat("First name is rene", a4.getFirstName(), is("rene"));
    }

    @Test
    public void getLastName() throws Exception {
        assertThat("last name is Oberacker", a1.getLastName(), is("Oberacker"));
        assertThat("last name is klug", a2.getLastName(), is("klug"));
        assertThat("last name is gruenhage", a3.getLastName(), is("gruenhage"));
        assertThat("last name is Schmied", a4.getLastName(), is("Schmied"));
    }

    @Test
    public void toStringTest() throws Exception {
        assertThat(a1.toString(), is("David Oberacker"));

    }

    @Test
    public void equals() throws Exception {
        assertTrue(a1.equals(a1));
        assertFalse(a1.equals(null));
        assertFalse(a1.equals(new Object()));
        assertFalse(a1.equals(a6));
        assertFalse(a1.equals(a2));
        assertFalse(a2.equals(a7));
    }

    @Test
    public void compareTest() throws Exception {
        assertTrue(a1.compareTo(a2) < 0);
        assertTrue(a2.compareTo(a1) > 0);
        assertTrue(a1.compareTo(a1) == 0);
        assertTrue(a1.compareTo(a6) > 0);
    }

    @Test
    public void hashCodeTest() throws Exception {
        assertTrue(Integer.compare(a1.hashCode(), a1.hashCode()) == 0);
    }
}