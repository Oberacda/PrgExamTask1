package edu.kit.informatik.management.literature;

import java.util.LinkedList;
import java.util.List;

/**
 * @author David Oberacker
 */
public class Article {
    private final String id;
    private final String title;
    private final int year;
    private List<Author> authorList;
    private LiteratureIndex literatureIndex;
    private LinkedList<String> keywords;
    private Venue parent;

    public Article(final String id, final String title, final int year, final Venue parent) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.authorList = new LinkedList<>();
        this.literatureIndex = new LiteratureIndex();
        this.keywords = new LinkedList<>();
        this.parent = parent;
    }

    public int getYear() {
        return this.year;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }
}
