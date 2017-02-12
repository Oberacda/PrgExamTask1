package edu.kit.informatik.management.literature;

import edu.kit.informatik.management.literature.interfaces.Entity;

import java.util.*;

/**
 * @author David Oberacker
 */
public class Article implements Entity{
    private final String id;
    private final String title;
    private final int year;
    private List<Author> authorList;
    private LiteratureIndex literatureIndex;
    private TreeSet<String> keywords;

    public Article(final String id,
                   final String title,
                   final int year,
                   final SortedSet<String> keywords) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.authorList = new LinkedList<>();
        this.literatureIndex = new LiteratureIndex();
        this.keywords = new TreeSet<>(keywords);
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

    @Override
    public void addKeyword(final String keyword) {
        this.keywords.add(keyword);
    }

    @Override
    public SortedSet<String> getKeywords() {
        return this.keywords.descendingSet();
    }
}
