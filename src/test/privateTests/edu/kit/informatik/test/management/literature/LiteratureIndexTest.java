package edu.kit.informatik.test.management.literature;

import edu.kit.informatik.management.literature.Article;
import edu.kit.informatik.management.literature.Author;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class LiteratureIndexTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addEntry() throws Exception {

    }

    @Test
    public void addAllEntrys() throws Exception {

    }

    @Test
    public void hasEntry() throws Exception {

    }

    @Test
    public void getArticles() throws Exception {

    }

    @Test
    public void printIndexInStyle() throws Exception {

    }

    @Test
    public void directPrintIndexInStyle() throws Exception {

    }

    @Test
    public void addCitation() throws Exception {
        Article a1 = new Article("id1", "Test", 1997, new TreeSet<>());
        Article a2 = new Article("id2", "Test", 1996, new TreeSet<>());
        Article a3 = new Article("id3", "Test", 1996, new TreeSet<>());
        Article a4 = new Article("id4", "Test", 1996, new TreeSet<>());
        Article a5 = new Article("id5", "Test", 1996, new TreeSet<>());
        a2.addAuthor(new Author("David", "Oberacker"));
        a2.addAuthor(new Author("Alex", "Klug"));
        a4.addAuthor(new Author("David", "Oberacker"));
        a4.addAuthor(new Author("Alex", "Klug"));
        a3.addAuthor(new Author("Anna", "Oberacker"));
        a5.addAuthor(new Author("David", "Oberacker"));
        a1.addCitation(a2);
        a1.addCitation(a3);
        a1.addCitation(a4);
        a1.addCitation(a5);
        System.out.println(a1.toString());
    }

}