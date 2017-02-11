package edu.kit.informatik.management.literature;

import java.util.ArrayList;
import java.util.SortedSet;

/**
 * @author David Oberacker
 */
public abstract class Venue {
    private final String title;

    private ArrayList<String> keywords;

    public Venue(final String title) {
        this.title = title;
        this.keywords = new ArrayList<>();
    }

    public String getTitle() {
        return this.title;
    }

    public abstract SortedSet<Article> getArticles() ;

}
